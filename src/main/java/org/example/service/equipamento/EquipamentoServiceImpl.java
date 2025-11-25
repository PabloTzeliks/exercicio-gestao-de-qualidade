package org.example.service.equipamento;

import org.example.model.Equipamento;
import org.example.repository.equipamento.EquipamentoRepository;

import java.sql.SQLException;

public class EquipamentoServiceImpl implements EquipamentoService{

    private final EquipamentoRepository repository;

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

        System.out.println("Buscando Equipamento por ID: " + id);

        Equipamento equipamento = repository.findById(id);

        if (equipamento == null) {

            throw new RuntimeException("Equipamento não encontrado!");
        }

        return equipamento;
    }

    @Override
    public void alterarStatusEquipamento(Equipamento equipamento, String novoStatus) throws SQLException {

        repository.changeStatus(equipamento, novoStatus);
    }
}
