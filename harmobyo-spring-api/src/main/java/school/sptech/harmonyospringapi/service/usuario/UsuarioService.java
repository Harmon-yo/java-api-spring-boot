package school.sptech.harmonyospringapi.service.usuario;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.harmonyospringapi.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.lista.ListaGenericaObj;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoExibicaoDto;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoMapper;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UsuarioExibicaoDto cadastrarAluno(UsuarioCriacaoDto usuarioCriacaoDto){

        String senhaCriptofrada = passwordEncoder.encode(usuarioCriacaoDto.getSenha());

        usuarioCriacaoDto.setSenha(senhaCriptofrada);

        usuarioCriacaoDto.setCategoria("Aluno");

        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);

        this.usuarioRepository.save(novoUsuario);

        return UsuarioMapper.ofUsuarioExibicao(novoUsuario);
    }

    public UsuarioExibicaoDto cadastrarProfessor(UsuarioCriacaoDto usuarioCriacaoDto){

        String senhaCriptofrada = passwordEncoder.encode(usuarioCriacaoDto.getSenha());

        usuarioCriacaoDto.setSenha(senhaCriptofrada);

        usuarioCriacaoDto.setCategoria("Professor");

        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);

        this.usuarioRepository.save(novoUsuario);

        return UsuarioMapper.ofUsuarioExibicao(novoUsuario);
    }

    public List<UsuarioExibicaoDto> exibirCadastrados(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findAll();

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public List<UsuarioExibicaoDto> exibirAlunos(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findByCategoriaEquals("Aluno");

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public List<UsuarioExibicaoDto> exibirProfessores(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findByCategoriaEquals("Professor");

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }


    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto){

        final UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(
                        usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha()
                );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                usuarioRepository.findByEmail(usuarioLoginDto.getEmail())
                                .orElseThrow(
                                        () -> new ResponseStatusException(404, "Email de usuário não cadastrado", null)
                                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado,token);

    }


    public UsuarioExibicaoDto buscarProfessorPorNome(String nome){

        List<Usuario> ltUsuarios = this.usuarioRepository.findByCategoriaEquals("Professor");

        ListaGenericaObj<Usuario> ltUsuariosGenerica = new ListaGenericaObj<>(ltUsuarios.size());


        ltUsuarios.forEach(ltUsuariosGenerica::adiciona);


        ltUsuariosGenerica = new UsuarioComparador(ltUsuariosGenerica).ordenacaoAlfabetica();


        int indiceUsuarioEncontrado = new UsuarioComparador(ltUsuariosGenerica).pesquisaBinariaPorNome(nome);


        if (indiceUsuarioEncontrado == -1){
            throw new EntitadeNaoEncontradaException("Professor com o nome " + nome + " não encontrado !");
        }

        return UsuarioMapper.ofUsuarioExibicao(ltUsuariosGenerica.getElemento(indiceUsuarioEncontrado));

    }

    public UsuarioExibicaoDto buscarAlunoPorNome(String nome){

        List<Usuario> ltUsuarios = this.usuarioRepository.findByCategoriaEquals("Aluno");

        ListaGenericaObj<Usuario> ltUsuariosGenerica = new ListaGenericaObj<>(ltUsuarios.size());


        ltUsuarios.forEach(ltUsuariosGenerica::adiciona);


        ltUsuariosGenerica = new UsuarioComparador(ltUsuariosGenerica).ordenacaoAlfabetica();


        int indiceUsuarioEncontrado = new UsuarioComparador(ltUsuariosGenerica).pesquisaBinariaPorNome(nome);


        if (indiceUsuarioEncontrado == -1){
            throw new EntitadeNaoEncontradaException("Aluno com o nome " + nome + " não encontrado !");
        }

        return UsuarioMapper.ofUsuarioExibicao(ltUsuariosGenerica.getElemento(indiceUsuarioEncontrado));

    }

    public List<UsuarioExibicaoDto> exibeProfessoresOrdemAlfabetica(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findByCategoriaEquals("Professor");

        ListaGenericaObj<Usuario> ltUsuariosGenerica = new ListaGenericaObj<>(ltUsuarios.size());

        ltUsuarios.forEach(ltUsuariosGenerica::adiciona);

        ltUsuariosGenerica = new UsuarioComparador(ltUsuariosGenerica).ordenacaoAlfabetica();

        ltUsuarios.clear();

        for (int i = 0; i < ltUsuariosGenerica.size(); i ++){
            ltUsuarios.add(ltUsuariosGenerica.getElemento(i));
        }

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public List<UsuarioExibicaoDto> exibeAlunosOrdemAlfabetica(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findByCategoriaEquals("Aluno");

        ListaGenericaObj<Usuario> ltUsuariosGenerica = new ListaGenericaObj<>(ltUsuarios.size());

        ltUsuarios.forEach(ltUsuariosGenerica::adiciona);

        ltUsuariosGenerica = new UsuarioComparador(ltUsuariosGenerica).ordenacaoAlfabetica();

        ltUsuarios.clear();

        for (int i = 0; i < ltUsuariosGenerica.size(); i ++){
            ltUsuarios.add(ltUsuariosGenerica.getElemento(i));
        }

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public List<UsuarioExibicaoDto> exibeTodosOrdemAlfabetica(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findAll();

        ListaGenericaObj<Usuario> ltUsuariosGenerica = new ListaGenericaObj<>(ltUsuarios.size());

        ltUsuarios.forEach(ltUsuariosGenerica::adiciona);

        ltUsuariosGenerica = new UsuarioComparador(ltUsuariosGenerica).ordenacaoAlfabetica();

        ltUsuarios.clear();

        for (int i = 0; i < ltUsuariosGenerica.size(); i ++){
            ltUsuarios.add(ltUsuariosGenerica.getElemento(i));
        }

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    public UsuarioExibicaoDto inserirEndereco(Integer idUsuario, Endereco endereco ){
        Endereco enderecoInserido = enderecoService.cadastrarEndereco(endereco);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );
        usuario.setEndereco(enderecoInserido);
        usuarioRepository.save(usuario);
        return UsuarioMapper.ofUsuarioExibicao(usuario);
    }
    public UsuarioExibicaoDto atualizarEndereco(Integer idUsuario, Endereco endereco ){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );

        endereco.setId(usuario.getEndereco().getId());
        Endereco enderecoInserido = enderecoService.atualizarEndereco(endereco);
        usuario.setEndereco(enderecoInserido);

        usuarioRepository.save(usuario);
        return UsuarioMapper.ofUsuarioExibicao(usuario);

    }

    public void deletarEndereco(Integer idUsuario ){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );
        usuario.setEndereco(null);
        usuarioRepository.save(usuario);
        Endereco endereco = usuario.getEndereco();
        enderecoService.deletarEndereco(endereco);
    }

    public EnderecoExibicaoDto buscarEndereco(Integer idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );
        return EnderecoMapper.of(usuario.getEndereco());
    }


}
