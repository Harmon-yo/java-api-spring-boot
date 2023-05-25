package school.sptech.harmonyospringapi.controller;

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
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoExibicaoDto>> listarPedidos(){
        List<PedidoExibicaoDto> ltPedidosExibicao = this.pedidoService.obterTodos();

        if(ltPedidosExibicao.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ltPedidosExibicao);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoExibicaoDto> adicionarPedido(@RequestBody @Valid
                                                             PedidoCriacaoDto pedidoCriacaoDto){
        PedidoExibicaoDto pedidoExibicaoDto = this.pedidoService.criar(pedidoCriacaoDto);
        return ResponseEntity.created(null).body(pedidoExibicaoDto);
    }

    @PatchMapping("/cancela-pedido/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoExibicaoDto> cancelarPedidoPorId(@PathVariable Integer id){

        PedidoExibicaoDto pedido = pedidoService.cancelarPedido(id);

        return ResponseEntity.ok(pedido);

    }
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<PedidoExibicaoDto>> buscarPorUsuarioId(@PathVariable Integer id){
        List<PedidoExibicaoDto> pedidoExibicaoDto = this.pedidoService.buscarPorUsuarioId(id);

        if(pedidoExibicaoDto.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pedidoExibicaoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoExibicaoDto> buscarPorId(@PathVariable Integer id){
        PedidoExibicaoDto pedidoExibicaoDto = this.pedidoService.buscarPorIdParaExibicao(id);

        if(pedidoExibicaoDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pedidoExibicaoDto);
    }





}

