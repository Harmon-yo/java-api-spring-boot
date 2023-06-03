package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            @ApiResponse(responseCode = "200", description = "Primeiro Aluno esperando na fila: "),
            @ApiResponse(responseCode = "204", description = "Fila vazia ")

    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/primeiro-aluno/{idProfessor}")
    public ResponseEntity<AlunoFilaDeEsperaDTO> pollAlunoFilaDeEspera(@PathVariable int idProfessor){
        return ResponseEntity.ok().body(service.pollAluno(idProfessor));
    }

    @Operation( summary = "Adiciona um aluno à fila de espera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alunos na fila encontrados: ")
    })
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/adiciona-aluno/{idAluno}/{idAula}")
    public ResponseEntity<AlunoFilaDeEsperaDTO> postFilaAlunoDeEspera(@PathVariable  int idAluno,
                                                                      @PathVariable int idAula){
        return ResponseEntity.created(null).body(service.addAlunoFilaEspera(idAluno,idAula));
    }


}
