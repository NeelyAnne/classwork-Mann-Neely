package com.talentpath.tictactoe.controllers;


import com.talentpath.tictactoe.models.TicGame;
import com.talentpath.tictactoe.services.TicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public TicGame getGame(@PathVariable Integer gameId) {
        return service.getGameById(gameId);
    }

}
