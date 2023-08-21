package school.sptech.harmonyospringapi.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.repository.ExperienciaRepository;
import school.sptech.harmonyospringapi.service.experiencia.ExperienciaAtualizacaoDto;
import school.sptech.harmonyospringapi.service.experiencia.ExperienciaCriacaoDto;
import school.sptech.harmonyospringapi.service.experiencia.ExperienciaService;

@RestController
@RequestMapping("/experiencias")
public class ExperienciaController {

    @Autowired
    private ExperienciaService experienciaService;

    @Transactional
    @PutMapping("/atualiza-exp/{id}")
    private ResponseEntity<Void>  atualizarExperienciaPorId(@PathVariable int id, @RequestBody ExperienciaAtualizacaoDto experiencia){
        System.out.println("Crachei na controller antes de chamar a service");
        this.experienciaService.atualizarExperienciaPorId(id, experiencia.getTitulo(), experiencia.getDescricao());
        System.out.println("Crachei na controller depois de chamar a service");
        return ResponseEntity.ok().build();
    }

    @PostMapping
    private ResponseEntity<Void>  cadastrarExp(@RequestBody ExperienciaCriacaoDto novaExperiencia){
        this.experienciaService.cadastrarExp(novaExperiencia);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void>  deletarExperienciaPorId(@PathVariable int id){
        this.experienciaService.deletarExperienciaPorId(id);
        return ResponseEntity.ok().build();
    }
}
