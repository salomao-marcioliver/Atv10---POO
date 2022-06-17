package model.conta;

public class SaldoInsuficienteException extends Exception {
  Conta conta;

  public SaldoInsuficienteException(Conta conta) {
    super("Saldo insuficiente na conta " + conta.getNumero());
    this.conta = conta;
  }

  public Conta getConta() {
    return conta;
  }
}