package dev.gamelibrary.service;

import dev.gamelibrary.dto.GameDto;
import dev.gamelibrary.model.Game;
import dev.gamelibrary.repository.GameRepository;
import dev.gamelibrary.repository.LibraryEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GameService {
    @Inject
    GameRepository gameRepository;
    @Inject
    LibraryEntryRepository libraryRepository;
    @Inject
    GenreService genreService;

    public List<GameDto> getAll() {
        List<GameDto> result = new ArrayList<>();
        for (Game game : gameRepository.listAll())
            result.add(new GameDto(game));
        return result;
    }

    public Game getById(Long id) {
        Game game = id == null ? null : gameRepository.findById(id);
        if (game == null)
            throw new NotFoundException("Game not found");
        return game;
    }

    public GameDto getDtoById(Long id) {
        return new GameDto(getById(id));
    }

    private void apply(Game game, GameDto dto) {
        if (dto == null || dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new BadRequestException("Game title is required");
        }
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setReleaseDate(dto.getReleaseDate());
        game.setGenre(genreService.getById(dto.getGenreId()));
    }

    @Transactional
    public GameDto create(GameDto dto) {
        Game game = new Game();
        apply(game, dto);
        gameRepository.persist(game);
        return new GameDto(game);
    }

    @Transactional
    public GameDto update(Long id, GameDto dto) {
        Game game = getById(id);
        apply(game, dto);
        return new GameDto(game);
    }

    @Transactional
    public void delete(Long id) {
        getById(id);
        if (libraryRepository.count("game.id", id) > 0) {
            throw new BadRequestException("Remove the game from libraries first");
        }
        gameRepository.deleteById(id);
    }
}
