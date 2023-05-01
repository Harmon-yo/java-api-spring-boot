package school.sptech.harmonyospringapi.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.lista.ListaGenericaObj;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioExibicaoDto cadastrar(UsuarioCriacaoDto novoAlunoDto) {

        if (this.usuarioRepository.existsByEmail(novoAlunoDto.getEmail())) {

            throw new EntidadeConflitanteException("Erro ao cadastrar. Email já cadastrado !");

        } else if (this.usuarioRepository.existsByCpf(novoAlunoDto.getCpf())) {

            throw new EntidadeConflitanteException("Erro ao cadastrar. CPF já cadastrado !");

        } else {

            String senhaCriptofrada = passwordEncoder.encode(novoAlunoDto.getSenha());

            novoAlunoDto.setSenha(senhaCriptofrada);

            final Aluno novoAluno = UsuarioMapper.ofAlunoCriacao(novoAlunoDto);

            Aluno alunoCadastrado = this.alunoRepository.save(novoAluno);


            return UsuarioMapper.ofUsuarioExibicao(alunoCadastrado);
        }
    }

    public List<UsuarioExibicaoDto> exibirTodos() {

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

    public List<UsuarioExibicaoDto> exibeEmOrdemAlfabetica() {

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
