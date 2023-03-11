package school.sptech.harmonyoentregaveleda;

public class InstrumentoProfessor extends Instrumento{
    private boolean emprestaInstrumento;


    public InstrumentoProfessor(String nome, String nivelConhecimento, String descricaoNaipe, boolean emprestaInstrumento) {
        super(nome, nivelConhecimento, descricaoNaipe);
        this.emprestaInstrumento = emprestaInstrumento;
    }

    public boolean isEmprestaInstrumento() {
        return emprestaInstrumento;
    }

    public void setEmprestaInstrumento(boolean emprestaInstrumento) {
        this.emprestaInstrumento = emprestaInstrumento;
    }
}
