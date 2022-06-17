package controler;

import repository.cliente.RepositorioCliente;
import repository.cliente.CPFJaCadastradoException;
import repository.cliente.ClienteNaoCadastradoException;
import model.cliente.Cliente;
import repository.conta.ContaNaoCadastradaException;
import repository.conta.ContaJaCadastradaException;
import repository.conta.RepositorioConta;
import model.conta.Conta;
import model.conta.SaldoInsuficienteException;
import repository.cliente.RepositorioClienteLista;
import repository.conta.RepositorioContaLista;
import java.util.List;
import strategy.*;
import java.io.*;

public class ControladorBanco {
    // Arquivo para salvar os dados
    private final File file = new File("banco.dat");
  
    private RepositorioCliente repositorioCliente;
    private RepositorioConta repositorioConta;
    private NumberGeneratorStrategy numberStrategy;

    public ControladorBanco() {
      if (file.exists()) {
        loadData();
      } else {
        repositorioCliente = new RepositorioClienteLista();
        repositorioConta = new RepositorioContaLista();
        numberStrategy = new SequentialNumberStrategy();
      }
    }

    public void inserirCliente(Cliente cliente) throws CPFJaCadastradoException {
        repositorioCliente.inserirCliente(cliente);
    }
    
    public void alterarCliente(Cliente cliente) throws ClienteNaoCadastradoException {
        repositorioCliente.alterarCliente(cliente);
    }
    
    public Cliente buscarCliente(String cpf) throws ClienteNaoCadastradoException {
        return repositorioCliente.buscarCliente(cpf);
    }
    
    public void excluirCliente(Cliente cliente) throws ControladorException, ClienteNaoCadastradoException {
    	// Busca listas de contas do Cliente
    	List<Conta> contasCliente = this.getAllContas(cliente.getCpf());
    	
        if (contasCliente.isEmpty()) {
            repositorioCliente.deletarCliente(cliente);
        } else {
            throw new ControladorException("Cliente com contas ativas não pode ser excluído");
        }
    }
    
    public List<Cliente> getAllClientes() {
        return repositorioCliente.getAll();
    }
    
    public Conta inserirConta(Conta conta) throws ContaJaCadastradaException {
        conta.setNumero(numberStrategy.nextNumber());
        return repositorioConta.inserirConta(conta);
    }

    public Conta buscarConta(String numero) throws ContaNaoCadastradaException  {
        return repositorioConta.buscarConta(numero);
    }

    public void alterarConta(Conta conta) throws ContaNaoCadastradaException  {
        repositorioConta.alterarConta(conta);
    }
    
    public void excluirConta(Conta conta) throws ContaNaoCadastradaException, ControladorException {
      if (conta.getSaldo() == 0) {
        repositorioConta.deletarConta(conta);
      } else {
        throw new ControladorException("Conta com saldo não pode ser excluída.");
      }
    }
    
    public void deposito(String numero, double valor) throws ContaNaoCadastradaException {
        Conta conta = repositorioConta.buscarConta(numero);
        conta.depositar(valor);
        repositorioConta.alterarConta(conta);
    }

    public void saque(String numero, double valor) throws ContaNaoCadastradaException, SaldoInsuficienteException {
        Conta conta = repositorioConta.buscarConta(numero);
        conta.sacar(valor);
        repositorioConta.alterarConta(conta);
    }

    public void tranferir(String origem, String destino, double valor) throws ContaNaoCadastradaException, SaldoInsuficienteException {
        Conta conta1 = repositorioConta.buscarConta(origem);
        Conta conta2 = repositorioConta.buscarConta(destino);
        conta1.transferir(conta2, valor);
        repositorioConta.alterarConta(conta1);
        repositorioConta.alterarConta(conta2);
    }
    
    public List<Conta> getAllContas() {
        return repositorioConta.getAll();
    }

    public List<Conta> getAllContas(String cpf) {
        return repositorioConta.getAll(cpf);
    }

    public void exit() {
      try {
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Salvar meus dados
        o.writeObject(repositorioCliente);
        o.writeObject(repositorioConta);
        o.writeObject(numberStrategy);

        o.close();
        f.close();
      } catch (IOException e) {
        System.err.println("Erro de serialização de objeto");
        e.printStackTrace();
      }
    }

  private void loadData() {
        try {
          FileInputStream f = new FileInputStream(file);
          ObjectInputStream o = new ObjectInputStream(f);

          repositorioCliente = (RepositorioCliente) o.readObject();
          repositorioConta = (RepositorioConta) o.readObject();
          numberStrategy = (SequentialNumberStrategy) o.readObject();

          o.close();
          f.close();          
        } catch (ClassNotFoundException e) {
          System.err.println("Definição da classe não encontrada");
        } catch (IOException e) {
          System.err.println("Erro ao carregar dados do arquivo");
        }
  }
}
