package CalorieTrack.Classes;

import java.util.ArrayList;
import java.util.Scanner;

public class Funcoes {
	ArrayList<Usuario> usuarios = new ArrayList<>();
	ArrayList<Exercicio> exercicios = new ArrayList<>();
	ArrayList<Refeicao> refeicoes = new ArrayList<>();
	
	public Funcoes() {
		
	}
	
	//LOGIN
	
	public boolean cadastrarUsuario(Scanner sc) {
		Usuario temp = new Usuario();
		Usuario novoUsuario = temp.criarUsuario(sc);
		
		if (novoUsuario == null) {
            System.out.println("Cadastro cancelado ou inválido.");
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
	public boolean cadastrarExercicio(Scanner sc) {
		Exercicio exe = new Exercicio();
		Exercicio novoExe= exe.criarExercicio(sc);
		
		if (novoExe == null) {
            System.out.println("Cadastro cancelado ou inválido.");
            return false;
        }
		exercicios.add(novoExe);
		return true;
	}
	public boolean alterarExercicio(int codigo, Exercicio exercicioparaalterar) {
		for (Exercicio e : exercicios) {
			if (e.getIdExercicio() == codigo) {
				
				e.setTipo(exercicioparaalterar.getTipo());
				e.setIntensidade(exercicioparaalterar.getIntensidade());
				e.setDuracao(exercicioparaalterar.getDuracao());
				e.setCaloriasGastas(exercicioparaalterar.getCaloriasGastas());
				
				return true;
			}
		}
		return false; 
	}
	
	public Exercicio buscarExercicio(int id) {
		
		for(Exercicio e: exercicios) {
			if(e.getIdExercicio()==id) {
				return e;
			}
		}
		return null;
	}
	public boolean excluirExercicio(int Id) {
	    return exercicios.removeIf(u -> u.getIdExercicio() == Id);
	}
	public boolean cadastrarRefeicao(Scanner sc) {
		Refeicao ref = new Refeicao();
		Refeicao novaRefeicao = ref.criarRefeicao(sc);
		
		if (novaRefeicao == null) {
            System.out.println("Cadastro cancelado ou inválido.");
            return false;
        }
		
		
        
		
		refeicoes.add(novaRefeicao);
        return true;
	}
	public Refeicao buscarRefeicao(int Id) {
		for( Refeicao r: refeicoes) {
			if(r.getIdRefeicao()==Id) {
				return r;
			}
		}
		return null;
		
	}
	
	public boolean excluirRefeicao(int Id) {
		return refeicoes.removeIf(r->r.getIdRefeicao()==Id);
	}
	public boolean alterarRefeicao(Scanner sc, int codigo, Refeicao refeicaoparaalterar, String nome) {
		for (Refeicao r : refeicoes) {
			if (r.getIdRefeicao() == codigo) {
				
				r.setNomeRefeicao(refeicaoparaalterar.getNomeRefeicao());
				r.setHorario(refeicaoparaalterar.getHorario());
				r.alterarAlimentos(sc, nome);
				
				return true;
			}
		}
		return false; 
	}

}
