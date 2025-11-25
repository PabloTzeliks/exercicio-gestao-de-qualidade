package org.example.repository.equipamento;

import org.example.database.Conexao;
import org.example.model.Equipamento;

import java.sql.*;

public class EquipamentoRepository {

    public Equipamento save(Equipamento equipamento) throws SQLException {

        String query = """
                INSERT INTO Equipamento
                (nome,
                numeroDeSerie,
                areaSetor,
                statusOperacional)
                VALUES
                (?, ?, ?, ?);
                """;

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, equipamento.getNome());
            stmt.setString(2, equipamento.getNumeroDeSerie());
            stmt.setString(3, equipamento.getAreaSetor());
            stmt.setString(4, equipamento.getStatusOperacional());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {

                    equipamento.setId(rs.getLong(1));
                }
            }
        }

        return equipamento;
    }
}
