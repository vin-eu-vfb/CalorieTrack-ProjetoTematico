package CalorieTrack.Classes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TabelaNutricional {
	private int idTabela;
	private static int ULTIMATABELA;
	private double quantidade;
	private double calorias;
	private String nome;
	private double carboidratos;
	private double proteinas;
	private double gorduras;
	
	
	
	public TabelaNutricional() {
		super();
		ULTIMATABELA++;
	}

	public TabelaNutricional(double quantidade, double calorias, String nome) {
		super();
		this.idTabela= ULTIMATABELA;
		this.quantidade = quantidade;
		this.calorias = calorias;
		this.nome = nome;
	}
	
	

	

	public TabelaNutricional(double quantidade, double calorias, String nome, double carboidratos, double proteinas,
			double gorduras) {
		super();
		this.idTabela= ULTIMATABELA;
		this.quantidade = quantidade;
		this.calorias = calorias;
		this.nome = nome;
		this.carboidratos = carboidratos;
		this.proteinas = proteinas;
		this.gorduras = gorduras;
	}

	public int getIdTabela() {
		return idTabela;
	}

	public void setIdTabela(int idTabela) {
		this.idTabela = idTabela;
	}

	public double getCarboidratos() {
		return carboidratos;
	}

	public void setCarboidratos(double carboidratos) {
		this.carboidratos = carboidratos;
	}

	public double getProteinas() {
		return proteinas;
	}

	public void setProteinas(double proteinas) {
		this.proteinas = proteinas;
	}

	public double getGorduras() {
		return gorduras;
	}

	public void setGorduras(double gorduras) {
		this.gorduras = gorduras;
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
	
	
	
	
	public TabelaNutricional criarAlimentos(Scanner sc) {
        
        System.out.println("Digite o nome do alimento:");
        String nome = sc.nextLine();
        
        double quantidade = 0;
        while (true) {
            try {
                System.out.println("Informe a quantidade em gramas");
                quantidade = sc.nextDouble();
                sc.nextLine(); // Consume newline
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido para a quantidade.");
                sc.nextLine(); // Clear the invalid input
            }
        }
        
        double calorias = 0;
        while (true) {
            try {
                System.out.println("Calorias a cada 100g:");
                calorias = sc.nextDouble();
                sc.nextLine(); // Consume newline
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido para o n° de calorias");
                sc.nextLine(); // Clear the invalid input
            }
        }
        
        double proteinas = 0;
        while (true) {
            try {
                System.out.println("Digite o n° de proteinas a cada 100g");
                proteinas = sc.nextDouble();
                sc.nextLine(); // Consume newline
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido.");
                sc.nextLine(); // Clear the invalid input
            }
        }
        
        double gorduras = 0;
        while (true) {
            try {
                System.out.println("Digite o n° de gorduras a cada 100g");
                gorduras = sc.nextDouble();
                sc.nextLine(); // Consume newline
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido.");
                sc.nextLine(); // Clear the invalid input
            }
        }
        
        double carboidratos = 0;
        while (true) {
            try {
                System.out.println("Digite o n° de carboidratos a cada 100g");
                carboidratos = sc.nextDouble();
                sc.nextLine(); // Consume newline
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido.");
                sc.nextLine(); // Clear the invalid input
            }
        }
        
        // Create and return a new TabelaNutricional object with all the values
        TabelaNutricional novoAlimento = new TabelaNutricional();
        novoAlimento.setNome(nome);
        novoAlimento.setQuantidade(quantidade);
        novoAlimento.setCalorias(calorias);
        novoAlimento.setProteinas(proteinas);
        novoAlimento.setGorduras(gorduras);
        novoAlimento.setCarboidratos(carboidratos);

        
        return novoAlimento;
    }




	@Override
	public String toString() {
		return "TabelaNutricional [quantidade=" + quantidade + ", calorias=" + calorias + ", nome=" + nome + "]";
	}
	
	
}
