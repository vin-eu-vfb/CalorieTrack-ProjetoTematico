package CalorieTrack.Classes;

import java.util.ArrayList;
import java.util.Scanner;

public class RegistroCalorias {

	private int idRegistro;
	private static int ULTIMOREGISTRO=0;
	private String data;
	private double caloriasConsumidas;
	private double caloriasGastas;
	private double saldoCalorico;
	private ArrayList<Refeicao> refeicoes= new ArrayList<>();
	private ArrayList<Exercicio> exercicios= new ArrayList<>();
	
	public RegistroCalorias() {
		super();
		ULTIMOREGISTRO++;
	}
	
	
	 public RegistroCalorias(String data, ArrayList<Refeicao> refeicoesUsuario, ArrayList<Exercicio> exerciciosUsuario) {
		 this.data = data;
		 this.refeicoes = new ArrayList<>(refeicoesUsuario);
		 this.exercicios = new ArrayList<>(exerciciosUsuario);
		 this.idRegistro=ULTIMOREGISTRO;
		 calcularTotais();
}

	public int getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(int idRegistro) {
		this.idRegistro = idRegistro;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public double getCaloriasConsumidas() {
		return caloriasConsumidas;
	}
	public void setCaloriasConsumidas(double caloriasConsumidas) {
		this.caloriasConsumidas = caloriasConsumidas;
	}
	public double getCaloriasGastas() {
		return caloriasGastas;
	}
	public void setCaloriasGastas(double caloriasGastas) {
		this.caloriasGastas = caloriasGastas;
	}
	public double getSaldoCalorico() {
		return saldoCalorico;
	}
	public void setSaldoCalorico(double saldoCalorico) {
		this.saldoCalorico = saldoCalorico;
	}
	public void addRefeicao(Refeicao ref) {
		refeicoes.add(ref);
	}
	public void addExercicio(Exercicio exe) {
		exercicios.add(exe);
	}
	 public RegistroCalorias criarRegistroDiario( Scanner sc, ArrayList<Exercicio> exercicios, ArrayList<Refeicao>refeicoes) {
		 String data;
		 System.out.println("Digite a data atual para o registro");
		 data=sc.nextLine();
	        return new RegistroCalorias(data,refeicoes,exercicios);
	    }
	
	private void calcularTotais() {
        caloriasConsumidas = 0;
        caloriasGastas = 0;

        for (Refeicao r : refeicoes) {
            caloriasConsumidas += r.getCaloriasTotais();
        }

        for (Exercicio e : exercicios) {
            caloriasGastas += e.getCaloriasGastas();
        }

        this.saldoCalorico = caloriasConsumidas - caloriasGastas;
    }

	

	@Override
	public String toString() {
		return "RegistroCalorias [idRegistro=" + idRegistro + ", data=" + data + ", caloriasConsumidas="
				+ caloriasConsumidas + ", caloriasGastas=" + caloriasGastas + ", saldoCalorico=" + saldoCalorico + "]";
	}
	
	
	
	
}
