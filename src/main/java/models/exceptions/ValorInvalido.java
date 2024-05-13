package models.exceptions;

public class ValorInvalido extends NumberFormatException{
    public ValorInvalido(String msg){
        super(msg);
    }
}
