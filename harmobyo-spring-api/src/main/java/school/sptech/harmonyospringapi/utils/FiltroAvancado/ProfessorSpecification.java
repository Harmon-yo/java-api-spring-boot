package school.sptech.harmonyospringapi.utils.FiltroAvancado;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import school.sptech.harmonyospringapi.domain.*;

import java.util.Objects;

public class ProfessorSpecification implements Specification<Professor> {

    private CriteriosDePesquisa criterio;

    public ProfessorSpecification(CriteriosDePesquisa criterio) {
        this.criterio = criterio;
    }

    @Override
    public Predicate toPredicate
            (Root<Professor> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (Objects.equals(criterio.getKey(), "preco")) {
            return construirCriterioComJoinValorAula(root, builder);
        } else if (Objects.equals(criterio.getKey(), "avaliacao")) {
            return construirCriterioComJoinPararMediaAvaliacao(builder, query, root);
        } else if (Objects.equals(criterio.getKey(), "instrumentos")) {
            return construirCriterioComJoinInstrumentos(builder, query, root);
        } else if (Objects.equals(criterio.getKey(), "instrumentosAula")) {
            return construirCriterioComJoinInstrumentosParaAula(builder, query, root);
        } else {
            return construirCriterioSemJoin(root, builder);
        }
    }

    public Predicate construirCriterioComJoinValorAula(Root<Professor> root, CriteriaBuilder builder) {
        String key = "valorAula";
        Join<Professor, Aula> joinAula = root.join("aulas", JoinType.INNER);

        if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_OU_IGUAL_A)) return builder.greaterThanOrEqualTo(joinAula.get(key), criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_OU_IGUAL_A)) return builder.lessThanOrEqualTo(joinAula.get(key),  criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_QUE)) return builder.lessThan(joinAula.get(key), criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_QUE)) return builder.greaterThan(joinAula.get(key), criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.ENTRE) && Objects.nonNull(criterio.getValue2())) return builder.between(joinAula.get(key), criterio.getValue().toString(), criterio.getValue2().toString());
        return null;
    }

    public Predicate construirCriterioSemJoin(Root<Professor> root, CriteriaBuilder builder) {
        if (criterio.getOperation().equals(OperacoesDePesquisa.IGUALDADE)) return builder.equal(root.get(criterio.getKey()), criterio.getValue());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_QUE)) return builder.greaterThan(root.get(criterio.getKey()), criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_QUE)) return builder.lessThan(root.get(criterio.getKey()), criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.MAIOR_OU_IGUAL_A)) return builder.greaterThanOrEqualTo(root.get(criterio.getKey()), criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.MENOR_OU_IGUAL_A)) return builder.lessThanOrEqualTo(root.get(criterio.getKey()), criterio.getValue().toString());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.CONTEM)) return builder.like(root.get(criterio.getKey()), "%" + criterio.getValue() + "%");
        else if (criterio.getOperation().equals(OperacoesDePesquisa.INICIA_COM)) return builder.like(root.get(criterio.getKey()), criterio.getValue() + "%");
        else if (criterio.getOperation().equals(OperacoesDePesquisa.TERMINA_COM)) return builder.like(root.get(criterio.getKey()), "%" + criterio.getValue());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.ENTRE) && Objects.nonNull(criterio.getValue2())) return builder.between(root.get(criterio.getKey()), criterio.getValue().toString(), criterio.getValue2().toString());

        return null;
    }

    public Predicate construirCriterioComJoinPararMediaAvaliacao(CriteriaBuilder builder, CriteriaQuery<?> query, Root<Professor> root) {
        Subquery<Double> subquery = query.subquery(Double.class);
        Root<Professor> subRoot = subquery.from(Professor.class);
        Join<Professor, Avaliacao> joinAvaliacao2 = subRoot.join("avaliacoesRecebidas", JoinType.INNER);
        subquery.select(builder.avg(joinAvaliacao2.get("valor")));
        subquery.where(builder.equal(subRoot.get("id"), root.get("id")));

        return builder.greaterThanOrEqualTo(subquery, Double.parseDouble((String) criterio.getValue()));
    }

    public Predicate construirCriterioComJoinInstrumentos(CriteriaBuilder builder, CriteriaQuery<?> query, Root<Professor> root) {
        Subquery<Long> subquery = query.subquery(Long.class);
        Root<Professor> subRoot = subquery.from(Professor.class);
        Join<Professor, ProfessorInstrumento> joinProfessorInstrumentos = subRoot.join("instrumentos", JoinType.INNER);
        Predicate possuiOMesmoId = builder.equal(subRoot.get("id"), root.get("id"));
        Predicate possuiOMesmoInstrumento = null;
        Path<String> nomeInstrumento = joinProfessorInstrumentos.get("instrumento").get("nome");

        if (criterio.getOperation().equals(OperacoesDePesquisa.IGUALDADE)) possuiOMesmoInstrumento = builder.equal(nomeInstrumento, criterio.getValue());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.CONTEM)) possuiOMesmoInstrumento = builder.like(nomeInstrumento, "%" + criterio.getValue() + "%");
        else if (criterio.getOperation().equals(OperacoesDePesquisa.INICIA_COM)) possuiOMesmoInstrumento = builder.like(nomeInstrumento, criterio.getValue() + "%");
        else if (criterio.getOperation().equals(OperacoesDePesquisa.TERMINA_COM)) possuiOMesmoInstrumento = builder.like(nomeInstrumento, "%" + criterio.getValue());

        subquery.select(builder.count(joinProfessorInstrumentos))
                .where(possuiOMesmoId, possuiOMesmoInstrumento);

        return builder.equal(subquery, 1L);
    }

    public Predicate construirCriterioComJoinInstrumentosParaAula(CriteriaBuilder builder, CriteriaQuery<?> query, Root<Professor> root) {
        Subquery<Long> subquery = query.subquery(Long.class);
        Root<Professor> subRoot = subquery.from(Professor.class);
        Join<Professor, Aula> joinAula = subRoot.join("aulas", JoinType.INNER);
        Predicate possuiOMesmoId = builder.equal(subRoot.get("id"), root.get("id"));
        Predicate ensinaOInstrumento = null;
        Path<String> nomeInstrumento = joinAula.get("instrumento").get("nome");

        if (criterio.getOperation().equals(OperacoesDePesquisa.IGUALDADE)) ensinaOInstrumento = builder.equal(nomeInstrumento, criterio.getValue());
        else if (criterio.getOperation().equals(OperacoesDePesquisa.CONTEM)) ensinaOInstrumento = builder.like(nomeInstrumento, "%" + criterio.getValue() + "%");
        else if (criterio.getOperation().equals(OperacoesDePesquisa.INICIA_COM)) ensinaOInstrumento = builder.like(nomeInstrumento, criterio.getValue() + "%");
        else if (criterio.getOperation().equals(OperacoesDePesquisa.TERMINA_COM)) ensinaOInstrumento = builder.like(nomeInstrumento, "%" + criterio.getValue());

        subquery.select(builder.count(joinAula))
                .where(possuiOMesmoId, ensinaOInstrumento);

        return builder.greaterThanOrEqualTo(subquery, 1L);
    }
}
