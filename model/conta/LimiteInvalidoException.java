package model.conta;

public class LimiteInvalidoException extends Exception {

  public LimiteInvalidoException() {
    super("Valor definido para o limite inválido.");
  }

}