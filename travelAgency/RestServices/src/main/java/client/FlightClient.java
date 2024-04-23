package app.rest.client;

import app.model.Game;
import app.services.rest.ServiceException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class GamesClient {
    public static final String URL = "http://localhost:8080/app/games";
    private final RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable){
        try {
            return callable.call();
        } catch (Exception e) { // server down, resource exception
            throw new ServiceException(e);
        }
    }

    public Game[] findAll(){
        return execute(() -> restTemplate.getForObject(URL, Game[].class));
    }

    public Game getById(Long id){
        return execute(() ->restTemplate.getForObject(String.format("%s/%s", URL, id.toString()), Game.class));
    }

    public Game create(Game game) {
        return execute(() -> restTemplate.postForObject(URL, game, Game.class));
    }

    public void update(Game game) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, game.getId()), game);
            return null;
        });
    }

    public void delete(Long id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id.toString()));
            return null;
        });
    }
}