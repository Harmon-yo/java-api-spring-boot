package school.sptech.harmonyospringapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.harmonyospringapi.service.pedido.PedidoService;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private PedidoService aulaService;
}
