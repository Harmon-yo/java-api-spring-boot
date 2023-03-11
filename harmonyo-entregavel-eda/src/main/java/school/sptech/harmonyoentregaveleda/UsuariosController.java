package school.sptech.harmonyoentregaveleda;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private List<Usuario> ltUsuarios;

    public UsuariosController() {
        this.ltUsuarios = new ArrayList<>();
    }

    @GetMapping()
    public List<Usuario> exibirUsuarios(){return ltUsuarios;}

    @PostMapping("/cadastro/professor")
    public String cadastrarProfessor(@RequestBody Professor professor){
        if (professor.validarIdade() && !this.validarEmail(professor.getEmail())) {
            ltUsuarios.add(professor);

            return "Professor Cadastrado com Sucesso !";
        }

        return "Professor não cadastrado. Motivo: Menor de 18 anos !";
    }

    @PostMapping("/cadastro/aluno")
    public String cadastrarAluno(@RequestBody Aluno aluno){

        if (aluno.validarIdade() && !this.validarEmail(aluno.getEmail())) {
            ltUsuarios.add(aluno);
            return "Aluno Cadastrado com Sucesso !";
        }

        return "Aluno não cadastrado. Motivo: Menor de 15 anos !";
    }

    @PostMapping("/autenticacao/aluno")
    public void autenticarAluno(@RequestBody Aluno aluno) {
         this.ltUsuarios.stream()
                 .filter(usuario -> usuario.getEmail().equals(aluno.getEmail()) &&
                                    usuario.getSenha().equals(aluno.getSenha()))
                 .findFirst()
                 .ifPresent(Usuario::autenticarConta);
    }

    @PostMapping("/desautenticacao/aluno/{email}")
    public void deslogarAluno(@PathVariable String email) {
        this.ltUsuarios.stream()
                .filter(usuario -> usuario.getEmail().equals(email))
                .findFirst()
                .ifPresent(Usuario::deslogarConta);
    }

    @DeleteMapping("/desativacao/aluno")
    public void desativarAluno(@RequestBody Aluno aluno) {
        this.ltUsuarios.stream()
                .filter(usuario -> usuario.getEmail().equals(aluno.getEmail()) &&
                        usuario.getSenha().equals(aluno.getSenha()))
                .findFirst()
                .ifPresent(Usuario::desativarConta);
    }

    public boolean validarEmail(String email) {
        return this.ltUsuarios.stream()
                .anyMatch(usuarioCadastrado -> usuarioCadastrado.getEmail().equals(email));
    }
}
