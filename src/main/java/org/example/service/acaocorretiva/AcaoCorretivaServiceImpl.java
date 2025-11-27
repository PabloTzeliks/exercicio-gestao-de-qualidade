package org.example.service.acaocorretiva;

import org.example.model.AcaoCorretiva;
import org.example.model.Falha;
import org.example.repository.acaocorretiva.AcaoCorretivaRepository;
import org.example.service.equipamento.EquipamentoService;
import org.example.service.falha.FalhaService;

import java.sql.SQLException;

public class AcaoCorretivaServiceImpl implements AcaoCorretivaService{

    private AcaoCorretivaRepository repository;
    private EquipamentoService equipamentoService;
    private FalhaService falhaService;

    public AcaoCorretivaServiceImpl(AcaoCorretivaRepository repository, EquipamentoService equipamentoService, FalhaService falhaService) {
        this.repository = repository;
        this.equipamentoService = equipamentoService;
        this.falhaService = falhaService;
    }

    @Override
    public AcaoCorretiva registrarConclusaoDeAcao(AcaoCorretiva acao) throws SQLException {

        var falha = buscarFalhaOuErro(acao.getFalhaId());

        var acaoPersistency = repository.save(acao);

        falha.setStatus("RESOLVIDA");

        falhaService.alterarStatusFalha(falha, "RESOLVIDA");

        if (falha.getCriticidade().equals("CRITICA")) {

            var equipamentoPersistency = equipamentoService.buscarEquipamentoPorId(falha.getEquipamentoId());

            equipamentoService.alterarStatusEquipamento(equipamentoPersistency, "OPERACIONAL");
        }

        return acaoPersistency;
    }

    private Falha buscarFalhaOuErro(long id) throws SQLException {

        try {

            return falhaService.buscarFalhaPorId(id);
        } catch (RuntimeException ex) {

            throw new RuntimeException(ex.getMessage());
        }
    }
}
