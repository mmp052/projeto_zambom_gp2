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

    @GetMapping()
    public Plano getUsuario(@RequestHeader("Authorization") String authorization){

        String email = TokenUtils.getEmailFromToken(authorization);
        return usuarioService.getPlanoUsuario(email);
    }

    @GetMapping("/historico")
    public HistoricoAlteracaoPlano getHistorico(@RequestHeader("Authorization") String authorization) {
        String email = TokenUtils.getEmailFromToken(authorization);
        return usuarioService.getHistoricoUsuario(email);
    }

    @PostMapping("/associar-plano/{idPlano}")
    public Usuario associarPlano(@PathVariable String idPlano, @RequestHeader("Authorization") String authorization){
        String email = TokenUtils.getEmailFromToken(authorization);
        return usuarioService.associarPlanoAUsuario(email, idPlano);
    }

    @PostMapping("/cancelar-plano")
    public void cancelarPlano(@RequestBody String motivo, @RequestHeader("Authorization") String authorization){
        String email = TokenUtils.getEmailFromToken(authorization);

        usuarioService.cancelarPlanoUsuario(email, motivo);
    }
}

