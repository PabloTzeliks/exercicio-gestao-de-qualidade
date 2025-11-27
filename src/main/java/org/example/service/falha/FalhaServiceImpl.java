package org.example.service.falha;

import org.example.model.Equipamento;
import org.example.model.Falha;
import org.example.repository.falha.FalhaRepository;
import org.example.service.equipamento.EquipamentoService;
import org.example.service.equipamento.EquipamentoServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class FalhaServiceImpl implements FalhaService{

    private EquipamentoService equipamentoService;
    private FalhaRepository repository;

    public FalhaServiceImpl(FalhaRepository repository, EquipamentoService equipamentoService) {
        this.repository = repository;
        this.equipamentoService = equipamentoService;
    }

    @Override
    public Falha registrarNovaFalha(Falha falha) throws SQLException {

        var equipamento = buscarEquipamentoOuErro(falha.getEquipamentoId());

        falha.setStatus("ABERTA");

        var falhaPersistency = repository.save(falha);

        if (falhaPersistency.getCriticidade().equals("CRITICA")) {

            equipamentoService.alterarStatusEquipamento(equipamento, "EM_MANUTENCAO");
        }

        return falha;
    }

    private Equipamento buscarEquipamentoOuErro(long id) throws SQLException {

        try {

            return equipamentoService.buscarEquipamentoPorId(id);
        } catch (RuntimeException ex) {

            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public List<Falha> buscarFalhasCriticasAbertas() throws SQLException {

        return repository.findAllWithFilters();
    }

    @Override
    public Falha buscarFalhaPorId(long id) throws SQLException {

        var falhaPersistency = repository.findById(id);

        if (falhaPersistency == null) {

            throw new RuntimeException("Falha não encontrada!");
        }

        return falhaPersistency;
    }

    @Override
    public void alterarStatusFalha(Falha falha, String novoStatus) throws SQLException {

        repository.changeStatus(falha, novoStatus);
    }
}
