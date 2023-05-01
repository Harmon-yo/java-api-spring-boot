package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController{

    @Autowired
    private AlunoService alunoService;

    @Operation(summary = "Cadastra um aluno", description = "")
    @ApiResponse(responseCode = "201", description = "Aluno cadastrado.")
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioExibicaoDto> cadastrar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto){

        UsuarioExibicaoDto alunoCadastrado = this.alunoService.cadastrar(usuarioCriacaoDto);

        return ResponseEntity.status(201).body(alunoCadastrado);
    }

    @Operation(summary = "Obtém uma lista de todos os alunos cadastrados", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há alunos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioExibicaoDto>> listarAlunos(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.alunoService.exibirTodos();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Obtém um aluno pelo seu id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado."),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioExibicaoDto> buscarPorId(@RequestParam Integer id){
        return ResponseEntity.status(200).body(this.alunoService.buscarPorId(id));
    }

    /*
        Recomendo adicionar mais status,
        mesma coisa que o professor
        Ass. João
    */
    @Operation(summary = "Obtém um aluno pelo seu nome", description = "")
    @ApiResponse(responseCode = "200", description = "Aluno encontrado.")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/nome")
    public ResponseEntity<UsuarioExibicaoDto> buscarPorNome(@RequestParam String nome){

        UsuarioExibicaoDto alunoEncontrado = this.alunoService.buscarPorNome(nome);

        return ResponseEntity.status(200).body(alunoEncontrado);
    }


    @Operation(summary = "Obtém uma lista de alunos ordenada alfabéticamente", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos ordenados por ordem alfabética encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há alunos cadastrados.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/ordem-alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeAlunosOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.alunoService.exibeEmOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Deleta um aluno através do seu ID", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno deletado do sistema"),
            @ApiResponse(responseCode = "404", description = "ID Inválido. Aluno não encontrado")
    })
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/exclusao/conta/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){

        this.alunoService.deletarPorId(id);

        return ResponseEntity.status(200).build();
    }
}
