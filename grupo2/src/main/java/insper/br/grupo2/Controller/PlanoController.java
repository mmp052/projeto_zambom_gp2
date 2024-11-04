package insper.br.grupo2.Controller;

import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Service.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @PostMapping
    public Plano criarPlano(@RequestBody Plano plano) {
        return planoService.criarPlano(plano);
    }

    @GetMapping
    public List<Plano> listarPlanos() {
        return planoService.listarPlanos();
    }

    @GetMapping("/{id}")
    public Plano buscarPlanoPorId(@PathVariable String id) {
        return planoService.buscarPlanoPorId(id).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }

    @PutMapping("/{id}")
    public Plano atualizarPlano(@PathVariable String id, @RequestBody Map<String, Object> alteracoes) {
        return planoService.atualizarPlano(id, alteracoes);
    }

    @DeleteMapping("/{id}")
    public void deletarPlano(@PathVariable String id) {
        planoService.deletarPlano(id);
    }
}
