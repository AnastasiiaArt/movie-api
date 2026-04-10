package com.filmsociety.movies.controller;

import com.filmsociety.movies.entity.Actor;
import com.filmsociety.movies.entity.Genre;
import com.filmsociety.movies.entity.Movie;
import com.filmsociety.movies.exception.ResourceNotFoundException;
import com.filmsociety.movies.repository.ActorRepository;
import com.filmsociety.movies.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ActorRepository actorRepository;

    public MovieController(MovieService movieService, ActorRepository actorRepository) {
        this.movieService = movieService;
        this.actorRepository = actorRepository;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.createMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long actor) {
        if (genre != null)
            return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
        if (year != null)
            return ResponseEntity.ok(movieService.getMoviesByReleaseYear(year));
        if (actor != null)
            return ResponseEntity.ok(movieService.getMoviesByActor(actor));
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchMoviesByTitle(title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    // 🔹 Получение актёров фильма через DTO
    @GetMapping("/{id}/actors")
    public ResponseEntity<List<ActorDTO>> getActorsInMovie(@PathVariable Long id) {
        Set<Actor> actors = movieService.getActorsByMovieId(id);
        List<ActorDTO> actorDTOs = actors.stream()
                .map(actor -> new ActorDTO(actor.getId(), actor.getName(), actor.getBirthDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(actorDTOs);
    }

    // 🔹 PATCH для обновления фильма целиком
    @PatchMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return ResponseEntity.ok(
                movieService.updateMovie(
                        id,
                        movie.getTitle(),
                        movie.getReleaseYear(),
                        movie.getDuration(),
                        movie.getGenres(),
                        movie.getActors()));
    }

    // 🔹 PATCH для обновления списка актёров (только массив ID)
    @PatchMapping("/{id}/actors")
    public ResponseEntity<Movie> updateMovieActors(
            @PathVariable Long id,
            @RequestBody Set<Long> actorIds) {

        Movie movie = movieService.getMovieById(id);

        Set<Actor> actors = actorIds.stream()
                .map(actorId -> actorRepository.findById(actorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Actor not found with id " + actorId)))
                .collect(Collectors.toSet());

        movie.setActors(actors);
        Movie updated = movieService.saveMovie(movie);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 DTO класс для актёров
    public static class ActorDTO {
        private Long id;
        private String name;
        private java.time.LocalDate birthDate;

        public ActorDTO(Long id, String name, java.time.LocalDate birthDate) {
            this.id = id;
            this.name = name;
            this.birthDate = birthDate;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public java.time.LocalDate getBirthDate() {
            return birthDate;
        }
    }
}
