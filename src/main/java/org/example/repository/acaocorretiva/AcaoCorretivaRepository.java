package org.example.repository.acaocorretiva;

import org.example.database.Conexao;
import org.example.model.AcaoCorretiva;

import java.sql.*;

public class AcaoCorretivaRepository {

    public AcaoCorretiva save(AcaoCorretiva acaoCorretiva) throws SQLException {

        String query = """
                INSERT INTO AcaoCorretiva
                (falhaId,
                dataHoraInicio,
                dataHoraFim,
                responsavel,
                descricaoAcao)
                VALUES
                (?, ?, ?, ?, ?);
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, acaoCorretiva.getFalhaId());
            stmt.setTimestamp(2, Timestamp.valueOf(acaoCorretiva.getDataHoraInicio()));
            stmt.setTimestamp(3, Timestamp.valueOf(acaoCorretiva.getDataHoraFim()));
            stmt.setString(4, acaoCorretiva.getResponsavel());
            stmt.setString(5, acaoCorretiva.getDescricaoArea());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {

                    long id = rs.getLong(1);

                    acaoCorretiva.setId(id);
                }
            }
        }

        return acaoCorretiva;
    }
}
