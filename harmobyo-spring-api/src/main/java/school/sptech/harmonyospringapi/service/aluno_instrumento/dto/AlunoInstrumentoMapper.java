package school.sptech.harmonyospringapi.service.aluno_instrumento.dto;

import school.sptech.harmonyospringapi.domain.AlunoInstrumento;
import school.sptech.harmonyospringapi.domain.AlunoInstrumentoKey;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

public class AlunoInstrumentoMapper {

    public static AlunoInstrumentoExibicaoDto ofAlunoInstrumentoExibicao(AlunoInstrumento alunoInstrumento) {
        AlunoInstrumentoExibicaoDto alunoInstrumentoExibicaoDto = new AlunoInstrumentoExibicaoDto();

        AlunoInstrumentoKey alunoInstrumentoKey = new AlunoInstrumentoKey();
        alunoInstrumentoKey.setInstrumentoFk(alunoInstrumento.getInstrumento().getId());
        alunoInstrumentoKey.setAlunoFk(alunoInstrumento.getAluno().getId());

        alunoInstrumentoExibicaoDto.setId(alunoInstrumentoKey);
        alunoInstrumentoExibicaoDto
                .setAluno(UsuarioMapper.ofUsuarioExibicao(alunoInstrumento.getAluno()));
        alunoInstrumentoExibicaoDto
                .setInstrumento(InstrumentoMapper.ofInstrumentoExibicao(alunoInstrumento.getInstrumento()));
        alunoInstrumentoExibicaoDto.setNivelConhecimento(alunoInstrumento.getNivelConhecimento());

        return alunoInstrumentoExibicaoDto;
    }

    public static AlunoInstrumento of(AlunoInstrumentoCriacaoDto alunoInstrumentoCriacaoDto) {
        AlunoInstrumentoKey alunoInstrumentoKey = new AlunoInstrumentoKey();
        alunoInstrumentoKey.setAlunoFk(alunoInstrumentoCriacaoDto.getAluno().getId());
        alunoInstrumentoKey.setInstrumentoFk(alunoInstrumentoCriacaoDto.getInstrumento().getId());

        AlunoInstrumento alunoInstrumento = new AlunoInstrumento();
        alunoInstrumento.setId(alunoInstrumentoKey);
        alunoInstrumento.setAluno(alunoInstrumentoCriacaoDto.getAluno());
        alunoInstrumento.setInstrumento(alunoInstrumentoCriacaoDto.getInstrumento());
        alunoInstrumento.setNivelConhecimento(alunoInstrumentoCriacaoDto.getNivelConhecimento());

        return alunoInstrumento;
    }
}
