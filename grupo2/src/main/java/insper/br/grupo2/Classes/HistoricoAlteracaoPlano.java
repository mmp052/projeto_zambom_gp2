package insper.br.grupo2.Classes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "historico_alteracao_plano")
public class HistoricoAlteracaoPlano {
    @Id
    private String id;

    private String usuarioId;
    private String planoAnterior; // Ex.: "Básico", "Padrão", "Premium"
    private String planoAtual; // Ex.: "Básico", "Padrão", "Premium"
    private LocalDateTime dataAlteracao;
    private String tipoAlteracao; // Ex.: "UPGRADE", "DOWNGRADE", "CANCELAMENTO"
    private String motivoCancelamento; // Ex.: "Mudança de plano", "Insatisfação com o serviço", "Problemas financeiros"

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPlanoAnterior() {
        return planoAnterior;
    }

    public void setPlanoAnterior(String planoAnterior) {
        this.planoAnterior = planoAnterior;
    }

    public String getPlanoAtual() {
        return planoAtual;
    }

    public void setPlanoAtual(String planoAtual) {
        this.planoAtual = planoAtual;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getTipoAlteracao() {
        return tipoAlteracao;
    }

    public void setTipoAlteracao(String tipoAlteracao) {
        this.tipoAlteracao = tipoAlteracao;
    }
}
