package model.conta;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;

public class ExtratoConta implements Serializable{
  private List<ExtratoItem> extrato;
  public ExtratoConta(String historico, double valor, String tipo, double saldoA, double saldoP){
    this.extrato = new ArrayList<>();
    ExtratoItem lancamento = new ExtratoItem("",historico,valor,tipo,saldoA, saldoP);
    this.addLancamento(lancamento);
  }


  public void addLancamento(ExtratoItem lancamento){
    this.extrato.add(lancamento);
  }

  public List<ExtratoItem> getExtrato(){
    return new ArrayList<>(this.extrato);
  }
  
  public List<ExtratoItem> getExtrato(String date){
    List<ExtratoItem> extrato = new ArrayList<>();
    for(ExtratoItem item : this.extrato){
       if(date.equals(item.getData())){
        extrato.add(item);
       }
    }
    return new ArrayList<>(extrato);
  }

}