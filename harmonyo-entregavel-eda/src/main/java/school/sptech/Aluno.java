package school.sptech;

public class Aluno extends Usuario{

    public Aluno(Integer id, String nome, String sobrenome, String cpf, String sexo, String email, String senha, String telefone, Integer fkEndereco) {
        super(id, nome, sobrenome, cpf, sexo, email, senha, telefone, fkEndereco);
    }
}
