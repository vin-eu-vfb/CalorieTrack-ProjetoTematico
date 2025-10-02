package CalorieTrack.Classes;
import java.util.ArrayList;
import java.util.Scanner;

public class Refeicao {
	
	private int idRefeicao;
	private String nomeRefeicao;
	private ArrayList<Alimentos> alimentos= new ArrayList<>();
	private String horario;
	private static int ULTIMAREFEICAO = 0;
	
	
	public Refeicao() {
		ULTIMAREFEICAO++;
	}

	
	
	public Refeicao(String nomeRefeicao, String horario) {
		this.idRefeicao = ULTIMAREFEICAO;
		this.nomeRefeicao = nomeRefeicao;	
		this.horario = horario;
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



	public ArrayList<Alimentos> getAlimentos1() {
		return alimentos;
	}
	public void setAlimentos1(ArrayList<Alimentos> alimentos1) {
		this.alimentos = alimentos1;
	}



	public Refeicao criarRefeicao(Scanner sc) {
		
		System.out.println("Digite o nome da refeição:");
		String nome=sc.nextLine();
		
		    String horario;
		    System.out.println("Informe o horario em HH:MM");
		    horario=sc.nextLine();
		    
		   // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		    
		    //try {
	            
	        //    LocalTime hora = LocalTime.parse(horario, formatter);
	        //    System.out.println("Horário armazenado: " + horario);

	      //     
	     //   } catch (DateTimeParseException e) {
	      //      System.out.println("Formato inválido! Use HH:mm.");
	       // }
		 return new Refeicao(nome,horario);
	}
	public boolean associarAlimentos(Scanner sc,Alimentos a){
		a.criarAlimentos(sc);
		return alimentos.add(a);
//lembrar de corrigir possiveis erros
	}
	public boolean alterarAlimentos(Scanner sc, String nome) {
		String nomeAux;
		Double calorias;
		Double quantidade;
		for(Alimentos al: alimentos) {
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
				
				return true;
			}
		}
		return false;
	}


	@Override
	public String toString() {
		return "Refeicao [idRefeicao=" + idRefeicao + ", nomeRefeicao=" + nomeRefeicao + ", alimentos="
				+ alimentos + ", horario=" + horario + "]";
	}
	
	
	
	
		
}
