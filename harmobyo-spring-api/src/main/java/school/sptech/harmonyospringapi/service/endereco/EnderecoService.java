package school.sptech.harmonyospringapi.service.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.repository.EnderecoRepository;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco cadastrarEndereco(Endereco endereco){
        return  enderecoRepository.save(endereco);
    }

    public Endereco atualizarEndereco(Endereco endereco){
        return enderecoRepository.save(endereco);
    }

    public void deletarEndereco(Endereco endereco){
        enderecoRepository.delete(endereco);
    }

    public List<Endereco> listarEnderecos(){
        return this.enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Integer id){
        if(id <= 0)
            throw new RuntimeException("Id inválido");

        return enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }


}
