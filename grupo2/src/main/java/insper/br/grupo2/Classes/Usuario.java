package insper.br.grupo2.Classes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;

    private String nome;
    private String email;
    private Plano planoAtivo;
    private boolean planoAtivoStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Plano getPlanoAtivo() {
        return planoAtivo;
    }

    public void setPlanoAtivo(Plano planoAtivo) {
        this.planoAtivo = planoAtivo;
    }

    public boolean isPlanoAtivoStatus() {
        return planoAtivoStatus;
    }

    public void setPlanoAtivoStatus(boolean planoAtivoStatus) {
        this.planoAtivoStatus = planoAtivoStatus;
    }

    // Construtores, getters e setters
}