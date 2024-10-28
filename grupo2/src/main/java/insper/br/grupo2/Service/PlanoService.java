package insper.br.grupo2.Service;

import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    public Plano criarPlano(Plano plano) {
        return planoRepository.save(plano);
    }

    public List<Plano> listarPlanos() {
        return planoRepository.findAll();
    }

    public Optional<Plano> buscarPlanoPorId(String id) {
        return planoRepository.findById(id);
    }

    public Plano atualizarPlano(String id, Plano planoAtualizado) {
        planoAtualizado.setId(id);
        return planoRepository.save(planoAtualizado);
    }

    public void deletarPlano(String id) {
        planoRepository.deleteById(id);
    }
}
