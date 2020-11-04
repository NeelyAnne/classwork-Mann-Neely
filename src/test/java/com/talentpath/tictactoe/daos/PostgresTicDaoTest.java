package com.talentpath.tictactoe.daos;

import com.talentpath.tictactoe.services.TicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles( "daotesting" )
public class PostgresTicDaoTest {

    TicService service;

    @Autowired
    PostgresTicDao dao;

    @BeforeEach
    void setUp() {
        dao.reset();
    }

    @Test
    void reset() {

    }

    @Test
    void getAllGames() {

    }

    @Test
    void getGameById() {

    }

    @Test
    void getPastMoves() {

    }
}
