package model.conta;

import model.cliente.Cliente;

public class ContaCorrente extends Conta {

  private double limiteEspecial;

  public ContaCorrente(Cliente titular) {
    this(titular, 0);
  }

  public ContaCorrente(Cliente titular, double limiteEspecial) {
    super(titular);
    this.limiteEspecial = limiteEspecial;
  }
  
  public String getTipo() {
    return "Corrente";
  }

  public void setLimiteEspecial(double limiteEspecial) throws LimiteInvalidoException {
    if (this.getSaldo() + limiteEspecial >= 0) {
	    this.limiteEspecial = limiteEspecial;
    } else {
      throw new LimiteInvalidoException();
    }
  }

  public double getLimiteEspecial() {
    return this.limiteEspecial;
  }

  @Override
  public double getDisponivelParaSaque() {
      return (this.getSaldo() + this.limiteEspecial);
  }
    
}