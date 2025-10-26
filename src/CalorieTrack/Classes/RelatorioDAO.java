package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
private Connection conexao;
	
	public RelatorioDAO(Connection conect){
		this.conexao= conect;
		
	}
	public void adicionarBD(Relatorio relatorio) throws SQLException{
		String sql = "INSERT INTO Relatorio (periodoInicio, periodoFim, totalConsumido, totalGasto) VALUES (?, ?, ?, ?)";
		 PreparedStatement stmt = conexao.prepareStatement(sql);
		 stmt.setString(1, relatorio.getPeriodoInicio());
	        stmt.setString(2, relatorio.getPeriodoFim());
	        stmt.setDouble(3, relatorio.getTotalConsumido());
	        stmt.setDouble(4, relatorio.getTotalGasto());
	        stmt.executeUpdate();
	        
	        stmt.close();
	}
	public void removerBD(int ID, int idUsuario) throws SQLException{
	        String sql = "DELETE FROM Relatorio WHERE idRelatorio = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1,ID);
	        stmt.setInt(2,idUsuario);
	        stmt.executeUpdate();
	        stmt.close();
	    
	}
	
	public void editarBD(Relatorio relatorio, int idUsuario, int idRelatorio) throws SQLException {
		
	        String sql = "UPDATE Relatorio SET nome = ?, duracao = ?, intensidade = ?, caloriasGastas = ? WHERE idRelatorio = ? AND idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1, idRelatorio);
	        stmt.setInt(2, idUsuario);
	        stmt.setString(3, relatorio.getPeriodoInicio());
	        stmt.setString(4, relatorio.getPeriodoFim());
	        stmt.setDouble(5, relatorio.getTotalConsumido());
	        stmt.setDouble(6, relatorio.getTotalGasto());
	        stmt.executeUpdate();
	        stmt.close();
	  
	}
	
	public List<Relatorio> buscarBD() throws SQLException {
		String sql = "SELECT * FROM Relatorio WHERE idUsuario = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        List<Relatorio> lista = new ArrayList<>();

        while (rs.next()) {
        	Relatorio relatorio = new Relatorio();
        	relatorio.setIdRelatorio(rs.getInt("idRelatorio"));
        	relatorio.setPeriodoInicio(rs.getString("periodoInicio"));
        	relatorio.setPeriodoFim(rs.getString("periodoFim"));
        	relatorio.setTotalConsumido(rs.getDouble("totalConsumido"));
        	relatorio.setTotalGasto(rs.getDouble("totalGasto"));
            lista.add(relatorio);
        }
        rs.close();
        stmt.close();
        return lista;
		
	}
}
