package insper.br.grupo2.Service;

import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;






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

    public Plano atualizarPlano(String id, Map<String, Object> alteracoes) {
        Optional<Plano> planoOpt = planoRepository.findById(id);
        if (!planoOpt.isPresent()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Plano não encontrado para atualização");
        }
        for (String key : alteracoes.keySet()) {
            switch (key) {
                case "nome":
                    String nome = (String) alteracoes.get(key);
                    if (nome == null || nome.isEmpty()){
                        throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Nome de plano inválido");
                    }
                    planoOpt.get().setNome(nome);

                case "preco":
                    double preco = (double) alteracoes.get(key);
                    if (preco <= 0) {
                        throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Preço de plano inválido");
                    }
                    planoOpt.get().setPreco(preco);

                case "beneficios":
                    String beneficios = (String) alteracoes.get(key);
                    if (beneficios == null || beneficios.isEmpty()){
                        throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Benefícios de plano inválidos");
                    }
                    planoOpt.get().setBeneficios(beneficios);

                case "dispositivosSimultaneos":
                    int dispositivosSimultaneos = (int) alteracoes.get(key);
                    if (dispositivosSimultaneos <= 0) {
                        throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Número de dispositivos simultâneos inválido");
                    }
                    planoOpt.get().setDispositivosSimultaneos(dispositivosSimultaneos);

                default:
                    throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Campo inválido para atualização");
            }
        }
        planoOpt.get().setId(id);
        return planoRepository.save(planoOpt.get());
    }

    public void deletarPlano(String id) {
        if (!planoRepository.existsById(id)) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Plano não encontrado para exclusão");
        }
        planoRepository.deleteById(id);
    }
}
