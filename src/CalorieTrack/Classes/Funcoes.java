package CalorieTrack.Classes;

import java.util.ArrayList;
import java.util.Scanner;

public class Funcoes {
	ArrayList<Usuario> usuarios = new ArrayList<>();
	
	public Funcoes() {
		
	}
	
	//LOGIN
	
	public boolean cadastrarUsuario(Scanner sc) {
		Usuario temp = new Usuario();
		Usuario novoUsuario = temp.criarUsuario(sc);
		
		if (novoUsuario == null) {
            System.out.println("Cadastro cancelado ou inválido.");
            return false;
        }
		
		for (Usuario u : usuarios) {
            if(u.getEmail().equalsIgnoreCase(novoUsuario.getEmail())) {
                System.out.println("Erro: Já existe um usuário com este email.");
                return false;
            }
        }
		
		usuarios.add(novoUsuario);
        return true;
	}
	
	public Usuario buscarUsuario(String login, String senha) {
			for (Usuario u : usuarios) {
				if (u != null) {
					if(u.getEmail().equals(login) && u.getSenha().equals(senha)) {
						return u;
					}
					
				}
			}
			return null;
	}
	
	

    

}
