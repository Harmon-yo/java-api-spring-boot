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
        if (professor.validarIdade()) {
            ltUsuarios.add(professor);

            return "Professor Cadastrado com Sucesso !";
        }

        return "Professor não cadastrado. Motivo: Menor de 18 anos !";
    }

    @PostMapping("/cadastro/aluno")
    public String cadastrarAluno(@RequestBody Aluno aluno){

        if (aluno.validarIdade()) {
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

    @DeleteMapping("/desativacao/aluno")
    public void desativarAluno(@RequestBody Aluno aluno) {
        this.ltUsuarios.stream()
                .filter(usuario -> usuario.getEmail().equals(aluno.getEmail()) &&
                        usuario.getSenha().equals(aluno.getSenha()))
                .findFirst()
                .ifPresent(Usuario::desativarConta);
    }
}
