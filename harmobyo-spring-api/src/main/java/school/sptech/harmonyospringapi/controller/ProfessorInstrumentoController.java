package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.professor_instrumento.ProfessorInstrumentoService;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/professor-instrumentos")
public class ProfessorInstrumentoController {

    @Autowired
    private ProfessorInstrumentoService professorInstrumentoService;

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<List<ProfessorInstrumentoExibicaoDto>> listar() {
        List<ProfessorInstrumentoExibicaoDto> professorInstrumentoExibicaoDtos = this.professorInstrumentoService.listar();
        return professorInstrumentoExibicaoDtos.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(professorInstrumentoExibicaoDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<ProfessorInstrumentoExibicaoDto> cadastrar(@RequestBody @Valid ProfessorInstrumentoCriacaoDto professorInstrumentoCriacaoDto)  {
        ProfessorInstrumentoExibicaoDto professorInstrumentoExibicaoDto = this.professorInstrumentoService.cadastrar(professorInstrumentoCriacaoDto);

        return ResponseEntity.status(201).body(professorInstrumentoExibicaoDto);
    }
}
