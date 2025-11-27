package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReceitaDAO {
    private Connection conexao;
    // ReceitaDAO dao = new ReceitaDAO(conexao);
    
    public ReceitaDAO(Connection conexao) {
        this.conexao = conexao;
    }

   
    public void adicionarDB(Receita receita, int idUsuario) throws SQLException {
        String sql = "INSERT INTO Receita (idUsuario, nome, caloriasTotais) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, idUsuario);
        stmt.setString(2, receita.getNome());
        stmt.setDouble(3, receita.getCaloriasTotais());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int idReceita = 0;
        if (rs.next()) idReceita = rs.getInt(1);
        stmt.close();

       
        for (TabelaNutricional t : receita.getIngredientes()) {
            String sql2 = "INSERT INTO Receita_Tabela (idReceita, idTabela) VALUES (?, ?)";
            PreparedStatement stmt2 = conexao.prepareStatement(sql2);
            stmt2.setInt(1, idReceita);
            stmt2.setInt(2, t.getIdTabela());
            stmt2.executeUpdate();
            stmt2.close();
        }
    }

   
    public ArrayList<Receita> procurarDB(int idUsuario) throws SQLException {
        ArrayList<Receita> lista = new ArrayList<>();

        String sql = "SELECT * FROM Receita WHERE idUsuario = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Receita r = new Receita();
            r.setIdReceita(rs.getInt("idReceita"));
            r.setNome(rs.getString("nome"));
            r.setCaloriasTotais(rs.getDouble("caloriasTotais"));

          
            String sql2 = "SELECT t.* FROM TabelaNutricional t "
                        + "INNER JOIN Receita_Tabela rt ON t.idTabela = rt.idTabela "
                        + "WHERE rt.idReceita = ?";
            PreparedStatement stmt2 = conexao.prepareStatement(sql2);
            stmt2.setInt(1, r.getIdReceita());
            ResultSet rs2 = stmt2.executeQuery();

            ArrayList<TabelaNutricional> ingredientes = new ArrayList<>();
            while (rs2.next()) {
                TabelaNutricional t = new TabelaNutricional();
                t.setIdTabela(rs2.getInt("idTabela"));
                t.setNome(rs2.getString("nome"));
                t.setCalorias(rs2.getDouble("calorias"));
                t.setCarboidratos(rs2.getDouble("carboidratos"));
                t.setProteinas(rs2.getDouble("proteinas"));
                t.setGorduras(rs2.getDouble("gorduras"));
                ingredientes.add(t);
            }

            rs2.close();
            stmt2.close();

            r.setIngredientes(ingredientes);
            lista.add(r);
        }

        stmt.close();
        return lista;
    }

  
    public void editarDB(Receita receita) throws SQLException {
        String sql = "UPDATE Receita SET nome = ?, caloriasTotais = ? WHERE idReceita = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, receita.getNome());
        stmt.setDouble(2, receita.getCaloriasTotais());
        stmt.setInt(3, receita.getIdReceita());
        stmt.executeUpdate();
        stmt.close();
    }

   
    public void removerDB(int idReceita) throws SQLException {
        String sql1 = "DELETE FROM Receita_Tabela WHERE idReceita = ?";
        PreparedStatement stmt1 = conexao.prepareStatement(sql1);
        stmt1.setInt(1, idReceita);
        stmt1.executeUpdate();
        stmt1.close();

        String sql2 = "DELETE FROM Receita WHERE idReceita = ?";
        PreparedStatement stmt2 = conexao.prepareStatement(sql2);
        stmt2.setInt(1, idReceita);
        stmt2.executeUpdate();
        stmt2.close();
    }
}

