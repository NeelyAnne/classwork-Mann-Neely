package com.talentpath.tictactoe.daos;

import com.talentpath.tictactoe.exceptions.InvalidIdException;
import com.talentpath.tictactoe.models.TicGame;
import com.talentpath.tictactoe.models.TicMove;
import com.talentpath.tictactoe.services.TicService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


//this will test the dao connection using the testing db
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles( "daotesting" )
public class PostgresTicDaoTest {

    TicService service;

    @Autowired
    PostgresTicDao dao;


    //CLEAN OUR THE DB BEFORE all of our test run
    //AND THEN INSERT two NEW GAMEs TO BE ABLE TO TEST (1)ARRANGE!!
    @BeforeEach
    void setUp() {
        service = new TicService( dao );
        dao.reset();

        //make the first list of pastmoves
        List<TicMove> movesOne = new ArrayList<>();

        //make the first move and add it to the movesOne list
        TicMove moveOne = new TicMove();
        moveOne.setGameId(1);
        moveOne.setChoice("A1");
        moveOne.setPlayer("X");
        movesOne.add(moveOne);

        //make the second move and add it to the movesOne list
        TicMove moveTwo = new TicMove();
        moveTwo.setGameId(1);
        moveTwo.setChoice("C3");
        moveTwo.setPlayer("O");
        movesOne.add(moveTwo);

        //create and add the first game to the db
        TicGame gameOne = new TicGame();
        gameOne.setGameId(1);
        gameOne.setWinner("X");
        gameOne.setPastMoves(movesOne);
        TicGame newGame = dao.addGame(gameOne);

        //create the second moves list for the second game and create one move for it
        List<TicMove> movesTwo = new ArrayList<>();
        TicMove moveThree = new TicMove();
        moveThree.setGameId(2);
        moveThree.setChoice("B2");
        moveThree.setPlayer("X");
        movesTwo.add(moveThree);

        //create the second game and set the properties then push to the db
        TicGame gameTwo = new TicGame();
        gameTwo.setGameId(2);
        gameTwo.setWinner("O");
        gameTwo.setPastMoves(movesTwo);
        TicGame newGameTwo = dao.addGame(gameTwo);

    }

    // (2)ACT AND (3)ASSERT

    //in the DAO layer the getAllGames only return the information from the games table
    //aka the gameId and the winner, past moves are added from another table in the service layer
    @Test
    void getAllGames() {

        List<TicGame> games = dao.getAllGames();
        TicGame gameOne = games.get(0);
        TicGame gameTwo = games.get(1);

        TicMove moveOne = new TicMove();
        moveOne.setGameId(1);
        moveOne.setChoice("A1");
        moveOne.setPlayer("X");

        assertEquals(1, gameOne.getGameId());
        assertEquals("X", gameOne.getWinner());

        assertEquals(2, gameTwo.getGameId());
        assertEquals("O", gameTwo.getWinner());

    }

    //this also only return the gameId and the winner
    //retrieve a single game by the gameId
    @Test
    void getGameById() {
        try {
            TicGame game = dao.getGameById(1);

            assertEquals(1, game.getGameId());
            assertEquals("X", game.getWinner());
        } catch (InvalidIdException e) {
            fail("Failed to get game by id due to an invalid id");
        }

    }

    @Test
    void getPastMoves() {

        List<TicMove> moves = dao.getPastMoves(2);
        TicMove move = new TicMove();
        move.setGameId(2);
        move.setChoice("B2");
        move.setPlayer("X");
        moves.add(move);


        assertEquals(move.getGameId(), moves.get(0).getGameId());
        assertEquals(move.getChoice(), moves.get(0).getChoice());
        assertEquals(move.getPlayer(), moves.get(0).getPlayer());

    }

    //adding a single game and then checking to see if the gameId processes correctly
    @Test
    void addGame() {
        //Arrange
        TicGame game = new TicGame();
        game.setWinner("x");

        List<TicMove> moves = new ArrayList<>();
        TicMove move = new TicMove();
        move.setGameId(3);
        move.setPlayer("X");
        move.setChoice("B3");
        moves.add(move);

        //act
        game.setPastMoves(moves);
        TicGame addGameToDB = dao.addGame(game);
        List<TicGame> dbGames = dao.getAllGames();
        dbGames.stream().forEach( possibleGame ->
                possibleGame.setPastMoves(dao.getPastMoves(possibleGame.getGameId())) );
        TicGame addedGame = dbGames.get(2);

        //assert
        assertEquals(3, addedGame.getGameId());
        assertEquals("X", addedGame.getWinner());
        assertEquals("B3", addedGame.getPastMoves().get(0).getChoice());
        assertEquals("X", addedGame.getPastMoves().get(0).getPlayer());

    }

    @AfterAll
    void cleanUp() {
        dao.reset();
    }
}
