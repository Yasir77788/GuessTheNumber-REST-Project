package com.sg.guessthenumber.models;

import java.util.Objects;

// POJO class for the Game entity
public class Game {
    private int gameId;
    private String answer;
    private boolean gameFinished; // if gameStatus is true, it means the game is finished
                                // otherwise, it means the game is in progress


    public Game() {
    }

    public Game(String answer, boolean gameFinished) {
        this.answer = answer;
        this.gameFinished = gameFinished;
    }

    public Game(int gameId, String answer, boolean gameFinished) {
        this.gameId = gameId;
        this.answer = answer;
        this.gameFinished = gameFinished;
    }

    public int getGameId() {
        return gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId && gameFinished == game.gameFinished && Objects.equals(answer, game.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, answer, gameFinished);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", answer='" + answer + '\'' +
                ", gameFinished=" + gameFinished +
                '}';
    }
}
