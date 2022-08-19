package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Round;

import java.util.List;

public interface RoundDao {

    List<Round> getAllRoundsByGameId(int gameId);
    Round addRound(Round round);
    Round getRoundById(int roundId);

}
