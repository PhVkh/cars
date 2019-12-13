package ru.lanit.phvkh.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.lanit.phvkh.config.SpringConfig;

// потом попробовать https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
