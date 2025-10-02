package CalorieTrack.Classes;

import java.util.Scanner;

public class Menu {
	
	private Funcoes funcoes;
	
	public Menu() {
		this.funcoes  = new Funcoes();
	}

	public static void main(String[] args) {
		Menu m = new Menu();
		
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
				if(m.funcoes.cadastrarUsuario(sc)) {
					System.out.println("\nUsuário cadastrado com sucesso!");
				} else {
					System.out.println("\nErro no cadastro do cliente!");
				}
				break;
			case "2":
				m.fazerLogin(sc);
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
			System.out.println(" 5 - Refeições");
			System.out.println(" 6 - Conquistas");
	    	System.out.println(" 0 - Voltar ao menu de login");
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
				menuExercicios(usuario, sc);
				break;
			case "5":
				menuRefeicoes(usuario, sc);
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
	
	public void menuExercicios(Usuario usuario, Scanner sc) {
    	boolean continuar = true;
    	int idExercicio;
    	
    	while (continuar) {
    	
			System.out.println("\n -------------------------------- \n         Menu Exercícios\n --------------------------------");
			System.out.println(" 1 - Cadastrar exercício");
			System.out.println(" 2 - Alterar exercício");
			System.out.println(" 3 - Excluir exercício");
			System.out.println(" 4 - Mostrar um exercício");
	    	System.out.println(" 0 - Voltar ao menu principal");
	    	System.out.print("Opção: ");
	    	
	    	String resposta = sc.nextLine();
			
			switch (resposta) {
			
			case "1":
				if(funcoes.cadastrarExercicio(usuario, sc)) {
					System.out.println("\nCadastro realizado com sucesso!");
				} else {
					System.out.println("\nErro ao cadastrar exercício!");
				}
				break;
			case "2":
				System.out.println("Id do exercício:");
	            idExercicio = sc.nextInt();
	            sc.nextLine();
	            
	            Exercicio novoExercicio = funcoes.buscarExercicio(usuario, idExercicio);
	            
	            System.out.println("Digite o novo nome do exercício (ou deixe em branco para manter): ");
				String nome = sc.nextLine();
				if (!nome.isBlank()) {
					novoExercicio.setNome(nome);
				}
				System.out.println("Digite a nova intensidade do exercício (ou deixe em branco para manter): ");
				String intensidade = sc.nextLine();
				if (!intensidade.isBlank()) {
					novoExercicio.setIntensidade(intensidade);
				}
				System.out.println("Digite a nova duração do exercício: ");
				Double duracao = sc.nextDouble();
				sc.nextLine();
				if (duracao > 0) {
					novoExercicio.setDuracao(duracao);
				}
				System.out.println("Digite o novo valor para calorias gastas do exercício: ");
				Double calorias = sc.nextDouble();
				sc.nextLine();
				if (calorias > 0) {
					novoExercicio.setCaloriasGastas(calorias);
				}
				
				if(funcoes.alterarExercicio(usuario, idExercicio, novoExercicio)) {
					System.out.println("\nAlteração realizada com sucesso!");
				} else {
					System.out.println("\nErro ao alterar exercício!");
				}
				break;
			case "3":
				System.out.println("Id do exercício:");
	            idExercicio = sc.nextInt();
	            sc.nextLine();
	            Exercicio exercicio2 = funcoes.buscarExercicio(usuario, idExercicio);
				System.out.println("Deseja mesmo excluir o exercício: " + exercicio2 + "? (s/n)");
				String confirmacao = sc.nextLine();
				if ("s".equals(confirmacao) || "sim".equals(confirmacao) || "S".equals(confirmacao)) {
					if(funcoes.excluirExercicio(usuario, idExercicio)) {
						System.out.println("\nExclusão realizada com sucesso!");
					} else {
						System.out.println("\nErro ao excluir exercício!");
					}
				}
				break;
			case "4":
				System.out.println("Id do exercício:");
	            idExercicio = sc.nextInt();
	            sc.nextLine();
	            Exercicio exercicio = funcoes.buscarExercicio(usuario, idExercicio);
				if(exercicio != null) {
					System.out.println(exercicio);
				} else {
					System.out.println("\nErro ao encontrar exercício!");
				}
				break;
			case "0":
				continuar = false;
				System.out.println("\nSaindo de exercícios...");
				break;
			default:
				System.out.println("\nOpção inválida.");
			}
    	}
    }
	
	public void menuRefeicoes(Usuario usuario, Scanner sc) {
    	boolean continuar = true;
    	int idRefeicao;
    	
    	while (continuar) {
    	
			System.out.println("\n -------------------------------- \n         Menu Refeições\n --------------------------------");
			System.out.println(" 1 - Cadastrar refeição");
			System.out.println(" 2 - Alterar refeição");
			System.out.println(" 3 - Excluir refeição");
			System.out.println(" 4 - Mostrar um refeição");
	    	System.out.println(" 0 - Voltar ao menu principal");
	    	System.out.print("Opção: ");
	    	
	    	String resposta = sc.nextLine();
			
			switch (resposta) {
			
			case "1":
				if(funcoes.cadastrarRefeicao(usuario, sc)) {
					System.out.println("\nCadastro realizado com sucesso!");
				} else {
					System.out.println("\nErro ao cadastrar refeição!");
				}
				break;
			case "2":
				System.out.println("Id da refeição:");
	            idRefeicao = sc.nextInt();
	            sc.nextLine();
	            
	            Refeicao novaRefeicao = funcoes.buscarRefeicao(usuario, idRefeicao);
	            
	            System.out.println("Digite o novo nome da refeição (ou deixe em branco para manter): ");
				String nome = sc.nextLine();
				if (!nome.isBlank()) {
					novaRefeicao.setNomeRefeicao(nome);
				}
				System.out.println("Digite o novo horário da refeição (ou deixe em branco para manter): ");
				String horario = sc.nextLine();
				if (!horario.isBlank()) {
					novaRefeicao.setHorario(horario);
				}
	            
				if(funcoes.alterarRefeicao(usuario, idRefeicao, novaRefeicao)) {
					System.out.println("\nAlteração realizada com sucesso!");
				} else {
					System.out.println("\nErro ao excluir refeição!");
				}
				break;
			case "3":
				System.out.println("Id da refeição:");
				idRefeicao = sc.nextInt();
	            sc.nextLine();
	            Refeicao refeicao2 = funcoes.buscarRefeicao(usuario, idRefeicao);
	            System.out.println("Deseja mesmo excluir a refeição: " + refeicao2 + "? (s/n)");
				String confirmacao = sc.nextLine();
				if ("s".equals(confirmacao) || "sim".equals(confirmacao) || "S".equals(confirmacao)) {
					if(funcoes.excluirRefeicao(usuario, idRefeicao)) {
						System.out.println("\nExclusão realizada com sucesso!");
					} else {
						System.out.println("\nErro ao excluir refeição!");
					}
				}
				break;
			case "4":
				System.out.println("Id da receita:");
				idRefeicao = sc.nextInt();
	            sc.nextLine();
	            Refeicao refeicao = funcoes.buscarRefeicao(usuario, idRefeicao);
				if(refeicao != null) {
					System.out.println(refeicao);
				} else {
					System.out.println("\nErro ao encontrar refeição!");
				}
				break;
			case "0":
				continuar = false;
				System.out.println("\nSaindo de refeições...");
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
