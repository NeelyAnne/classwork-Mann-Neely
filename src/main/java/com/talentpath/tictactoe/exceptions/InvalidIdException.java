package com.talentpath.tictactoe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.NOT_FOUND, reason = "Invalid ID, no game found" )
public class InvalidIdException extends Exception{

    Integer wrongNumber;

    public Integer getAttemptedId() {
        return wrongNumber;
    }


    public InvalidIdException( String message ){
        super( message );
    }

    public InvalidIdException( String message, Throwable innerException ){
        super( message, innerException );
    }

}
