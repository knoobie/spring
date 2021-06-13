package com.vaadin.flow.spring.test;

import javax.servlet.Servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({Servlet.class, Server.class, Loader.class,
                     WebAppContext.class})
public class EmbeddedJettyConfiguration {

    @Bean
    JettyServerCustomizer stopTimeoutCustomizer() {
        return (server) -> server.setStopTimeout(1);
    }
}
