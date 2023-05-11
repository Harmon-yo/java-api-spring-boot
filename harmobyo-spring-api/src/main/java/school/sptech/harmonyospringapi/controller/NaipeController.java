package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.naipe.NaipeService;
import school.sptech.harmonyospringapi.service.naipe.dto.NaipeCriacaoDto;
import school.sptech.harmonyospringapi.service.naipe.dto.NaipeExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/naipes")
@Tag(name = "Naipes")
public class NaipeController {

    @Autowired
    private NaipeService naipeService;

    @GetMapping
    public ResponseEntity<List<NaipeExibicaoDto>> listar() {
        List<NaipeExibicaoDto> naipeExibicaoDtos = this.naipeService.listar();

        return naipeExibicaoDtos.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(naipeExibicaoDtos);
    }

    @PostMapping
    public ResponseEntity<NaipeExibicaoDto> cadastrar(@RequestBody @Valid NaipeCriacaoDto naipeCriacaoDto) {
        NaipeExibicaoDto naipeExibicaoDto = this.naipeService.cadastrar(naipeCriacaoDto);

        return ResponseEntity.status(201).body(naipeExibicaoDto);
    }
}
