package school.sptech.harmonyospringapi.service.fila_de_espera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.FilaEspera;
import school.sptech.harmonyospringapi.domain.FilaEsperaKey;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.repository.AulaRepository;
import school.sptech.harmonyospringapi.repository.FilaEsperaRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.exceptions.FilaVaziaException;
import school.sptech.harmonyospringapi.service.fila_de_espera.dto.AlunoFilaDeEsperaDTO;
import school.sptech.harmonyospringapi.utils.FilaObj;

import java.util.List;

@Service
public class AlunoFilaDeEsperaService {


    @Autowired
    private FilaEsperaRepository repository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private AulaRepository aulaRepository;

    public FilaObj<AlunoFilaDeEsperaDTO> getFilaEsperaAluno(int idProfessor){
        List<FilaEspera> filaEspera = repository.findAllByIdProfessor(idProfessor);
        FilaObj<AlunoFilaDeEsperaDTO>  filaAluno1 = new FilaObj<AlunoFilaDeEsperaDTO>(filaEspera.size());
        for(FilaEspera f : filaEspera){
            AlunoFilaDeEsperaDTO aluno = new AlunoFilaDeEsperaDTO();
            aluno.setAlunoFila(f.getId().getAluno().getNome());
            aluno.setAlunoInstrumento(f.getId().getAula().getInstrumento().getNome());
            aluno.setIdAlunoFila(f.getId().getAluno().getId());
            filaAluno1.insert(aluno);
        }
        return filaAluno1;

    }

    public AlunoFilaDeEsperaDTO pollAluno(int idProfessor){
        List<FilaEspera> filaEsperaBanco = repository.findAllByIdProfessor(idProfessor);
        FilaObj<AlunoFilaDeEsperaDTO> filaAluno1 = new FilaObj<AlunoFilaDeEsperaDTO>(filaEsperaBanco.size());

        if(filaEsperaBanco.isEmpty() ){
            throw new FilaVaziaException("Fila vazia");
        }

        for(FilaEspera f : filaEsperaBanco){
            AlunoFilaDeEsperaDTO aluno = new AlunoFilaDeEsperaDTO();
            aluno.setAlunoFila(f.getId().getAluno().getNome());
            aluno.setAlunoInstrumento(f.getId().getAula().getInstrumento().getNome());
            aluno.setIdAlunoFila(f.getId().getAluno().getId());
            filaAluno1.insert(aluno);
        }


        AlunoFilaDeEsperaDTO aluno = filaAluno1.poll();
        FilaEspera filaEspera = repository.findByIdAlunoIdAndProfessorId(aluno.getIdAlunoFila(), idProfessor);
        repository.delete(filaEspera);
        return aluno;
    }

    public AlunoFilaDeEsperaDTO addAlunoFilaEspera(int idAluno, int idAula){
        FilaEspera filaEspera = new FilaEspera();
        FilaEsperaKey filaEsperaKey = new FilaEsperaKey();
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Aluno não encontrado")
        );
        Aula aula = aulaRepository.findById(idAula).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Aula não encontrada")
        );
        filaEsperaKey.setAluno(aluno);
        filaEsperaKey.setAula(aula);
        filaEspera.setId(filaEsperaKey);
        repository.save(filaEspera);

        AlunoFilaDeEsperaDTO alunoFilaDeEsperaDTO = new AlunoFilaDeEsperaDTO();
        alunoFilaDeEsperaDTO.setAlunoFila(aluno.getNome());
        alunoFilaDeEsperaDTO.setAlunoInstrumento(aula.getInstrumento().getNome());
        alunoFilaDeEsperaDTO.setIdAlunoFila(aluno.getId());

        return alunoFilaDeEsperaDTO;
    }
}
