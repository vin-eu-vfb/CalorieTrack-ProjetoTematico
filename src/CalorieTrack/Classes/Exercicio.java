package CalorieTrack.Classes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercicio {
	private int idExercicio;
	private String tipo;
	private double duracao;
	private String intensidade;
	private double caloriasGastas;
	
	
	
	public Exercicio() {
		super();
	}
	public Exercicio(String tipo, double duracao, String intensidade, double caloriasGastas) {
		super();
		
		this.tipo = tipo;
		this.duracao = duracao;
		this.intensidade = intensidade;
		this.caloriasGastas = caloriasGastas;
	}
	public int getIdExercicio() {
		return idExercicio;
	}
	public void setIdExercicio(int idExercicio) {
		this.idExercicio = idExercicio;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getDuracao() {
		return duracao;
	}
	public void setDuracao(double duracao) {
		this.duracao = duracao;
	}
	public String getIntensidade() {
		return intensidade;
	}
	public void setIntensidade(String intensidade) {
		this.intensidade = intensidade;
	}
	public double getCaloriasGastas() {
		return caloriasGastas;
	}
	public void setCaloriasGastas(double caloriasGastas) {
		this.caloriasGastas = caloriasGastas;
	}
	public Exercicio criarExercicio(Scanner sc) {
		
		System.out.println("Digite o nome do exercicio:");
		String nome=sc.nextLine();
		System.out.println("Digite a intensidade:");
		String intensidade=sc.nextLine();
		
		 double duracao = 0;
		    while (true) {
		        try {
		            System.out.println("Duracao:");
		            duracao = sc.nextDouble();
		            break;
		        } catch (InputMismatchException e) {
		            System.out.println("Por favor, insira um número válido para a duracao.");
		            sc.nextLine();
		        }
		    }
		 double caloriasgastas = 0;
		    while (true) {
		        try {
		            System.out.println("Calorias gastas:");
		            caloriasgastas = sc.nextDouble();
		            break;
		        } catch (InputMismatchException e) {
		            System.out.println("Por favor, insira um número válido para o n° de calorias gastas");
		            sc.nextLine();
		        }
		    }
		 return   new Exercicio(nome,duracao,intensidade,caloriasgastas);
	}
	
}
