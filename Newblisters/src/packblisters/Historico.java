package packblisters;




public class Historico {
    
    private String Usuario, Medicamento, Evento;
    private String fecha;
    private int id;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private int CodNac,IdCorte;
    
    
    public String getUsuario() {
        return Usuario;
    }
    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
    public String getMedicamento() {
        return Medicamento;
    }
    public void setMedicamento(String medicamento) {
        Medicamento = medicamento;
    }
    public String getEvento() {
        return Evento;
    }
    public void setEvento(String evento) {
        Evento = evento;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fe) {
        this.fecha = fe;
    }
    public int getCodNac() {
        return CodNac;
    }
    public void setCodNac(int codNac) {
        CodNac = codNac;
    }
    public int getIdCorte() {
        return IdCorte;
    }
    public void setIdCorte(int idCorte) {
        IdCorte = idCorte;
    }
}
