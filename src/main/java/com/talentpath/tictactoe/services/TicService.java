package com.talentpath.tictactoe.services;

import com.talentpath.tictactoe.daos.TicDao;
import com.talentpath.tictactoe.exceptions.InvalidIdException;
import com.talentpath.tictactoe.models.TicGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TicService {

    //bring in the type of DAO automatically based on the active profile (testing vs production)
    //service testing use the in mem dao (servicetesting)
    //actual service uses the postgres dao (production)
    TicDao dao;

    //initialize the service to an instance of the DAO (connection)
    @Autowired
    public TicService( TicDao dao ){
        this.dao = dao;
    }

    //return all games
    public List<TicGame> getGames(){

        //get all the games from the db and separately set the past moves because they're a list
        List<TicGame> allGames = dao.getAllGames();
        allGames.stream().forEach( game -> game.setPastMoves(dao.getPastMoves(game.getGameId())) );

        return allGames;
    }

    //return one game by ID
    public TicGame getGameById(int gameId) throws InvalidIdException {

        //again setting the past moves separately
        TicGame toReturn = dao.getGameById(gameId);
        toReturn.setPastMoves(dao.getPastMoves(gameId));

        return toReturn;
    }

    //will add a new game to the database with no winner and no past moves based on the id of the
    //game before this
    public Integer beginGame() {

        TicGame toAdd = new TicGame();
        toAdd.setPastMoves( new ArrayList<>());

        toAdd = dao.addGame( toAdd );

        return toAdd.getGameId();
    }

}
