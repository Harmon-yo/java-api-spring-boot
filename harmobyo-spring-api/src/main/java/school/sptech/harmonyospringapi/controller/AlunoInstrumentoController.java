package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.aluno_instrumento.AlunoInstrumentoService;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/aluno-instrumentos")
public class AlunoInstrumentoController {

    @Autowired
    private AlunoInstrumentoService alunoInstrumentoService;

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<List<AlunoInstrumentoExibicaoDto>> listar() {
        List<AlunoInstrumentoExibicaoDto> alunoInstrumentoExibicaoDtos = this.alunoInstrumentoService.listar();

        return alunoInstrumentoExibicaoDtos.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(alunoInstrumentoExibicaoDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<AlunoInstrumentoExibicaoDto> cadastrar(@RequestBody @Valid AlunoInstrumentoCriacaoDto alunoInstrumentoCriacaoDto) {
        AlunoInstrumentoExibicaoDto alunoInstrumentoExibicaoDto = this.alunoInstrumentoService.cadastrar(alunoInstrumentoCriacaoDto);

        return ResponseEntity.status(201).body(alunoInstrumentoExibicaoDto);
    }
}
