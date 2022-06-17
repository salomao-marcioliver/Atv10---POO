package repository.conta;

import model.conta.Conta;
import java.util.List;

public interface RepositorioConta {

    Conta inserirConta(Conta conta) throws ContaJaCadastradaException;

    void alterarConta(Conta conta) throws ContaNaoCadastradaException;

    void deletarConta(Conta conta) throws ContaNaoCadastradaException;

    Conta buscarConta(String numero) throws ContaNaoCadastradaException;

    List<Conta> getAll();

    List<Conta> getAll(String cpf);
    
}
