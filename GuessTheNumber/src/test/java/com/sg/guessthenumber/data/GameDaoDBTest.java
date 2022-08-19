package com.sg.guessthenumber.data;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.models.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    public GameDaoDBTest() {
    }

    @Test // test getAllGames method from the GameDaoDB class
    public void testGetAllGames() {

        // ARRANGE
        Game game1 = new Game();
        game1.setAnswer("4321");
        game1.setGameFinished(false);
        gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setAnswer("8765");
        game2.setGameFinished(false);
        gameDao.addGame(game2);

        // ACT - call the method under test
        List<Game> games = gameDao.getAllGames();
        int expectedSize = 2; // expected size of the arrayList
        int actualSize = games.size(); // the actual returned size

        // ASSERT
        assertEquals(expectedSize, actualSize);
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

    @Test // test getGameById from the GameDaoDB class
    public void testGetGameById() {
        // ARRANGE
        Game expectedGame = new Game();
        expectedGame.setAnswer("5123");
        expectedGame.setGameFinished(false);
        expectedGame = gameDao.addGame(expectedGame);

        // ACT - call the method under test
        Game actualGame = gameDao.getGameById(expectedGame.getGameId());

        // ASSERT
        assertEquals(expectedGame, actualGame);
    }

    @Test // test testUpdateGame method
    public void testUpdateGame() {
        // ARRANGE
        Game expetedGame = new Game();
        expetedGame.setAnswer("1234");
        expetedGame.setGameFinished(false);
        expetedGame = gameDao.addGame(expetedGame);

        Game actualGame = gameDao.getGameById(expetedGame.getGameId());

        assertEquals(expetedGame, actualGame);

        // change the status in the expectedGame
        expetedGame.setGameFinished(true);

        // ACT - update the game - call the method under test
        gameDao.updateGame(expetedGame);


        // ASSERT
        assertNotEquals(expetedGame, actualGame); // they shouldn't be equal

        actualGame = gameDao.getGameById(expetedGame.getGameId());

        assertEquals(expetedGame, actualGame); // now, both should be equal to each other
    }
}

