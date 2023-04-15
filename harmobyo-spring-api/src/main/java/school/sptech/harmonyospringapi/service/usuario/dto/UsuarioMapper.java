package school.sptech.harmonyospringapi.service.usuario.dto;

import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;

import java.time.LocalDateTime;

public class UsuarioMapper {

    public static Usuario of(UsuarioCriacaoDto usuarioCriacaoDto){

        Usuario usuario = new Usuario();

        usuario.setNome(usuarioCriacaoDto.getNome());

        usuario.setEmail(usuarioCriacaoDto.getEmail());

        usuario.setCpf(usuarioCriacaoDto.getCpf());

        usuario.setSexo(usuarioCriacaoDto.getSexo());

        usuario.setSenha(usuarioCriacaoDto.getSenha()) ;

        usuario.setCategoria(usuarioCriacaoDto.getCategoria());

        usuario.setAtivo(true);

        usuario.setUltimaVezOnline(LocalDateTime.now());

        return usuario;
    }

    public static UsuarioExibicaoDto ofUsuarioExibicao(Usuario usuario){

        UsuarioExibicaoDto usuarioExibicaoDto = new UsuarioExibicaoDto();

        usuarioExibicaoDto.setId(usuario.getId());

        usuarioExibicaoDto.setNome(usuario.getNome());

        usuarioExibicaoDto.setEmail(usuario.getEmail());

        usuarioExibicaoDto.setCategoria(usuario.getCategoria());

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

        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}
