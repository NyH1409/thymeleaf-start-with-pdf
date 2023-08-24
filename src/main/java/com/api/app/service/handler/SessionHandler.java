package com.api.app.service.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.ObjectFactory;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Setter
public class SessionHandler {
  private static SessionHandler SESSION_HANDLER;
  @Getter(AccessLevel.NONE)
  private ObjectFactory<HttpSession> sessionFactory;

  private SessionHandler() {
  }

  public static SessionHandler getInstance() {
    if (SESSION_HANDLER == null) {
      return new SessionHandler();
    }
    return SESSION_HANDLER;
  }

  public HttpSession getSessionFactory() {
    return sessionFactory.getObject();
  }

  @Override
  public String toString() {
    return "SessionHandler{" +
      "sessionFactory=" + sessionFactory +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SessionHandler that = (SessionHandler) o;
    return Objects.equals(sessionFactory, that.sessionFactory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sessionFactory);
  }
}
