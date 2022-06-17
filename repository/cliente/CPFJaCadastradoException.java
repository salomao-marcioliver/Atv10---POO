package repository.cliente;

public class CPFJaCadastradoException extends Exception {

    public CPFJaCadastradoException() {
        super("CPF já cadastrado");
    }
    
    
}
