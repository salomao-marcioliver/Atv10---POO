package repository.cliente;

public class ClienteNaoCadastradoException extends Exception {

    public ClienteNaoCadastradoException() {
        super("Cliente não cadastrado");
    }
    
    
}
