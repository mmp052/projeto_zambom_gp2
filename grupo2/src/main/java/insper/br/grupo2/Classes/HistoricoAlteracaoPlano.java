package insper.br.grupo2.Classes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@Document(collection = "historico_alteracao_plano")
public class HistoricoAlteracaoPlano {
    @Id
    private String id;

    private String email;
    private String planoAnterior; // Ex.: "Básico", "Padrão", "Premium"
    private String planoAtual; // Ex.: "Básico", "Padrão", "Premium"
    private ArrayList<Plano> historicoPlanos;
    private LocalDateTime dataAlteracao;
    private String tipoAlteracao; // Ex.: "UPGRADE", "DOWNGRADE", "CANCELAMENTO"
    private String motivoCancelamento; // Ex.: "Mudança de plano", "Insatisfação com o serviço", "Problemas financeiros"


}
