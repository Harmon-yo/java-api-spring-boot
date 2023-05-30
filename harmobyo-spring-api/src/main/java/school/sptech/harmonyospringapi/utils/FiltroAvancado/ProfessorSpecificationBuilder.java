package school.sptech.harmonyospringapi.utils.FiltroAvancado;

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
            List<Object> values = List.of(((String) value).split("&"));

            if (values.size() == 2) {
                this.parametros.add(new CriteriosDePesquisa(false, key, op, values.get(0), values.get(1)));
            } else {
                for (int i = 0; i < values.size(); i++) {
                    value = values.get(i);
                    if (op == OperacoesDePesquisa.IGUALDADE) {

                        if (values.size() == 1) {
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


                    }

                    if (i > 0) this.parametros.add(new CriteriosDePesquisa(true, key, op, value));
                    else this.parametros.add(new CriteriosDePesquisa(false, key, op, value));
                }
            }
        }
        return;
    }


    public Specification<Professor> build() {
        if (this.parametros.size() == 0) return null;

        Specification<Professor> resultado = new ProfessorSpecification(this.parametros.get(0));

        for (int i = 1; i < this.parametros.size(); i++) {
            resultado = parametros.get(i).isOrPredicate()
                    ? Specification.where(resultado).or(new ProfessorSpecification(parametros.get(i)))
                    : Specification.where(resultado).and(new ProfessorSpecification(parametros.get(i)));
        }

        return resultado;
    }

    public List<CriteriosDePesquisa> getParametros() {
        return parametros;
    }
}
