package org.example.service.equipamento;

import org.example.model.Equipamento;
import org.example.repository.equipamento.EquipamentoRepository;

import java.sql.SQLException;

public class EquipamentoServiceImpl implements EquipamentoService{

    private EquipamentoRepository repository;

    public EquipamentoServiceImpl(EquipamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Equipamento criarEquipamento(Equipamento equipamento) throws SQLException {

        equipamento.setStatusOperacional("OPERACIONAL");

        return repository.save(equipamento);
    }

    @Override
    public Equipamento buscarEquipamentoPorId(Long id) throws SQLException {
        return null;
    }
}
