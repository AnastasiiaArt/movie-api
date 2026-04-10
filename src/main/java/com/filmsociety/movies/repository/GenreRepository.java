package com.filmsociety.movies.repository;

import com.filmsociety.movies.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
