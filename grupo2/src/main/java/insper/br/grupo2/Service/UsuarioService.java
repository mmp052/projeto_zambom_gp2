package insper.br.grupo2.Service;

import insper.br.grupo2.Classes.HistoricoAlteracaoPlano;
import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Classes.Usuario;
import insper.br.grupo2.Repository.HistoricoAlteracaoPlanoRepository;
import insper.br.grupo2.Repository.PlanoRepository;
import insper.br.grupo2.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private HistoricoAlteracaoPlanoRepository historicoRepository;

    public Plano getPlanoUsuario(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get().getPlanoAtivo();
        }
        throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado");
    }

    public HistoricoAlteracaoPlano getHistoricoUsuario(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            HistoricoAlteracaoPlano historico =  historicoRepository.findByEmail(email);
            if (historico != null) {
                return historico;
            }
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Histórico não encontrado");

        }
        throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado");
    }

    public Usuario associarPlanoAUsuario(String email, String idPlano) {
        System.out.println(idPlano);
        Usuario usuario;
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            usuario = new Usuario();
            usuario.setEmail(email);
        }
        else{
            usuario = usuarioOpt.get();
        }
        Plano novoPlano = planoRepository.findById(idPlano).orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Plano não encontrado"));
        Plano planoAnterior = usuario.getPlanoAtivo();
        usuario.setPlanoAtivo(novoPlano);
        usuario.setPlanoAtivoStatus(true);
        usuarioRepository.save(usuario);

        HistoricoAlteracaoPlano historico = historicoRepository.findByEmail(email);
        if (historico == null) {
            historico = new HistoricoAlteracaoPlano();
            historico.setEmail(email);
            historico.setHistoricoPlanos(new ArrayList<Plano>());
        }
        ArrayList<Plano> historicoPlanos = historico.getHistoricoPlanos();
        historicoPlanos.add(novoPlano);
        historico.setHistoricoPlanos(historicoPlanos);
        historico.setEmail(usuario.getEmail());
        historico.setPlanoAnterior(planoAnterior != null ? planoAnterior.getNome() : "N/A");
        historico.setPlanoAtual(novoPlano.getNome());
        historico.setDataAlteracao(LocalDateTime.now());

        
        historico.setTipoAlteracao(planoAnterior != null ? planoAnterior.getNome(): "ASSOCIAÇÃO");
        if (planoAnterior == null){
            System.out.println(1);
        }
        else if (planoAnterior.getPreco() > novoPlano.getPreco()) {
            historico.setTipoAlteracao("DOWNGRADE");
        } else if (planoAnterior.getPreco() < novoPlano.getPreco()) {
            historico.setTipoAlteracao("UPGRADE");
        } else {
            historico.setTipoAlteracao("ASSOCIAÇÃO");
        }

        historicoRepository.save(historico);
        return usuario;


    }

    public void cancelarPlanoUsuario(String email, String motivo) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setPlanoAtivoStatus(false);
            usuarioRepository.save(usuario);

            // Registrar cancelamento no histórico
            HistoricoAlteracaoPlano historico = new HistoricoAlteracaoPlano();
            historico.setEmail(usuario.getEmail());
            historico.setPlanoAnterior(usuario.getPlanoAtivo().getNome());
            historico.setPlanoAtual("N/A");
            historico.setDataAlteracao(LocalDateTime.now());
            historico.setTipoAlteracao("CANCELAMENTO");
            if(motivo == null) {
                motivo = "Motivo não informado";
            }
            historico.setMotivoCancelamento(motivo);

            historicoRepository.save(historico);
        } else {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }
}
