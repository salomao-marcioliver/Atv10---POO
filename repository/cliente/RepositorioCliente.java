package repository.cliente;

import model.cliente.Cliente;
import java.util.List;

public interface RepositorioCliente {

    void inserirCliente(Cliente cliente) throws CPFJaCadastradoException;

    void alterarCliente(Cliente cliente) throws ClienteNaoCadastradoException;

    void deletarCliente(Cliente cliente) throws ClienteNaoCadastradoException;

    Cliente buscarCliente(String cpf) throws ClienteNaoCadastradoException;

    List<Cliente> getAll();
    
}
