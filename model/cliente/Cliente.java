package model.cliente;

import java.io.Serializable;

public class Cliente implements Serializable {
    
    private String cpf;
    private String nome;
    private char sexo;
    private String telefone;
    
    public Cliente(String cpf, String nome, char sexo, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.telefone = telefone;
    }
    
    public String getCpf() {
        return this.cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public char getSexo() {
        return this.sexo;
    }
    
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    
    public String toString() {
        return this.cpf + "-" + this.nome;
    }
    
}