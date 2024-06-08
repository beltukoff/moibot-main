package baranow.laba2.telebot.service;

import baranow.laba2.telebot.model.Joke;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

//Интерфейс для сервиса
public interface JokeService {

    void addJoke(Joke joke);

    Page<Joke> getAllJokes(Pageable pageable);

    Optional<Joke> getJokeById(Long id,Long userId);

    Optional<Joke> putJokeById(Long id, Joke updatedJoke);

    void deleteJokeById(Long id);

    public List<Joke> getTopFiveJokes();


}
