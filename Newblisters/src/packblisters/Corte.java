package packblisters;

public class Corte {

	private int id;
	private String gcode;
	private static String cancelar = "^x\r";
	private static String pausar = "!\r";
	private static String reanudar = "~\r";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGcode() {
		return gcode;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public String getCancelar() {
		return cancelar;
	}

	public String getPausar() {
		return pausar;
	}

	public String getReanudar() {
		return reanudar;
	}

}
