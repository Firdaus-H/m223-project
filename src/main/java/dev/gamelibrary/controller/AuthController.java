package dev.gamelibrary.controller;

import dev.gamelibrary.dto.AuthRequest;
import dev.gamelibrary.dto.UserResponse;
import dev.gamelibrary.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(AuthRequest request) {
        return Response.status(201).entity(authService.register(request)).build();
    }

    @POST
    @Path("/login")
    public UserResponse login(AuthRequest request) {
        return authService.login(request);
    }
}
