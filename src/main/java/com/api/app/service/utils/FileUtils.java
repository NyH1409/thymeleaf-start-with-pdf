package com.api.app.service.utils;

import com.api.app.model.exception.ApiException;
import com.lowagie.text.DocumentException;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.thymeleaf.templatemode.TemplateMode.HTML;
@Component
public class FileUtils<T, K> {
    public static TemplateEngine configureTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    public Context contextConf(T employee, K company) {
        Context context = new Context();
        context.setVariable("employee", employee);
        context.setVariable("company", company);
        return context;
    }

    public T generateFile(T employee, K company, String template, HttpServletResponse response) {
        Context context = contextConf(employee, company);

        String renderedHtmlContent = configureTemplateEngine().process(template, context);

        ITextRenderer renderer = new ITextRenderer();

        renderer.setDocumentFromString(renderedHtmlContent);
        renderer.layout();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=employee.pdf";
            response.setHeader(headerKey, headerValue);
            var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (DocumentException | IOException e) {
            throw new ApiException(e.getMessage());
        }
        return employee;
    }
}
