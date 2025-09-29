package CalorieTrack.Classes;

import java.util.ArrayList;
import java.util.Scanner;

public class Funcoes {
	private ArrayList<Usuario> usuarios = new ArrayList<>();
	ArrayList<Refeicao> refeicoes = new ArrayList<>();
	
	public Funcoes() {
		
	}
	
	//LOGIN
	
	public boolean cadastrarUsuario(Scanner sc) {
		Usuario temp = new Usuario();
		Usuario novoUsuario = temp.criarUsuario(sc);
		
		if (novoUsuario == null) {
            return false;
        }
		
		for (Usuario u : usuarios) {
            if(u.getEmail().equalsIgnoreCase(novoUsuario.getEmail())) {
                System.out.println("Erro: Já existe um usuário com este email.");
                return false;
            }
        }
		
		usuarios.add(novoUsuario);
        return true;
	}
	
	public Usuario buscarUsuario(String login, String senha) {
			for (Usuario u : usuarios) {
				if (u != null) {
					if(u.getEmail().equals(login) && u.getSenha().equals(senha)) {
						return u;
					}
					
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
	
	public boolean alterarExercicio(Usuario usuario, int id) {
		ArrayList<Exercicio> exercicios = usuario.getExercicios();
		for (Exercicio e : exercicios) {
			if (e.getIdExercicio() == id) {
				e.setTipo(e.getTipo());
				e.setIntensidade(e.getIntensidade());
				e.setDuracao(e.getDuracao());
				e.setCaloriasGastas(e.getCaloriasGastas());
				return true;
			}
		}
		return false; 
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
	
	public boolean alterarRefeicao(Usuario usuario, int id, String nome, Scanner sc) {
		ArrayList<Refeicao> refeicoes = usuario.getRefeicoes();
		for (Refeicao r : refeicoes) {
			if (r.getIdRefeicao() == id) {
				r.setNomeRefeicao(r.getNomeRefeicao());
				r.setHorario(r.getHorario());
				r.alterarAlimentos(sc, nome);
				
				return true;
			}
		}
		return false; 
	}

}
