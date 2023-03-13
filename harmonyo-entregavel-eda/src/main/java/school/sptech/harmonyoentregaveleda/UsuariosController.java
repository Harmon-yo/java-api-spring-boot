package school.sptech.harmonyoentregaveleda;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private List<Usuario> ltUsuarios;

    public UsuariosController() {
        this.ltUsuarios = new ArrayList<>();
    }

    @GetMapping()
    public List<UsuarioDTO> exibirUsuarios(){
        return this.ltUsuarios.stream()
            .map(UsuarioDTO::new)
            .collect(Collectors.toList());}

    @PostMapping("/cadastro/professor")
    public String cadastrarProfessor(@RequestBody Professor professor){
        if (professor.validarIdade()) {

            if (this.validarEmailCadastrado(professor.getEmail())) {
                ltUsuarios.add(professor);

                return "Professor Cadastrado com Sucesso!";
            }

            return "Email já cadastrado.";
        }

        return "Professor não cadastrado. Motivo: Menor de 18 anos!";
    }

    @PostMapping("/cadastro/aluno")
    public String cadastrarAluno(@RequestBody Aluno aluno){

        if (aluno.validarIdade()) {
            if (this.validarEmailCadastrado(aluno.getEmail())) {
                ltUsuarios.add(aluno);

                return "Aluno Cadastrado com Sucesso!";
            }

            return "Email já cadastrado.";
        }

        return "Aluno não cadastrado. Motivo: Menor de 15 anos !";
    }

    @PostMapping("/autenticacao/aluno")
    public String autenticarAluno(@RequestBody Aluno aluno) {
        Optional<Usuario> usuarioOptional = procurarUsuarioCadastrado(aluno.getEmail(),
                                                                          aluno.getSenha());
        if (usuarioOptional.isPresent()) {
            Aluno alunoCadastrado = (Aluno) usuarioOptional.get();
            alunoCadastrado.autenticarConta();

            return String.format("Aluno %s Autenticado com sucesso.", alunoCadastrado.getNome());
        }

        return "Usuário não encontrado";
    }

    @GetMapping("/desautenticacao/aluno/{email}")
    public String deslogarAluno(@PathVariable String email) {
        Optional<Usuario> usuarioOptional = procurarUsuarioCadastrado(email);

        if (usuarioOptional.isPresent()) {
            Aluno alunoCadastrado = (Aluno) usuarioOptional.get();
            alunoCadastrado.desativarConta();

            return "Deslogado com sucesso.";
        }

        return "Usuario Não encontrado.";
    }

    @DeleteMapping("/desativacao/aluno")
    public String desativarAluno(@RequestBody Aluno aluno) {
        Optional<Usuario> usuarioOptional = procurarUsuarioCadastrado(aluno.getEmail(),
                                                                      aluno.getSenha());

        if (usuarioOptional.isPresent()) {
            Aluno alunoCadastrado = (Aluno) usuarioOptional.get();
            alunoCadastrado.desativarConta();

            return String.format("Conta com o email %s desativada com sucesso.", alunoCadastrado.getEmail());
        }

        return "Usuario Não encontrado.";
    }

    @PatchMapping("/edicao-email/{email}")
    public String editarEmailAluno(@PathVariable String email, @RequestBody Aluno aluno) {
        Optional<Usuario> usuarioOptional = procurarUsuarioCadastrado(email);

        if (usuarioOptional.isPresent()) {
            Aluno alunoEncontrado = (Aluno) usuarioOptional.get();

            if (alunoEncontrado.getSenha().equals(aluno.getSenha())) {

                if (!alunoEncontrado.getEmail().equals(aluno.getEmail())) {
                    alunoEncontrado.setEmail(aluno.getEmail());
                    return "Email alterado.";
                }

                return "Email semelhante.";
            }

            return "Senha inválida.";

        }

        return "usuário não encontrado";
    }

    public boolean validarEmailCadastrado(String email) {
        return this.ltUsuarios.stream()
                .noneMatch(usuarioCadastrado -> usuarioCadastrado.getEmail().equals(email));
    }

    public Optional<Usuario> procurarUsuarioCadastrado(String email) {
        return this.ltUsuarios.stream()
                .filter(usuarioCadastrado -> usuarioCadastrado.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Usuario> procurarUsuarioCadastrado(String email, String senha) {
        return this.ltUsuarios.stream()
                .filter(usuarioCadastrado -> usuarioCadastrado.getEmail().equals(email) &&
                        usuarioCadastrado.getSenha().equals(senha))
                .findFirst();
    }
}
