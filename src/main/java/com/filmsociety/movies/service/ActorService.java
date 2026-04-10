package com.filmsociety.movies.service;

import com.filmsociety.movies.entity.Actor;
import com.filmsociety.movies.repository.ActorRepository;
import com.filmsociety.movies.exception.BadRequestException;
import com.filmsociety.movies.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(Long id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found with id " + id));
    }

    public List<Actor> getActorsByName(String name) {
        return actorRepository.findByNameContainingIgnoreCase(name);
    }

    public Actor updateActor(Long id, String name, LocalDate birthDate) {
        Actor actor = getActorById(id);
        if (name != null)
            actor.setName(name);
        if (birthDate != null)
            actor.setBirthDate(birthDate);
        return actorRepository.save(actor);
    }

    public void deleteActor(Long id, boolean force) {
        Actor actor = getActorById(id);
        if (!force && !actor.getMovies().isEmpty()) {
            throw new BadRequestException(
                    "Cannot delete actor '" + actor.getName() + "' because they are associated with "
                            + actor.getMovies().size() + " movies.");
        }
        if (force) {
            actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
        }
        actorRepository.delete(actor);
    }
}
