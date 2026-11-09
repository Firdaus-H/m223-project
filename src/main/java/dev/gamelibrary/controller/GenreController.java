package dev.gamelibrary.controller;

import dev.gamelibrary.model.Genre;
import dev.gamelibrary.service.GenreService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/genres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreController {
    @Inject
    GenreService genreService;

    @GET
    public List<Genre> getAll() {
        return genreService.getAll();
    }

    @POST
    public Response create(Genre genre) {
        return Response.status(201).entity(genreService.create(genre)).build();
    }

    @PUT
    @Path("/{id}")
    public Genre update(@PathParam("id") Long id, Genre genre) {
        return genreService.update(id, genre);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        genreService.delete(id);
        return Response.noContent().build();
    }
}
