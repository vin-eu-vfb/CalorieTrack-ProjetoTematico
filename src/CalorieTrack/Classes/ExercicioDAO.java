package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExercicioDAO {
	private Connection conexao;
	ExercicioDAO dao = new ExercicioDAO(conexao);
	
	public ExercicioDAO(Connection conect){
		this.conexao= conect;
		
	}
	public void adicionarBD(Exercicio exercicio, int idUsuario) throws SQLException{
		String sql = "INSERT INTO Exercicio (idUsuario,nome, duracao, intensidade, caloriasGastas) VALUES (?, ?, ?, ?)";
		 PreparedStatement stmt = conexao.prepareStatement(sql);
		 	stmt.setInt(1, idUsuario);
		 	stmt.setString(2, exercicio.getNome());
	        stmt.setDouble(3, exercicio.getDuracao());
	        stmt.setString(4, exercicio.getIntensidade());
	        stmt.setDouble(5, exercicio.getCaloriasGastas());
	        stmt.executeUpdate();
	        
	        stmt.close();
	}
	public void removerBD(int idUsuario, int idExercicio) throws SQLException{
	        String sql = "DELETE FROM Exercicio WHERE idExercicio = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1,idExercicio);
	        stmt.setInt(2, idUsuario);
	        stmt.executeUpdate();
	        stmt.close();
	    
	}
	
	public void editarBD(Exercicio exercicio, int idUsuario, int idExercicio) throws SQLException {
		
	        String sql = "UPDATE Exercicio SET nome = ?, duracao = ?, intensidade = ?, caloriasGastas = ?  WHERE idExercicio = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1, idExercicio);
	        stmt.setInt(2, idUsuario);
	        stmt.setString(2, exercicio.getNome());
	        stmt.setDouble(3, exercicio.getDuracao());
	        stmt.setString(4, exercicio.getIntensidade());
	        stmt.setDouble(5, exercicio.getCaloriasGastas());
	        stmt.executeUpdate();
	        stmt.close();
	  
	}
	
	public List<Exercicio> buscarBD() throws SQLException {
		String sql = "SELECT * FROM Exercicio WHERE idUsuario = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        List<Exercicio> lista = new ArrayList<>();

        while (rs.next()) {
            Exercicio exercicio = new Exercicio();
            exercicio.setIdExercicio(rs.getInt("idExercicio"));
            exercicio.setNome(rs.getString("nome"));
            exercicio.setDuracao(rs.getDouble("duracao"));
            exercicio.setIntensidade(rs.getString("intensidade"));
            exercicio.setCaloriasGastas(rs.getDouble("caloriasGastas"));
            lista.add(exercicio);
        }
        rs.close();
        stmt.close();
        return lista;
		
	}
	
	
	
	
	
	
}
