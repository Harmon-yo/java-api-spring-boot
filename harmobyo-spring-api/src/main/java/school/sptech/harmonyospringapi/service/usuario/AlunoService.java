package school.sptech.harmonyospringapi.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.aula.AulaService;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.aluno_instrumento.AlunoInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.aluno_instrumento.AlunoInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.aluno_instrumento.AlunoInstrumentoMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.professor.ProfessorExibicaoResumidoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor.ProfessorMapper;
import school.sptech.harmonyospringapi.utils.ListaGenericaObj;
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
    private ProfessorRepository professorRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private AlunoInstrumentoRepository alunoInstrumentoRepository;

    @Autowired
    private InstrumentoService instrumentoService;

    /* ============= ALUNO ================ */

    public UsuarioExibicaoDto cadastrar(UsuarioCriacaoDto novoAlunoDto) {
        if (this.usuarioService.existeUsuarioPorEmail((novoAlunoDto.getEmail()))) throw new EntidadeConflitanteException("Erro ao cadastrar. Email já cadastrado !");
        else if (this.usuarioService.existeUsuarioPorCpf(novoAlunoDto.getCpf())) throw new EntidadeConflitanteException("Erro ao cadastrar. CPF já cadastrado !");


        String senhaCriptofrada = passwordEncoder.encode(novoAlunoDto.getSenha());
        novoAlunoDto.setSenha(senhaCriptofrada);

        final Aluno novoAluno = UsuarioMapper.ofAlunoCriacao(novoAlunoDto);
        Aluno alunoCadastrado = this.alunoRepository.save(novoAluno);

        return UsuarioMapper.ofUsuarioExibicao(alunoCadastrado);
    }

    public List<UsuarioExibicaoDto> listar() {

        List<Aluno> ltAlunos = this.alunoRepository.findAll();

        return ltAlunos.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    /* ============= PESQUISA ================ */

    public UsuarioExibicaoDto buscarPorIdParaExibicao(Integer id) {

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

    public Aluno buscarPorId(Integer id) {

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

    /* ============= DELETAR ================ */

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

    /* ============= INSTRUMENTOS ================ */

    public AlunoInstrumentoExibicaoDto adicionarInstrumento(Integer alunoId, AlunoInstrumentoCriacaoDto alunoInstrumentoCriacaoDto) {
        Integer instrumentoId = alunoInstrumentoCriacaoDto.getInstrumentoId();

        Aluno aluno = buscarPorId(alunoId);
        Instrumento instrumento = this.instrumentoService.buscarPorId(instrumentoId);

        AlunoInstrumento alunoInstrumento = this.alunoInstrumentoRepository
                .save(AlunoInstrumentoMapper.of(alunoInstrumentoCriacaoDto, aluno, instrumento));

        return AlunoInstrumentoMapper.ofAlunoInstrumentoExibicao(alunoInstrumento);
    }

    public List<InstrumentoExibicaoDto> listarInstrumentos(Integer alunoId) {

        List<Instrumento> instrumentos = this.alunoInstrumentoRepository.listarInstrumentosPeloIdDoAluno(alunoId);

        if (instrumentos.isEmpty()) throw new EntitadeNaoEncontradaException(String.format("Aluno com o id %d não encontrado !", alunoId));

        return instrumentos.stream().map(InstrumentoMapper::ofInstrumentoExibicao).toList();
    }

    /* ============= ENCONTRAR PROFESSORES ================ */
   public List<ProfessorExibicaoResumidoDto> listarProfessores(){
        List<Professor> professores = this.professorRepository.findTop50ByOrderByAvaliacaoDesc();

        if (professores.isEmpty()) throw new EntitadeNaoEncontradaException("Nenhum professor encontrado !");

        return professores.stream().map(p -> ProfessorMapper.of(p)).toList();
    }
}