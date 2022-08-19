package com.sg.guessthenumber.data;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoDBTest {

    @Autowired
    RoundDao roundDao;
    @Autowired
    GameDao gameDao;

    public RoundDaoDBTest() {
    }

    @Test // test getAllRoundsByGameId and addRound methods from the RoundDaoDB class
    public void testGetAllRoundsByGameId() {
        // ARRANGE
        Game testGame = new Game();
        gameDao.addGame(testGame);

        Round round1 = new Round();
        round1.setGameId(testGame.getGameId());
        round1.setGuess("1234");
        roundDao.addRound(round1);

        Round round2 = new Round();
        round2.setGameId(1);
        round2.setGuess("5678");
        roundDao.addRound(round2);

        // ACT - call the method under test
        List<Round> listOfActualRounds = roundDao.getAllRoundsByGameId(round1.getGameId());
        int expectedSize = 2; // expected size of the arrayList
        int actualSize = listOfActualRounds.size(); // the actual returned size

        // ASSERT
        assertEquals(expectedSize, actualSize);

//        assertTrue(listOfActualRounds.contains(round1));
//        assertTrue(listOfActualRounds.contains(round2));
    }

    @Test // test getRoundById from the RoundDaoDB class
    public void testGetRoundById() {
        // ARRANGE

        Game testGame = new Game();
        gameDao.addGame(testGame);

        Round expectedRound = new Round();
        expectedRound.setGameId(testGame.getGameId());
        expectedRound.setGuess("1234");
        expectedRound = roundDao.addRound(expectedRound);

        // ACT - call the method under test
        Round actualRound = roundDao.getRoundById(expectedRound.getRoundId());

        // ASSERT
        assertEquals(expectedRound, actualRound);
    }

}
