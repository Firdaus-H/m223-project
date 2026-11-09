package dev.gamelibrary.repository;

import dev.gamelibrary.model.LibraryEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LibraryEntryRepository implements PanacheRepository<LibraryEntry> {
}
