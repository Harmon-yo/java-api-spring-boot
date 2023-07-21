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
import school.sptech.harmonyospringapi.domain.FiltroMinimoMaximo;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioDadosPerfilDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoExibicaoDto;

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


    /*
        Recomendo adicionar mais status.
        Ass. João
    */
    @Operation(summary = "Entra em uma conta", description = "")
    @ApiResponses( value= {
            @ApiResponse(responseCode= "200", description = "Login realizado."),
            @ApiResponse(responseCode = "404", description = "Email de usuário não cadastrado")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){

        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }


    @Operation(summary = "Obtém uma lista de usuários ordenada alfabéticamente", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários ordenados por ordem alfabética encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há usuários cadastrados.")
    })

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/ordem-alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> exibeTodosOrdemAlfabetica(){

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.usuarioService.exibeTodosOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }

    @Operation(summary = "Adiciona uma avaliação a um usuário", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação adicionada."),
            @ApiResponse(responseCode = "404", description = "Não é possível avaliar a si mesmo"),
    })
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/{id}/avaliacoes")
    public ResponseEntity<AvaliacaoExibicaoDto> adicionarAvaliacao(@PathVariable int id, @RequestBody @Valid AvaliacaoCriacaoDto avaliacaoCriacaoDto) {

        AvaliacaoExibicaoDto avaliacaoExibicaoDto = this.usuarioService.criarAvaliacao(id, avaliacaoCriacaoDto);

        return ResponseEntity.created(null).body(avaliacaoExibicaoDto);
    }

    @Operation(summary = "Listar avaliações de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada")
    })
    @GetMapping("/{id}/avaliacoes")
    public ResponseEntity<List<AvaliacaoExibicaoDto>> listarAvaliacao(@PathVariable int id) {

        List<AvaliacaoExibicaoDto> ltAvaliacaoExibicaoDto = this.usuarioService.listarAvaliacoesPorUsuario(id);

        if (ltAvaliacaoExibicaoDto.isEmpty()) return ResponseEntity.noContent().build();


        return ResponseEntity.ok(ltAvaliacaoExibicaoDto);
    }

    @GetMapping("/dados-perfil/{id}")
    public ResponseEntity<UsuarioDadosPerfilDto> obterDadosPerfilUsuario(@PathVariable int id){
        return ResponseEntity.ok(this.usuarioService.obterDadosPerfilUsuario(id));
    }

    @GetMapping("/filtro-minimo-maximo")
    public ResponseEntity<FiltroMinimoMaximo> filtroMinimoMaximo(){

        FiltroMinimoMaximo filtroMinimoMaximo = this.usuarioService.filtroMinimoMaximo();

        return ResponseEntity.ok(filtroMinimoMaximo);
    }
}
