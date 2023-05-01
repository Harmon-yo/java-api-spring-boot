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
        List<Endereco> ltEnderecos = this.enderecoRepository.findAll();
        return ltEnderecos;
    }


}
