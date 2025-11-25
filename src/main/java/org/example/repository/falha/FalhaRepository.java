package org.example.repository.falha;

import org.example.database.Conexao;
import org.example.model.Equipamento;
import org.example.model.Falha;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FalhaRepository {

    public Falha save(Falha falha) throws SQLException {

        String query = """
                INSERT INTO Falha
                (equipamentoId,
                dataHoraOcorrencia,
                descricao,
                criticidade,
                status,
                tempoParadaHoras)
                VALUES
                (?, ?, ?, ?, ?, ?);
                """;

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, falha.getEquipamentoId());
            stmt.setTimestamp(2, Timestamp.valueOf(falha.getDataHoraOcorrencia()));
            stmt.setString(3, falha.getDescricao());
            stmt.setString(4, falha.getCriticidade());
            stmt.setString(5, falha.getStatus());
            stmt.setBigDecimal(6, falha.getTempoParadaHoras());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {

                    falha.setId(rs.getLong(1));
                }
            }
        }

        return falha;
    }

    public List<Falha> findAllWithFilters() throws SQLException {

        List<Falha> falhas = new ArrayList<>();

        String query = """
                SELECT
                id,
                equipamentoId,
                dataHoraOcorrencia,
                descricao,
                criticidade,
                status,
                tempoParadaHoras
                FROM Falha
                WHERE
                criticidade = ? AND status = ?;
        """;

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "CRITICA");
            stmt.setString(2, "ABERTA");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Falha falha = new Falha(
                        rs.getLong("id"),
                        rs.getLong("equipamentoId"),
                        rs.getTimestamp("dataHoraOcorrencia").toLocalDateTime(),
                        rs.getString("descricao"),
                        rs.getString("criticidade"),
                        rs.getString("status"),
                        rs.getBigDecimal("tempoParadaHoras")
                );

                falhas.add(falha);
            }
        }

        return falhas;
    }

    public Falha findById(long id) throws SQLException {

        String query = """
                SELECT
                id,
                equipamentoId,
                dataHoraOcorrencia,
                descricao,
                criticidade,
                status,
                tempoParadaHoras
                FROM Falha
                WHERE
                id = ?;
        """;

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Falha(
                        id,
                        rs.getLong("equipamentoId"),
                        rs.getTimestamp("dataHoraOcorrencia").toLocalDateTime(),
                        rs.getString("descricao"),
                        rs.getString("criticidade"),
                        rs.getString("status"),
                        rs.getBigDecimal("tempoParadaHoras")
                );
            }
        }

        return null;
    }

    public void changeStatus(Falha falha, String status) throws SQLException {

        String query = """
                UPDATE Falha
                SET status = ?
                WHERE id = ?;
        """;

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setLong(2, falha.getId());

            stmt.executeUpdate();
        }
    }
}
