package baranow.laba2.telebot.controller;
import baranow.laba2.telebot.model.Joke;
import baranow.laba2.telebot.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@RestController
@RequiredArgsConstructor
@RequestMapping("/jokes")
public class JokeController {
    private final JokeService jokeService;


    @PostMapping
    ResponseEntity<Void> addJoke(@RequestBody Joke joke){
        jokeService.addJoke(joke);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}")
    ResponseEntity<Joke> getJokeById(@PathVariable Long id, @RequestParam(name = "userId", required = false) Long userId) {
        Optional<Joke> jokeOptional = jokeService.getJokeById(id, userId);
        return jokeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    ResponseEntity<Page<Joke>> getAllJokes(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Joke> jokesPage = jokeService.getAllJokes(pageable);
        return ResponseEntity.ok(jokesPage);
    }

    @PutMapping("/{id}")
    ResponseEntity<Joke> updateJokeById(@PathVariable Long id, @RequestBody Joke updatedJoke) {
        Optional<Joke> updatedJokeOptional = jokeService.putJokeById(id, updatedJoke);
        return updatedJokeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteJokeById(@PathVariable Long id){
        jokeService.deleteJokeById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top5")
    ResponseEntity<List<Joke>> getTopFiveJokes() {
        return ResponseEntity.ok(jokeService.getTopFiveJokes());
    }



}
