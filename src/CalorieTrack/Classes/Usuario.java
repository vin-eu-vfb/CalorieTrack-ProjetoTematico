package CalorieTrack.Classes;

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
	
	
	
	
}