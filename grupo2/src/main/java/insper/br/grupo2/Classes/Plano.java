package insper.br.grupo2.Classes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planos")
public class Plano {
    @Id
    private String id;

    private String nome; // Ex.: "Básico", "Padrão", "Premium"
    private double preco; // Ex.: 15.90, 29.90, 45.90
    private String beneficios; // Ex.: "Qualidade de imagem HD, 1 tela simultânea e anuncios", "Qualidade de imagem Full HD, 2 telas simultâneas e sem anuncios", "Qualidade de imagem 4K, 4 telas simultâneas e sem anuncios"
    private int dispositivosSimultaneos; // Ex.: 1, 2, 4

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