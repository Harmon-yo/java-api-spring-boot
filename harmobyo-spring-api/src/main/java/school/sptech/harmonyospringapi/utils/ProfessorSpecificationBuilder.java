package school.sptech.harmonyospringapi.utils;

import org.springframework.data.jpa.domain.Specification;
import school.sptech.harmonyospringapi.domain.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfessorSpecificationBuilder {

    private final List<CriteriosDePesquisa> parametros;

    public ProfessorSpecificationBuilder() {
        this.parametros = new ArrayList<>();
    }


    public final void adicionarParametro(String key, String operation, Object value) {
        OperacoesDePesquisa op = OperacoesDePesquisa.getOperacaoSimples(operation);
        if (Objects.nonNull(op)) {
            if (op == OperacoesDePesquisa.IGUALDADE) {
                boolean startWithAsterisk = ((String) value).startsWith("*");
                boolean endWithAsterisk = ((String) value).endsWith("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = OperacoesDePesquisa.CONTEM;
                    value = ((String) value).substring(1, ((String) value).length() - 1);
                } else if (startWithAsterisk) {
                    op = OperacoesDePesquisa.TERMINA_COM;
                    value = ((String) value).substring(1);
                } else if (endWithAsterisk) {
                    op = OperacoesDePesquisa.INICIA_COM;
                    value = ((String) value).substring(0, ((String) value).length() - 1);
                }
            }
            this.parametros.add(new CriteriosDePesquisa(key, op, value));
        }
        return;
    }


    public Specification<Professor> build() {
        if (this.parametros.size() == 0) return null;

        Specification<Professor> resultado = new ProfessorSpecification(this.parametros.get(0));

        for (int i = 1; i < this.parametros.size(); i++) {
            resultado = Specification.where(resultado).and(new ProfessorSpecification(this.parametros.get(i)));
        }

        return resultado;
    }

    public List<CriteriosDePesquisa> getParametros() {
        return parametros;
    }
}
