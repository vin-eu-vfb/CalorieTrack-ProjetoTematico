package CalorieTrack.Classes;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Usuario {
	private static int ULTIMOIDUSUARIO = 0;
	private int idUsuario;
	private String nome;
	private String email;
	private String senha;
	private double peso;
	private double altura;
	private int idade;
	private String sexo;
	private double metacalorica;
	private ArrayList<Exercicio> exercicios = new ArrayList<>();
	private ArrayList<Refeicao> refeicoes = new ArrayList<>();
	private ArrayList<Receita> receitas = new ArrayList<>();
	private ArrayList<RegistroCalorias> registros= new ArrayList<>();
	private ArrayList<Relatorio> relatorios = new ArrayList<>();
	private ArrayList<Perfil> perfil= new ArrayList<>();
	private ArrayList<Conquista> conquista= new ArrayList<>();
	
	public Usuario() {
		ULTIMOIDUSUARIO++;
	}
	
	public Usuario(String nome, String email, String senha, double peso, double altura, int idade, String sexo, double metacalorica) {
		this.idUsuario = ULTIMOIDUSUARIO;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.peso = peso;
		this.altura = altura;
		this.idade = idade;
		this.sexo = sexo;
		this.metacalorica = metacalorica;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}

	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public double getMetacalorica() {
		return metacalorica;
	}
	public void setMetacalorica(double metacalorica) {
		this.metacalorica = metacalorica;
	}
	
	public ArrayList<Exercicio> getExercicios() {
		return exercicios;
	}
	public void setExercicios(ArrayList<Exercicio> exercicios) {
		this.exercicios = exercicios;
	}

	public ArrayList<Refeicao> getRefeicoes() {
		return refeicoes;
	}
	public void setRefeicoes(ArrayList<Refeicao> refeicoes) {
		this.refeicoes = refeicoes;
	}
	

	public ArrayList<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(ArrayList<Receita> receitas) {
		this.receitas = receitas;
	}

	public ArrayList<RegistroCalorias> getRegistros() {
		return registros;
	}

	public void setRegistros(ArrayList<RegistroCalorias> registros) {
		this.registros = registros;
	}

	public ArrayList<Relatorio> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(ArrayList<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}

	public ArrayList<Perfil> getPerfil() {
		return perfil;
	}

	public void setPerfil(ArrayList<Perfil> perfil) {
		this.perfil = perfil;
	}

	public ArrayList<Conquista> getConquista() {
		return conquista;
	}

	public void setConquista(ArrayList<Conquista> conquista) {
		this.conquista = conquista;
	}

	public Usuario criarUsuario(Scanner sc) {		
		System.out.println("Nome:");
		String nome = sc.nextLine();
		
		System.out.println("Email:");
		String email = sc.nextLine();
		
		System.out.println("Senha:");
		String senha = sc.nextLine();
		
		double peso = 0;
	    while (true) {
	        try {
	            System.out.println("Peso:");
	            peso = sc.nextDouble();
	            break;
	        } catch (InputMismatchException e) {
	            System.out.println("Por favor, insira um número válido para o peso.");
	            sc.nextLine();
	        }
	    }
	    
	    double altura = 0;
	    while (true) {
	        try {
	            System.out.println("Altura:");
	            altura = sc.nextDouble();
	            break;
	        } catch (InputMismatchException e) {
	            System.out.println("Por favor, insira um número válido para a altura.");
	            sc.nextLine();
	        }
	    }
	    
	    int idade = 0;
	    while (true) {
	        try {
	            System.out.println("Idade:");
	            idade = sc.nextInt();
	            break;
	        } catch (InputMismatchException e) {
	            System.out.println("Por favor, insira um número válido para a idade.");
	            sc.nextLine();
	        }
	    }
		sc.nextLine();
		
		System.out.println("Sexo:");
		String sexo = sc.nextLine();
		
		double metacalorica = 0;
	    while (true) {
	        try {
	            System.out.println("Meta de Calorias:");
	            metacalorica = sc.nextDouble();
	            sc.nextLine();
	            break;
	        } catch (InputMismatchException e) {
	            System.out.println("Por favor, insira um número válido para a meta calórica.");
	            sc.nextLine();
	        }
	    }
		
		return new Usuario(nome, email, senha, peso, altura, idade, sexo, metacalorica);
	}
	
	public void addRegistroDiario(RegistroCalorias reg) {
	        registros.add(reg);
	    }
	
	public void removeRegistroDiario(RegistroCalorias reg) {
	        registros.remove(reg);
	    }
	
	
	public void addExercicio(Exercicio exe) {
		exercicios.add(exe);
	}
	
	public void removeExercicio(Exercicio exe) {
		exercicios.remove(exe);
	}
	
	public void addRefeicao(Refeicao ref) {
		refeicoes.add(ref);
	}
	
	public void removeRefeicao(Refeicao ref) {
		refeicoes.remove(ref);
	}
	public void addReceita(Receita rec) {
		receitas.add(rec);
	}
	public void removeReceita(Receita rec) {
		receitas.remove(rec);
	}
	
	public void addRelatorio(Relatorio re) {
		relatorios.add(re);
	}
	
	public void removeRelatorio(Relatorio re) {
		relatorios.remove(re);
	}
	
	
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nome=" + nome + ", email=" + email + ", senha=" + senha
				+ ", peso=" + peso + ", altura=" + altura + ", idade=" + idade + ", sexo=" + sexo + ", metacalorica="
				+ metacalorica + "]";
	}
	
	
	
	
}