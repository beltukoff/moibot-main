package baranow.laba2.telebot.service;

import baranow.laba2.telebot.model.Joke;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;
import java.util.Random;
@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    private final JokeService jokeService;

    @Autowired
    public TelegramBotService(TelegramBot telegramBot, JokeService jokeService) {
        this.telegramBot = telegramBot;
        this.jokeService = jokeService;
        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }

    private void processUpdate(Update update) {
        if (update.message() != null && update.message().text() != null && update.message().text().equals("/start")) {
            sendStartMessage(update.message().chat().id());
        }else if(update.message() != null && update.message().text() != null && update.message().text().equals("/kek")) {
                sendMessage(update.message().chat().id());

        }else if (update.callbackQuery() != null) {
            handleCallbackQuery(update.callbackQuery());
        }
    }
    //изменение 1
    private void sendStartMessage(Long chatId) {
        SendMessage request = new SendMessage(chatId, "Привет! Я могу");
        request.replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton("Ну моги").callbackData("/joke")));
        this.telegramBot.execute(request);
    }

    private void sendMessage(Long chatId) {
        SendMessage request = new SendMessage(chatId, "rauaghkhjsr");
        this.telegramBot.execute(request);
    }


    private void handleCallbackQuery(com.pengrad.telegrambot.model.CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        if ("/joke".equals(data)) {
            Long chatId = callbackQuery.message().chat().id();
            Long userId = callbackQuery.from().id(); // Получаем идентификатор пользователя
            sendRandomJoke(chatId, userId); // Передаем идентификатор пользователя
            telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id()));
        }
    }


    public Optional<Joke> getRandomJoke(Long userId) {
        Page<Joke> jokePage = jokeService.getAllJokes(Pageable.unpaged()); // Получаем все анекдоты без пагинации
        if (jokePage.hasContent()) {
            int randomIndex = new Random().nextInt(jokePage.getContent().size());
            Joke randomJoke = jokePage.getContent().get(randomIndex); // Получаем случайный анекдот
            return jokeService.getJokeById(randomJoke.getIdJoke(), userId);
        } else {
            return Optional.empty();
        }
    }

    private void sendRandomJoke(Long chatId, Long userId) {
        Optional<Joke> randomJokeOptional = getRandomJoke(userId);
        if (randomJokeOptional.isPresent()) {
            Joke randomJoke = randomJokeOptional.get();
            SendMessage request = new SendMessage(chatId, randomJoke.getTextJoke());
            this.telegramBot.execute(request);
        } else {
            SendMessage request = new SendMessage(chatId, "Не удалось получить случайную шутку.");
            this.telegramBot.execute(request);
        }
    }


}