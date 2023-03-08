package school.sptech.harmonyoentregaveleda;

import java.util.Date;

public class Professor extends Usuario{

    public Professor(Integer id, String nome, String sobrenome, String cpf, Date dataNasc, String sexo, String email, String senha, String telefone, Integer fkEndereco) {
        super(id, nome, sobrenome, cpf, dataNasc, sexo, email, senha, telefone, fkEndereco);
    }

    public Professor(Usuario usuario) {
        super(usuario.getId(), usuario.getNome(), usuario.getSobrenome(),
                usuario.getCpf(), usuario.getDataNasc(), usuario.getSexo(),
                usuario.getEmail(), usuario.getSenha(), usuario.getTelefone(), usuario.getFkEndereco());
    }

//    public Professor(Usuario usuario) {
//        super.setId(usuario.getId());
//        super.setNome(usuario.getNome());
//        super.setSobrenome(usuario.getSobrenome());
//        super.setCpf(usuario.getCpf());
//        super.setDataNasc(usuario.getDataNasc());
//        super.setSexo(usuario.getSexo());
//        super.setEmail(usuario.getEmail());
//        super.setSenha(usuario.getSenha());
//        super.setTelefone(usuario.getTelefone());
//        super.setFkEndereco(usuario.getFkEndereco());
//    }

    @Override
    public Boolean validarIdade() {
        return super.getIdade() >= 18;
    }
}
