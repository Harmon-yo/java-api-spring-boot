package school.sptech.harmonyospringapi.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.aula.AulaService;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.utils.ListaGenericaObj;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;
import school.sptech.harmonyospringapi.utils.PilhaObj;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    public static final int MAX_AULAS = 10;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AulaService aulaService;



    public UsuarioExibicaoDto cadastrar(UsuarioCriacaoDto novoAlunoDto) {
        if (this.usuarioService.existeUsuarioPorEmail((novoAlunoDto.getEmail()))) throw new EntidadeConflitanteException("Erro ao cadastrar. Email já cadastrado !");
        else if (this.usuarioService.existeUsuarioPorCpf(novoAlunoDto.getCpf())) throw new EntidadeConflitanteException("Erro ao cadastrar. CPF já cadastrado !");


        String senhaCriptofrada = passwordEncoder.encode(novoAlunoDto.getSenha());
        novoAlunoDto.setSenha(senhaCriptofrada);

        final Aluno novoAluno = UsuarioMapper.ofAlunoCriacao(novoAlunoDto);
        Aluno alunoCadastrado = this.alunoRepository.save(novoAluno);

        return UsuarioMapper.ofUsuarioExibicao(alunoCadastrado);
    }

    public List<UsuarioExibicaoDto> obterTodos() {

        List<Aluno> ltAlunos = this.alunoRepository.findAll();

        return ltAlunos.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public UsuarioExibicaoDto buscarPorId(Integer id) {

        Optional<Aluno> alunoOpt = this.alunoRepository.findById(id);


        if (alunoOpt.isEmpty()) {
            throw new EntitadeNaoEncontradaException(
                    String.format(
                            "Aluno com o id %d não encontrado !",
                            id
                    ));
        }

        return UsuarioMapper.ofUsuarioExibicao(alunoOpt.get());
    }

    public UsuarioExibicaoDto buscarPorNome(String nome) {

        List<Aluno> ltAlunos = this.alunoRepository.findAll();

        ListaGenericaObj<Usuario> ltAlunosGenerica = new ListaGenericaObj<>(ltAlunos.size());


        ltAlunos.forEach(ltAlunosGenerica::adiciona);


        ltAlunosGenerica = new UsuarioComparador(ltAlunosGenerica).ordenacaoAlfabetica();


        int indiceUsuarioEncontrado = new UsuarioComparador(ltAlunosGenerica).pesquisaBinariaPorNome(nome);


        if (indiceUsuarioEncontrado == -1) {
            throw new EntitadeNaoEncontradaException("Aluno com o nome " + nome + " não encontrado !");
        }

        return UsuarioMapper.ofUsuarioExibicao(ltAlunosGenerica.getElemento(indiceUsuarioEncontrado));

    }

    public Aluno obterAlunoPorId(Integer id) {

        Optional<Aluno> alunoOpt = this.alunoRepository.findById(id);

        if (alunoOpt.isEmpty()) throw new EntitadeNaoEncontradaException(
                String.format(
                        "Aluno com o id %d não encontrado !",
                        id
                ));

        return alunoOpt.get();
    }


    public List<UsuarioExibicaoDto> obterTodosEmOrdemAlfabetica() {

        List<Aluno> ltAlunos = this.alunoRepository.findAll();

        ListaGenericaObj<Usuario> ltAlunosGenerica = new ListaGenericaObj<>(ltAlunos.size());

        ltAlunos.forEach(ltAlunosGenerica::adiciona);

        ltAlunosGenerica = new UsuarioComparador(ltAlunosGenerica).ordenacaoAlfabetica();

        ltAlunos.clear();

        for (int i = 0; i < ltAlunosGenerica.size(); i++) {
            ltAlunos.add((Aluno) ltAlunosGenerica.getElemento(i));
        }

        return ltAlunos.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public void deletarPorId(Integer id){

        if (this.alunoRepository.existsById(id)){
            this.alunoRepository.deleteById(id);
        }
        else {
            throw new EntitadeNaoEncontradaException(
                    String.format("Aluno com o id %d não encontrado !", id)
            );
        }
    }


}
