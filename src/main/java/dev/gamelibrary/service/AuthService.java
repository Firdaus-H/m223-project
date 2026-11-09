package dev.gamelibrary.service;

import dev.gamelibrary.dto.AuthRequest;
import dev.gamelibrary.dto.UserResponse;
import dev.gamelibrary.model.ApplicationUser;
import dev.gamelibrary.repository.ApplicationUserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AuthService {
    @Inject
    ApplicationUserRepository userRepository;

    @Transactional
    public UserResponse register(AuthRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null
                || request.getUsername().isBlank() || request.getPassword().isBlank()) {
            throw new BadRequestException("Username and password are required");
        }
        if (userRepository.find("username", request.getUsername()).firstResult() != null) {
            throw new BadRequestException("Username already exists");
        }
        ApplicationUser user = new ApplicationUser();
        user.setUsername(request.getUsername());
        user.setPasswordHash(BcryptUtil.bcryptHash(request.getPassword()));
        userRepository.persist(user);
        return new UserResponse(user.getId(), user.getUsername());
    }

    public UserResponse login(AuthRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            throw new NotAuthorizedException("Invalid credentials");
        }
        ApplicationUser user = userRepository.find("username", request.getUsername()).firstResult();
        if (user == null || !BcryptUtil.matches(request.getPassword(), user.getPasswordHash())) {
            throw new NotAuthorizedException("Invalid credentials");
        }
        // Demo login only: JWT authentication to be added later.
        return new UserResponse(user.getId(), user.getUsername());
    }
}
