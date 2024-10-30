package insper.br.grupo2.Service;

import insper.br.grupo2.Classes.HistoricoAlteracaoPlano;
import insper.br.grupo2.Classes.Plano;
import insper.br.grupo2.Classes.Usuario;
import insper.br.grupo2.Repository.HistoricoAlteracaoPlanoRepository;
import insper.br.grupo2.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistoricoAlteracaoPlanoRepository historicoRepository;

    public Plano getPlanoUsuario(String usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get().getPlanoAtivo();
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public HistoricoAlteracaoPlano getHistoricoUsuario(String usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            HistoricoAlteracaoPlano historico =  historicoRepository.findByUsuarioId(usuarioId);
            if (historico != null) {
                return historico;
            }
            throw new RuntimeException("Histórico não encontrado");
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public Usuario associarPlanoAUsuario(String usuarioId, Plano novoPlano) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Plano planoAnterior = usuario.getPlanoAtivo();
            usuario.setPlanoAtivo(novoPlano);
            usuario.setPlanoAtivoStatus(true);
            usuarioRepository.save(usuario);
            HistoricoAlteracaoPlano historico = new HistoricoAlteracaoPlano();
            historico.setUsuarioId(usuarioId);
            historico.setPlanoAnterior(planoAnterior != null ? planoAnterior.getNome() : "N/A");
            historico.setPlanoAtual(novoPlano.getNome());
            historico.setDataAlteracao(LocalDateTime.now());
            historico.setTipoAlteracao("ASSOCIACAO");
            historicoRepository.save(historico);
            return usuario;
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public void cancelarPlanoUsuario(String usuarioId, String motivo) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setPlanoAtivoStatus(false);
            usuarioRepository.save(usuario);

            // Registrar cancelamento no histórico
            HistoricoAlteracaoPlano historico = new HistoricoAlteracaoPlano();
            historico.setUsuarioId(usuarioId);
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
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
