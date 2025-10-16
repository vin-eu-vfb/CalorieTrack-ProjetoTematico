package CalorieTrack.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Funcoes {
	private ArrayList<Usuario> usuarios = new ArrayList<>();
	
	public Funcoes() {
		
	}
	
	//LOGIN
	
	public boolean cadastrarUsuario(Scanner sc) {
		Usuario temp = new Usuario();
		Usuario novoUsuario = temp.criarUsuario(sc);
		
		if (novoUsuario == null) {
            return false;
        }
		
		return cadastrarUsuario(novoUsuario);
	}
	
	public boolean cadastrarUsuario(Usuario novoUsuario) {
		if (novoUsuario == null) {
			return false;
		}
		
		Connection conn = ConexaoBD.conectar();
		
		try {
			String sqlVerifica = "SELECT id_usuario FROM Usuario WHERE email = ?";
			PreparedStatement psVerifica = conn.prepareStatement(sqlVerifica);
			psVerifica.setString(1, novoUsuario.getEmail());
			ResultSet rs = psVerifica.executeQuery();
			
			if (rs.next()) {
				System.out.println("Email já cadastrado!");
				return false;
			}
			
			String sql = "INSERT INTO Usuario (nome, email, senha, peso, altura, idade, sexo, meta_calorica) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, novoUsuario.getNome());
			ps.setString(2, novoUsuario.getEmail());
			ps.setString(3, novoUsuario.getSenha());
			ps.setDouble(4, novoUsuario.getPeso());
			ps.setDouble(5, novoUsuario.getAltura());
			ps.setInt(6, novoUsuario.getIdade());
			ps.setString(7, novoUsuario.getSexo());
			ps.setDouble(8, novoUsuario.getMetacalorica());
			
			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				System.out.println("Usuário cadastrado no banco de dados com sucesso!");
				usuarios.add(novoUsuario); 
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar no banco: " + e.getMessage());
			e.printStackTrace();
		} finally {
			ConexaoBD.fecharConexao(conn);
		}
		
		return false;
	}
	
	public Usuario buscarUsuario(String login, String senha) {
		Connection conn = ConexaoBD.conectar();
		
		if (conn != null) {
			try {
				String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, login);
				ps.setString(2, senha);
				
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					Usuario usuario = new Usuario();
					usuario.setIdUsuario(rs.getInt("id_usuario"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setPeso(rs.getDouble("peso"));
					usuario.setAltura(rs.getDouble("altura"));
					usuario.setIdade(rs.getInt("idade"));
					usuario.setSexo(rs.getString("sexo"));
					usuario.setMetacalorica(rs.getDouble("meta_calorica"));
					
					System.out.println("Usuário encontrado no banco de dados!");
					return usuario;
				}
				
			} catch (SQLException e) {
				System.out.println("Erro ao buscar usuário no banco: " + e.getMessage());
			} finally {
				ConexaoBD.fecharConexao(conn);
			}
		}
		return null;
	}
	
	public boolean alterarUsuario(int codigo, Usuario usuarioparaalterar) {
		for (Usuario u : usuarios) {
			if (u.getIdUsuario() == codigo) {
				
				u.setAltura(usuarioparaalterar.getAltura());
				u.setNome(usuarioparaalterar.getNome());
				u.setEmail(usuarioparaalterar.getEmail());
				u.setIdade(usuarioparaalterar.getIdade());
				u.setSexo(usuarioparaalterar.getSexo());
				u.setMetacalorica(usuarioparaalterar.getMetacalorica());
				u.setPeso(usuarioparaalterar.getPeso());
				return true;
			}
		}
		return false; 
	}
	
	public boolean excluirUsuario(int Id) {
	    return usuarios.removeIf(u -> u.getIdUsuario() == Id);
	}
	
	//EXERCICIOS
	
	public boolean cadastrarExercicio(Usuario usuario, Scanner sc) {
		Exercicio exe = new Exercicio();
		Exercicio novoExe = exe.criarExercicio(sc);
		
		if (novoExe == null) {
            return false;
        }
		usuario.addExercicio(novoExe);
		return true;
	}
	
	public Exercicio buscarExercicio(Usuario usuario, int id) {
		ArrayList<Exercicio> exercicios = usuario.getExercicios();
		for(Exercicio e: exercicios) {
			if(e.getIdExercicio()==id) {
				return e;
			}
		}
		return null;
	}
	
	public boolean excluirExercicio(Usuario usuario, int id) {
		ArrayList<Exercicio> exercicios = usuario.getExercicios();
		for(Exercicio e: exercicios) {
			if(e.getIdExercicio() == id) {
				usuario.removeExercicio(e);
			}
			return true;
		}
	    return false;
	}
	
	public boolean alterarExercicio(Usuario usuario, int id, Exercicio novoExercicio) {
		try {
			Exercicio exercicioEncontrado = null;
			for (Exercicio e : usuario.getExercicios()) {
                if (e != null && e.getIdExercicio() == id) {
                	exercicioEncontrado = e;
                    break;
                }
            }
		
			if (novoExercicio.getNome() != null && !novoExercicio.getNome().isBlank()) {
				exercicioEncontrado.setNome(exercicioEncontrado.getNome());
			}
			if (novoExercicio.getIntensidade() != null && !novoExercicio.getIntensidade().isBlank()) {
				exercicioEncontrado.setIntensidade(exercicioEncontrado.getIntensidade());
			}
			if (novoExercicio.getDuracao() > 0) {
				exercicioEncontrado.setDuracao(exercicioEncontrado.getDuracao());
			}
			if (novoExercicio.getCaloriasGastas() > 0) {
				exercicioEncontrado.setCaloriasGastas(exercicioEncontrado.getCaloriasGastas());
			}
			return true;
			
		} catch (Exception e) {
			return false;
		}
	
	}
	
	//REFEIÇÕES
	
	public boolean cadastrarRefeicao(Usuario usuario, Scanner sc) {
		Refeicao ref = new Refeicao();
		Refeicao novaRefeicao = ref.criarRefeicao(sc);
		
		if (novaRefeicao == null) {
            return false;
        }
		
		usuario.addRefeicao(novaRefeicao);
        return true;
	}
	
	public Refeicao buscarRefeicao(Usuario usuario, int Id) {
		ArrayList<Refeicao> refeicoes = usuario.getRefeicoes();
		for (Refeicao r: refeicoes) {
			if(r.getIdRefeicao()==Id) {
				return r;
			}
		}
		return null;
		
	}
	
	public boolean excluirRefeicao(Usuario usuario, int id) {
		ArrayList<Refeicao> refeicoes = usuario.getRefeicoes();
		for(Refeicao r: refeicoes) {
			if(r.getIdRefeicao() == id) {
				usuario.removeRefeicao(r);
			}
			return true;
		}
	    return false;
	}
	
	public boolean alterarRefeicao(Usuario usuario, int id, Refeicao novaRefeicao) {
		try {
			Refeicao refeicaoEncontrada = null;
			for (Refeicao r : usuario.getRefeicoes()) {
                if (r != null && r.getIdRefeicao() == id) {
                	refeicaoEncontrada = r;
                    break;
                }
            }
		
			if (novaRefeicao.getNomeRefeicao() != null && !novaRefeicao.getNomeRefeicao().isBlank()) {
				refeicaoEncontrada.setNomeRefeicao(refeicaoEncontrada.getNomeRefeicao());
			}
			if (novaRefeicao.getHorario() != null && !novaRefeicao.getHorario().isBlank()) {
				refeicaoEncontrada.setHorario(refeicaoEncontrada.getHorario());
			}
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

}
