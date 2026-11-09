package dev.gamelibrary.dto;

import dev.gamelibrary.model.LibraryEntry;

public class LibraryEntryDto {
    private Long id;
    private Long userId;
    private Long gameId;
    private String status;
    private Integer rating;

    public LibraryEntryDto() {
    }

    public LibraryEntryDto(LibraryEntry entry) {
        id = entry.getId();
        userId = entry.getUser().getId();
        gameId = entry.getGame().getId();
        status = entry.getStatus();
        rating = entry.getRating();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
