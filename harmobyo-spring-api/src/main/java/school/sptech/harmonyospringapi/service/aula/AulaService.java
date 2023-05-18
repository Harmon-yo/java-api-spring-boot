package school.sptech.harmonyospringapi.service.aula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.lista.FilaObj;
import school.sptech.harmonyospringapi.lista.ListaGenericaObj;
import school.sptech.harmonyospringapi.repository.AulaRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.usuario.UsuarioComparador;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.List;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioExibicaoDto> setFilaDeEspera(int idProfessor, int idAula, int idAluno) {
        //TODO: Colocar o Id do professor, Id da aula e Id do aluno a ser add na fila
        //TODO: Adicionar uma AlunoFilaDeEsperaDTO contendo o nome do aluno e o instrumento desejado

//        FilaObj<Usuario> l = new FilaObj<>(listAulas.size());
//        return ltAlunos.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
        return null;
    }
}
