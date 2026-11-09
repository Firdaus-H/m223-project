package dev.gamelibrary.service;

import dev.gamelibrary.model.*;
import dev.gamelibrary.repository.*;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.time.LocalDate;

@ApplicationScoped
public class ExampleDataService {
    @Inject
    ApplicationUserRepository userRepository;
    @Inject
    GenreRepository genreRepository;
    @Inject
    GameRepository gameRepository;
    @Inject
    LibraryEntryRepository libraryRepository;

    @ConfigProperty(name = "gamelibrary.example-data.enabled", defaultValue = "false")
    boolean enabled;

    void onStart(@Observes StartupEvent event) {
        if (enabled)
            createExampleData();
    }

    @Transactional
    public void createExampleData() {
        if (userRepository.count() > 0 || genreRepository.count() > 0
                || gameRepository.count() > 0 || libraryRepository.count() > 0)
            return;

        ApplicationUser user = new ApplicationUser();
        user.setUsername("demo");
        user.setPasswordHash(BcryptUtil.bcryptHash("demo"));
        userRepository.persist(user);

        Genre sandbox = new Genre();
        sandbox.setName("Sandbox");
        genreRepository.persist(sandbox);
        Genre rpg = new Genre();
        rpg.setName("RPG");
        genreRepository.persist(rpg);

        Game minecraft = new Game();
        minecraft.setTitle("Minecraft");
        minecraft.setDescription("Sandbox game");
        minecraft.setReleaseDate(LocalDate.of(2011, 11, 18));
        minecraft.setGenre(sandbox);
        gameRepository.persist(minecraft);

        Game example = new Game();
        example.setTitle("Example RPG");
        example.setDescription("Role-playing game");
        example.setReleaseDate(LocalDate.of(2024, 1, 1));
        example.setGenre(rpg);
        gameRepository.persist(example);

        LibraryEntry entry = new LibraryEntry();
        entry.setUser(user);
        entry.setGame(minecraft);
        entry.setStatus("PLAYING");
        entry.setRating(8);
        libraryRepository.persist(entry);
    }
}
