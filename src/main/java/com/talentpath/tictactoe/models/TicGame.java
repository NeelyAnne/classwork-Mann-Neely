package com.talentpath.tictactoe.models;

import java.util.List;

public class TicGame {

    Integer gameId;
    List<TicMove> pastMoves;
    String winner;

    public TicGame() {}

    //be able to pass in an object of the same type and return that abject
    public TicGame(TicGame copy) {
        this.gameId = copy.gameId;
        this.pastMoves = copy.pastMoves;
        this.winner = copy.winner;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public List<TicMove> getPastMoves() {
        return pastMoves;
    }

    public void setPastMoves(List<TicMove> pastMoves) {
        this.pastMoves = pastMoves;
    }

    public String getWinner() {
        return winner.toUpperCase();
    }

    public void setWinner(String winner) {
        this.winner = winner.toUpperCase();
    }
}
