package school.sptech.harmonyospringapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.service.aula.AulaService;
import school.sptech.harmonyospringapi.service.aula.dto.AulaCriacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;


    @PostMapping
    public ResponseEntity<AulaExibicaoDto> cadastrar(@RequestBody @Valid AulaCriacaoDto aulaCriacaoDto) {
        AulaExibicaoDto aulaCriada = this.aulaService.criar(aulaCriacaoDto);

        return ResponseEntity.ok(aulaCriada);
    }

    @GetMapping("/{fkProfessor}")
    public ResponseEntity<List<AulaExibicaoDto>> buscarAulasPorIdProfessor(@PathVariable int fkProfessor) {

        List<AulaExibicaoDto> ltAulas = this.aulaService.buscarAulasPorIdProfessor(fkProfessor);

        if (ltAulas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ltAulas);
    }
}
