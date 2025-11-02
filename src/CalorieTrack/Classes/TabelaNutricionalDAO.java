package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabelaNutricionalDAO {
	private Connection conexao;
	TabelaNutricionalDAO dao = new TabelaNutricionalDAO(conexao);
	
	public TabelaNutricionalDAO(Connection conect){
		this.conexao= conect;
		
	}
	public void adicionarBD(TabelaNutricional tabela) throws SQLException{
		String sql = "INSERT INTO TabelaNutricional (nome, calorias, carboidratos, proteinas, gorduras) VALUES (?, ?, ?, ?, ?)";
		 PreparedStatement stmt = conexao.prepareStatement(sql);
		 stmt.setString(1, tabela.getNome());
	        stmt.setDouble(2, tabela.getCalorias());
	        stmt.setDouble(3, tabela.getCarboidratos());
	        stmt.setDouble(4, tabela.getProteinas());
	        stmt.setDouble(5, tabela.getGorduras());
	        stmt.executeUpdate();
	        
	        stmt.close();
	}
	public void removerBD(int ID, int idUsuario) throws SQLException{
	        String sql = "DELETE FROM TabelaNutricional WHERE idTabela = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1,ID);
	        stmt.setInt(2,idUsuario);
	        stmt.executeUpdate();
	        stmt.close();
	    
	}
	
	public void editarBD(TabelaNutricional tabela, int idUsuario, int idTabelaNutricional) throws SQLException {
		
	        String sql = "UPDATE TabelaNutricional SET nome = ?, calorias = ?, carboidratos = ?, proteinas = ?, gorduras = ? WHERE idTabelaNutricional = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1, idTabelaNutricional);
	        stmt.setInt(2, idUsuario);
	        stmt.setString(1,tabela.getNome());
	        stmt.setDouble(2, tabela.getCalorias());
	        stmt.setDouble(3, tabela.getCarboidratos());
	        stmt.setDouble(4, tabela.getProteinas());
	        stmt.setDouble(5, tabela.getGorduras());
	        stmt.executeUpdate();
	        stmt.close();
	  
	}
	
	public List<TabelaNutricional> buscarBD() throws SQLException {
		String sql = "SELECT * FROM TabelaNutricional WHERE idUsuario = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        List<TabelaNutricional> lista = new ArrayList<>();

        while (rs.next()) {
            TabelaNutricional tabela = new TabelaNutricional();
            tabela.setIdTabela(rs.getInt("idTabelaNutricional"));
            tabela.setNome(rs.getString("nome"));
            tabela.setCalorias(rs.getDouble("calorias"));
            tabela.setCarboidratos(rs.getDouble("carboidratos"));
            tabela.setProteinas(rs.getDouble("proteinas"));
            tabela.setGorduras(rs.getDouble("gorduras"));
            lista.add(tabela);
        }
        rs.close();
        stmt.close();
        return lista;
		
	}

}
