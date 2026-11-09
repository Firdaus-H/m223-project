package dev.gamelibrary.service;

import dev.gamelibrary.dto.LibraryEntryDto;
import dev.gamelibrary.model.ApplicationUser;
import dev.gamelibrary.model.LibraryEntry;
import dev.gamelibrary.repository.ApplicationUserRepository;
import dev.gamelibrary.repository.LibraryEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LibraryService {
    @Inject
    LibraryEntryRepository libraryRepository;
    @Inject
    ApplicationUserRepository userRepository;
    @Inject
    GameService gameService;

    public List<LibraryEntryDto> getByUser(Long userId) {
        if (userId == null || userRepository.findById(userId) == null) {
            throw new NotFoundException("User not found");
        }
        List<LibraryEntryDto> result = new ArrayList<>();
        for (LibraryEntry entry : libraryRepository.list("user.id", userId)) {
            result.add(new LibraryEntryDto(entry));
        }
        return result;
    }

    private LibraryEntry getById(Long id) {
        LibraryEntry entry = libraryRepository.findById(id);
        if (entry == null)
            throw new NotFoundException("Library entry not found");
        return entry;
    }

    private void check(LibraryEntryDto dto) {
        if (dto == null || dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new BadRequestException("Status is required");
        }
        if (dto.getRating() != null && (dto.getRating() < 1 || dto.getRating() > 10)) {
            throw new BadRequestException("Rating must be between 1 and 10");
        }
    }

    @Transactional
    public LibraryEntryDto create(LibraryEntryDto dto) {
        check(dto);
        ApplicationUser user = dto.getUserId() == null ? null : userRepository.findById(dto.getUserId());
        if (user == null)
            throw new NotFoundException("User not found");
        if (dto.getGameId() == null)
            throw new NotFoundException("Game not found");
        if (libraryRepository.count("user.id = ?1 and game.id = ?2", user.getId(), dto.getGameId()) > 0) {
            throw new BadRequestException("Game is already in this user's library");
        }
        LibraryEntry entry = new LibraryEntry();
        entry.setUser(user);
        entry.setGame(gameService.getById(dto.getGameId()));
        entry.setStatus(dto.getStatus());
        entry.setRating(dto.getRating());
        libraryRepository.persist(entry);
        return new LibraryEntryDto(entry);
    }

    @Transactional
    public LibraryEntryDto update(Long id, LibraryEntryDto dto) {
        check(dto);
        LibraryEntry entry = getById(id);
        entry.setStatus(dto.getStatus());
        entry.setRating(dto.getRating());
        return new LibraryEntryDto(entry);
    }

    @Transactional
    public void delete(Long id) {
        getById(id);
        libraryRepository.deleteById(id);
    }
}
