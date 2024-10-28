package insper.br.grupo2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planos")
public class Plano {
    @Id
    private String id;

    private String nome;
    private double preco;
    private String beneficios;
    private int dispositivosSimultaneos;

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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public int getDispositivosSimultaneos() {
        return dispositivosSimultaneos;
    }

    public void setDispositivosSimultaneos(int dispositivosSimultaneos) {
        this.dispositivosSimultaneos = dispositivosSimultaneos;
    }

    // Construtores, getters e setters
}