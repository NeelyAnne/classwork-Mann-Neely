package com.talentpath.tictactoe.daos;

import com.talentpath.tictactoe.exceptions.InvalidIdException;
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
    TicGame getGameById(int gameId) throws InvalidIdException;

    //retrieves list of all of the past moves in a TicMove object
    List<TicMove> getPastMoves(Integer gameId);

    //adding a game to the db with no PastMoves (mainly for testing purposes)
    TicGame addGame(TicGame toAdd);


}
