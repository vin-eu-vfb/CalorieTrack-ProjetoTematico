package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO {
	
	private Connection conexao;
	// PerfilDAO dao= new PerfilDAO(conexao);
	
	public PerfilDAO(Connection conect){
		this.conexao= conect;
		
	}
	public void adicionarBD(Perfil perfil) throws SQLException{
		String sql = "INSERT INTO Perfil (preferencias, configuracoes) VALUES (?, ?)";
		 PreparedStatement stmt = conexao.prepareStatement(sql);
		 stmt.setString(1, perfil.getPreferencias());
		 stmt.setString(2, perfil.getConfiguracoes());   
	        stmt.executeUpdate();
	        
	        stmt.close();
	}
	public void removerBD(int ID, int idUsuario) throws SQLException{
	        String sql = "DELETE FROM Perfil WHERE idPerfil = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1,ID);
	        stmt.setInt(2, idUsuario);
	        stmt.executeUpdate();
	        stmt.close();
	    
	}
	
	public void editarBD(Perfil perfil, int idPerfil, int idUsuario) throws SQLException {
		
	        String sql = "UPDATE Perfil SET preferencias = ?, configuracoes = ? WHERE idPerfil = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1, idPerfil);
	        stmt.setInt(2, idUsuario);
	        stmt.setString(3,perfil.getPreferencias());
	        stmt.setString(4,perfil.getConfiguracoes());
	        stmt.executeUpdate();
	        stmt.close();
	  
	}
	
	public List<Perfil> buscarBD() throws SQLException {
		String sql = "SELECT * FROM Perfil WHERE idUsuario = ? ";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        List<Perfil> lista = new ArrayList<>();

        while (rs.next()) {
            Perfil perfil = new Perfil();
            perfil.setIdPerfil(rs.getInt("idPerfil"));
            perfil.setPreferencias(rs.getString("preferencias"));
            perfil.setConfiguracoes(rs.getString("configuracoes"));
            lista.add(perfil);
        }
        rs.close();
        stmt.close();
        return lista;
		
	}
	
}
