package school.sptech.harmonyospringapi.service.usuario.dto.professor;

import org.springframework.beans.factory.annotation.Autowired;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;

import java.util.List;

public class ProfessorMapper {

    public static ProfessorExibicaoResumidoDto of(Professor p,
                                                  List<InstrumentoExibicaoDto> instrumentos,
                                                  double valorMinimo,
                                                  double valorMaximo,
                                                  boolean empresaInstrumento,
                                                  double mediaAvaliacao,
                                                  int qtdeAvaliacoes){
        ProfessorExibicaoResumidoDto dto = new ProfessorExibicaoResumidoDto();
        List<InstrumentoExibicaoDto> instrumentosLista = instrumentos;
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setLtInstrumentos(instrumentosLista);
        dto.setValorMinimo(valorMinimo);
        dto.setValorMaximo(valorMaximo);
        dto.setEmprestaInstrumento(empresaInstrumento);
        dto.setMediaAvaliacao(mediaAvaliacao);
        dto.setQtdeAvaliacoes(qtdeAvaliacoes);

        /*dto.setDistancia(p.getDi());*/
        return dto;
    }

}
