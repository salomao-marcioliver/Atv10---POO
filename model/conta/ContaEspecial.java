package model.conta;

import model.cliente.Cliente;

public class ContaEspecial extends ContaCorrente {

  private double bonus;
  
  public ContaEspecial(Cliente titular) {
    super(titular);
  }

  public ContaEspecial(Cliente titular, double limiteEspecial) {
    super(titular, limiteEspecial);
  }  

  @Override
  public void sacar(double valor) throws SaldoInsuficienteException {
    super.sacar(valor);
    this.verificarUsoLimite();
  }

  @Override
  public void depositar(double valor) {
    this.bonus = this.bonus + (valor * 0.01);
    super.depositar(valor);
    this.verificarUsoLimite();
  }
  
  public void renderBonus() {
    super.depositar(bonus);
    this.bonus = 0;   
  }

  public double getBonus() {
    return bonus;
  }

  public String getTipo() {
    return "Especial";
  }

  private void verificarUsoLimite() {
    if (this.getSaldo() < 0) {
      this.bonus = 0;
    }
  }
}