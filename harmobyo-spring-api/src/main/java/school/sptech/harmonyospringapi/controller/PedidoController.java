package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.service.pedido.PedidoService;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoCriacaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.hashing.HashTableService;
import school.sptech.harmonyospringapi.utils.PilhaObj;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private HashTableService hashTableService;

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

    @GetMapping("/pedidos-pendentes/{idProfessor}")
    @Operation(summary = "Lista todos os pedidos pendentes de um professor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado"),
            @ApiResponse(responseCode =  "204", description = "Nenhum pedido pendente encontrado")
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PilhaObj<PedidoExibicaoDto>> listarPedidosPendentes(@PathVariable int idProfessor){
        PilhaObj<PedidoExibicaoDto> ltPedidosExibicao = this.pedidoService.obterPedidosPendentes(idProfessor);

        if(ltPedidosExibicao.isEmpty()) return ResponseEntity.noContent().build();

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



    @PutMapping("/aceita-pedido/{id}")
    @Operation(summary = "Aceita a proposta do aluno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proposta aceita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity<PedidoExibicaoDto> aceitarPropostaDoAluno(@PathVariable Integer id){
        PedidoExibicaoDto pedido = this.pedidoService.aceitarPropostaDoAluno(id);

        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/recusa-pedido/{id}")
    @Operation(summary = "Recusa a proposta do aluno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proposta recusada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity<PedidoExibicaoDto> recusarPropostaDoAluno(@PathVariable Integer id){
        return ResponseEntity.ok(this.pedidoService.recusarPropostaDoAluno(id));
    }

    @PutMapping("/cancela-pedido/{id}")
    @Operation(summary = "Cancela o pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoExibicaoDto> cancelarPedidoPorId(@PathVariable Integer id){
        return ResponseEntity.ok(pedidoService.cancelarPedido(id));
    }

    @PutMapping("/conclui-pedido/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoExibicaoDto> concluirPedidoPorId(@PathVariable Integer id){
        return ResponseEntity.ok(pedidoService.concluirPedidoPorId(id));
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Lista todos os pedidos cadastrados por usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos por usuario"),
            @ApiResponse(responseCode = "400", description = "Usuario não encontrado")
    })
    public ResponseEntity<List<PedidoExibicaoDto>> buscarPorUsuarioId(@PathVariable Integer id){
        List<PedidoExibicaoDto> pedidoExibicaoDto = this.pedidoService.buscarPorUsuarioId(id);

        if(pedidoExibicaoDto.isEmpty()) return ResponseEntity.noContent().build();
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

        if(pedidoExibicaoDto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pedidoExibicaoDto);
    }

    @GetMapping("/pedidos-por-data-id-usuario")
    @Operation(summary = "Obtém uma lista de todos os pedidos confirmados de um professor pelo dia", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aulas encontradss."),
            @ApiResponse(responseCode = "204", description = "Este professor não possui aulas cadastradas.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "ID do Professor inválido !", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<List<PedidoExibicaoDto>> buscarAulasPorIdUsuarioEDataAula(@RequestParam int fkUsuario,
                                                                                    @RequestParam String data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(data + " 00:00:00", formatter);
        List<PedidoExibicaoDto> ltAulas = this.pedidoService.buscarAulasPorIdUsuarioEDataAula(fkUsuario, localDateTime);

        if (ltAulas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ltAulas);
    }

    @GetMapping("/pedidos-por-mes-id-usuario")
    @Operation(summary = "Obtém uma lista de todos os pedidos confirmados de um professor pelo dia", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aulas encontradss."),
            @ApiResponse(responseCode = "204", description = "Este professor não possui aulas cadastradas.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "ID do Professor inválido !", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<List<PedidoExibicaoDto>> buscarAulasPorIdUsuarioEMesAula(@RequestParam int fkUsuario,
                                                                                    @RequestParam String data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(data + " 00:00:00", formatter);
        List<PedidoExibicaoDto> ltAulas = this.pedidoService.buscarAulasPorIdUsuarioEMesAula(fkUsuario, localDateTime);

        if (ltAulas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ltAulas);
    }

    @GetMapping("/usuario/{id}/confirmado")
    public ResponseEntity<List<PedidoExibicaoDto>> buscarPorUsuarioIdConcluido(@PathVariable Integer id){
        List<PedidoExibicaoDto> pedidoExibicaoDto = this.pedidoService.buscarPorUsuarioIdConfirmado(id);

        if(pedidoExibicaoDto.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pedidoExibicaoDto);
    }

    // ------------------------------- HashingTable ------------------------------- //

    @GetMapping("/hashing")
    public ResponseEntity<Void> adicionarPedidosNaHashing(){
        this.hashTableService.adicionarBanco();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/hashing/{id}")
    public ResponseEntity<List<PedidoExibicaoDto>> buscarPedidosPorUsuarioIdEStatusHashing(@PathVariable Integer id, @RequestParam String status){
        List<PedidoExibicaoDto> pedidoExibicaoDto = this.hashTableService.buscarPedidosPorIdEStatus(id, status);

        if(pedidoExibicaoDto.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pedidoExibicaoDto);
    }
}

