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
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Operation(summary = "Cadastra um professor", description = "")
    @ApiResponse(responseCode = "201", description = "Professor cadastrado.")
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioExibicaoDto> cadastrar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto) {

        UsuarioExibicaoDto professorCadastrado = this.professorService.cadastrar(usuarioCriacaoDto);

        return ResponseEntity.status(201).body(professorCadastrado);
    }

    @Operation(summary = "Obtém uma lista de todos os professores cadastrados", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há professores cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    })


    @GetMapping
    public ResponseEntity<List<UsuarioExibicaoDto>> exibirTodos() {

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.professorService.exibirTodos();

        if (ltUsuariosExibicao.isEmpty()) {

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }


    @Operation(summary = "Obtém um professor pelo seu id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado."),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioExibicaoDto> buscarPorId(@RequestParam Integer id) {

        return ResponseEntity.status(200).body(this.professorService.buscarPorId(id));
    }


    @Operation(summary = "Obtém uma lista de professores ordenada alfabéticamente", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores ordenados por ordem alfabética encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há professores cadastrados.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/ordem-alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeEmOrdemAlfabetica() {

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.professorService.exibeEmOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()) {

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }


    /*
    Recomendo adicionar mais status e
    em vez de retornar apenas 1 usuário, retorna vários, pois daria para utilizar como pesquisa.
    Ass. João
*/
    @Operation(summary = "Obtém um professor pelo seu nome", description = "")
    @ApiResponse(responseCode = "200", description = "Professor encontrado.")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/nome")
    public ResponseEntity<UsuarioExibicaoDto> buscarPorNome(@RequestParam String nome) {

        UsuarioExibicaoDto professorEncontrado = this.professorService.buscarPorNome(nome);

        return ResponseEntity.status(200).body(professorEncontrado);
    }

    @Operation(summary = "Deleta um professor através do seu ID", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor deletado do sistema"),
            @ApiResponse(responseCode = "404", description = "ID Inválido. Professor não encontrado")
    })
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/exclusao/conta/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){

        this.professorService.deletarPorId(id);

        return ResponseEntity.status(200).build();
    }
}
