package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;

import java.util.List;

public interface GameDao {
    // abstract methods to be implemented
    List<Game> getAllGames();

    Game addGame(Game game);

    Game getGameById(int gameId);

    void updateGame(Game round);
}
