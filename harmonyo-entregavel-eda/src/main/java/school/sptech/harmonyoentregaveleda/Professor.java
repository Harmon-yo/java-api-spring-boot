package school.sptech.harmonyoentregaveleda;

import java.time.LocalDate;
import java.util.Date;

public class Professor extends Usuario{

    public Professor(Integer id, String nome, String sobrenome,
                     String cpf, LocalDate dataNasc, String sexo,
                     String email, String senha, String telefone) {
        super(nome, sobrenome, cpf, dataNasc, sexo, email, senha, telefone);
    }

    @Override
    public Boolean validarIdade() {
        return super.calcularIdade() >= 18;
    }


    public void adicionarInstrumento(String nome, String nivelConhecimento, String descricaoNaipe) {
        /*
        Adicionar emprestaInstrumento.
        */
        Instrumento novoInstrumento = new InstrumentoProfessor(nome, nivelConhecimento, descricaoNaipe, false);
        super.getInstrumentos().add(novoInstrumento);
    }
}
