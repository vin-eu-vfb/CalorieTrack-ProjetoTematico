package CalorieTrack.Classes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RefeicaoDAO {
    private Connection conexao;

    public RefeicaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // CREATE
    public void adicionarDB(Refeicao refeicao, int idUsuario) throws SQLException {
        String sql = "INSERT INTO Refeicao (idUsuario, nomeRefeicao, horario, caloriasTotais) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, idUsuario);
        stmt.setString(2, refeicao.getNomeRefeicao());
        stmt.setString(3, refeicao.getHorario());
        stmt.setDouble(4, refeicao.getCaloriasTotais());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int idRefeicao = 0;
        if (rs.next()) idRefeicao = rs.getInt(1);

        stmt.close();

        
        for (TabelaNutricional t : refeicao.getTabelaNutricional()) {
            String sql2 = "INSERT INTO Refeicao_Tabela (idRefeicao, idTabela) VALUES (?, ?)";
            PreparedStatement stmt2 = conexao.prepareStatement(sql2);
            stmt2.setInt(1, idRefeicao);
            stmt2.setInt(2, t.getIdTabela());
            stmt2.executeUpdate();
            stmt2.close();
        }
    }

    // READ
    public ArrayList<Refeicao> procurarDB(int idUsuario) throws SQLException {
        ArrayList<Refeicao> lista = new ArrayList<>();

        String sql = "SELECT * FROM Refeicao WHERE idUsuario = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Refeicao r = new Refeicao();
            r.setIdRefeicao(rs.getInt("idRefeicao"));
            r.setNomeRefeicao(rs.getString("nomeRefeicao"));
            r.setHorario(rs.getString("horario"));
            r.setCaloriasTotais(rs.getDouble("caloriasTotais"));

           
            String sql2 = "SELECT t.* FROM TabelaNutricional t "
                        + "INNER JOIN Refeicao_Tabela rt ON t.idTabela = rt.idTabela "
                        + "WHERE rt.idRefeicao = ?";
            PreparedStatement stmt2 = conexao.prepareStatement(sql2);
            stmt2.setInt(1, r.getIdRefeicao());
            ResultSet rs2 = stmt2.executeQuery();

            ArrayList<TabelaNutricional> alimentos = new ArrayList<>();
            while (rs2.next()) {
                TabelaNutricional t = new TabelaNutricional();
                t.setIdTabela(rs2.getInt("idTabela"));
                t.setNome(rs2.getString("nome"));
                t.setCalorias(rs2.getDouble("calorias"));
                t.setCarboidratos(rs2.getDouble("carboidratos"));
                t.setProteinas(rs2.getDouble("proteinas"));
                t.setGorduras(rs2.getDouble("gorduras"));
                alimentos.add(t);
            }
            rs2.close();
            stmt2.close();

            r.setTabelaNutricional(alimentos);
            lista.add(r);
        }

        stmt.close();
        return lista;
    }

    
    public void editarDB(Refeicao refeicao) throws SQLException {
        String sql = "UPDATE Refeicao SET nomeRefeicao = ?, horario = ?, caloriasTotais = ? WHERE idRefeicao = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, refeicao.getNomeRefeicao());
        stmt.setString(2, refeicao.getHorario());
        stmt.setDouble(3, refeicao.getCaloriasTotais());
        stmt.setInt(4, refeicao.getIdRefeicao());
        stmt.executeUpdate();
        stmt.close();
    }

    
    public void removerDB(int idRefeicao) throws SQLException {
       
        String sql1 = "DELETE FROM Refeicao_Tabela WHERE idRefeicao = ?";
        PreparedStatement stmt1 = conexao.prepareStatement(sql1);
        stmt1.setInt(1, idRefeicao);
        stmt1.executeUpdate();
        stmt1.close();

       
        String sql2 = "DELETE FROM Refeicao WHERE idRefeicao = ?";
        PreparedStatement stmt2 = conexao.prepareStatement(sql2);
        stmt2.setInt(1, idRefeicao);
        stmt2.executeUpdate();
        stmt2.close();
    }
}

