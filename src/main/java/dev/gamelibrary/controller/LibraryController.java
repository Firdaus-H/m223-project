package dev.gamelibrary.controller;

import dev.gamelibrary.dto.LibraryEntryDto;
import dev.gamelibrary.service.LibraryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/library")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibraryController {
    @Inject
    LibraryService libraryService;

    // Temporary userId parameter until real authentication is implemented.
    @GET
    public List<LibraryEntryDto> getByUser(@QueryParam("userId") Long userId) {
        return libraryService.getByUser(userId);
    }

    @POST
    public Response create(LibraryEntryDto entry) {
        return Response.status(201).entity(libraryService.create(entry)).build();
    }

    @PUT
    @Path("/{id}")
    public LibraryEntryDto update(@PathParam("id") Long id, LibraryEntryDto entry) {
        return libraryService.update(id, entry);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        libraryService.delete(id);
        return Response.noContent().build();
    }
}
