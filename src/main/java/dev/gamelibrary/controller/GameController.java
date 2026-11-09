package dev.gamelibrary.controller;

import dev.gamelibrary.dto.GameDto;
import dev.gamelibrary.service.GameService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameController {
    @Inject
    GameService gameService;

    @GET
    public List<GameDto> getAll() {
        return gameService.getAll();
    }

    @GET
    @Path("/{id}")
    public GameDto getById(@PathParam("id") Long id) {
        return gameService.getDtoById(id);
    }

    @POST
    public Response create(GameDto game) {
        return Response.status(201).entity(gameService.create(game)).build();
    }

    @PUT
    @Path("/{id}")
    public GameDto update(@PathParam("id") Long id, GameDto game) {
        return gameService.update(id, game);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        gameService.delete(id);
        return Response.noContent().build();
    }
}
