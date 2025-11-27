package org.example.service.relatorioservice;

import org.example.dto.EquipamentoContagemFalhasDTO;
import org.example.dto.FalhaDetalhadaDTO;
import org.example.dto.RelatorioParadaDTO;
import org.example.model.Equipamento;
import org.example.repository.relatorio.RelatorioRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RelatorioServiceImpl implements RelatorioService{

    private RelatorioRepository repository;

    public RelatorioServiceImpl(RelatorioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RelatorioParadaDTO> gerarRelatorioTempoParada() throws SQLException {

        return repository.findAllTimeWasted();
    }

    @Override
    public List<Equipamento> buscarEquipamentosSemFalhasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        return repository.findAllNoFailInPeriod(dataInicio, dataFim);
    }

    @Override
    public Optional<FalhaDetalhadaDTO> buscarDetalhesCompletosFalha(long falhaId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<EquipamentoContagemFalhasDTO> gerarRelatorioManutencaoPreventiva(int contagemMinimaFalhas) throws SQLException {
        return List.of();
    }
}
