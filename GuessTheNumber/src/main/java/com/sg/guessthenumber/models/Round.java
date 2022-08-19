package com.sg.guessthenumber.models;

import java.time.LocalDateTime;
import java.util.Objects;

// POJO class for the Round entity
public class Round {

    private int roundId;
    private int gameId;
    private LocalDateTime guessTime;
    private String guess;
    private String result;

    public Round() {
    }

    public Round(int gameId, String guess) {
        this.gameId = gameId;
        this.guess = guess;
    }

    public Round(int roundId, int gameId, LocalDateTime guessTime, String guess, String result) {
        this.roundId = roundId;
        this.gameId = gameId;
        this.guessTime = guessTime;
        this.guess = guess;
        this.result = result;
    }

    public int getRoundId() {
        return roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public String getGuess() {
        return guess;
    }

    public String getResult() {
        return result;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return roundId == round.roundId && gameId == round.gameId && Objects.equals(guessTime, round.guessTime) && Objects.equals(guess, round.guess) && Objects.equals(result, round.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, gameId, guessTime, guess, result);
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundId=" + roundId +
                ", gameId=" + gameId +
                ", guessTime=" + guessTime +
                ", guess='" + guess + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
