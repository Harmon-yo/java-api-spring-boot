package school.sptech.harmonyospringapi.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos(){
        List<Endereco> ltEnderecos = this.enderecoService.listarEnderecos();

        if (ltEnderecos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltEnderecos);
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<UsuarioExibicaoDto> cadastrarEndereco(@RequestBody @Valid Endereco endereco
            , @PathVariable Integer idUsuario){

        UsuarioExibicaoDto enderecoCadastrado = this.usuarioService.inserirEndereco(idUsuario, endereco);

        if(enderecoCadastrado != null){
            return ResponseEntity.status(201).body(enderecoCadastrado);
        }
        return ResponseEntity.status(400).build();

    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioExibicaoDto> atualizarEndereco(@Valid @RequestBody Endereco endereco, @PathVariable Integer idUsuario){

        UsuarioExibicaoDto enderecoAtualizado = this.usuarioService.atualizarEndereco( idUsuario, endereco);

        return ResponseEntity.status(200).body(enderecoAtualizado);


    }

    @DeleteMapping()
    public ResponseEntity<Endereco> deletarEndereco(@RequestBody @Valid Endereco endereco){
       this.enderecoService.deletarEndereco(endereco);
        return ResponseEntity.status(200).build();
    }


}
