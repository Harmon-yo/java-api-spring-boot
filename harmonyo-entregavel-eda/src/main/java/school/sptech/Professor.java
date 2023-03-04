package school.sptech;

public class Professor extends Usuario{

    public Professor(Integer id, String nome, String sobrenome, String cpf, String sexo, String email, String senha, String telefone, Integer fkEndereco) {
        super(id, nome, sobrenome, cpf, sexo, email, senha, telefone, fkEndereco);
    }
}
