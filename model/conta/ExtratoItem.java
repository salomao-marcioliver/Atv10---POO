package model.conta;

import java.io.Serializable;


public class ExtratoItem implements Serializable{
    private double saldoAnterior;
    private String data;
    private String historico;
    private double valor;
    private String tipo;
    private double saldoPosterior;

  public ExtratoItem(String date, String historico, double valor, String tipo, double saldoA, double saldoP) {
      this.data = date;
      this.historico = historico;
      this.valor = valor;
      this.tipo = tipo;
      this.saldoAnterior = saldoA;
      this.saldoPosterior = saldoP;
  }

 
  public String getData() {
      return data;
  }
  
  public String getHistorico() {
      return historico;
  }

  public double getValor() {
      return valor;
  }

  public String getTipo() {
      return tipo;
  }

  public double getSaldoPosterior() {
      return saldoPosterior;
  }
 
  public double getSaldoAnterior() {
      return saldoAnterior;
  }
   
}