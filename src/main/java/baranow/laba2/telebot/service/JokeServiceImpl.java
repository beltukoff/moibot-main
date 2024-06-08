package baranow.laba2.telebot.service;

import baranow.laba2.telebot.model.CallJoke;
import baranow.laba2.telebot.model.Joke;
import baranow.laba2.telebot.repository.JokeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
@Service
public class JokeServiceImpl implements JokeService {

    private final JokeRepository jokeRepository;

    @Override
    public void addJoke(Joke joke) {
            jokeRepository.save(joke);
    }

    @Override
    public Page<Joke> getAllJokes(Pageable pageable) {
        return jokeRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public Optional<Joke> getJokeById(Long id, Long userId) {
        Optional<Joke> jokeOptional = jokeRepository.findById(id);
        if (jokeOptional.isPresent()) {
            Joke joke = jokeOptional.get();
            joke.getCallJokes().size();
            CallJoke callJoke = new CallJoke();
            callJoke.setJoke(joke);
            callJoke.setTimeCall(new Date());
            callJoke.setIdUserCall(userId); // Устанавливаем id пользователя
            joke.getCallJokes().add(callJoke);
            joke.setCountCall(joke.getCountCall() + 1);
            jokeRepository.saveAndFlush(joke);
            return jokeOptional;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Joke> putJokeById(Long id, Joke updatedJoke) {
        Optional<Joke> existingJoke = jokeRepository.findById(id);
        if (existingJoke.isPresent()) {
            Joke jokeToUpdate = existingJoke.get();
            jokeToUpdate.setTextJoke(updatedJoke.getTextJoke());
            jokeRepository.save(jokeToUpdate);
        }
        return existingJoke;
    }

    @Override
    public void deleteJokeById(Long id){

        jokeRepository.deleteById(id);
    }

    // В реализации JokeServiceImpl
    @Override
    public List<Joke> getTopFiveJokes() {
        return jokeRepository.findTop5ByOrderByCountCallDesc();
    }
}