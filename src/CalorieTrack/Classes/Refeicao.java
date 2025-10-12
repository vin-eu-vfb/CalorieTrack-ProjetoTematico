package CalorieTrack.Classes;
import java.util.ArrayList;
import java.util.Scanner;

public class Refeicao {
	
	private int idRefeicao;
	private String nomeRefeicao;
	private ArrayList<TabelaNutricional> tabelaNutricional= new ArrayList<>();
	private String horario;
	private static int ULTIMAREFEICAO = 0;
	private Double caloriasTotais;
	
	public Refeicao() {
		ULTIMAREFEICAO++;
	}

	
	
	public Refeicao(String nomeRefeicao, String horario,Double caloriasTotais) {
		this.idRefeicao = ULTIMAREFEICAO;
		this.nomeRefeicao = nomeRefeicao;	
		this.horario = horario;
		this.caloriasTotais= caloriasTotais;
	}



	public Double getCaloriasTotais() {
		return caloriasTotais;
	}



	public void setCaloriasTotais(Double caloriasTotais) {
		this.caloriasTotais = caloriasTotais;
	}



	public int getIdRefeicao() {
		return idRefeicao;
	}
	public void setIdRefeicao(int idRefeicao) {
		this.idRefeicao = idRefeicao;
	}
	
	public	String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	

	public String getNomeRefeicao() {
		return nomeRefeicao;
	}
	public void setNomeRefeicao(String nomeRefeicao) {
		this.nomeRefeicao = nomeRefeicao;
	}



	public ArrayList<TabelaNutricional> getAlimentos1() {
		return tabelaNutricional;
	}
	public void setAlimentos1(ArrayList<TabelaNutricional> alimentos1) {
		this.tabelaNutricional = alimentos1;
	}



	public Refeicao criarRefeicao(Scanner sc) {
		
		System.out.println("Digite o nome da refeição:");
		String nome=sc.nextLine();
		
		    String horario;
		    System.out.println("Informe o horario em HH:MM");
		    horario=sc.nextLine();
		    
		    for(TabelaNutricional a: tabelaNutricional) {
		    	caloriasTotais= a.getCalorias();
		    }
		    
		   // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		    
		    //try {
	            
	        //    LocalTime hora = LocalTime.parse(horario, formatter);
	        //    System.out.println("Horário armazenado: " + horario);

	      //     
	     //   } catch (DateTimeParseException e) {
	      //      System.out.println("Formato inválido! Use HH:mm.");
	       // }
		 return new Refeicao(nome,horario,caloriasTotais);
	}
	public boolean associarAlimentos(Scanner sc,TabelaNutricional a){
		a.criarAlimentos(sc);
		return tabelaNutricional.add(a);
//lembrar de corrigir possiveis erros
	}
	public boolean alterarTabelaNutricional(Scanner sc, String nome) {
		String nomeAux;
		double calorias=0.0;
		double quantidade=0.0;
		double proteinas=0.0;
		double gorduras=0.0;
		double carboidratos=0.0;
		for(TabelaNutricional al: tabelaNutricional) {
			if(al.getNome().equalsIgnoreCase(nome)) {
				System.out.println("Digite o novo nome");
				nomeAux=sc.nextLine();
				al.setNome(nomeAux);
				
				System.out.println("Digite o n° de calorias a cada 100g");
				calorias= sc.nextDouble();
				al.setCalorias(calorias);
				sc.nextLine();
				System.out.println("Digite a quantidade em gramas");
				quantidade= sc.nextDouble();
				al.setQuantidade(quantidade);
				sc.nextLine();
				System.out.println("Digite o n° de proteinas a cada 100g");
				proteinas= sc.nextDouble();
				al.setProteinas(proteinas);
				sc.nextLine();
				System.out.println("Digite o n° de gorduras a cada 100g");
				gorduras= sc.nextDouble();
				al.setGorduras(gorduras);
				sc.nextLine();
				System.out.println("Digite o n° de carboidratos a cada 100g");
				carboidratos= sc.nextDouble();
				al.setCarboidratos(carboidratos);
				sc.nextLine();
				
				return true;
			}
		}
		return false;
	}
	

	@Override
	public String toString() {
		return "Refeicao [idRefeicao=" + idRefeicao + ", nomeRefeicao=" + nomeRefeicao + ", tabelaNutricional="
				+ tabelaNutricional + ", horario=" + horario + "]";
	}
	
	
	
	
		
}
