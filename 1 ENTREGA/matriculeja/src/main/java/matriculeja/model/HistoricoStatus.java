package matriculeja.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoricoStatus {

    private final StatusSolicitacao status;
    private final String comentario;
    private final String responsavel;
    private final LocalDateTime dataHora;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public HistoricoStatus(StatusSolicitacao status, String comentario, String responsavel) {
        this.status = status;
        this.comentario = comentario;
        this.responsavel = responsavel;
        this.dataHora = LocalDateTime.now();
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public String getComentario() {
        return comentario;
    }

    @Override
    public String toString() {
        return "[" + dataHora.format(FORMATTER) + "] "
                + status.getDescricao()
                + " — " + comentario
                + " (por: " + responsavel + ")";
    }
}
