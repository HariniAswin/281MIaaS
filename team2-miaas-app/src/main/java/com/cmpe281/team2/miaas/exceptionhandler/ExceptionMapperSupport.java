package com.cmpe281.team2.miaas.exceptionhandler;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.cmpe281.team2.miaas.restws.model.GenericResponse;

@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {

    private final static Logger logger = Logger.getLogger(ExceptionMapperSupport.class);

    @Override
    public Response toResponse(Exception e) {
    	
    	logger.error(e);
    	e.printStackTrace();
    	logger.info("Inside Exception Mapper");
        GenericResponse errResponse = new GenericResponse();
        errResponse.setHasErrors(true);
        errResponse.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        errResponse.setErrorMessage(e.getMessage());
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errResponse).type(MediaType.APPLICATION_JSON).build();
    }
}
