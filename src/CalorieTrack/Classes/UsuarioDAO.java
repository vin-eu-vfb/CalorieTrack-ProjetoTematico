package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO {
	 private Connection conexao;
	 UsuarioDAO dao = new UsuarioDAO(conexao);
	 
	public UsuarioDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	 
	public void adicionarDB(Usuario usuario) throws SQLException {
	    String sql = "INSERT INTO Usuario (nome, email, senha, peso, altura, idade, sexo, metacalorica) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    stmt.setString(1, usuario.getNome());
	    stmt.setString(2, usuario.getEmail());
	    stmt.setString(3, usuario.getSenha());
	    stmt.setDouble(4, usuario.getPeso());
	    stmt.setDouble(5, usuario.getAltura());
	    stmt.setInt(6, usuario.getIdade());
	    stmt.setString(7, usuario.getSexo());
	    stmt.setDouble(8, usuario.getMetacalorica());
	    stmt.executeUpdate();

	   
	    ResultSet rs = stmt.getGeneratedKeys();
	    int idUsuario = 0;
	    if (rs.next()) idUsuario = rs.getInt(1);
	    stmt.close();

	    for (Exercicio e : usuario.getExercicios()) {
	        String sqlEx = "INSERT INTO Usuario_Exercicio (idUsuario, idExercicio) VALUES (?, ?)";
	        PreparedStatement stmtEx = conexao.prepareStatement(sqlEx);
	        stmtEx.setInt(1, idUsuario);
	        stmtEx.setInt(2, e.getIdExercicio());
	        stmtEx.executeUpdate();
	        stmtEx.close();
	    }

	    for (Refeicao r : usuario.getRefeicoes()) {
	        String sqlRef = "INSERT INTO Usuario_Refeicao (idUsuario, idRefeicao) VALUES (?, ?)";
	        PreparedStatement stmtRef = conexao.prepareStatement(sqlRef);
	        stmtRef.setInt(1, idUsuario);
	        stmtRef.setInt(2, r.getIdRefeicao());
	        stmtRef.executeUpdate();
	        stmtRef.close();
	    }

	    for (Receita rec : usuario.getReceitas()) {
	        String sqlRec = "INSERT INTO Usuario_Receita (idUsuario, idReceita) VALUES (?, ?)";
	        PreparedStatement stmtRec = conexao.prepareStatement(sqlRec);
	        stmtRec.setInt(1, idUsuario);
	        stmtRec.setInt(2, rec.getIdReceita());
	        stmtRec.executeUpdate();
	        stmtRec.close();
	    }

	    for (RegistroCalorias reg : usuario.getRegistros()) {
	        String sqlReg = "INSERT INTO Usuario_RegistroCalorias (idUsuario, idRegistro) VALUES (?, ?)";
	        PreparedStatement stmtReg = conexao.prepareStatement(sqlReg);
	        stmtReg.setInt(1, idUsuario);
	        stmtReg.setInt(2, reg.getIdRegistro());
	        stmtReg.executeUpdate();
	        stmtReg.close();
	    }

	    for (Relatorio rel : usuario.getRelatorios()) {
	        String sqlRel = "INSERT INTO Usuario_Relatorio (idUsuario, idRelatorio) VALUES (?, ?)";
	        PreparedStatement stmtRel = conexao.prepareStatement(sqlRel);
	        stmtRel.setInt(1, idUsuario);
	        stmtRel.setInt(2, rel.getIdRelatorio());
	        stmtRel.executeUpdate();
	        stmtRel.close();
	    }

	    for (Perfil p : usuario.getPerfil()) {
	        String sqlP = "INSERT INTO Usuario_Perfil (idUsuario, idPerfil) VALUES (?, ?)";
	        PreparedStatement stmtP = conexao.prepareStatement(sqlP);
	        stmtP.setInt(1, idUsuario);
	        stmtP.setInt(2, p.getIdPerfil());
	        stmtP.executeUpdate();
	        stmtP.close();
	    }

	    for (Conquista c : usuario.getConquista()) {
	        String sqlC = "INSERT INTO Usuario_Conquista (idUsuario, idConquista) VALUES (?, ?)";
	        PreparedStatement stmtC = conexao.prepareStatement(sqlC);
	        stmtC.setInt(1, idUsuario);
	        stmtC.setInt(2, c.getIdConquista());
	        stmtC.executeUpdate();
	        stmtC.close();
	    }
	}

	        
	        
	    
	public Usuario procurarPorId(int idUsuario) throws SQLException {
	    Usuario usuario = null;

	  
	    String sql = "SELECT * FROM Usuario WHERE idUsuario = ?";
	    PreparedStatement stmt = conexao.prepareStatement(sql);
	    stmt.setInt(1, idUsuario);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) {
	        usuario = new Usuario();
	        usuario.setNome(rs.getString("nome"));
	        usuario.setEmail(rs.getString("email"));
	        usuario.setSenha(rs.getString("senha"));
	        usuario.setPeso(rs.getDouble("peso"));
	        usuario.setAltura(rs.getDouble("altura"));
	        usuario.setIdade(rs.getInt("idade"));
	        usuario.setSexo(rs.getString("sexo"));
	        usuario.setMetacalorica(rs.getDouble("metacalorica"));
	    }
	    stmt.close();

	    if (usuario == null) return null; 

	   
	    String sqlEx = """
	        SELECT e.* FROM Exercicio e
	        INNER JOIN Usuario_Exercicio ue ON e.idExercicio = ue.idExercicio
	        WHERE ue.idUsuario = ?
	    """;
	    PreparedStatement stmtEx = conexao.prepareStatement(sqlEx);
	    stmtEx.setInt(1, idUsuario);
	    ResultSet rsEx = stmtEx.executeQuery();
	    while (rsEx.next()) {
	        Exercicio e = new Exercicio();
	        e.setIdExercicio(rsEx.getInt("idExercicio"));
	        e.setNome(rsEx.getString("nome"));
	        e.setDuracao(rsEx.getDouble("duracao"));
	        e.setIntensidade(rsEx.getString("intensidade"));
	        e.setCaloriasGastas(rsEx.getDouble("caloriasGastas"));
	        usuario.getExercicios().add(e);
	    }
	    stmtEx.close();

	  
	    String sqlRef = """
	        SELECT r.* FROM Refeicao r
	        INNER JOIN Usuario_Refeicao ur ON r.idRefeicao = ur.idRefeicao
	        WHERE ur.idUsuario = ?
	    """;
	    PreparedStatement stmtRef = conexao.prepareStatement(sqlRef);
	    stmtRef.setInt(1, idUsuario);
	    ResultSet rsRef = stmtRef.executeQuery();
	    while (rsRef.next()) {
	        Refeicao r = new Refeicao();
	        r.setIdRefeicao(rsRef.getInt("idRefeicao"));
	        r.setNomeRefeicao(rsRef.getString("nomeRefeicao"));
	        r.setHorario(rsRef.getString("horario"));
	        r.setCaloriasTotais(rsRef.getDouble("caloriasTotais"));
	        usuario.getRefeicoes().add(r);
	    }
	    stmtRef.close();

	
	    String sqlRec = """
	        SELECT re.* FROM Receita re
	        INNER JOIN Usuario_Receita ur ON re.idReceita = ur.idReceita
	        WHERE ur.idUsuario = ?
	    """;
	    PreparedStatement stmtRec = conexao.prepareStatement(sqlRec);
	    stmtRec.setInt(1, idUsuario);
	    ResultSet rsRec = stmtRec.executeQuery();
	    while (rsRec.next()) {
	        Receita rec = new Receita();
	        rec.setIdReceita(rsRec.getInt("idReceita"));
	        rec.setNome(rsRec.getString("nome"));
	        rec.setCaloriasTotais(rsRec.getDouble("caloriasTotais"));
	        usuario.getReceitas().add(rec);
	    }
	    stmtRec.close();

	 
	    String sqlReg = """
	        SELECT rc.* FROM RegistroCalorias rc
	        INNER JOIN Usuario_RegistroCalorias ur ON rc.idRegistro = ur.idRegistro
	        WHERE ur.idUsuario = ?
	    """;
	    PreparedStatement stmtReg = conexao.prepareStatement(sqlReg);
	    stmtReg.setInt(1, idUsuario);
	    ResultSet rsReg = stmtReg.executeQuery();
	    while (rsReg.next()) {
	        RegistroCalorias rc = new RegistroCalorias();
	        rc.setIdRegistro(rsReg.getInt("idRegistro"));
	        rc.setData(rsReg.getString("data"));
	        rc.setCaloriasConsumidas(rsReg.getDouble("caloriasConsumidas"));
	        rc.setCaloriasGastas(rsReg.getDouble("caloriasGastas"));
	        rc.setSaldoCalorico(rsReg.getDouble("saldoCalorico"));
	        usuario.getRegistros().add(rc);
	    }
	    stmtReg.close();

	    
	    String sqlRel = """
	        SELECT rel.* FROM Relatorio rel
	        INNER JOIN Usuario_Relatorio ur ON rel.idRelatorio = ur.idRelatorio
	        WHERE ur.idUsuario = ?
	    """;
	    PreparedStatement stmtRel = conexao.prepareStatement(sqlRel);
	    stmtRel.setInt(1, idUsuario);
	    ResultSet rsRel = stmtRel.executeQuery();
	    while (rsRel.next()) {
	        Relatorio rel = new Relatorio();
	        rel.setIdRelatorio(rsRel.getInt("idRelatorio"));
	        rel.setPeriodoInicio(rsRel.getString("periodoInicio"));
	        rel.setPeriodoFim(rsRel.getString("periodoFim"));
	        rel.setTotalConsumido(rsRel.getDouble("totalConsumido"));
	        rel.setTotalGasto(rsRel.getDouble("totalGasto"));
	        usuario.getRelatorios().add(rel);
	    }
	    stmtRel.close();

	 
	    String sqlP = """
	        SELECT p.* FROM Perfil p
	        INNER JOIN Usuario_Perfil up ON p.idPerfil = up.idPerfil
	        WHERE up.idUsuario = ?
	    """;
	    PreparedStatement stmtP = conexao.prepareStatement(sqlP);
	    stmtP.setInt(1, idUsuario);
	    ResultSet rsP = stmtP.executeQuery();
	    while (rsP.next()) {
	        Perfil p = new Perfil();
	        p.setIdPerfil(rsP.getInt("idPerfil"));
	        p.setPreferencias(rsP.getString("preferencias"));
	        p.setConfiguracoes(rsP.getString("configuracoes"));
	        usuario.getPerfil().add(p);
	    }
	    stmtP.close();

	   
	    String sqlC = """
	        SELECT c.* FROM Conquista c
	        INNER JOIN Usuario_Conquista uc ON c.idConquista = uc.idConquista
	        WHERE uc.idUsuario = ?
	    """;
	    PreparedStatement stmtC = conexao.prepareStatement(sqlC);
	    stmtC.setInt(1, idUsuario);
	    ResultSet rsC = stmtC.executeQuery();
	    while (rsC.next()) {
	        Conquista c = new Conquista();
	        c.setIdConquista(rsC.getInt("idConquista"));
	        c.setTitulo(rsC.getString("titulo"));
	        c.setDescricao(rsC.getString("descricao"));
	        c.setDataAlcance(rsC.getString("dataAlcance"));
	        usuario.getConquista().add(c);
	    }
	    stmtC.close();

	    return usuario;
	}
	 public void editarDB(Usuario usuario) throws SQLException {
	        String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ?, peso = ? , altura = ?, idade = ?, sexo = ?, metaCalorica = ? WHERE idUsuario = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setString(1, usuario.getNome());
	        stmt.setString(2, usuario.getEmail());
	        stmt.setString(3, usuario.getSenha());
	        stmt.setDouble(4, usuario.getPeso());
	        stmt.setDouble(5, usuario.getAltura());
	        stmt.setInt(6, usuario.getIdade());
	        stmt.setString(7, usuario.getSexo());
	        stmt.setDouble(8, usuario.getMetacalorica());
	        stmt.setInt(9, usuario.getIdUsuario());
	        stmt.executeUpdate();
	        stmt.close();
	    }
	
	 public void deletarDB(int idUsuario) throws SQLException {
		    
		    conexao.setAutoCommit(false);
		    
		    try {
		       
		        String[] tabelasLigacao = {
		            "Usuario_Exercicio",
		            "Usuario_Refeicao",
		            "Usuario_Receita",
		            "Usuario_RegistroCalorias",
		            "Usuario_Relatorio",
		            "Usuario_Perfil",
		            "Usuario_Conquista"
		        };
		        
		        for (String tabela : tabelasLigacao) {
		            String sqlDeleteLigacao = "DELETE FROM " + tabela + " WHERE idUsuario = ?";
		            PreparedStatement stmt = conexao.prepareStatement(sqlDeleteLigacao);
		            stmt.setInt(1, idUsuario);
		            stmt.executeUpdate();
		            stmt.close();
		        }

		        
		        String sqlUsuario = "DELETE FROM Usuario WHERE idUsuario = ?";
		        PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario);
		        stmtUsuario.setInt(1, idUsuario);
		        stmtUsuario.executeUpdate();
		        stmtUsuario.close();

		       
		        conexao.commit();

		    } catch (SQLException e) {
		       
		        conexao.rollback();
		        throw e;
		    } finally {
		       
		        conexao.setAutoCommit(true);
		    }
		}
	 
	 public void deletarExerciciosUsuario(int idUsuario) throws SQLException {
		    String sql = "DELETE FROM Usuario_Exercicio WHERE idUsuario = ?";
		    PreparedStatement stmt = conexao.prepareStatement(sql);
		    stmt.setInt(1, idUsuario);
		    stmt.executeUpdate();
		    stmt.close();
		}

	 public void deletarRefeicoesUsuario(int idUsuario) throws SQLException {
		    String sql = "DELETE FROM Usuario_Refeicoes WHERE idUsuario = ?";
		    PreparedStatement stmt = conexao.prepareStatement(sql);
		    stmt.setInt(1, idUsuario);
		    stmt.executeUpdate();
		    stmt.close();
		}
	 
	 public void deletarReceitasUsuario(int idUsuario) throws SQLException {
		    String sql = "DELETE FROM Usuario_Receitas WHERE idUsuario = ?";
		    PreparedStatement stmt = conexao.prepareStatement(sql);
		    stmt.setInt(1, idUsuario);
		    stmt.executeUpdate();
		    stmt.close();
		}
	 
	 public void deletarRegistroCaloriasUsuario(int idUsuario) throws SQLException {
		    String sql = "DELETE FROM Usuario_Registro WHERE idUsuario = ?";
		    PreparedStatement stmt = conexao.prepareStatement(sql);
		    stmt.setInt(1, idUsuario);
		    stmt.executeUpdate();
		    stmt.close();
		}
	 
	 public void deletarRelatorioUsuario(int idUsuario) throws SQLException {
		    String sql = "DELETE FROM Usuario_Relatorio WHERE idUsuario = ?";
		    PreparedStatement stmt = conexao.prepareStatement(sql);
		    stmt.setInt(1, idUsuario);
		    stmt.executeUpdate();
		    stmt.close();
		}
	 public void deletarPerfilUsuario(int idUsuario) throws SQLException {
		    String sql = "DELETE FROM Usuario_Perfil WHERE idUsuario = ?";
		    PreparedStatement stmt = conexao.prepareStatement(sql);
		    stmt.setInt(1, idUsuario);
		    stmt.executeUpdate();
		    stmt.close();
		}
	 
	 public void deletarConquistaUsuario(int idUsuario) throws SQLException {
		    String sql = "DELETE FROM Usuario_Conquista WHERE idUsuario = ?";
		    PreparedStatement stmt = conexao.prepareStatement(sql);
		    stmt.setInt(1, idUsuario);
		    stmt.executeUpdate();
		    stmt.close();
		}




	
	
	
}
