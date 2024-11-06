package insper.br.grupo2.Classes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    private String id;

    private String email;
    private Plano planoAtivo;
    private boolean planoAtivoStatus;

    public boolean getPlanoAtivoStatus() {
        return planoAtivoStatus;
    }
    public void setPlanoAtivoStatus(boolean planoAtivoStatus) {
        this.planoAtivoStatus = planoAtivoStatus;
    }


}