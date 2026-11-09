package dev.gamelibrary.dto;

import dev.gamelibrary.model.Game;
import java.time.LocalDate;

public class GameDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Long genreId;

    public GameDto() {
    }

    public GameDto(Game game) {
        id = game.getId();
        title = game.getTitle();
        description = game.getDescription();
        releaseDate = game.getReleaseDate();
        genreId = game.getGenre().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
