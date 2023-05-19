package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.professor_instrumento.ProfessorInstrumentoService;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorInstrumentoService professorInstrumentoService;

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
    public ResponseEntity<List<UsuarioExibicaoDto>> obterTodos() {

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.professorService.obterTodos();

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
    public ResponseEntity<List<UsuarioExibicaoDto>> obterTodosEmOrdemAlfabetica() {

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.professorService.obterTodosEmOrdemAlfabetica();

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
    public ResponseEntity<UsuarioExibicaoDto> obterPorNome(@RequestParam String nome) {

        UsuarioExibicaoDto professorEncontrado = this.professorService.obterPorNome(nome);

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

        return ResponseEntity.status(204).build();
    }


    @GetMapping("/{id}/instrumentos")
    public ResponseEntity<List<ProfessorInstrumentoExibicaoDto>> obterTodosInstrumentos(@PathVariable int id) {
        List<ProfessorInstrumentoExibicaoDto> professorInstrumentoExibicaoDtos = this.professorInstrumentoService.obterTodos(id);
        return professorInstrumentoExibicaoDtos.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(professorInstrumentoExibicaoDtos);
    }

    @PostMapping("/{id}/instrumentos")
    public ResponseEntity<ProfessorInstrumentoExibicaoDto> adicionarInstrumentos(@PathVariable int id, @RequestBody @Valid ProfessorInstrumentoCriacaoDto professorInstrumentoCriacaoDto) {

        ProfessorInstrumentoExibicaoDto professorInstrumentoExibicaoDto = this.professorInstrumentoService.criar(id, professorInstrumentoCriacaoDto);

        return ResponseEntity.status(201).body(professorInstrumentoExibicaoDto);
    }
}
