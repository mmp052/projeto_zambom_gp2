package insper.br.grupo2.Controller;

import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Classes.HistoricoAlteracaoPlano;
import insper.br.grupo2.Classes.Usuario;
import insper.br.grupo2.Common.TokenUtils;
import insper.br.grupo2.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{usuarioId}")
    public Plano getUsuario(@PathVariable String usuarioId, @RequestHeader("Authorization") String authorization){

        String email = TokenUtils.getEmailFromToken(authorization);
        return usuarioService.getPlanoUsuario(email);
    }

    @GetMapping("/{usuarioId}/historico")
    public HistoricoAlteracaoPlano getHistorico(@PathVariable String usuarioId, @RequestHeader("Authorization") String authorization) {
        String email = TokenUtils.getEmailFromToken(authorization);
        return usuarioService.getHistoricoUsuario(email);
    }

    @PostMapping("/associar-plano/{idPlano}")
    public Usuario associarPlano(@RequestParam String idPlano, @RequestHeader("Authorization") String authorization){
        String email = TokenUtils.getEmailFromToken(authorization);
        return usuarioService.associarPlanoAUsuario(email, idPlano);
    }

    @PostMapping("/{usuarioId}/cancelar-plano")
    public void cancelarPlano(@PathVariable String usuarioId, @RequestParam String motivo, @RequestHeader("Authorization") String authorization){
        String email = TokenUtils.getEmailFromToken(authorization);

        usuarioService.cancelarPlanoUsuario(email, motivo);
    }
}

