package insper.br.grupo2.Repository;
import insper.br.grupo2.Classes.Plano;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanoRepository extends MongoRepository<Plano, String> {
    Plano findByNome(String nome);
}