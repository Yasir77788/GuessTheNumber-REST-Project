package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class RoundDaoDB implements RoundDao{

    @Autowired
    JdbcTemplate jdbc; // field injection, the jdbc template

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        try {
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round "
                    + "WHERE game_id = ? ORDER BY guess_time";

            // use RoundMapper, inner class, to map a DB-row into a round-object.
            List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
            return rounds;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE round_id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional // do all queries, in this method, successfully, or do nothing
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(game_id, guess, result) VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND, round.getGameId(), round.getGuess(), round.getResult());

        // use MySql LAST_INSERT_ID() function to find the last-inserted-id (incremented)
        // it returns an auto-incremented id of the last row that has been inserted or updated
        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return getRoundById(newRoundId);
    }

    // GameMapper, is an inner class, that implements a RowMapper interface.
    // Use RowMapper to turn a row of the ResultSet into an object of type Round.
    // mapRow is an overridden method that pulls a row from the DB into a round object
    public static final class RoundMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round(); // object ot intercept the DB row
            round.setRoundId(rs.getInt("round_id"));
            round.setGameId(rs.getInt("game_id"));
            round.setGuess(rs.getString("guess"));

            Timestamp timestamp = rs.getTimestamp("guess_time");
            round.setGuessTime(timestamp.toLocalDateTime()); // covert back from UTC to the current time zone

            round.setResult(rs.getString("result"));
            return round;
        }
    }

}
