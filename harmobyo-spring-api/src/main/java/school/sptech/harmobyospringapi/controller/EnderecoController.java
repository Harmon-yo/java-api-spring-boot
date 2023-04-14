package school.sptech.harmobyospringapi.controller;


import jakarta.persistence.PostRemove;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmobyospringapi.domain.Endereco;
import school.sptech.harmobyospringapi.repository.EnderecoRepository;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos(){
        List<Endereco> ltEnderecos = this.enderecoRepository.findAll();

        if (ltEnderecos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltEnderecos);
    }

    @PostMapping
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody  @Valid Endereco endereco){
        Endereco enderecoCadastrado = this.enderecoRepository.save(endereco);

        return ResponseEntity.status(201).body(enderecoCadastrado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(@Valid @RequestBody Endereco endereco, Integer id){

        Endereco enderecoAntigo = this.enderecoRepository.findById(id).get();
        enderecoAntigo.setCep(endereco.getCep());
        enderecoAntigo.setLogradouro(endereco.getLogradouro());
        enderecoAntigo.setNumero(endereco.getNumero());
        enderecoAntigo.setComplemento(endereco.getComplemento());
        enderecoAntigo.setBairro(endereco.getBairro());
        enderecoAntigo.setCidade(endereco.getCidade());
        enderecoAntigo.setEstado(endereco.getEstado());
        return ResponseEntity.status(200).body(this.enderecoRepository.save(enderecoAntigo));


    }

    @DeleteMapping
    public ResponseEntity<Endereco> deletarEndereco(@Valid @RequestBody Endereco endereco){
        this.enderecoRepository.delete(endereco);

        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Endereco> deletarEnderecoPorId(@PathVariable Integer id){
        this.enderecoRepository.deleteById(id);

        return ResponseEntity.status(200).build();
    }


}
