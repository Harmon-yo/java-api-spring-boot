package school.sptech.harmonyoentregaveleda;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Aluno extends Usuario {

    public Aluno(Integer id, String nome, String sobrenome,
                 String cpf, LocalDate dataNasc, String sexo,
                 String email, String senha, String telefone) {
        super(nome, sobrenome, cpf, dataNasc, sexo, email, senha, telefone);
    }

    @Override
    public Boolean validarIdade() {
        return super.calcularIdade() >= 15;
    }

    public void adicionarInstrumento(String nome, String nivelConhecimento, String descricaoNaipe) {
        /*
        Adicionar emprestaInstrumento.
        */
        Instrumento novoInstrumento = new InstrumentoProfessor(nome, nivelConhecimento, descricaoNaipe, false);
        super.getInstrumentos().add(novoInstrumento);
    }
}
