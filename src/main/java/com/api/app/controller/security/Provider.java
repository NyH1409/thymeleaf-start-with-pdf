package com.api.app.controller.security;

import com.api.app.model.Employee;
import com.api.app.model.Principal;
import com.api.app.model.Session;
import com.api.app.model.exception.ForbiddenException;
import com.api.app.service.EmployeeService;
import com.api.app.service.SessionService;
import com.api.app.service.handler.SessionHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;

import static com.api.app.service.SessionService.isValid;
import static java.util.UUID.randomUUID;

@Component
public class Provider {
    private final Base64.Decoder decoder = Base64.getDecoder();
    @Autowired
    private ObjectFactory<HttpSession> sessionFactory;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SessionService sessionService;
    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    public void authenticate(Principal principal) {
        Employee auth = employeeService.getEmployeeByEmail(principal.getUsername());
        Principal authPrincipal = auth != null ? auth.getPrincipal() : null;
        if (authPrincipal != null && Arrays.equals(decoder.decode(authPrincipal.getPassword()), principal.getPassword().getBytes())) {
            Session currentSession = sessionService.save(new Session(randomUUID().toString(), authPrincipal, Instant.now(), 60000 * 5));
            sessionFactory.getObject().setAttribute(currentSession.getId(), currentSession.getId());
            sessionHandler.setSessionFactory(sessionFactory);
        } else {
            throw new ForbiddenException("Access denied");
        }
    }

    public void clearSession() {
        Iterator<String> iterator = sessionHandler.getSessionFactory().getAttributeNames().asIterator();
        if (iterator.hasNext()) {
            String sessionId = iterator.next();
            sessionHandler.getSessionFactory().removeAttribute(sessionId);
            sessionService.deleteSession(sessionId);
        }
    }

    public void isAuthenticated() {
        sessionHandler.setSessionFactory(sessionFactory);
        HttpSession currentSession = sessionHandler.getSessionFactory();
        Iterator<String> attributes = currentSession.getAttributeNames().asIterator();
        if (attributes.hasNext()) {
            Session session = sessionService.getSessionById(attributes.next());
            if (session != null) {
                if (isValid(session)) {
                    return;
                } else {
                    sessionService.deleteSession(session.getId());
                }
            }
            throw new ForbiddenException("Access denied");
        }
        throw new ForbiddenException("Access denied");
    }
}
