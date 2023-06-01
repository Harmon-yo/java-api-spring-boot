package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.pedido.PedidoService;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoCriacaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoPilhaDto;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Lista todos os pedidos cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado")
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoExibicaoDto>> listarPedidos(){
        List<PedidoExibicaoDto> ltPedidosExibicao = this.pedidoService.obterTodos();

        if(ltPedidosExibicao.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ltPedidosExibicao);
    }

    @PostMapping
    @Operation(summary = "Cadastra um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoExibicaoDto> adicionarPedido(@RequestBody @Valid
                                                             PedidoCriacaoDto pedidoCriacaoDto){
        PedidoExibicaoDto pedidoExibicaoDto = this.pedidoService.criar(pedidoCriacaoDto);
        return ResponseEntity.created(null).body(pedidoExibicaoDto);
    }



    @PostMapping("/aceita-pedido/{id}")
    @Operation(summary = "Aceita a proposta do aluno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proposta aceita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity<PedidoExibicaoDto> aceitarPropostaDoAluno(@PathVariable Integer id){
        PedidoExibicaoDto pedido = this.pedidoService.aceitarPropostaDoAluno(id);

        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/recusa-pedido/{id}")
    @Operation(summary = "Recusa a proposta do aluno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proposta recusada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity<PedidoExibicaoDto> recusarPropostaDoAluno(@PathVariable Integer id){
        PedidoExibicaoDto pedido = this.pedidoService.recusarPropostaDoAluno(id);

        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/cancela-pedido/{id}")
    @Operation(summary = "Cancela o pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoExibicaoDto> cancelarPedidoPorId(@PathVariable Integer id){
        PedidoExibicaoDto pedido = pedidoService.cancelarPedido(id);

        return ResponseEntity.ok(pedido);

    }
    @GetMapping("/usuario/{id}")
    @Operation(summary = "Lista todos os pedidos cadastrados por usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos por usuario"),
            @ApiResponse(responseCode = "400", description = "Usuario não encontrado")
    })
    public ResponseEntity<List<PedidoExibicaoDto>> buscarPorUsuarioId(@PathVariable Integer id){
        List<PedidoExibicaoDto> pedidoExibicaoDto = this.pedidoService.buscarPorUsuarioId(id);

        if(pedidoExibicaoDto.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pedidoExibicaoDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoExibicaoDto> buscarPorId(@PathVariable Integer id){
        PedidoExibicaoDto pedidoExibicaoDto = this.pedidoService.buscarPorIdParaExibicao(id);

        if(pedidoExibicaoDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pedidoExibicaoDto);
    }





}

