package school.sptech.harmonyoentregaveleda;

import java.time.LocalDateTime;

public class EstadoConta {
    private boolean ativo;
    private boolean online;
    private LocalDateTime ultimaVezOnline;

    public EstadoConta() {
        this.ativo = true;
        this.online = false;
        this.ultimaVezOnline = LocalDateTime.now();
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isOnline() {
        return online;
    }

    public LocalDateTime getUltimaVezOnline() {
        return ultimaVezOnline;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setUltimaVezOnline(LocalDateTime ultimaVezOnline) {
        this.ultimaVezOnline = ultimaVezOnline;
    }
}
