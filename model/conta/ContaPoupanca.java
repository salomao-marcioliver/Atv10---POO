package model.conta;

import model.cliente.Cliente;

public class ContaPoupanca extends Conta {

  public ContaPoupanca(Cliente titular) {
    super(titular);
  }

  public void renderJuros(double taxa) {
    this.depositar(this.getSaldo() * taxa);
  }

  public String getTipo() {
    return "Poupança";
  }
  
}