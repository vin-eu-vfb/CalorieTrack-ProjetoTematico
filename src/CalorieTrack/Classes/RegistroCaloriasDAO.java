package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RegistroCaloriasDAO {
    private Connection conexao;
    // RegistroCaloriasDAO dao = new RegistroCaloriasDAO(conexao);
    
    public RegistroCaloriasDAO(Connection conexao) {
        this.conexao = conexao;
    }

   
    public void adicionarDB(RegistroCalorias registro, int idUsuario) throws SQLException {
        String sql = "INSERT INTO RegistroCalorias (idUsuario, data, caloriasConsumidas, caloriasGastas, saldoCalorico) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, idUsuario);
        stmt.setString(2, registro.getData());
        stmt.setDouble(3, registro.getCaloriasConsumidas());
        stmt.setDouble(4, registro.getCaloriasGastas());
        stmt.setDouble(5, registro.getSaldoCalorico());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int idRegistro = 0;
        if (rs.next()) idRegistro = rs.getInt(1);
        stmt.close();

  
        for (Refeicao r : registro.getRefeicoes()) {
            String sql2 = "INSERT INTO Registro_Refeicao (idRegistro, idRefeicao) VALUES (?, ?)";
            PreparedStatement stmt2 = conexao.prepareStatement(sql2);
            stmt2.setInt(1, idRegistro);
            stmt2.setInt(2, r.getIdRefeicao());
            stmt2.executeUpdate();
            stmt2.close();
        }

      
        for (Exercicio e : registro.getExercicios()) {
            String sql3 = "INSERT INTO Registro_Exercicio (idRegistro, idExercicio) VALUES (?, ?)";
            PreparedStatement stmt3 = conexao.prepareStatement(sql3);
            stmt3.setInt(1, idRegistro);
            stmt3.setInt(2, e.getIdExercicio());
            stmt3.executeUpdate();
            stmt3.close();
        }
    }

    // READ
    public ArrayList<RegistroCalorias> procurarDB(int idUsuario) throws SQLException {
        ArrayList<RegistroCalorias> registros = new ArrayList<>();

        String sql = "SELECT * FROM RegistroCalorias WHERE idUsuario = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            RegistroCalorias rc = new RegistroCalorias();
            rc.setIdRegistro(rs.getInt("idRegistro"));
            rc.setData(rs.getString("data"));
            rc.setCaloriasConsumidas(rs.getDouble("caloriasConsumidas"));
            rc.setCaloriasGastas(rs.getDouble("caloriasGastas"));
            rc.setSaldoCalorico(rs.getDouble("saldoCalorico"));

           
            String sql2 = "SELECT r.* FROM Refeicao r " +
                          "INNER JOIN Registro_Refeicao rr ON r.idRefeicao = rr.idRefeicao " +
                          "WHERE rr.idRegistro = ?";
            PreparedStatement stmt2 = conexao.prepareStatement(sql2);
            stmt2.setInt(1, rc.getIdRegistro());
            ResultSet rs2 = stmt2.executeQuery();

            ArrayList<Refeicao> refeicoes = new ArrayList<>();
            while (rs2.next()) {
                Refeicao r = new Refeicao();
                r.setIdRefeicao(rs2.getInt("idRefeicao"));
                r.setNomeRefeicao(rs2.getString("nomeRefeicao"));
                r.setHorario(rs2.getString("horario"));
                r.setCaloriasTotais(rs2.getDouble("caloriasTotais"));
                refeicoes.add(r);
            }
            rs2.close();
            stmt2.close();
            rc.setRefeicoes(refeicoes);

       
            String sql3 = "SELECT e.* FROM Exercicio e " +
                          "INNER JOIN Registro_Exercicio re ON e.idExercicio = re.idExercicio " +
                          "WHERE re.idRegistro = ?";
            PreparedStatement stmt3 = conexao.prepareStatement(sql3);
            stmt3.setInt(1, rc.getIdRegistro());
            ResultSet rs3 = stmt3.executeQuery();

            ArrayList<Exercicio> exercicios = new ArrayList<>();
            while (rs3.next()) {
                Exercicio e = new Exercicio();
                e.setIdExercicio(rs3.getInt("idExercicio"));
                e.setNome(rs3.getString("nome"));
                e.setDuracao(rs3.getDouble("duracao"));
                e.setIntensidade(rs3.getString("intensidade"));
                e.setCaloriasGastas(rs3.getDouble("caloriasGastas"));
                exercicios.add(e);
            }
            rs3.close();
            stmt3.close();
            rc.setExercicios(exercicios);

            registros.add(rc);
        }

        stmt.close();
        return registros;
    }

   
    public void editarDB(RegistroCalorias registro) throws SQLException {
        String sql = "UPDATE RegistroCalorias SET data = ?, caloriasConsumidas = ?, caloriasGastas = ?, saldoCalorico = ? WHERE idRegistro = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, registro.getData());
        stmt.setDouble(2, registro.getCaloriasConsumidas());
        stmt.setDouble(3, registro.getCaloriasGastas());
        stmt.setDouble(4, registro.getSaldoCalorico());
        stmt.setInt(5, registro.getIdRegistro());
        stmt.executeUpdate();
        stmt.close();
    }

 
    public void removerDB(int idRegistro) throws SQLException {
      
        String sql1 = "DELETE FROM Registro_Refeicao WHERE idRegistro = ?";
        PreparedStatement stmt1 = conexao.prepareStatement(sql1);
        stmt1.setInt(1, idRegistro);
        stmt1.executeUpdate();
        stmt1.close();

        String sql2 = "DELETE FROM Registro_Exercicio WHERE idRegistro = ?";
        PreparedStatement stmt2 = conexao.prepareStatement(sql2);
        stmt2.setInt(1, idRegistro);
        stmt2.executeUpdate();
        stmt2.close();

        
        String sql3 = "DELETE FROM RegistroCalorias WHERE idRegistro = ?";
        PreparedStatement stmt3 = conexao.prepareStatement(sql3);
        stmt3.setInt(1, idRegistro);
        stmt3.executeUpdate();
        stmt3.close();
    }
}

