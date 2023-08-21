package school.sptech.harmonyospringapi.service.experiencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Experiencia;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.repository.ExperienciaRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;

import java.util.Optional;

@Service
public class ExperienciaService {

    @Autowired
    private ExperienciaRepository experienciaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public void atualizarExperienciaPorId(int id, String titulo, String descricao){
        System.out.println("Crachei aq na service antes do if");
        if (experienciaRepository.existsById(id)){
            System.out.println("Crachei aq");
           experienciaRepository.atualizarExperienciaPorId(id, titulo, descricao);
        }
        else{
            throw new EntitadeNaoEncontradaException("ID de Experiência Inválido!");
        }
    }

    public void cadastrarExp(ExperienciaCriacaoDto novaExperiencia){

        Optional<Professor> professorEncontrado = this.professorRepository.findById(novaExperiencia.getIdProfessor());

        if (professorEncontrado.isPresent()){

           Professor professor  = professorEncontrado.get();

            Experiencia experiencia = ExperienciaMapper.of(novaExperiencia, professor);

            this.experienciaRepository.save(experiencia);
        }
        else{
            throw new EntitadeNaoEncontradaException("ID de Professor Inválido!");
        }
    }

    public void deletarExperienciaPorId(int id){

        if (this.experienciaRepository.existsById(id)){
            this.experienciaRepository.deleteById(id);
        }
        else{
            throw new EntitadeNaoEncontradaException("ID de Experiência Inválido!");
        }
    }

}
