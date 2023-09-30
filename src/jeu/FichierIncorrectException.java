package jeu;

public class FichierIncorrectException extends Exception{

  public FichierIncorrectException(String message_erreur){
    super(message_erreur);
  }
}
