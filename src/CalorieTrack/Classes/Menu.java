package CalorieTrack.Classes;

import java.util.Scanner;

public class Menu {
	
	private static Funcoes funcoes = new Funcoes();
	private static Menu menu = new Menu();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("\n -------------------------------- \n         Menu Principal\n --------------------------------");
			System.out.println(" 1 - Realizar Cadastro");
			System.out.println(" 2 - Realizar Login");
			System.out.println(" 0 - Sair");
			System.out.print("Opção: ");
			
			String resposta = sc.nextLine();
			
			switch (resposta) {
			case "1":
				if(funcoes.cadastrarUsuario(sc)) {
					System.out.println("\nUsuário cadastrado com sucesso!");
				} else {
					System.out.println("\nErro no cadastro do cliente!");
				}
				break;
			case "2":
				menu.fazerLogin(sc);
				break;
			case "0":
				continuar = false;
				System.out.println("\nEncerrando sistema...");
				break;
			default:
				System.out.println("\nOpção inválida!");
			}
		}
		
		sc.close();
	}
	
	public void menuUsuario(Usuario usuario, Scanner sc) {
    	boolean continuar = true;
    	System.out.println("\nLogin realizado com sucesso!");
    	
    	while (continuar) {
    	
			System.out.println("\n -------------------------------- \n         Menu Usuário\n --------------------------------");
			System.out.println(" 1 - Perfil");
			System.out.println(" 2 - Relatórios");
			System.out.println(" 3 - Registro de Calorias");
			System.out.println(" 4 - Exercícios");
			System.out.println(" 5 - Receitas");
			System.out.println(" 6 - Conquistas");
	    	System.out.println(" 0 - Voltar ao menu principal");
	    	System.out.print("Opção: ");
	    	
	    	String resposta = sc.nextLine();
			
			switch (resposta) {
			case "1":
				System.out.println("\nEm Construção");
				break;
			case "2":
				System.out.println("\nEm Construção");
				break;
			case "3":
				System.out.println("\nEm Construção");
				break;
			case "4":
				System.out.println("\nEm Construção");
				break;
			case "5":
				System.out.println("\nEm Construção");
				break;
			case "6":
				System.out.println("\nEm Construção");
				break;
			case "99":
				System.out.println("\nTeste");
				System.out.println("ID: " + usuario.getIdUsuario());
				System.out.println("Nome: " + usuario.getNome());
				break;
			case "0":
				continuar = false;
				System.out.println("\nSaindo do acesso...");
				break;
			default:
				System.out.println("\nOpção inválida.");
			}
    	}
    }
	
	public void fazerLogin(Scanner sc) {
        System.out.print("Email: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario usuario = funcoes.buscarUsuario(login, senha);

        if (usuario != null) {
        	menuUsuario(usuario, sc);
        } else {
            System.out.println("\nErro: Login ou senha incorretos ou não encontrados");
        }
    }
	
}
