package com.sg.guessthenumber.service;

import com.sg.guessthenumber.data.GameDao;
import com.sg.guessthenumber.data.RoundDao;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

// this GameService handles both GameDao and RoundDao
@Service
public class GameService {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    // This method creates a new game, generates an answer, and returns the game_id
    // the default game status is false, for each new game
    public int createNewGame() {
        Game game = new Game();
        game.setAnswer(getRandom4DigitNumber()); // set the randomly generated answer for the game
        game = gameDao.addGame(game);

        return game.getGameId();
    }

    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            if (!game.isGameFinished()) { // if game-status is (false) in progress
                game.setAnswer("****");   // hide the answer
            }
        }

        return games;
    }

    public List<Round> getAllRoundsByGameId(int gameId) {

        return roundDao.getAllRoundsByGameId(gameId);
    }
    // this method processes the guess by taking the Round object from the controller.
    // It calls calculateResult method to determine the status of the game (finished or still in progress)
    // it returns the round object
    public Round makeGuess(Round round) {
        // get the answer from the referenced-Game using the reference, fk, game_id in the Round object
        String answer = gameDao.getGameById(round.getGameId()).getAnswer();
        String guess = round.getGuess(); // get the guess from the passed round-object
        String result = calculateResult(guess, answer);
        round.setResult(result);

        if (guess.equals(answer)) { // if the guess is exact match
            Game game = getGameById(round.getGameId());
            game.setGameFinished(true); // set the status of the game to true (finished)
            gameDao.updateGame(game); // update the game
        }

        return roundDao.addRound(round);
    }

    public Game getGameById(int gameId) {
        Game game = gameDao.getGameById(gameId);
        if (!game.isGameFinished()) { // if the game in progress
            game.setAnswer("****"); // hide the answer
        }

        return game;
    }

    public Game addGame(Game game) {

        return gameDao.addGame(game);
    }

    private String getRandom4DigitNumber() {

        Random randomObject = new Random(); // use Random class to generate the random digits

        // get a random digit between 0 and 9
        int firstDigit = randomObject.nextInt(10);

        int secondDigit = randomObject.nextInt(10);

        // discard duplicate digit - and get a new one - no two digits should be the same
        while (secondDigit == firstDigit) {
            secondDigit = randomObject.nextInt(10);
        }

        int thirdDigit = randomObject.nextInt(10);

        // discard the duplicate digit, and get a new one
        while (thirdDigit == secondDigit || thirdDigit == firstDigit) {
            thirdDigit = randomObject.nextInt(10); // get a new one
        }

        int fourthDigit = randomObject.nextInt(10);

        //discard the duplicate digit - and get a new one
        while (fourthDigit == thirdDigit || fourthDigit == secondDigit || fourthDigit == firstDigit) {
            fourthDigit = randomObject.nextInt(10);
        }

        // convert and concatenate the randomly generated int-digits to a string
        String randomAnswer = String.valueOf(firstDigit) + String.valueOf(secondDigit)
                + String.valueOf(thirdDigit) + String.valueOf(fourthDigit);

        return randomAnswer;
    }

    public String calculateResult(String guess, String answer) {
        char[] guessCharacters = guess.toCharArray(); // convert the guess to an array of characters
        char[] answerCharacters = answer.toCharArray();

        int exactMatch = 0;
        int partialMatch = 0;

        for (int i = 0; i < guessCharacters.length; i++) {

            // check the guess digit to see if it exists in the answer already.
            // If the guess digit exits in the answer, its index must be > -1
            // Otherwise, the guess digit is not in the answer
            // So, move to the next digit
            if (answer.indexOf(guessCharacters[i]) > -1) { // if the guess-char is not in the answer,
                                                           // answer.indexOf(guessCharacters[i]) returns -1
                if (guessCharacters[i] == answerCharacters[i]) { // if I have an exactMatch match
                    exactMatch++; // increment exactMatch
                } else { // not an exactMatch match, but the digit is in the answer
                    partialMatch++; // increment partialMatch match, because the guess-digit still in the answer
                }
            }
        }

        String finalResult = "e:" + exactMatch + ":p:" + partialMatch; // concatenate the results

        return finalResult;
    }

}
