package com.talentpath.tictactoe.daos;

import com.talentpath.tictactoe.models.TicGame;
import com.talentpath.tictactoe.models.TicMove;

import java.util.List;

//interface that requires the implementation of each function based on if they are pulling
//from the in mem dao or the actual postgres db
public interface TicDao {

    //begin a game
    void reset();

    //return all games
    List<TicGame> getAllGames();

    //return a single game based on id
    TicGame getGameById(int gameId);

    //retreives list of all of the past moves in a TicMove object
    List<TicMove> getPastMoves(Integer gameId);

}
