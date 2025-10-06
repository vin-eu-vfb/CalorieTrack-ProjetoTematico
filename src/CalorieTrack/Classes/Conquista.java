package CalorieTrack.Classes;



public class Conquista {
	private static int ULTIMACONQUISTA=0;
	private int idConquista;
	private String titulo;
	private String descricao;
	private String dataAlcance;
	
	
	public Conquista() {
		super();
		ULTIMACONQUISTA++;
	}


	public Conquista(String titulo, String descricao, String dataAlcance) {
		super();
		this.idConquista = ULTIMACONQUISTA;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataAlcance = dataAlcance;
	}


	public int getIdConquista() {
		return idConquista;
	}


	public void setIdConquista(int idConquista) {
		this.idConquista = idConquista;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getDataAlcance() {
		return dataAlcance;
	}


	public void setDataAlcance(String dataAlcance) {
		this.dataAlcance = dataAlcance;
	}

	
	
	
	
	@Override
	public String toString() {
		return "Conquista [idConquista=" + idConquista + ", titulo=" + titulo + ", descricao=" + descricao
				+ ", dataAlcance=" + dataAlcance + "]";
	}
	
	
	
	
	
	
}
