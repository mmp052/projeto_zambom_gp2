package insper.br.grupo2.Controller;

import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Classes.Usuario;
import insper.br.grupo2.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/{usuarioId}/associar-plano")
    public Usuario associarPlano(@PathVariable String usuarioId, @RequestBody Plano novoPlano) {
        return usuarioService.associarPlanoAUsuario(usuarioId, novoPlano);
    }

    @PostMapping("/{usuarioId}/cancelar-plano")
    public void cancelarPlano(@PathVariable String usuarioId, @RequestParam String motivo) {
        usuarioService.cancelarPlanoUsuario(usuarioId, motivo);
    }
}

