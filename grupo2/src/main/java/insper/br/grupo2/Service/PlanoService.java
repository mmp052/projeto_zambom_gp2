package insper.br.grupo2.Service;

import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    // Lista de nomes de planos válidos
    private static final List<String> NOMES_PLANOS_VALIDOS = Arrays.asList("Básico", "Padrão", "Premium");

    // Map para preços esperados de cada plano
    private static final Map<String, Double> PRECOS_PLANOS_ESPERADOS = new HashMap<>();
    static {
        PRECOS_PLANOS_ESPERADOS.put("Básico", 15.90);
        PRECOS_PLANOS_ESPERADOS.put("Padrão", 29.90);
        PRECOS_PLANOS_ESPERADOS.put("Premium", 45.90);
    }

    // Map para benefícios esperados de acordo com o nome do plano e dispositivos simultâneos
    private static final Map<String, String> BENEFICIOS_ESPERADOS = new HashMap<>();
    private static final Map<String, Integer> DISPOSITIVOS_ESPERADOS = new HashMap<>();
    static {
        BENEFICIOS_ESPERADOS.put("Básico", "Qualidade de imagem HD, 1 tela simultânea e anuncios");
        BENEFICIOS_ESPERADOS.put("Padrão", "Qualidade de imagem Full HD, 2 telas simultâneas e sem anuncios");
        BENEFICIOS_ESPERADOS.put("Premium", "Qualidade de imagem 4K, 4 telas simultâneas e sem anuncios");

        DISPOSITIVOS_ESPERADOS.put("Básico", 1);
        DISPOSITIVOS_ESPERADOS.put("Padrão", 2);
        DISPOSITIVOS_ESPERADOS.put("Premium", 4);
    }






    public Plano criarPlano(Plano plano) {
        if (!(plano.getNome() != null && !plano.getNome().isEmpty()
                && plano.getPreco() > 0
                && plano.getBeneficios() != null && !plano.getBeneficios().isEmpty()
                && plano.getDispositivosSimultaneos() > 0)){
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Dados do plano inválidos");
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
        if (!(planoAtualizado.getNome() != null && !planoAtualizado.getNome().isEmpty()
                && planoAtualizado.getPreco() > 0
                && planoAtualizado.getBeneficios() != null && !planoAtualizado.getBeneficios().isEmpty()
                && planoAtualizado.getDispositivosSimultaneos() > 0)){
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Dados do plano inválidos");
        }
        planoAtualizado.setId(id);
        return planoRepository.save(planoAtualizado);
    }

    public void deletarPlano(String id) {
        if (!planoRepository.existsById(id)) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Plano não encontrado para exclusão");
        }
        planoRepository.deleteById(id);
    }
}
