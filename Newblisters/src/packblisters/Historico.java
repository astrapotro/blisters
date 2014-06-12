package packblisters;

public class Historico {

	private String fecha, usuario, medicamento, evento, incidencia;
	private int id, codNac, codBar, idCorte;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fe) {
		this.fecha = fe;
	}

	public int getCodNac() {
		return codNac;
	}

	public void setCodNac(int codNac) {
		this.codNac = codNac;
	}
	
	public int getCodBar() {
		return codBar;
	}

	public void setCodBar(int codBar) {
		this.codBar = codBar;
	}
	public int getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(int idCorte) {
		this.idCorte = idCorte;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}


}
