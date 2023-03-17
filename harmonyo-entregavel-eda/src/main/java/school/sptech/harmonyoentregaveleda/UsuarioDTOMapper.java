package school.sptech.harmonyoentregaveleda;

public class UsuarioDTOMapper {
    public static UsuarioDTO mapearUsuario(Usuario usuario) {
        UsuarioDTO usuarioRestrito = new UsuarioDTO();

        usuarioRestrito.setNome(usuario.getNome());
        usuarioRestrito.setSobrenome(usuario.getSobrenome());
        usuarioRestrito.setDataNasc(usuario.getDataNasc());
        usuarioRestrito.setSexo(usuario.getSexo());
        usuarioRestrito.setEstadoConta(usuario.getEstadoConta());
        usuarioRestrito.setInstrumentos(usuario.getInstrumentos());

        return usuarioRestrito;
    }
}
