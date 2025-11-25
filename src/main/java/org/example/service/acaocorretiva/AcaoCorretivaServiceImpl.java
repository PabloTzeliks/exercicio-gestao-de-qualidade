package org.example.service.acaocorretiva;

import org.example.model.AcaoCorretiva;
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
        return null;
    }
}
