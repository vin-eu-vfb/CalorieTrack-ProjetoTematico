package CalorieTrack.Classes;

import java.util.Scanner;

public class Relatorio {

	private int idRelatorio;
	private static int ULTIMORELATORIO = 0;
	private String periodoInicio;
	private String periodoFim;
	private double totalConsumido;
	private double totalGasto;
	
	
	public Relatorio() {
		super();
		ULTIMORELATORIO++;
	}


	public Relatorio(String periodoInicio, String periodoFim, double totalConsumido, double totalGasto) {
		super();
		this.periodoInicio = periodoInicio;
		this.periodoFim = periodoFim;
		this.totalConsumido = totalConsumido;
		this.totalGasto = totalGasto;
		this.idRelatorio=ULTIMORELATORIO;
	}


	public int getIdRelatorio() {
		return idRelatorio;
	}


	public void setIdRelatorio(int idRelatorio) {
		this.idRelatorio = idRelatorio;
	}


	public String getPeriodoInicio() {
		return periodoInicio;
	}


	public void setPeriodoInicio(String periodoInicio) {
		this.periodoInicio = periodoInicio;
	}


	public String getPeriodoFim() {
		return periodoFim;
	}


	public void setPeriodoFim(String periodoFim) {
		this.periodoFim = periodoFim;
	}


	public double getTotalConsumido() {
		return totalConsumido;
	}


	public void setTotalConsumido(double totalConsumido) {
		this.totalConsumido = totalConsumido;
	}


	public double getTotalGasto() {
		return totalGasto;
	}


	public void setTotalGasto(double totalGasto) {
		this.totalGasto = totalGasto;
	}
	
	
	public Relatorio criarRelatorio(Scanner sc, double a, double b ) {
		String periodoInicio;
		String periodoFim;
		System.out.println("Informe o periodo de inicio em DD:MM:AAAA");
		periodoInicio= sc.nextLine();
		System.out.println("Informe o periodo de fim em DD:MM:AAAA");
		periodoFim=sc.nextLine();
		
		return new Relatorio(periodoInicio,periodoFim,a,b);
		
	}
	
	
}
