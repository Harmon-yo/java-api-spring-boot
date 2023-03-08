package school.sptech.harmonyoentregaveleda;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Aluno extends Usuario {

    public Aluno(Integer id, String nome, String sobrenome,
                 String cpf, LocalDate dataNasc, String sexo,
                 String email, String senha, String telefone) {
        super(id, nome, sobrenome, cpf, dataNasc, sexo, email, senha, telefone);
    }

    /*public Aluno(Usuario usuario) {
        super(usuario.getId(), usuario.getNome(), usuario.getSobrenome(),
                usuario.getCpf(), usuario.getDataNasc(), usuario.getSexo(),
                usuario.getEmail(), usuario.getSenha(), usuario.getTelefone(), usuario.getEndereco());
    }*/

    @Override
    public Boolean validarIdade() {
        return super.calcularIdade() >= 15;
    }
}
