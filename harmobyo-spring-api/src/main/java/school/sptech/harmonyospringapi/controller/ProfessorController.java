package school.sptech.harmonyospringapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDashboardDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoHistoricoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor.ProfessorExibicaoResumidoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor_instrumento.ProfessorInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.professor_instrumento.ProfessorInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.List;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Operation(summary = "Cadastra um professor", description = "")
    @ApiResponse(responseCode = "201", description = "Professor cadastrado.")
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioExibicaoDto> cadastrar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto) {

        UsuarioExibicaoDto professorCadastrado = this.professorService.cadastrar(usuarioCriacaoDto);

        return ResponseEntity.status(201).body(professorCadastrado);
    }

    @Operation(summary = "Obtém uma lista de todos os professores cadastrados", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há professores cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioExibicaoDto>> obterTodos() {

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.professorService.listar();

        if (ltUsuariosExibicao.isEmpty()) {

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }


    @Operation(summary = "Obtém um professor pelo seu id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado."),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioExibicaoDto> buscarPorId(@RequestParam Integer id) {

        return ResponseEntity.status(200).body(this.professorService.buscarPorIdParaExibicao(id));
    }


    @Operation(summary = "Obtém uma lista de professores ordenada alfabéticamente", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores ordenados por ordem alfabética encontrados."),
            @ApiResponse(responseCode = "204", description = "Não há professores cadastrados.")
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/ordem-alfabetica")
    public ResponseEntity<List<UsuarioExibicaoDto>> obterTodosEmOrdemAlfabetica() {

        List<UsuarioExibicaoDto> ltUsuariosExibicao = this.professorService.obterTodosEmOrdemAlfabetica();

        if (ltUsuariosExibicao.isEmpty()) {

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ltUsuariosExibicao);
    }


    /*
    Recomendo adicionar mais status e
    em vez de retornar apenas 1 usuário, retorna vários, pois daria para utilizar como pesquisa.
    Ass. João
*/
    @Operation(summary = "Obtém um professor pelo seu nome", description = "")
    @ApiResponse(responseCode = "200", description = "Professor encontrado.")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/nome")
    public ResponseEntity<UsuarioExibicaoDto> obterPorNome(@RequestParam String nome) {

        UsuarioExibicaoDto professorEncontrado = this.professorService.buscarPorNome(nome);

        return ResponseEntity.status(200).body(professorEncontrado);
    }

    @Operation(summary = "Deleta um professor através do seu ID", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor deletado do sistema"),
            @ApiResponse(responseCode = "404", description = "ID Inválido. Professor não encontrado")
    })
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/exclusao/conta/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){

        this.professorService.deletarPorId(id);

        return ResponseEntity.status(204).build();
    }


    @GetMapping("/{id}/instrumentos")
    public ResponseEntity<List<InstrumentoExibicaoDto>> listarInstrumentos(@PathVariable int id) {
        List<InstrumentoExibicaoDto> instrumentos = this.professorService.listarInstrumentos(id);
        return instrumentos.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(instrumentos);
    }

    @PostMapping("/{id}/instrumentos")
    public ResponseEntity<ProfessorInstrumentoExibicaoDto> adicionarInstrumentos(@PathVariable int id, @RequestBody @Valid ProfessorInstrumentoCriacaoDto professorInstrumentoCriacaoDto) {

        ProfessorInstrumentoExibicaoDto professorInstrumentoExibicaoDto = this.professorService.criar(id, professorInstrumentoCriacaoDto);

        return ResponseEntity.status(201).body(professorInstrumentoExibicaoDto);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/melhores-avaliados")
    public ResponseEntity<List<ProfessorExibicaoResumidoDto>> getProfessoresMelhoresAvaliados(){
        List<ProfessorExibicaoResumidoDto> professores = this.professorService.getProfessoresMelhoresAvaliados();

        return professores.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(professores);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/menores-valor-aula")
    public ResponseEntity<List<ProfessorExibicaoResumidoDto>> getProfessoresMenorValorAula(){
        List<ProfessorExibicaoResumidoDto> professores = this.professorService.getProfessoresComMenorValorAula();

        return professores.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(professores);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/maiores-valor-aula")
    public ResponseEntity<List<ProfessorExibicaoResumidoDto>> getProfessoreMaiorValorAula(){
        List<ProfessorExibicaoResumidoDto> professores = this.professorService.getProfessoresComMaiorValorAula();

        return professores.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(professores);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/por-instrumento/{id}")
    public ResponseEntity<List<ProfessorExibicaoResumidoDto>> getProfessorPorInstrumento(
            @PathVariable int id
    ){
        List<ProfessorExibicaoResumidoDto> professores = this.professorService.getProfessorByInstrumento(id);

        return professores.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(professores);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/dashboard/media-tempo-resposta/{id}")
    public ResponseEntity<Long> getMediaTempoResposta(@PathVariable int id){
        Long rendimento = this.professorService.getMediaTempoResposta(id);
        return ResponseEntity.status(200).body(rendimento);
    }


    @SecurityRequirement(name = "Bearer")
    @GetMapping("/dashboard/ultimas-24-horas/rendimento/{id}")
    public ResponseEntity<Double> getRendimentoUltimas24Horas(@PathVariable int id){
        Double rendimento = this.professorService.getRendimentoUltimas24Horas(id);
        return ResponseEntity.status(200).body(rendimento);
    }
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/dashboard/ultimas-24-horas/qtd-alunos/{id}")
    public ResponseEntity<Integer> getQuantidadeAlunosUltimas24Horas(@PathVariable int id){
        Integer qtdAlunos = this.professorService.getQuantidadeAlunos24Horas(id);
        return ResponseEntity.status(200).body(qtdAlunos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/dashboard/ultimas-24-horas/qtd-aulas/{id}")
    public ResponseEntity<Integer> getQuantidadeAulasUltimas24Horas(@PathVariable int id){
        Integer qtdAulas = this.professorService.getQuantidadeAulasUltimas24Horas(id);
        return ResponseEntity.status(200).body(qtdAulas);
    }


    /*  GRAFICO DASH   */

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/dashboard/grafico/historico/{id}")
    public ResponseEntity<List<PedidoHistoricoDto>> getHistoricoPedidos(@PathVariable int id){
        List<PedidoHistoricoDto> historico = this.professorService.getHistoricoPedidos(id);
        return  historico.isEmpty()? ResponseEntity.status(204).build() :
                ResponseEntity.status(200).body(historico);

    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/dashboard/minhas-aulas/{id}")
    public ResponseEntity<List<PedidoExibicaoDashboardDto>> getAulasRealizadas(@PathVariable int id){
        List<PedidoExibicaoDashboardDto> aulas = this.professorService.getAulasRealizadas(id);
        return  aulas.isEmpty()? ResponseEntity.status(204).build() :
                ResponseEntity.status(200).body(aulas);

    }

    @GetMapping("/busca")
    public ResponseEntity<List<ProfessorExibicaoResumidoDto>> filtrarProfessor(@RequestParam String params) {
        List<ProfessorExibicaoResumidoDto> professores = this.professorService.buscarTodosFiltrado(params);

        if (professores.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(professores);
    }


}
