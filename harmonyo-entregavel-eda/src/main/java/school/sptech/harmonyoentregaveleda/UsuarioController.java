package school.sptech.harmonyoentregaveleda;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private List<Usuario> ltUsuarios;

    public UsuarioController() {
        this.ltUsuarios = new ArrayList<>();
    }

    @GetMapping()
    public List<Usuario> exibirUsuarios(){return ltUsuarios;}

    @GetMapping("/cadastro/{tipo}")
    public String cadastrar(@PathVariable String tipo, @RequestBody Usuario usuario){

        if (tipo.equalsIgnoreCase("aluno")){

            Aluno aluno = new Aluno(usuario);

            if (aluno.validarIdade()){
                ltUsuarios.add(aluno);

                return "Aluno Cadastrado com Sucesso !";
            }
            else {
                return "Aluno não cadastrado. Motivo: Menor de 15 anos !";
            }
        }
        else if (tipo.equalsIgnoreCase("professor")) {

            Professor professor = new Professor(usuario);

            if (professor.validarIdade()) {
                ltUsuarios.add(professor);

                return "Professor Cadastrado com Sucesso !";
            } else {
                return "Professor não cadastrado. Motivo: Menor de 18 anos !";
            }
        }

        return "Error Ao Cadastrar. Objeto Usuário Inválido !";
    }
}
