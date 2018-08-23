package edu.si.trellis.cassandra;

import com.google.common.base.Throwables;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AllExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        String body = e.getMessage() + "\n" + Throwables.getStackTraceAsString(e);
        return Response.status(500).entity(body).build();
    }

}