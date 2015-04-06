package com.cmpe281.team2.miaas.core.jersey;


import java.util.logging.Logger;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfiguration extends ResourceConfig {
	
	private static boolean isLogEntity;
    private static Logger logger =  Logger.getLogger("JerseyConfiguration");

    public JerseyConfiguration() {
        packages("com.cmpe281.team2");
        register(new LoggingFilter(logger, isLogEntity));
        register(RequestContextFilter.class);
    }
    
}
