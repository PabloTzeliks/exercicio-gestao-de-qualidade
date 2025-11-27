package org.example.repository.relatorio;

import org.example.database.Conexao;
import org.example.dto.RelatorioParadaDTO;
import org.example.model.Equipamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioRepository {

    public List<RelatorioParadaDTO> findAllTimeWasted() throws SQLException {

        List<RelatorioParadaDTO> relatorioParadas = new ArrayList<>();

        String query = """
                SELECT e.id as idEquipamento, e.nome as nomeEquipamento, SUM(f.tempoParadaHoras) as totalHorasParadas
                FROM Equipamento e
                JOIN Falha f ON e.id = f.equipamentoId
                GROUP BY e.id, e.nome;
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                RelatorioParadaDTO dto = new RelatorioParadaDTO(rs.getLong("idEquipamento"), rs.getString("nomeEquipamento"), rs.getDouble("totalHorasParadas"));

                relatorioParadas.add(dto);
            }
        }

        return relatorioParadas;
    }

    public List<Equipamento> findAllNoFailInPeriod(LocalDate inicio, LocalDate fim) throws SQLException {

        List<Equipamento> equipamentos = new ArrayList<>();

        String query = """
                SELECT e.id, e.nome, e.numeroDeSerie, e.areaSetor, e.statusOperacional
                FROM Equipamento e
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM Falha f
                    WHERE e.id = f.equipamentoId
                    AND f.dataHoraOcorrencia BETWEEN ? AND ?
                );
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(inicio));
            stmt.setDate(2, Date.valueOf(fim));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Equipamento equipamento = new Equipamento(rs.getLong("id"), rs.getString("nome"), rs.getString("numeroDeSerie"), rs.getString("areaSetor"), rs.getString("statusOperacional") );

                equipamentos.add(equipamento);
            }
        }

        return equipamentos;
    }
}
