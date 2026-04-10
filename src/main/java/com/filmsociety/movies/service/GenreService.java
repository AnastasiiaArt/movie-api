package com.filmsociety.movies.service;

import com.filmsociety.movies.entity.Genre;
import com.filmsociety.movies.repository.GenreRepository;
import com.filmsociety.movies.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Genre not found with id " + id));
    }

    public Genre updateGenre(Long id, String name) {
        Genre genre = getGenreById(id);
        if (name != null)
            genre.setName(name);
        return genreRepository.save(genre);
    }

    public void deleteGenre(Long id, boolean force) {
        Genre genre = getGenreById(id);
        if (!force && !genre.getMovies().isEmpty()) {
            throw new BadRequestException(
                    "Cannot delete genre '" + genre.getName() + "' because it has "
                            + genre.getMovies().size() + " associated movies.");
        }
        if (force) {
            genre.getMovies().forEach(movie -> movie.getGenres().remove(genre));
        }
        genreRepository.delete(genre);
    }
}
