package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Requisições relacionadas aos usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @Operation(summary = "Obtém uma lista de todos os usuários cadastrados", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há usuários cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioExibicaoDto>> listarCadastrados(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.listarCadastrados();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Obtém uma lista de todos os alunos cadastrados", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há alunos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/alunos")
    public ResponseEntity<List<UsuarioExibicaoDto>> listarAlunos(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.listarAlunos();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Obtém uma lista de todos os professores cadastrados", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há professores cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/professores")
    public ResponseEntity<List<UsuarioExibicaoDto>> listarProfessores(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.listarProfessores();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Cadastra um aluno", description = "")
    @ApiResponse(responseCode = "201", description = "Aluno cadastrado.")
    @PostMapping("/cadastro/aluno")
    public ResponseEntity<UsuarioExibicaoDto> cadastrarAluno(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto){

        UsuarioExibicaoDto usuarioCadastrado = this.usuarioService.cadastrarAluno(usuarioCriacaoDto);

        return ResponseEntity.status(201).body(usuarioCadastrado);
    }

    @Operation(summary = "Cadastra um professor", description = "")
    @ApiResponse(responseCode = "201", description = "Professor cadastrado.")
    @PostMapping("/cadastro/professor")
    public ResponseEntity<UsuarioExibicaoDto> cadastrarProfessor(@RequestBody  @Valid UsuarioCriacaoDto usuarioCriacaoDto){

        UsuarioExibicaoDto usuarioCadastrado = this.usuarioService.cadastrarProfessor(usuarioCriacaoDto);

        return ResponseEntity.status(201).body(usuarioCadastrado);
    }

    /*
        Recomendo adicionar mais status.
        Ass. João
    */
    @Operation(summary = "Entra em uma conta", description = "")
    @ApiResponse(responseCode = "200", description = "Login realizado.")
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){

        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }


    // Criei mas não sei se está certo
    @Operation(summary = "Obtém um professor pelo seu id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado."),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/busca/professor/{id}")
    public ResponseEntity<UsuarioExibicaoDto> buscarProfessorPorId(@RequestParam Integer id){

        try {
            UsuarioExibicaoDto professorEncontrado = this.usuarioService.buscarProfessorPorId(id);
            return ResponseEntity.status(200).body(professorEncontrado);

        } catch (EntitadeNaoEncontradaException e) {
            return ResponseEntity.status(404).build();
        }
    }

    /*
        Recomendo adicionar mais status e
        em vez de retornar apenas 1 usuário, retorna vários, pois daria para utilizar como pesquisa.
        Ass. João
    */
    @Operation(summary = "Obtém um professor pelo seu nome", description = "")
    @ApiResponse(responseCode = "200", description = "Professor encontrado.")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/busca/professor")
    public ResponseEntity<UsuarioExibicaoDto> buscarProfessorPorNome(@RequestParam String nome){

        UsuarioExibicaoDto professorEncontrado = this.usuarioService.buscarProfessorPorNome(nome);

        return ResponseEntity.status(200).body(professorEncontrado);
    }

    /*
        Recomendo adicionar mais status,
        mesma coisa que o professor
        Ass. João
    */
    @Operation(summary = "Obtém um aluno pelo seu nome", description = "")
    @ApiResponse(responseCode = "200", description = "Aluno encontrado.")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/busca/aluno")
    public ResponseEntity<UsuarioExibicaoDto> buscarAlunoPorNome(@RequestParam String nome){

        UsuarioExibicaoDto alunoEncontrado = this.usuarioService.buscarAlunoPorNome(nome);

        return ResponseEntity.status(200).body(alunoEncontrado);
    }

    @Operation(summary = "Obtém uma lista de professores ordenada alfabéticamente", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores ordenados por ordem alfabética encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há professores cadastrados.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/professores/alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeProfessoresOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibeProfessoresOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Obtém uma lista de alunos ordenada alfabéticamente", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos ordenados por ordem alfabética encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há alunos cadastrados.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/alunos/alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeAlunosOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibeAlunosOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Obtém uma lista de usuários ordenada alfabéticamente", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários ordenados por ordem alfabética encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há usuários cadastrados.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeTodosOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibeTodosOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }
}
