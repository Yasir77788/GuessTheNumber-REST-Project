package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoDB implements GameDao{

    @Autowired
    JdbcTemplate jdbc; // field injection, the jdbc template

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";

        // use GameMapper, inner class, to map a DB-row into a game-object.
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE game_id = ?";

            // use GameMapper, inner class, to map a DB-row into a game-object.
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional // do all queries, in this method, successfully, or do nothing
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer) VALUES (?)";
        jdbc.update(INSERT_GAME, game.getAnswer());

        //  use MySql LAST_INSERT_ID() function to get the last inserted-id (incremented)
        // it returns an auto-incremented id of the last row that has been inserted or updated
        int newGameId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newGameId);
        return game;
    }

    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET game_finished = ? WHERE game_id = ?";
        jdbc.update(UPDATE_GAME, game.isGameFinished(), game.getGameId());
    }

    // GameMapper, is an inner class, that implements a RowMapper interface.
    // Use RowMapper to turn a row of the ResultSet into an object of type Game.
    // mapRow is an overridden method that pulls a row from the DB into a game object
    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("game_id"));
            game.setAnswer(rs.getString("answer"));
            game.setGameFinished(rs.getBoolean("game_finished"));
            return game;
        }
    }

}
