package school.sptech.harmonyospringapi.utils;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.Professor;

import java.awt.print.Book;
import java.util.Objects;


public class ProfessorSpecification implements Specification<Professor> {

    private CriteriosDePesquisa criterio;

    public ProfessorSpecification(CriteriosDePesquisa criterio) {
        this.criterio = criterio;
    }

    @Override
    public Predicate toPredicate
            (Root<Professor> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (Objects.equals(criterio.getKey(), "valorAula")) {
            Root<Aula> aulaRoot = query.from(Aula.class);
            Join<Aula, Professor> aulaDoProfessor = aulaRoot.join("professor");
            if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_OU_IGUAL_A)) return builder.greaterThanOrEqualTo(aulaDoProfessor.get(criterio.getKey()), (Double) criterio.getValue());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_OU_IGUAL_A)) return builder.lessThanOrEqualTo(aulaDoProfessor.get(criterio.getKey()), (Double)  criterio.getValue());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_QUE)) return builder.lessThan(aulaDoProfessor.get(criterio.getKey()), (Double) criterio.getValue());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_QUE)) return builder.greaterThan(aulaDoProfessor.get(criterio.getKey()), (Double) criterio.getValue());
        } else {
            if (criterio.getOperation().equals(OperacoesDePesquisa.IGUALDADE)) return builder.equal(root.get(criterio.getKey()), criterio.getValue());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_QUE)) return builder.greaterThan(root.get(criterio.getKey()), criterio.getValue().toString());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_QUE)) return builder.lessThan(root.get(criterio.getKey()), criterio.getValue().toString());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_OU_IGUAL_A)) return builder.greaterThanOrEqualTo(root.get(criterio.getKey()), criterio.getValue().toString());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_OU_IGUAL_A)) return builder.lessThanOrEqualTo(root.get(criterio.getKey()), criterio.getValue().toString());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.CONTEM)) return builder.like(root.get(criterio.getKey()), "%" + criterio.getValue() + "%");
            else if (criterio.getOperation().equals(OperacoesDePesquisa.NAO_CONTEM)) return builder.notLike(root.get(criterio.getKey()), "%" + criterio.getValue() + "%");
            else if (criterio.getOperation().equals(OperacoesDePesquisa.INICIA_COM)) return builder.like(root.get(criterio.getKey()), criterio.getValue() + "%");
            else if (criterio.getOperation().equals(OperacoesDePesquisa.TERMINA_COM)) return builder.like(root.get(criterio.getKey()), "%" + criterio.getValue());
            else if (criterio.getOperation().equals(OperacoesDePesquisa.ENTRE)) return builder.between(root.get(criterio.getKey()), criterio.getValue().toString(), criterio.getValue2().toString());
        }

        return null;
    }

    public static Specification<Professor> obterPrecoMinimo(CriteriosDePesquisa criterio) {
        return new ProfessorSpecification(criterio);
    }
}
