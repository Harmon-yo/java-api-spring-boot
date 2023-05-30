package school.sptech.harmonyospringapi.utils;

public class CriteriosDePesquisa {

    private String key;
    private OperacoesDePesquisa operation;
    private Object value;

    private Object value2;

    public CriteriosDePesquisa(String key, OperacoesDePesquisa operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public OperacoesDePesquisa getOperation() {
        return operation;
    }

    public void setOperation(OperacoesDePesquisa operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }
}
