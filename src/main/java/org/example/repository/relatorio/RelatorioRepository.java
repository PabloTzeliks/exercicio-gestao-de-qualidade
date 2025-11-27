package org.example.repository.relatorio;

import org.example.database.Conexao;
import org.example.dto.RelatorioParadaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
