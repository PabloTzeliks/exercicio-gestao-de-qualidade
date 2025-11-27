package org.example.repository.relatorio;

import org.example.database.Conexao;
import org.example.dto.FalhaDetalhadaDTO;
import org.example.dto.RelatorioParadaDTO;
import org.example.model.AcaoCorretiva;
import org.example.model.Equipamento;
import org.example.model.Falha;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public Optional<FalhaDetalhadaDTO> findDetailedFailById(Long id) throws SQLException {

        String query = """
                SELECT f.*, e.*, GROUP_CONCAT(a.descricaoAcao SEPARATOR ';') AS todasAcoes
                FROM Equipamento e
                JOIN Falha f ON e.id = f.equipamentoId
                JOIN AcaoCorretiva a ON f.id = a.falhaId
                WHERE e.id = ?
                GROUP BY f.id, e.id;
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Falha falha = new Falha(
                        rs.getLong("f.id"),
                        rs.getLong("f.equipamentoId"),
                        rs.getTimestamp("f.dataHoraOcorrencia").toLocalDateTime(),
                        rs.getString("f.descricao"),
                        rs.getString("f.criticidade"),
                        rs.getString("f.status"),
                        rs.getBigDecimal("f.tempoParadaHoras")
                );

                Equipamento equipamento = new Equipamento(
                        rs.getLong("e.id"),
                        rs.getString("e.nome"),
                        rs.getString("e.numeroDeSerie"),
                        rs.getString("e.areaSetor"),
                        rs.getString("e.statusOperacional")
                );

                String acoesConcatenadas = rs.getString("todasAcoes");

                List<String> listaDescricoes = acoesConcatenadas != null ? Arrays.asList(acoesConcatenadas.split(";")) : new ArrayList<>();

                return Optional.of(new FalhaDetalhadaDTO(falha, equipamento, listaDescricoes));
            }
        }

        return Optional.empty();
    }
}
