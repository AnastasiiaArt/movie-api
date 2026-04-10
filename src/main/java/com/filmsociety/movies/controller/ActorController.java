package com.filmsociety.movies.controller;

import com.filmsociety.movies.entity.Actor;
import com.filmsociety.movies.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        return new ResponseEntity<>(actorService.createActor(actor), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Actor>> getActors(@RequestParam(required = false) String name) {
        if (name != null) {
            return ResponseEntity.ok(actorService.getActorsByName(name));
        }
        return ResponseEntity.ok(actorService.getAllActors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.getActorById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        LocalDate birthDate = actor.getBirthDate();
        return ResponseEntity.ok(actorService.updateActor(id, actor.getName(), birthDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean force) {
        actorService.deleteActor(id, force);
        return ResponseEntity.noContent().build();
    }
}
