package CalorieTrack.Classes;

public class Perfil {
	private int idPerfil;
	private String preferencias;
	private String configuracoes;
	
	
	public Perfil() {
		super();
	}


	public Perfil(int idPerfil, String preferencias, String configuracoes) {
		super();
		this.idPerfil = idPerfil;
		this.preferencias = preferencias;
		this.configuracoes = configuracoes;
	}


	public int getIdPerfil() {
		return idPerfil;
	}


	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}


	public String getPreferencias() {
		return preferencias;
	}


	public void setPreferencias(String preferencias) {
		this.preferencias = preferencias;
	}


	public String getConfiguracoes() {
		return configuracoes;
	}


	public void setConfiguracoes(String configuracoes) {
		this.configuracoes = configuracoes;
	}


	@Override
	public String toString() {
		return "Perfil [idPerfil=" + idPerfil + ", preferencias=" + preferencias + ", configuracoes=" + configuracoes
				+ "]";
	}
	
	
	
	
	
	
}
