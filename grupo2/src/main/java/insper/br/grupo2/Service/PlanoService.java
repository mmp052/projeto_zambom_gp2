package insper.br.grupo2.Service;

import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    public Plano criarPlano(Plano plano) {
        if (planoRepository.findByNome(plano.getNome()) != null) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Plano já existe");
        }
        Integer dispositivosSimultaneos = plano.getDispositivosSimultaneos();
        if (plano.getNome() == null || plano.getNome().isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Nome do plano é obrigatório");
        } else if (plano.getPreco() < 0.0) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Preço do plano inválido");
        } else if (plano.getDispositivosSimultaneos() < 0) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Número de dispositivos simultâneos inválido");
        }

        return planoRepository.save(plano);
    }

    public List<Plano> listarPlanos() {
        return planoRepository.findAll();
    }

    public Optional<Plano> buscarPlanoPorId(String id) {
        Optional<Plano> plano = planoRepository.findById(id);
        if (plano.isPresent()) {
            return plano;
        }
        throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Plano não encontrado");
    }

    public Plano atualizarPlano(String id, Plano planoAtualizado) {
        planoAtualizado.setId(id);
        return planoRepository.save(planoAtualizado);
    }

    public void deletarPlano(String id) {
        planoRepository.deleteById(id);
    }
}
