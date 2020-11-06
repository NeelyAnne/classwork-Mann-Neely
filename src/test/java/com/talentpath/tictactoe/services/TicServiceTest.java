package com.talentpath.tictactoe.services;

import com.talentpath.tictactoe.daos.TicDao;
import com.talentpath.tictactoe.exceptions.InvalidIdException;
import com.talentpath.tictactoe.models.TicGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//these tests are to test the service layer using the inMemDao and db
//does not connect to the db
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("servicetesting")
public class TicServiceTest {

    //initializing an instance of the service
    TicService service;

    //connecting the dao (the in mem one in this case)
    @Autowired
    TicDao dao;

    // clean out the in mem db and reset the games to test
    @BeforeEach
    void setUp() {
        service = new TicService( dao );
        dao.reset();
    }

    // retrieving the games from the in mem dao (db) to test the .getGames function
    @Test
    void getGames() {

        //A, A, A

        List<TicGame> games = service.getGames();
        assertEquals( 2, games.size() );

        TicGame gameOne = games.get(0);

        assertEquals( 1, gameOne.getGameId() );
        assertEquals( "X", gameOne.getWinner() );
        assertEquals("A1", gameOne.getPastMoves().get(0).getChoice());

        TicGame gameTwo = games.get(1);

        assertEquals( 2, gameTwo.getGameId() );
        assertEquals( "O", gameTwo.getWinner() );

    }

    //retrieving a single game by id
    @Test
    void getGameById() {
        try {
            TicGame game = service.getGameById(1);

            assertEquals(1, game.getGameId());
            assertEquals("X", game.getWinner());
            assertEquals("A1", game.getPastMoves().get(0).getChoice());
        } catch (InvalidIdException e) {
            fail("Failed to get game by id due to an invalid id");
        }

    }

    @Test
    void beginGame() {

    }

}
