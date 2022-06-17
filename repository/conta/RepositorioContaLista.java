package repository.conta;

import java.util.ArrayList;
import java.util.List;
import model.conta.Conta;

import java.io.Serializable;


public class RepositorioContaLista implements Serializable, RepositorioConta {
    
    List<Conta> contas;
    

    public RepositorioContaLista() {
        contas = new ArrayList<>();
    }
    
    @Override
    public Conta inserirConta(Conta conta) throws ContaJaCadastradaException {
        try {
            buscarConta(conta.getNumero());
            throw new ContaJaCadastradaException();
        } catch (ContaNaoCadastradaException ex) {
            contas.add(conta);
        }
        return conta;
    }
    
    @Override
    public void alterarConta(Conta conta) {
        // Em memória, não há necessidade de atualizar objeto
    }
    
    @Override
    public void deletarConta(Conta conta) throws ContaNaoCadastradaException {
        if (! contas.remove(conta)) {
           throw new ContaNaoCadastradaException();
        }
    }
    
    @Override
    public Conta buscarConta(String numero) throws ContaNaoCadastradaException {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        throw new ContaNaoCadastradaException();
    }

    @Override
    public List<Conta> getAll() {
        return new ArrayList<>(contas);
    }

    @Override
    public List<Conta> getAll(String cpf) {
        List<Conta> lista = new ArrayList<>();
        for (Conta conta : contas) {
            if (conta.getTitular().getCpf().equals(cpf)) {
                lista.add(conta);
            }
        }
        return lista;
    }

}
