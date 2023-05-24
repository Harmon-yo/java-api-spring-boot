package school.sptech.harmonyospringapi.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor.ProfessorExibicaoResumidoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor.ProfessorMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.professor_instrumento.ProfessorInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor_instrumento.ProfessorInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor_instrumento.ProfessorInstrumentoMapper;
import school.sptech.harmonyospringapi.utils.ListaGenericaObj;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfessorInstrumentoRepository professorInstrumentoRepository;

    @Autowired
    private InstrumentoService instrumentoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;



    /* ================ PROFESSOR ================ */

    public UsuarioExibicaoDto cadastrar(UsuarioCriacaoDto novoProfessorDto){
        if (this.usuarioService.existeUsuarioPorEmail(novoProfessorDto.getEmail())) throw new EntidadeConflitanteException("Erro ao cadastrar. Email já cadastrado !");
        else if (this.usuarioService.existeUsuarioPorCpf(novoProfessorDto.getCpf())) throw new EntidadeConflitanteException("Erro ao cadastrar. CPF já cadastrado !");

        String senhaCriptofrada = passwordEncoder.encode(novoProfessorDto.getSenha());

        novoProfessorDto.setSenha(senhaCriptofrada);

        final Professor novoProfessor = UsuarioMapper.ofProfessorCriacao(novoProfessorDto);

        Professor professorCadastrado = this.professorRepository.save(novoProfessor);

        return UsuarioMapper.ofUsuarioExibicao(professorCadastrado);
    }

    public List<UsuarioExibicaoDto> listar(){

        List<Professor> ltProfessores = this.professorRepository.findAll();

        return ltProfessores.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public void encontrarProfessores(){

        List<Professor> professores = this.professorRepository.findAll();
        List<ProfessorExibicaoResumidoDto> professorExibicaoDto = new ArrayList<>();

        for (Professor professor: professores) {
           // double mediaAvaliacao = this.avaliacaoRepository.getMediaAvaliacaoProfessor(professor.getId());
          //  List<Instrumento> instrumentos = this.professorInstrumentoRepository.listarInstrumentosPeloIdDoProfessor(professor.getId());

        }

    }

    /* ================ AVALIAÇÃO ================ */

    public AvaliacaoExibicaoDto criarAvaliacao(AvaliacaoCriacaoDto avaliacaoCriacaoDto) {
        return null;
    }

    /* ================ PESQUISA ================ */

    public UsuarioExibicaoDto buscarPorIdParaExibicao(Integer id){
        Optional<Professor> professorOpt = this.professorRepository.findById(id);

        if (professorOpt.isEmpty()){
            throw new EntitadeNaoEncontradaException(
                    String.format(
                            "Professor com o id %d não encontrado !",
                            id
                    ));
        }

        return UsuarioMapper.ofUsuarioExibicao(professorOpt.get());
    }

    public UsuarioExibicaoDto buscarPorNome(String nome){

        List<Professor> ltProfessores = this.professorRepository.findAll();

        ListaGenericaObj<Usuario> ltProfessoresGenerica = new ListaGenericaObj<>(ltProfessores.size());


        ltProfessores.forEach(ltProfessoresGenerica::adiciona);


        ltProfessoresGenerica = new UsuarioComparador(ltProfessoresGenerica).ordenacaoAlfabetica();


        int indiceUsuarioEncontrado = new UsuarioComparador(ltProfessoresGenerica).pesquisaBinariaPorNome(nome);


        if (indiceUsuarioEncontrado == -1){
            throw new EntitadeNaoEncontradaException("Professor com o nome " + nome + " não encontrado !");
        }

        return UsuarioMapper.ofUsuarioExibicao(ltProfessoresGenerica.getElemento(indiceUsuarioEncontrado));

    }

    public Professor buscarPorId(Integer id){
        Optional<Professor> professorOpt = this.professorRepository.findById(id);

        if (professorOpt.isEmpty()){
            throw new EntitadeNaoEncontradaException(
                    String.format(
                            "Professor com o id %d não encontrado !",
                            id
                    ));
        }

        return professorOpt.get();
    }


    public List<UsuarioExibicaoDto> obterTodosEmOrdemAlfabetica(){

        List<Professor> ltProfessores = this.professorRepository.findAll();

        ListaGenericaObj<Usuario> ltProfessoresGenerica = new ListaGenericaObj<>(ltProfessores.size());

        ltProfessores.forEach(ltProfessoresGenerica::adiciona);

        ltProfessoresGenerica = new UsuarioComparador(ltProfessoresGenerica).ordenacaoAlfabetica();

        ltProfessores.clear();

        for (int i = 0; i < ltProfessoresGenerica.size(); i ++){
            ltProfessores.add((Professor)ltProfessoresGenerica.getElemento(i));
        }

        return ltProfessores.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public boolean existeProfessorPorId(Integer id){
        return this.professorRepository.existsById(id);
    }

    /* ================ REMOVER ================ */

    public void deletarPorId(Integer id){

        if (this.professorRepository.existsById(id)){
            this.professorRepository.deleteById(id);
        }
        else {
            throw new EntitadeNaoEncontradaException(
                    String.format("Professor com o id %d não encontrado !", id)
            );
        }
    }

    /* ================ INSTRUMENTOS ================ */

    public List<InstrumentoExibicaoDto> listarInstrumentos(int professorId) {
        List<Instrumento> instrumentos = this.professorInstrumentoRepository.listarInstrumentosPeloIdDoProfessor(professorId);

        if (instrumentos.isEmpty()) throw new EntitadeNaoEncontradaException("Professor não possui instrumentos cadastrados !");

        return instrumentos.stream().map(InstrumentoMapper::ofInstrumentoExibicao).toList();
    }

    public ProfessorInstrumentoExibicaoDto criar(Integer professorId, ProfessorInstrumentoCriacaoDto professorInstrumentoCriacaoDto) {
        Professor professor = buscarPorId(professorId);
        Instrumento instrumento = this.instrumentoService.buscarPorId(professorInstrumentoCriacaoDto.getInstrumentoId());

        ProfessorInstrumento professorInstrumentoCadastrado = this.professorInstrumentoRepository
                .save(ProfessorInstrumentoMapper.of(professorInstrumentoCriacaoDto, professor, instrumento));

        return ProfessorInstrumentoMapper.ofProfessorInstrumentoExibicao(professorInstrumentoCadastrado);
    }

    public Boolean emprestaInstrumento(Integer idProfessor){
        Optional<Boolean> emprestimo = this.professorRepository.emprestaInstrumento(idProfessor);
        if(emprestimo.isEmpty()) return false;
        return emprestimo.get();
    }

    /* =============== AULAS ================== */
    public Double getMenorValorAula (Integer professorId){
        Optional<Aula> aula = this.aulaRepository.findFirstByProfessorIdOrderByValorAulaAsc(professorId);

        if (aula.isEmpty()) throw new EntitadeNaoEncontradaException("Professor não possui aulas cadastradas !");

        return aula.get().getValorAula();
    }

    /* =============== AVALIAÇÃO ================== */

    public Double getMediaAvaliacao(Integer professorId){
        Double media = avaliacaoRepository.getMediaAvaliacaoProfessor(professorId).orElse(0d);
        return media;
    }

    public Integer getQuantidadeAvaliacoes(Integer id) {
        Integer quantidadeAvaliacoes = avaliacaoRepository.getQuantidadeAvaliacoes(id).orElse(0);

        return quantidadeAvaliacoes;
    }

    public List<ProfessorExibicaoResumidoDto> getProfessoresMelhoresAvaliados(){
        List<Professor> professoresMelhoresAvaliados = this.professorRepository.findTop50ByOrderByAvaliacaoDesc();

        if (professoresMelhoresAvaliados.isEmpty()) throw new EntitadeNaoEncontradaException("Nenhum professor encontrado !");
        List<ProfessorExibicaoResumidoDto> professoresExibicao = new ArrayList<>();
        for(Professor p : professoresMelhoresAvaliados){
            List<InstrumentoExibicaoDto> instrumentos = listarInstrumentos(p.getId());
            Double valorMinimo = getMenorValorAula(p.getId());
            Boolean emprestaInstrumento = emprestaInstrumento(p.getId());
            Double mediaAvaliacao = getMediaAvaliacao(p.getId());
            Integer quantidadeAvaliacao = getQuantidadeAvaliacoes(p.getId());
            ProfessorExibicaoResumidoDto professorExibicao = ProfessorMapper.of(p,
                    instrumentos,
                    valorMinimo,
                    emprestaInstrumento,
                    mediaAvaliacao,
                    quantidadeAvaliacao);
            professoresExibicao.add(professorExibicao);
        }
        return professoresExibicao;
    }


    public List<ProfessorExibicaoResumidoDto> getProfessoresComMaiorValorAula(){
        List<Professor> professoresMaiorValorAula = this.professorRepository.findTop50MaisCarosValorAula();

        if (professoresMaiorValorAula.isEmpty()) throw new EntitadeNaoEncontradaException("Nenhum professor encontrado !");
        List<ProfessorExibicaoResumidoDto> professoresExibicao = new ArrayList<>();
        for(Professor p : professoresMaiorValorAula){
            List<InstrumentoExibicaoDto> instrumentos = listarInstrumentos(p.getId());
            Double valorMinimo = getMenorValorAula(p.getId());
            Boolean emprestaInstrumento = emprestaInstrumento(p.getId());
            Double mediaAvaliacao = getMediaAvaliacao(p.getId());
            Integer quantidadeAvaliacao = getQuantidadeAvaliacoes(p.getId());
            ProfessorExibicaoResumidoDto professorExibicao = ProfessorMapper.of(p,
                    instrumentos,
                    valorMinimo,
                    emprestaInstrumento,
                    mediaAvaliacao,
                    quantidadeAvaliacao);
            professoresExibicao.add(professorExibicao);
        }
        return professoresExibicao;
    }

    public List<ProfessorExibicaoResumidoDto> getProfessoresComMenorValorAula(){
        List<Professor> professoresMenorValorAula = this.professorRepository.findTop50MaisBaratosValorAula();

        if (professoresMenorValorAula.isEmpty()) throw new EntitadeNaoEncontradaException("Nenhum professor encontrado !");
        List<ProfessorExibicaoResumidoDto> professoresExibicao = new ArrayList<>();
        for(Professor p : professoresMenorValorAula){
            List<InstrumentoExibicaoDto> instrumentos = listarInstrumentos(p.getId());
            Double valorMinimo = getMenorValorAula(p.getId());
            Boolean emprestaInstrumento = emprestaInstrumento(p.getId());
            Double mediaAvaliacao = getMediaAvaliacao(p.getId());
            Integer quantidadeAvaliacao = getQuantidadeAvaliacoes(p.getId());
            ProfessorExibicaoResumidoDto professorExibicao = ProfessorMapper.of(p,
                    instrumentos,
                    valorMinimo,
                    emprestaInstrumento,
                    mediaAvaliacao,
                    quantidadeAvaliacao);
            professoresExibicao.add(professorExibicao);
        }
        return professoresExibicao;
    }

    public List<ProfessorExibicaoResumidoDto> getProfessorByInstrumento(int idInstrumento){
        List<Professor> professoresPorInstrumento = this.professorRepository.getProfessoresByInstrumento(idInstrumento);

        if (professoresPorInstrumento.isEmpty()) throw new EntitadeNaoEncontradaException("Nenhum professor encontrado !");
        List<ProfessorExibicaoResumidoDto> professoresExibicao = new ArrayList<>();
        for(Professor p : professoresPorInstrumento){
            List<InstrumentoExibicaoDto> instrumentos = listarInstrumentos(p.getId());
            Double valorMinimo = getMenorValorAula(p.getId());
            Boolean emprestaInstrumento = emprestaInstrumento(p.getId());
            Double mediaAvaliacao = getMediaAvaliacao(p.getId());
            Integer quantidadeAvaliacao = getQuantidadeAvaliacoes(p.getId());
            ProfessorExibicaoResumidoDto professorExibicao = ProfessorMapper.of(p,
                    instrumentos,
                    valorMinimo,
                    emprestaInstrumento,
                    mediaAvaliacao,
                    quantidadeAvaliacao);
            professoresExibicao.add(professorExibicao);
        }
        return professoresExibicao;
    }

}
