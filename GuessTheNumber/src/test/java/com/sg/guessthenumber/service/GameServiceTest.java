package com.sg.guessthenumber.service;

import com.sg.guessthenumber.TestApplicationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameServiceTest {

    @Autowired
    GameService service;

    public GameServiceTest() {
    }

    @Test // test calculateResult method in the GameService class
    public void testCalculateResult() { //
        //ARRANGE
        String guess = "4567";
        String answer = "7268";

        // ACT
        String actual = service.calculateResult(guess, answer);

        String expected = "e:1:p:1";
        // ASSERT
        assertEquals(expected, actual);
    }

    @Test // the second test of the calculateResult method in the GameService class
    public void testCalculateResult2() {
        //ARRANGE
        String guess = "1234";
        String answer = "1243";

        // ACT
        String actual = service.calculateResult(guess, answer);

        String expected = "e:2:p:2";
        // ASSERT
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateResult3() {
        //ARRANGE
        String guess = "1357";
        String answer = "1357";

        // ACT
        String actual = service.calculateResult(guess, answer);

        String expected = "e:4:p:0";
        // ASSERT
        assertEquals(expected, actual);
    }

}

