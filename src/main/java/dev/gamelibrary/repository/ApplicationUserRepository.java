package dev.gamelibrary.repository;

import dev.gamelibrary.model.ApplicationUser;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationUserRepository implements PanacheRepository<ApplicationUser> {
}
