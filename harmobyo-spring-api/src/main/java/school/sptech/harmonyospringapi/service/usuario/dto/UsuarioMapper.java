package school.sptech.harmonyospringapi.service.usuario.dto;

import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Experiencia;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.experiencia.ExperienciaResumidaDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioMapper {

    public static Aluno ofAlunoCriacao(UsuarioCriacaoDto usuarioCriacaoDto){

        Aluno novoAluno = new Aluno();

        novoAluno.setNome(usuarioCriacaoDto.getNome());

        novoAluno.setEmail(usuarioCriacaoDto.getEmail());

        novoAluno.setCpf(usuarioCriacaoDto.getCpf());

        novoAluno.setSexo(usuarioCriacaoDto.getSexo());

        novoAluno.setSenha(usuarioCriacaoDto.getSenha()) ;

        novoAluno.setEndereco(usuarioCriacaoDto.getEndereco());

        novoAluno.setAtivo(true);

        novoAluno.setUltimaVezOnline(LocalDateTime.now());

        return novoAluno;
    }

    public static Professor ofProfessorCriacao(UsuarioCriacaoDto usuarioCriacaoDto){

        Professor novoProfessor = new Professor();

        novoProfessor.setNome(usuarioCriacaoDto.getNome());

        novoProfessor.setEmail(usuarioCriacaoDto.getEmail());

        novoProfessor.setCpf(usuarioCriacaoDto.getCpf());

        novoProfessor.setSexo(usuarioCriacaoDto.getSexo());

        novoProfessor.setSenha(usuarioCriacaoDto.getSenha()) ;

        novoProfessor.setEndereco(usuarioCriacaoDto.getEndereco());

        novoProfessor.setAtivo(true);

        novoProfessor.setUltimaVezOnline(LocalDateTime.now());

        return novoProfessor;
    }

    public static UsuarioExibicaoDto ofUsuarioExibicao(Usuario usuario){

        String categoria = usuario instanceof Aluno ? "Aluno" : "Professor";

        UsuarioExibicaoDto usuarioExibicaoDto = new UsuarioExibicaoDto();

        usuarioExibicaoDto.setId(usuario.getId());

        usuarioExibicaoDto.setNome(usuario.getNome());

        usuarioExibicaoDto.setEmail(usuario.getEmail());

        usuarioExibicaoDto.setCategoria(categoria);

        usuarioExibicaoDto.setAtivo(usuario.isAtivo());

        usuarioExibicaoDto.setAutenticado(usuario.isAutenticado());

        usuarioExibicaoDto.setEndereco(usuario.getEndereco());

        usuarioExibicaoDto.setUltimaVezOnline(usuario.getUltimaVezOnline());

        return usuarioExibicaoDto;

    }

    public static UsuarioTokenDto of(Usuario usuario, String token){

        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
    
        usuarioTokenDto.setEmail(usuario.getEmail());

        usuarioTokenDto.setNome(usuario.getNome());

        usuarioTokenDto.setCategoria(usuario.getCategoria());

        usuarioTokenDto.setToken(token);


        return usuarioTokenDto;
    }

    public static UsuarioDadosPerfilDto ofDadosPerfilUsuario(Usuario usuario, List<ExperienciaResumidaDto> experiencias, Double mediaAvaliacao, String categoriaUsuario){

        UsuarioDadosPerfilDto usuarioDadosPerfilDto = new UsuarioDadosPerfilDto();

        usuarioDadosPerfilDto.setId(usuario.getId());
        usuarioDadosPerfilDto.setNome(usuario.getNome());
        usuarioDadosPerfilDto.setEmail(usuario.getEmail());
        usuarioDadosPerfilDto.setAvaliacaoMedia(mediaAvaliacao);
        usuarioDadosPerfilDto.setCpf(usuario.getCpf());
        usuarioDadosPerfilDto.setSexo(usuario.getSexo());
        usuarioDadosPerfilDto.setDataNasc(usuario.getDataNasc());
        usuarioDadosPerfilDto.setBibliografia(usuario.getBibliografia());
        usuarioDadosPerfilDto.setEndereco(usuario.getEndereco());
        usuarioDadosPerfilDto.setExperiencia(experiencias);
        usuarioDadosPerfilDto.setCategoriaUsuario(categoriaUsuario);
        return usuarioDadosPerfilDto;
    }
}
