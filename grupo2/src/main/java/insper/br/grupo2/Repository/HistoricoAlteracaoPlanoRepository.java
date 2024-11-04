package insper.br.grupo2.Repository;

import insper.br.grupo2.Classes.HistoricoAlteracaoPlano;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricoAlteracaoPlanoRepository extends MongoRepository<HistoricoAlteracaoPlano, String> {
    HistoricoAlteracaoPlano findByUsuarioId(String usuarioId);
    HistoricoAlteracaoPlano findByEmail(String email);
}
