package packblisters;



public class Medicamento {
    private int id;
    private int codnac;
    private long codbar;
    private String nombre;
    private String rutaimg;
    private int idcorte;
    private Corte micorte;
    private StringBuffer recorte;

    public int getIdcorte() {
	return idcorte;
    }

    public void setIdcorte(int idcorte) {
	this.idcorte = idcorte;
    }

    public Corte getMicorte() {
	return micorte;
    }

    public void setMicorte(Corte micorte) {
	this.micorte = micorte;
    }

    int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getCodnac() {
	return codnac;
    }

    public void setCodnac(int codnac) {
	this.codnac = codnac;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    @Override
    public String toString() {
	return codnac + " :	" + nombre; //$NON-NLS-1$
    }
    
    public long getCodbar() {
        return codbar;
    }

    public void setCodbar(long l) {
        this.codbar = l;
    }

    public void setRutaimg(String rutaimg) {
	this.rutaimg = rutaimg;
    }

    public String getRutaimg() {
	return rutaimg;
    }

    public void setRecorte(StringBuffer recorte) {
	// TODO Auto-generated method stub
	this.recorte = recorte;
    }

    public StringBuffer getRecorte() {
	return recorte;
    }

}
