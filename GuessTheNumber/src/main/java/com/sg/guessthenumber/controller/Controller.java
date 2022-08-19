package com.sg.guessthenumber.controller;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import com.sg.guessthenumber.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // combine both @Controller and @ResponseBody
@RequestMapping("/api") // require the prefix "/api/" on all requests for this Controller
public class Controller {

    @Autowired
    GameService service; // field injection, for the service layer class into the controller

    // this method calls the service layer createNewGame method
    // it returns a 201 CREATED message and the created gameId
    @PostMapping("/begin") // enable the method to accept POST requests for the path "/api/begin"
    @ResponseStatus(HttpStatus.CREATED) // set the HTTP status code to 201 Created for all responses generated from this method
    public ResponseEntity createGame() {

        int createdGameId = service.createNewGame();
        return new ResponseEntity(createdGameId , HttpStatus.CREATED);
    }


    // this method calls the service layer makeGuess method, passing a round object
    // it takes a round object as JSON object
    // It returns a Round object with the results filled in
    @PostMapping("/guess") // enable the method to accept POST requests for the path "/api/guess"
    public Round makeTheGuess(@RequestBody Round round) // model-binding - deserialize from JSON to Round-object
                                                     // the JSON object is passed as JSON through the request body
    {
        return service.makeGuess(round);
    }


    // this method calls the service getAllGames method
    // it returns a list of all games
    @GetMapping("/game") // activate this method for GET requests for the path "/api/game"
    public List<Game> getAllGames() {
        return service.getAllGames();
    }


    // this method takes a game_id, in the URL
    // it calls the service layer getGameById method
    // it returns a specific game based on the passed game_id
    @GetMapping("/game/{game_id}") // activate this method for GET requests for the path "/api/game/{game_id}"
    public Game getGameById(@PathVariable("game_id") int gameId) { // game_id is passed through the url

        return service.getGameById(gameId);
    }

    // This method calls the service layer getAllRoundsByGameId method
    // it takes the game_id referenced in the Round
    // it returns a list of rounds for the specified game, sorted by time
    @GetMapping("/rounds/{game_id}") // activate this method for GET requests for the path "/api/rounds/{game_id}"
    public List<Round> getRoundsUsingGameId(@PathVariable("game_id") int gameId) {
        return service.getAllRoundsByGameId(gameId); // game_id is passed through the url
    }

}
