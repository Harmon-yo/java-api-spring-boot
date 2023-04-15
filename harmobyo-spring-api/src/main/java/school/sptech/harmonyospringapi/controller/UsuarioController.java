package school.sptech.harmonyospringapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<UsuarioExibicaoDto>> exibirCadastrados(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibirCadastrados();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibirAlunos(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibirAlunos();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @GetMapping("/professores")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibirProfessores(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibirProfessores();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @PostMapping("/cadastro/aluno")
    public ResponseEntity<UsuarioExibicaoDto> cadastrarAluno(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto){

        UsuarioExibicaoDto usuarioCadastrado = this.usuarioService.cadastrarAluno(usuarioCriacaoDto);

        return ResponseEntity.status(201).body(usuarioCadastrado);
    }

    @PostMapping("/cadastro/professor")
    public ResponseEntity<UsuarioExibicaoDto> cadastrarProfessor(@RequestBody  @Valid UsuarioCriacaoDto usuarioCriacaoDto){

        UsuarioExibicaoDto usuarioCadastrado = this.usuarioService.cadastrarProfessor(usuarioCriacaoDto);

        return ResponseEntity.status(201).body(usuarioCadastrado);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){

        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @GetMapping("/busca/professor")
    public ResponseEntity<UsuarioExibicaoDto> buscarProfessorPorNome(@RequestParam String nome){

        UsuarioExibicaoDto professorEncontrado = this.usuarioService.buscarProfessorPorNome(nome);

        return ResponseEntity.status(200).body(professorEncontrado);
    }

    @GetMapping("/busca/aluno")
    public ResponseEntity<UsuarioExibicaoDto> buscarAlunoPorNome(@RequestParam String nome){

        UsuarioExibicaoDto alunoEncontrado = this.usuarioService.buscarAlunoPorNome(nome);

        return ResponseEntity.status(200).body(alunoEncontrado);
    }

    @GetMapping("/professores/alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeProfessoresOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibeProfessoresOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @GetMapping("/alunos/alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeAlunosOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibeAlunosOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @GetMapping("/alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeTodosOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibeTodosOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }



}
