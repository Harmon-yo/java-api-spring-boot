package school.sptech.harmonyospringapi.service.pedido.builder;

import school.sptech.harmonyospringapi.domain.*;

public class PedidoBuilder {

    public static Pedido pedido(){
        int idGeral = 1;
        int idAluno = 2;
        int idProfessor = 3;

        Aluno aluno = new Aluno();
        aluno.setId(idAluno);

        Professor professor = new Professor();
        professor.setId(idProfessor);

        Status status = new Status();
        status.setId(idGeral);

        Naipe naipe = new Naipe();
        naipe.setId(idGeral);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(idGeral);
        instrumento.setNaipe(naipe);

        Aula aula = new Aula();
        aula.setId(idGeral);
        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);

        Pedido pedido = new Pedido();
        pedido.setId(idGeral);
        pedido.setAluno(aluno);
        pedido.setProfessor(professor);
        pedido.setStatus(status);
        pedido.setAula(aula);

        return pedido;
    }
}
