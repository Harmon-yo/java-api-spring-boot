package school.sptech.harmonyoentregaveleda;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("1")
public class Professor extends Usuario{

    public Professor() {
    }

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
