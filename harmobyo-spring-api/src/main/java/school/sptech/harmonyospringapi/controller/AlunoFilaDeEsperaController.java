package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.harmonyospringapi.service.fila_de_espera.AlunoFilaDeEsperaService;
import school.sptech.harmonyospringapi.service.fila_de_espera.dto.AlunoFilaDeEsperaDTO;
import school.sptech.harmonyospringapi.utils.FilaObj;

@RestController
@RequestMapping("/fila")
public class AlunoFilaDeEsperaController {
    @Autowired
    private AlunoFilaDeEsperaService service;

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/primeiro-aluno")
    public ResponseEntity<AlunoFilaDeEsperaDTO> pollAlunoFilaDeEspera(){
        return ResponseEntity.ok().body(service.pollAluno());
    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<FilaObj<AlunoFilaDeEsperaDTO>> postFilaAlunoDeEspera(){
        return ResponseEntity.ok().body(service.getFilaEsperaAluno());
    }


}
