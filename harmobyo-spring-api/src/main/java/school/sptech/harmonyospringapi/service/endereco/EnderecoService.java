package school.sptech.harmonyospringapi.service.endereco;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.repository.EnderecoRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

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
