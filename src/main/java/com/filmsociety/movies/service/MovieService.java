package com.filmsociety.movies.service;

import com.filmsociety.movies.entity.Actor;
import com.filmsociety.movies.entity.Genre;
import com.filmsociety.movies.entity.Movie;
import com.filmsociety.movies.repository.ActorRepository;
import com.filmsociety.movies.repository.MovieRepository;
import com.filmsociety.movies.exception.BadRequestException;
import com.filmsociety.movies.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieService(MovieRepository movieRepository,
            ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    public Movie createMovie(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            throw new BadRequestException("Movie title cannot be null or empty.");
        }
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));
    }

    public List<Movie> getMoviesByGenre(Long genreId) {
        return movieRepository.findByGenres_Id(genreId);
    }

    public List<Movie> getMoviesByReleaseYear(Integer year) {
        return movieRepository.findByReleaseYear(year);
    }

    public List<Movie> getMoviesByActor(Long actorId) {
        return movieRepository.findByActors_Id(actorId);
    }

    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public Movie updateMovie(Long id,
            String title,
            Integer releaseYear,
            Integer duration,
            Set<Genre> genres,
            Set<Actor> actors) {

        Movie movie = getMovieById(id);

        if (title != null)
            movie.setTitle(title);
        if (releaseYear != null)
            movie.setReleaseYear(releaseYear);
        if (duration != null)
            movie.setDuration(duration);
        if (genres != null)
            movie.setGenres(genres);
        if (actors != null)
            movie.setActors(actors);

        return movieRepository.save(movie);
    }

    // 🔹 ВОТ ОН — НУЖНЫЙ МЕТОД ДЛЯ PATCH /movies/{id}/actors
    public Movie updateMovieActors(Long movieId, Set<Long> actorIds) {

        Movie movie = getMovieById(movieId);

        Set<Actor> actors = new HashSet<>(
                actorRepository.findAllById(actorIds));

        if (actors.size() != actorIds.size()) {
            throw new ResourceNotFoundException("One or more actors not found");
        }

        movie.setActors(actors);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = getMovieById(id);

        if (!movie.getGenres().isEmpty() || !movie.getActors().isEmpty()) {
            throw new BadRequestException(
                    "Cannot delete movie '" + movie.getTitle() + "' because it is associated with "
                            + movie.getGenres().size() + " genres and "
                            + movie.getActors().size() + " actors.");
        }

        movieRepository.delete(movie);
    }

    public Set<Actor> getActorsByMovieId(Long movieId) {
        Movie movie = getMovieById(movieId);
        return movie.getActors();
    }

    // 🔹 Оставляем — он тебе может ещё понадобиться
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
