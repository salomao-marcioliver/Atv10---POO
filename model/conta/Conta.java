package model.conta;

import model.cliente.Cliente;
import java.io.Serializable;
import strategy.GetDate;


public abstract class Conta implements Serializable {
    
    private String numero;
    private Cliente titular;
    private double saldo;
    private ExtratoConta extrato;
  
    public Conta(Cliente titular) {
        this.titular =  titular;
        this.saldo = 0;
        this.extrato = new ExtratoConta("Saldo anterior",0,"",0,0);
    }
    
    public void depositar(double valor) {
        this.saldo = this.saldo + valor;
        this.lancamento("Deposito",valor,"C",(this.getSaldo()-valor), this.getSaldo());
    }

    private void depositar(double valor, String historico) {
        this.saldo = this.saldo + valor;
        this.lancamento(historico,valor,"C",(this.getSaldo()-valor), this.getSaldo());
    }

    private void sacar(double valor, String historico) throws SaldoInsuficienteException {
        if (valor <= this.getDisponivelParaSaque()) {
            this.saldo -= valor;
            this.lancamento(historico,valor,"D",(this.getSaldo()-valor), this.getSaldo());
        } else {
            throw new SaldoInsuficienteException(this);
        }
    }
    
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= this.getDisponivelParaSaque()) {
            this.saldo -= valor;
            this.lancamento("Saque",valor,"D",(this.getSaldo()-valor), this.getSaldo());
        } else {
            throw new SaldoInsuficienteException(this);
        }
    }
    
    public void transferir(Conta destino, double valor) throws SaldoInsuficienteException {
        this.sacar(valor, "Transferido para conta #" + destino.getNumero());
        destino.depositar(valor, "Recebido da conta #" + this.getNumero());
        
    }

    public void lancamento(String historico, double valor, String tipo, double saldoA, double saldoP){
        GetDate date = new GetDate();
        ExtratoItem lancamento = new ExtratoItem(date.getDate(),historico,valor,tipo,saldoA, saldoP);
        this.extrato.addLancamento(lancamento);
      }

    public ExtratoConta getExtratoConta(){
        return this.extrato;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public Cliente getTitular() {
        return this.titular;
    }

    public void setNumero(String numero) {
	    this.numero = numero;
    }
    
    public String getNumero() {
        return this.numero;
    }
    
    public double getSaldo() {
        return this.saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getDisponivelParaSaque() {
        return this.saldo;
    }
  
    public abstract String getTipo();
    
    public String toString() {
        return "Número...: " + this.getNumero() +
               "\nTitular..: " + this.getTitular() +
               "\nSaldo....: " + this.getSaldo();
    }
}