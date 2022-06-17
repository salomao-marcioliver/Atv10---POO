package repository.cliente;

import model.cliente.Cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClienteLista implements RepositorioCliente, Serializable {

  List<Cliente> clientes;

  public RepositorioClienteLista() {
    this.clientes = new ArrayList<Cliente>();
  }

  @Override
  public void inserirCliente(Cliente cliente) throws CPFJaCadastradoException {
    try {
      buscarCliente(cliente.getCpf());
      throw new CPFJaCadastradoException();
    } catch (ClienteNaoCadastradoException ex) {
      clientes.add(cliente);
    }
  }

  @Override
  public void alterarCliente(Cliente cliente) throws ClienteNaoCadastradoException {
    // Em memória, não há necessidade de atualizar objeto
    buscarCliente(cliente.getCpf());
  }

  @Override
  public void deletarCliente(Cliente cliente) throws ClienteNaoCadastradoException {
    if (!clientes.remove(cliente)) {
      throw new ClienteNaoCadastradoException();
    }
  }

  @Override
  public Cliente buscarCliente(String cpf) throws ClienteNaoCadastradoException {

    for (Cliente cliente : clientes) {
      if (cliente.getCpf().equals(cpf)) {
        return cliente;
      }
    }
    throw new ClienteNaoCadastradoException();

  }

  @Override
  public List<Cliente> getAll() {
    return new ArrayList<>(clientes);
  }

}
