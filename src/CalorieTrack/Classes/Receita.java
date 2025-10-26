package CalorieTrack.Classes;

import java.util.ArrayList;
import java.util.Scanner;

public class Receita {
	private int idReceita;
	private String nome;
	private ArrayList<TabelaNutricional> ingredientes= new ArrayList<>();
	private double caloriasTotais;
	private static int ULTIMARECEITA = 0;
	
	public Receita() {
		
		super();
		ULTIMARECEITA++;
	}
	
	
	
	public Receita( String nome,  double caloriasTotais) {
		super();
		this.idReceita=ULTIMARECEITA;
		this.nome = nome;
		this.caloriasTotais = caloriasTotais;
	}

	

	public ArrayList<TabelaNutricional> getIngredientes() {
		return ingredientes;
	}



	public void setIngredientes(ArrayList<TabelaNutricional> ingredientes) {
		this.ingredientes = ingredientes;
	}



	public int getIdReceita() {
		return idReceita;
	}
	public void setIdReceita(int idReceita) {
		this.idReceita = idReceita;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getCaloriasTotais() {
		return caloriasTotais;
	}
	public void setCaloriasTotais(double caloriasTotais) {
		this.caloriasTotais = caloriasTotais;
	}
	public Receita criarReceita(Scanner sc) {
		String nome;
		double caloriasTotais=0;
		System.out.println("Digite o nome da receita:");
		nome= sc.nextLine();
		String resposta;
		System.out.println("Quer adicionar um novo alimentos");
		resposta=sc.nextLine();
		while(resposta.equalsIgnoreCase("sim")) {
			this.addIngredientes(sc);
			System.out.println("Quer adicionar um novo alimentos");
			resposta=sc.nextLine();
			
		}
		for(TabelaNutricional a: ingredientes) {
			caloriasTotais+=a.getCalorias();
		}
		return new Receita(nome,caloriasTotais);
		
	}
	public boolean addIngredientes(Scanner sc) {
		TabelaNutricional a= new TabelaNutricional();
		return ingredientes.add(a.criarAlimentos(sc));
	}

	@Override
	public String toString() {
		return "Receita [idReceita=" + idReceita + ", nome=" + nome 
				+ ", caloriasTotais=" + caloriasTotais + "]";
	}
	
	
	
}
