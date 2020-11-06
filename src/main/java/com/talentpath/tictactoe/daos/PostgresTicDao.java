package com.talentpath.tictactoe.daos;

import com.talentpath.tictactoe.exceptions.InvalidIdException;
import com.talentpath.tictactoe.models.TicGame;
import com.talentpath.tictactoe.models.TicMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//this DAO is used to actually connect the db to the DAO layer
//testing for this component is done by using another testing db
@Component
@Profile( {"production", "daotesting"} )
public class PostgresTicDao implements TicDao{

    //connecting an instance of the JDBC template in order to be able to query in SQL
    @Autowired
    private JdbcTemplate template;

    //clearing all of the previous games when we start the server and adding two games for db testing
    @Override
    public void reset() {
        template.update("TRUNCATE \"PastMoves\", \"Games\"  RESTART IDENTITY");
    }

    //return all games
    @Override
    public List<TicGame> getAllGames() {
        return template.query( "SELECT * FROM \"Games\"", new GameMapper() );
    }

    //return one game by id
    @Override
    public TicGame getGameById(int gameId) throws InvalidIdException {
        //selecting a matching game, get the info and then use gamemapper to create a new game for those
        //properties from the original game
        try {
            return template.queryForObject("SELECT * FROM \"Games\" WHERE \"gameId\" = '" + gameId + "'",
                    new GameMapper());
        } catch (DataAccessException ex){
            throw new InvalidIdException("There doesn't seem to be a game number " + gameId, ex);
        }
    }

    //getting a ticmove list of all of the previous moves including both the space and the player
    @Override
    public List<TicMove> getPastMoves(Integer gameId) {
        //string lists of the moves and players
        List<Integer> gameIds = template.query(
                "SELECT \"gameId\" FROM public.\"PastMoves\"",
                new IdMapper());
        List<String> moves = template.query(
                "SELECT \"move\" FROM \"PastMoves\" WHERE \"gameId\" = '"+gameId+"' ",
                new MoveMapper());
        List<String> players = template.query(
                "SELECT \"player\" FROM \"PastMoves\" WHERE \"gameId\" = '"+gameId+"' ",
                new PlayerMapper());

        //setting the moves and the players into ticmoves to be stored in a list of ticmoves
        List<TicMove> toReturn = new ArrayList<>();
        for ( int i = 0 ; i < moves.size() ; i++) {
            TicMove turn = new TicMove();
            turn.setGameId(gameId);
            turn.setChoice(moves.get(i));
            turn.setPlayer(players.get(i));
            toReturn.add(turn);
        }

        return toReturn;

    }

    //making the SQL command to inject the information into the Games SQL table
    //not going to make it absolutely necessary to have any moves to win so i can make a give up
    //option with no restrictions
    @Override
    public TicGame addGame(TicGame game) {
        //checking to see if there are any previous games
        List<Integer> insertedId = new ArrayList<>();
        insertedId = template.query("SELECT \"gameId\" FROM public.\"Games\";", new IdMapper());
        if (insertedId.size() == 0) {
            game.setGameId(1);
        } else {
            game.setGameId(insertedId.size()+1);
        }

        //checking to see if the game to be inserted has any past moves that should be inserted with it.
        //for testing purposes
        List<TicMove> pastMoves = game.getPastMoves();
        if (game.getPastMoves() != null) {
            for (int i = 0; i < pastMoves.size(); i++) {
                int rowsAffected = template.update(
                        "INSERT INTO \"PastMoves\" (\"gameId\", \"move\", \"player\") VALUES ("
                                + game.getGameId() + ", '" + pastMoves.get(i).getChoice() + "', '" + pastMoves.get(i).getPlayer() + "')");
            }
        } else if (game.getWinner() != null) {
            template.update("INSERT INTO public.\"Games\" (\"winner\") VALUES ('"+ game.getWinner()+"')");
        } else {
            template.update("INSERT INTO public.\"Games\" (\"winner\") VALUES ('')");
        }
        return game;
    }

    class GameMapper implements RowMapper<TicGame> {
        //take the results from the query and create a new game with these attributes
        @Override
        public TicGame mapRow(ResultSet resultSet, int i) throws SQLException {

            TicGame toReturn = new TicGame();
            toReturn.setGameId( resultSet.getInt("gameId"));
            toReturn.setWinner( resultSet.getString("winner"));
            return toReturn;

        }
    }

    //return a string list of the moves from the db
    class MoveMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString( "move");
        }
    }

    //return a string list of the players from the db
    class PlayerMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString( "player");
        }
    }

    //retrieving the id of the game toAdd in the addGame method
    //necessary to be able to use the template.update query... there should only be one
    //(ie the get(0))
    class IdMapper implements  RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("gameId");
        }
    }

}
