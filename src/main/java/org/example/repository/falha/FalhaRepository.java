package org.example.repository.falha;

import org.example.database.Conexao;
import org.example.model.Equipamento;
import org.example.model.Falha;

import java.sql.*;

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
}
