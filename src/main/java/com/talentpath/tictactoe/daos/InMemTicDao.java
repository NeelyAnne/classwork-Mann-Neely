package com.talentpath.tictactoe.daos;

import com.talentpath.tictactoe.models.TicGame;
import com.talentpath.tictactoe.models.TicMove;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//this dao is used when testing the service layer in order to eliminate error from the dao
//it runs the same functions as the postgres dao layer except it does not actually access the db
@Component
@Profile( "servicetesting" )
public class InMemTicDao implements TicDao{

    //list of games saved in the memory db to test
    List<TicGame> games = new ArrayList<>();

    //when this class is initialized the reset function will automatically be called
    public InMemTicDao(){
        reset();
    }

    //reset just clears in the in mem db and recreates those objects to be pulled from for testing
    //this specific method creates two games, one with two moves and one with none
    @Override
    public void reset() {
        games.clear();

        TicGame gameOne = new TicGame();
        gameOne.setGameId(1);
        gameOne.setWinner("X");

        List<TicMove> moves = new ArrayList<>();

        TicMove moveOne = new TicMove();
        moveOne.setGameId(1);
        moveOne.setChoice("A1");
        moveOne.setPlayer("X");
        moves.add(moveOne);

        TicMove moveTwo = new TicMove();
        moveTwo.setGameId(1);
        moveTwo.setChoice("C3");
        moveTwo.setPlayer("O");
        moves.add(moveTwo);

        gameOne.setPastMoves( moves );
        games.add(gameOne);

        TicGame gameTwo = new TicGame();
        gameTwo.setGameId(2);
        gameTwo.setWinner("O");

        TicMove moved = new TicMove();
        moved.setGameId(1);
        moved.setChoice("B2");
        moved.setPlayer("X");
        moves.add(moved);
        gameTwo.setPastMoves( new ArrayList<>() );
        games.add(gameTwo);

    }

    //return a copy of the list of games
    @Override
    public List<TicGame> getAllGames() {
        return games.stream().map( toCopy -> new TicGame(toCopy) ).collect(Collectors.toList());
    }

    //filter the list of games and return only the one with the right id if that id exists
    @Override
    public TicGame getGameById(int gameId) {
        TicGame toReturn = games.stream().filter( g -> g.getGameId() == gameId ).findAny().orElse(null);
        if( toReturn != null ) return toReturn;
        return null;
    }

    //filter the games for the correct gameid and if not null then return the past moves for that game
    @Override
    public List<TicMove> getPastMoves(Integer gameId) {
        TicGame toGrabFrom = games.stream().filter( g -> g.getGameId() == gameId ).findAny().orElse(null);
        if( toGrabFrom != null ) return toGrabFrom.getPastMoves();
        return null;
    }

}
