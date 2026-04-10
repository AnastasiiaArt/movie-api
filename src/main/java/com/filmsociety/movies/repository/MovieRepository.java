package com.filmsociety.movies.repository;

import com.filmsociety.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenres_Id(Long genreId);

    List<Movie> findByReleaseYear(Integer year);

    List<Movie> findByActors_Id(Long actorId);

    List<Movie> findByTitleContainingIgnoreCase(String title);
}
