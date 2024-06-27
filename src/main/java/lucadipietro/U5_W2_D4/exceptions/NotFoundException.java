package lucadipietro.U5_W2_D4.exceptions;

import java.util.UUID;

public class NotFoundException extends  RuntimeException{
    public NotFoundException(UUID id){
        super("Record con id " + id + " non è stato trovato!");
    }
}
