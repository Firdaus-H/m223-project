package dev.gamelibrary.service;

import dev.gamelibrary.model.Genre;
import dev.gamelibrary.repository.GameRepository;
import dev.gamelibrary.repository.GenreRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class GenreService {
    @Inject
    GenreRepository genreRepository;
    @Inject
    GameRepository gameRepository;

    public List<Genre> getAll() {
        return genreRepository.listAll();
    }

    public Genre getById(Long id) {
        Genre genre = id == null ? null : genreRepository.findById(id);
        if (genre == null)
            throw new NotFoundException("Genre not found");
        return genre;
    }

    @Transactional
    public Genre create(Genre genre) {
        if (genre == null || genre.getName() == null || genre.getName().isBlank()) {
            throw new BadRequestException("Genre name is required");
        }
        genre.setId(null);
        genreRepository.persist(genre);
        return genre;
    }

    @Transactional
    public Genre update(Long id, Genre updated) {
        if (updated == null || updated.getName() == null || updated.getName().isBlank()) {
            throw new BadRequestException("Genre name is required");
        }
        Genre genre = getById(id);
        genre.setName(updated.getName());
        return genre;
    }

    @Transactional
    public void delete(Long id) {
        getById(id);
        if (gameRepository.count("genre.id", id) > 0) {
            throw new BadRequestException("Delete games in this genre first");
        }
        genreRepository.deleteById(id);
    }
}
