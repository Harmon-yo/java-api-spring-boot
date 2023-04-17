package school.sptech.harmonyospringapi.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Requisições relacionadas aos endereços")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtém uma lista de todos os endereços cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereços encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há endereços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos(){
        List<Endereco> ltEnderecos = this.enderecoService.listarEnderecos();

        if (ltEnderecos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltEnderecos);
    }

    @Operation(summary = "Cadastra um endereço do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado."),
            @ApiResponse(responseCode = "400", description = "O endereço não foi digitado corretamente.", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/{idUsuario}")
    public ResponseEntity<UsuarioExibicaoDto> cadastrarEndereco(@RequestBody @Valid Endereco endereco
            , @PathVariable Integer idUsuario){

        UsuarioExibicaoDto enderecoCadastrado = this.usuarioService.inserirEndereco(idUsuario, endereco);

        if(enderecoCadastrado != null){
            return ResponseEntity.status(201).body(enderecoCadastrado);
        }
        return ResponseEntity.status(400).build();

    }

    @Operation(summary = "Atualiza um endereço do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço atualizado."),
            @ApiResponse(responseCode = "400", description = "O endereço não foi digitado corretamente.", content = @Content(schema = @Schema(hidden = true)))
    })
    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioExibicaoDto> atualizarEndereco(@Valid @RequestBody Endereco endereco, @PathVariable Integer idUsuario){

        UsuarioExibicaoDto enderecoAtualizado = this.usuarioService.atualizarEndereco( idUsuario, endereco);

        return ResponseEntity.status(200).body(enderecoAtualizado);
    }

    @Operation(summary = "Deleta um endereço")
    @ApiResponse(responseCode = "200", description = "Endereço removido.")
    @DeleteMapping()
    public ResponseEntity<Void> deletarEndereco(@RequestBody @Valid Endereco endereco){
       this.enderecoService.deletarEndereco(endereco);
        return ResponseEntity.status(200).build();
    }


}
