package school.sptech.harmobyospringapi.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.harmobyospringapi.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.harmobyospringapi.domain.Usuario;
import school.sptech.harmobyospringapi.repository.UsuarioRepository;
import school.sptech.harmobyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmobyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmobyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmobyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmobyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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


}
