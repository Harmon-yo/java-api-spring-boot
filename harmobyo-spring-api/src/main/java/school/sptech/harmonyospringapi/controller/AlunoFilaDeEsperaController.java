package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Fila de espera de Alunos, quando há uma aula para um dia já atribuído")
public class AlunoFilaDeEsperaController {
    @Autowired
    private AlunoFilaDeEsperaService service;

    @Operation( summary = "Retorna o primeiro aluno (prioridade) da fila")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Primeiro Aluno esperando na fila: ")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/primeiro-aluno")
    public ResponseEntity<AlunoFilaDeEsperaDTO> pollAlunoFilaDeEspera(){
        return ResponseEntity.ok().body(service.pollAluno());
    }

    @Operation( summary = "Adiciona um aluno à fila de espera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alunos na fila encontrados: ")
    })
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<FilaObj<AlunoFilaDeEsperaDTO>> postFilaAlunoDeEspera(){
        return ResponseEntity.ok().body(service.getFilaEsperaAluno());
    }


}
