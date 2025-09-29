package CalorieTrack.Classes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Alimentos {
	private double quantidade;
	private double calorias;
	private String nome;
	
	
	
	
	public Alimentos() {
		super();
	}

	public Alimentos(double quantidade, double calorias, String nome) {
		super();
		this.quantidade = quantidade;
		this.calorias = calorias;
		this.nome = nome;
	}


	

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}




	public double getCalorias() {
		return calorias;
	}

	public void setCalorias(double calorias) {
		this.calorias = calorias;
	}




	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	public Alimentos criarAlimentos(Scanner sc) {
		
		System.out.println("Digite o nome do alimento:");
		String nome=sc.nextLine();
		
		
		 double quantidade = 0;
		    while (true) {
		        try {
		            System.out.println("Informe a quantidade em gramas");
		            quantidade = sc.nextDouble();
		            break;
		        } catch (InputMismatchException e) {
		            System.out.println("Por favor, insira um número válido para a quantidade.");
		            sc.nextLine();
		        }
		    }
		 double calorias = 0;
		    while (true) {
		        try {
		            System.out.println("Calorias a cada 100g:");
		            calorias = sc.nextDouble();
		            break;
		        } catch (InputMismatchException e) {
		            System.out.println("Por favor, insira um número válido para o n° de calorias");
		            sc.nextLine();
		        }
		    }
		   return new Alimentos(quantidade,calorias,nome); 
	}




	@Override
	public String toString() {
		return "Alimentos [quantidade=" + quantidade + ", calorias=" + calorias + ", nome=" + nome + "]";
	}
	
	
}
