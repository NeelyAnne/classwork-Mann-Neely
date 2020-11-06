package com.talentpath.tictactoe.controllers;


import com.talentpath.tictactoe.exceptions.InvalidIdException;
import com.talentpath.tictactoe.models.TicGame;
import com.talentpath.tictactoe.services.TicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicController {

    //Connecting the service
    @Autowired
    TicService service;

    //provide the url for the service to display (get) the results
    @GetMapping("/games")
    public List<TicGame> getAllGames(){
        return service.getGames();
    }

    @GetMapping("/games/{gameId}")
    public TicGame getGame(@PathVariable Integer gameId)throws InvalidIdException {
        return service.getGameById(gameId);
    }

    //return the gameId int for the new game
    @PostMapping("/begin")
    public Integer beginGame() {
        return service.beginGame();
    }

}
