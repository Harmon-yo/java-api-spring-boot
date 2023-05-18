package school.sptech.harmonyospringapi.service.fila_de_espera;

import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.service.fila_de_espera.dto.AlunoFilaDeEsperaDTO;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.utils.FilaObj;

import java.util.List;

@Service
public class AlunoFilaDeEsperaService {
    FilaObj<AlunoFilaDeEsperaDTO> filaAluno1;
    public FilaObj<AlunoFilaDeEsperaDTO> getFilaEsperaAluno(){
        filaAluno1 = new FilaObj<>(10);

        filaAluno1.insert(new AlunoFilaDeEsperaDTO(
                1, "joaozinho", "teclado"
        ));
        filaAluno1.insert(new AlunoFilaDeEsperaDTO(
                2, "maria", "teclado"
        ));
        filaAluno1.insert(new AlunoFilaDeEsperaDTO(
                3, "leticia", "teclado"
        ));
        filaAluno1.insert(new AlunoFilaDeEsperaDTO(
                4, "123", "teclado"
        ));
        filaAluno1.insert(new AlunoFilaDeEsperaDTO(
                15, "asuka", "teclado"
        ));
        filaAluno1.insert(new AlunoFilaDeEsperaDTO(
                16, "carol", "teclado"
        ));

        return filaAluno1;
    }

    public AlunoFilaDeEsperaDTO pollAluno(){
        return filaAluno1.poll();
    }


}
