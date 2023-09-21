package school.sptech.harmonyospringapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.notificacao.NotificacaoService;
import school.sptech.harmonyospringapi.service.notificacao.dto.NotificacaoCriacaoDto;
import school.sptech.harmonyospringapi.service.notificacao.dto.NotificacaoExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping()
    public ResponseEntity<List<NotificacaoExibicaoDto>> listarNotificacoes() {
        List<NotificacaoExibicaoDto> ltNotificacoes = this.notificacaoService.obterTodos();

        if (ltNotificacoes.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(ltNotificacoes);
    }

    @PostMapping()
    public ResponseEntity<NotificacaoExibicaoDto> cadastrarNotificacao(@RequestBody NotificacaoCriacaoDto notificacaoCriacaoDto) {
        return ResponseEntity.created(null).body(this.notificacaoService.cadastrar(notificacaoCriacaoDto));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Page<NotificacaoExibicaoDto>> obterPorIdUsuario(@PathVariable int id,
                                                                          @PageableDefault(size = 10,
                                                                                  sort = "data",
                                                                                  direction = Sort.Direction.DESC)
                                                                          Pageable pageable) {
        Page<NotificacaoExibicaoDto> ltNotificacoes = this.notificacaoService.obterPorIdUsuario(id, pageable);

        if (ltNotificacoes.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(ltNotificacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoExibicaoDto> obterPorId(@PathVariable int id) {
        return ResponseEntity.ok().body(this.notificacaoService.obterPorIdNotificacaoExibicao(id));
    }

    @PutMapping("/lida/{id}")
    public ResponseEntity<Void> marcarComoLida(@PathVariable int id) {
        this.notificacaoService.marcarComoLida(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/lida-usuario/{id}")
    public ResponseEntity<Void> marcarComoLidaPorUsuario(@PathVariable int id) {
        this.notificacaoService.marcarTodasComoLida(id);
        return ResponseEntity.ok().build();
    }
}
