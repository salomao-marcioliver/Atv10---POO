import controler.ControladorBanco;
import controler.ControladorException;
import repository.cliente.CPFJaCadastradoException;
import model.cliente.Cliente;
import repository.cliente.ClienteNaoCadastradoException;
import model.conta.Conta;
import model.conta.ContaCorrente;
import model.conta.ContaPoupanca;
import model.conta.ExtratoItem;
import model.conta.ContaEspecial;
import repository.conta.ContaJaCadastradaException;
import repository.conta.ContaNaoCadastradaException;
import model.conta.SaldoInsuficienteException;
import model.conta.LimiteInvalidoException;
import java.util.List;
import java.util.Scanner;

class Main {

  static ControladorBanco controlador;
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    controlador = new ControladorBanco();

    insereDadosTeste();

    int opcao;
    do {
      limpaTela();
      System.out.println("MENU PRINCIPAL");
      System.out.println("==== =========");
      System.out.println();
      System.out.println("<1> Cadastro Clientes");
      System.out.println("<2> Cadastro Contas");
      System.out.println("<3> CAIXA ELETRONICO");
      System.out.println("<0> Sair");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          cadastroClientes();
          break;
        case 2:
          cadastroContas();
          break;
        case 3:
          caixaEletronico();
          break;
      }
    } while (opcao != 0);

    controlador.exit();
    System.out.println("Programa terminado");
  }

  private static void insereDadosTeste() {

    try {

      Cliente cliente01 = new Cliente("1", "João Batista", 'M', "99123-1234");
      controlador.inserirCliente(cliente01);
      Cliente cliente02 = new Cliente("2", "Paula Leite", 'F', "98765-4321");
      controlador.inserirCliente(cliente02);

      Conta c1 = new ContaPoupanca(cliente01);
      c1.depositar(120);
      controlador.inserirConta(c1);
      Conta c2 = new ContaEspecial(cliente02, 500);
      c2.depositar(300);
      controlador.inserirConta(c2);

    } catch (CPFJaCadastradoException | ContaJaCadastradaException ex) {
    }
  }

  private static void limpaTela() {
    for (int i = 0; i < 25; i++) {
      System.out.println();
    }
  }

  private static void cadastroClientes() {
    int opcao;
    do {
      limpaTela();
      System.out.println("CADASTRO CLIENTES");
      System.out.println("======== ========");
      System.out.println();
      System.out.println("<1> Incluir Cliente");
      System.out.println("<2> Alterar Cliente");
      System.out.println("<3> Excluir Cliente");
      System.out.println("<4> Listar Clientes");
      System.out.println("<0> Menu Principal");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          incluirCliente();
          break;
        case 2:
          alterarCliente();
          break;
        case 3:
          excluirCliente();
          break;
        case 4:
          listarCliente();
          break;
      }
    } while (opcao != 0);
  }

  private static void incluirCliente() {
    limpaTela();
    System.out.println("Cadastro de Cliente");
    System.out.println("===================");
    System.out.print("CPF: ");
    String cpf = scanner.nextLine();
    System.out.print("Nome: ");
    String nome = scanner.nextLine();
    System.out.print("Sexo: ");
    char sexo = scanner.nextLine().charAt(0);
    System.out.print("Telefone: ");
    String fone = scanner.nextLine();

    Cliente cliente = new Cliente(cpf, nome, sexo, fone);

    try {
      controlador.inserirCliente(cliente);
      System.out.println("Cliente cadastrado!");
    } catch (CPFJaCadastradoException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void listarCliente() {
    limpaTela();
    List<Cliente> clientes = controlador.getAllClientes();
    System.out.printf("CPF          Nome                 Sexo Telefone\n");
    System.out.printf("============ ==================== ==== ===============\n");
    for (Cliente cliente : clientes) {
      System.out.printf("%12s ", cliente.getCpf());
      System.out.printf("%-20s ", cliente.getNome());
      System.out.printf("%-4s ", String.valueOf(cliente.getSexo()));
      System.out.printf("%15s\n", cliente.getTelefone());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void excluirCliente() {
    limpaTela();
    System.out.println("Excluir de Cliente");
    System.out.println("==================");
    System.out.print("CPF: ");
    String cpf = scanner.nextLine();

    try {
      Cliente cliente = controlador.buscarCliente(cpf);
      System.out.println();
      System.out.println("Nome: " + cliente.getNome());
      System.out.println("Sexo: " + cliente.getSexo());
      System.out.println("Telefone: " + cliente.getTelefone());
      System.out.println();

      System.out.print("Exclui esse cliente? (s/n)?");
      String resposta = scanner.nextLine();

      if (resposta.equalsIgnoreCase("s")) {
        controlador.excluirCliente(cliente);
        System.out.println("Cliente excluído!");
      }

    } catch (ClienteNaoCadastradoException | ControladorException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void alterarCliente() {
    limpaTela();
    System.out.println("Alterar de Cliente");
    System.out.println("==================");
    System.out.print("CPF: ");
    String cpf = scanner.nextLine();

    try {
      Cliente cliente = controlador.buscarCliente(cpf);

      System.out.println();
      System.out.println("Nome: " + cliente.getNome());
      System.out.print("Nome (<enter> = Não alterar): ");
      String nome = scanner.nextLine();
      if (!nome.equals("")) {
        cliente.setNome(nome);
      }

      System.out.println("Sexo: " + cliente.getSexo());
      System.out.print("Sexo (<enter> = Não alterar): ");
      String sexo = scanner.nextLine();
      if (!sexo.equals("")) {
        cliente.setSexo(sexo.charAt(0));
      }

      System.out.println("Telefone: " + cliente.getTelefone());
      System.out.print("Telefone (<enter> = Não alterar): ");
      String fone = scanner.nextLine();
      if (!fone.equals("")) {
        cliente.setTelefone(fone);
      }

      System.out.println();

      controlador.alterarCliente(cliente);
      System.out.println("Cliente Alterado!");
      System.out.println();

    } catch (ClienteNaoCadastradoException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void cadastroContas() {
    int opcao;
    do {
      limpaTela();
      System.out.println("CADASTRO CONTAS");
      System.out.println("======== ======");
      System.out.println();
      System.out.println("<1> Incluir Conta");
      System.out.println("<2> Excluir Conta");
      System.out.println("<3> Consultar Conta");
      System.out.println("<4> Listar Contas");
      System.out.println("<5> Alterar Limite");
      System.out.println("<6> Render Juros");
      System.out.println("<7> Render Bônus");
      System.out.println("<0> Menu Principal");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          incluirConta();
          break;
        case 2:
          excluirConta();
          break;
        case 3:
          consultarConta();
          break;
        case 4:
          listarContas();
          break;
        case 5:
          alterarLimite();
          break;
        case 6:
          renderJuros();
          break;
        case 7:
          renderBonus();
          break;
      }
    } while (opcao != 0);
  }

  private static void incluirConta() {
    limpaTela();
    System.out.println("Cadastro de Conta");
    System.out.println("=================");

    try {
      System.out.print("CPF do titular: ");
      String cpf = scanner.nextLine();
      Cliente titular = controlador.buscarCliente(cpf);
      System.out.println("Cliente: " + titular.getNome());

      Conta conta = null;
      String tipo;
      do {
        System.out.print("Tipo de Conta: (C)orrente, (P)oupança, (E)special ou <ENTER> para sair? ");
        tipo = scanner.nextLine();
  
        if (tipo.equalsIgnoreCase("C")) {
          conta = new ContaCorrente(titular);
        } else if (tipo.equalsIgnoreCase("P")) {
          conta = new ContaPoupanca(titular);
        } else if (tipo.equalsIgnoreCase("E")) {
          conta = new ContaEspecial(titular);
        } 
      } while (! (conta != null || tipo.equals("")));
      
      if (conta != null) {
        conta = controlador.inserirConta(conta);
        System.out.println("Conta " + conta.getTipo() + " #" + conta.getNumero() + " criada!");
      } else {
        System.out.println("Nenhuma conta criada");
      }
      
    } catch (ClienteNaoCadastradoException | ContaJaCadastradaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void excluirConta() {
    limpaTela();
    System.out.println("Excluir de Conta");
    System.out.println("================");
    System.out.println();
    System.out.print("Numero da Conta: ");
    String numero = scanner.nextLine();

    try {
      Conta conta = controlador.buscarConta(numero);
      System.out.println();
      System.out.println("Numero: " + conta.getNumero());
      System.out.println("Titular: " + conta.getTitular().getNome());
      System.out.println("Saldo: " + conta.getSaldo());
      System.out.println();

      System.out.print("Exclui essa conta? (s/n)?");
      String resposta = scanner.nextLine();

      if (resposta.equalsIgnoreCase("s")) {
        controlador.excluirConta(conta);
        System.out.println("Conta excluída!");
      }

    } catch (ContaNaoCadastradaException | ControladorException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void consultarConta() {
    limpaTela();
    System.out.println("Consultar Saldo");
    System.out.println("===============");
    System.out.println();
    System.out.print("Numero da Conta: ");
    String numero = scanner.nextLine();

    try {
      Conta conta = controlador.buscarConta(numero);
      System.out.println();
      System.out.println("Numero: " + conta.getNumero());
      System.out.println("Titular: " + conta.getTitular().getNome());
      System.out.println("Tipo: Conta " + conta.getTipo());

      if (conta instanceof ContaEspecial) {
        System.out.println("Bônus: " + ((ContaEspecial)conta).getBonus());
      }
      
      System.out.printf("Saldo: R$ %-12.2f\n", conta.getSaldo());

      if (conta instanceof ContaCorrente) {
        System.out.printf("Limite: R$ %-12.2f\n", ((ContaCorrente)conta).getLimiteEspecial());
        System.out.printf("Disponível para saque: R$ %-12.2f\n", conta.getDisponivelParaSaque());
      }

      System.out.println();

    } catch (ContaNaoCadastradaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void alterarLimite() {
    limpaTela();
    System.out.println("Alterar Limite");
    System.out.println("==============");
    System.out.println();
    System.out.print("Numero da Conta: ");
    String numero = scanner.nextLine();

    try {
      Conta conta = controlador.buscarConta(numero);

      if (conta instanceof ContaCorrente) {
        System.out.println();
        System.out.println("Numero: " + conta.getNumero());
        System.out.println("Titular: " + conta.getTitular().getNome());
        System.out.println("Tipo: Conta " + conta.getTipo());
        System.out.printf("Saldo: R$ %-12.2f\n", conta.getSaldo());
        System.out.printf("Limite: R$ %-12.2f\n", ((ContaCorrente)conta).getLimiteEspecial());
        System.out.printf("Disponível para saque: R$ %-12.2f\n", conta.getDisponivelParaSaque());
        System.out.println();
  
        System.out.print("Novo Limite: ");
        double limite = scanner.nextDouble();
        System.out.println();
        
        ((ContaCorrente)conta).setLimiteEspecial(limite);
        controlador.alterarConta(conta);
      } else {
        System.out.println("Tipo de conta não possui limite.");
      }

    } catch (ContaNaoCadastradaException | LimiteInvalidoException ex) {
      System.err.println(ex.getMessage());
      System.out.println("Alteração não realizada!");
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
    scanner.nextLine();
  }

  private static void caixaEletronico() {

    try {
      limpaTela();
      System.out.println("CAIXA ELETRONICO");
      System.out.println("================");
      System.out.println();
      System.out.print("Numero da Conta: ");
      String numeroConta = scanner.nextLine();
      controlador.buscarConta(numeroConta);

      int opcao;
      do {
        limpaTela();
        System.out.println("CAIXA ELETRONICO");
        System.out.println("===== ==========");
        System.out.println();
        System.out.println("<1> Deposito");
        System.out.println("<2> Saque");
        System.out.println("<3> Transferência");
        System.out.println("<4> Consulta Saldo");
        System.out.println("<5> Extrato");
        System.out.println("<0> Menu Principal");
        System.out.println();
        System.out.print("Escolha uma opção: ");

        try {
          opcao = Integer.valueOf(scanner.nextLine());
        } catch (Exception e) {
          opcao = 0;
        }

        switch (opcao) {
          case 0:
            limpaTela();
            break;
          case 1:
            deposito(numeroConta);
            break;
          case 2:
            saque(numeroConta);
            break;
          case 3:
            tranferencia(numeroConta);
            break;
          case 4:
            consultarSaldo(numeroConta);
            break;
          case 5:
            extrato(numeroConta);
            break;
        }
      } while (opcao != 0);
    } catch (ContaNaoCadastradaException e) {
      System.err.println(e.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();

  }

  private static void deposito(String numero) {
    limpaTela();
    System.out.println("Deposito em Conta");
    System.out.println("=================");
    System.out.println();
    System.out.print("Valor: ");
    double valor = scanner.nextDouble();
    scanner.nextLine();

    try {
      controlador.deposito(numero, valor);
      System.out.println("Depósito Realizado!");
    } catch (ContaNaoCadastradaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void saque(String numero) {
    limpaTela();
    System.out.println("Saque de Conta");
    System.out.println("==============");
    System.out.println();
    System.out.print("Valor: ");
    double valor = scanner.nextDouble();
    scanner.nextLine();

    try {
      controlador.saque(numero, valor);
      System.out.println("Saque Realizado!");
    } catch (ContaNaoCadastradaException | SaldoInsuficienteException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void tranferencia(String numero) {
    limpaTela();
    System.out.println("Transferencia entre Contas");
    System.out.println("==========================");
    System.out.println();
    System.out.print("Conta destino: ");
    String destino = scanner.nextLine();
    System.out.print("Valor: ");
    double valor = scanner.nextDouble();
    scanner.nextLine();

    try {
      controlador.tranferir(numero, destino, valor);
      System.out.println("Transferência Realizada!");
    } catch (ContaNaoCadastradaException | SaldoInsuficienteException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void consultarSaldo(String numero) {
    limpaTela();
    System.out.println("Consultar Saldo");
    System.out.println("===============");
    System.out.println();
    try {
      Conta conta = controlador.buscarConta(numero);
      System.out.println();
      System.out.println("Numero: " + conta.getNumero());
      System.out.println("Titular: " + conta.getTitular().getNome());
      System.out.println("Tipo: Conta " + conta.getTipo());
      System.out.printf("Saldo: R$ %-12.2f\n", conta.getSaldo());

      if (conta instanceof ContaCorrente) {
        System.out.printf("Limite: R$ %-12.2f\n", ((ContaCorrente)conta).getLimiteEspecial());
        System.out.printf("Disponível para saque: R$ %-12.2f\n", conta.getDisponivelParaSaque());
      }
      
      System.out.println();

    } catch (ContaNaoCadastradaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void extrato(String numeroConta) {
    limpaTela();
    try {
      Conta conta = controlador.buscarConta(numeroConta);
      System.out.printf("Data     Histórico                 Valor           T\n");
      System.out.printf("======== ========================= =============== =\n");

      List<ExtratoItem> extrato = conta.getExtratoConta().getExtrato();
      int size = extrato.size() - 1;
      for(ExtratoItem item : extrato){
        System.out.printf("%8s ",item.getData());
        System.out.printf("%-25s ",item.getHistorico());
        System.out.printf("R$%13.2f ",item.getValor());
        System.out.printf("%s\n",item.getTipo());
      }
      System.out.printf("%8s ","");
      System.out.printf("%-25s ","Saldo atual");
      System.out.printf("R$%13.2f ",extrato.get(size).getSaldoPosterior());
      System.out.printf("%s\n","");

      
    } catch (ContaNaoCadastradaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void listarContas() {
    limpaTela();
    List<Conta> contas = controlador.getAllContas();
    System.out.printf("Conta  T Titular              Saldo           Disponível\n");
    System.out.printf("====== = ==================== =============== ===============\n");
    
    for (Conta conta : contas) {
      System.out.printf("%6s ", conta.getNumero());
      System.out.printf("%1s ", conta.getTipo().substring(0, 1));
      System.out.printf("%-20s ", conta.getTitular().getNome());
      System.out.printf("R$ %12.2f ", conta.getSaldo());
      if (conta instanceof ContaCorrente) {
        System.out.printf("R$ %12.2f", conta.getDisponivelParaSaque());
      }
      System.out.println();
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();  
  }

  public static void renderJuros() {
    limpaTela();
    List<Conta> listaContas = controlador.getAllContas();
    System.out.printf("Conta  T Titular              Saldo Anterior  Saldo Atual\n");
    System.out.printf("====== = ==================== =============== ===============\n");
    for(Conta conta: listaContas) {
      if (conta instanceof ContaPoupanca) {
        System.out.printf("%6s ", conta.getNumero());
        System.out.printf("%1s ", conta.getTipo().substring(0, 1));
        System.out.printf("%-20s ", conta.getTitular().getNome());
        System.out.printf("R$ %12.2f ", conta.getSaldo());
        ((ContaPoupanca)conta).renderJuros(0.05);
        System.out.printf("R$ %12.2f\n", conta.getSaldo());
      }
    }
    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();  
  }

  public static void renderBonus() {
    limpaTela();
    List<Conta> listaContas = controlador.getAllContas();
    System.out.printf("Conta  T Titular              Saldo Anterior  Saldo Atual\n");
    System.out.printf("====== = ==================== =============== ===============\n");
    for(Conta conta: listaContas) {
      if (conta instanceof ContaEspecial) {
        System.out.printf("%6s ", conta.getNumero());
        System.out.printf("%1s ", conta.getTipo().substring(0, 1));
        System.out.printf("%-20s ", conta.getTitular().getNome());
        System.out.printf("R$ %12.2f ", conta.getSaldo());
        ((ContaEspecial)conta).renderBonus();
        System.out.printf("R$ %12.2f\n", conta.getSaldo());
      }
    }
    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();  
  }
}