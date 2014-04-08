package packblisters;

import java.util.LinkedList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;




class ModeloTablaHistorico extends AbstractTableModel implements TableModel {

    private static final long serialVersionUID = 1L;

    private static LinkedList<Historico> Datos = new LinkedList<Historico>();
    private LinkedList<TableModelListener> suscriptores = new LinkedList<TableModelListener>();
    private static Historico aux;

   
    
    public void setValueAt(Object dato, int fila, int columna) {
	System.out.println("MODELTABLAHISTORICO entrando en setvalueat");
	// Obtenemos la persona de la fila indicada
	aux = (Historico) ModeloTablaHistorico.Datos.get(fila);

	switch (columna) {
	// Nos pasan el apellido.
	case 0:
	    aux.setFecha((String) dato);
	    break;
	// Nos pasan la edad.
	case 1:
	    aux.setUsuario((String) dato);
	    break;
	    
	case 2:
	    aux.setMedicamento((String) dato);
	    break;
	    
	case 3:
	    aux.setCodNac((Integer) dato);
	    break;
	    
	case 4:
	    aux.setIdCorte((Integer) dato);
	    break;
	    
	case 5:
	    aux.setEvento((String) dato);
	    break;

	}

	ModeloTablaHistorico.this.fireTableDataChanged();
	// Aquí hay que avisar a los sucriptores del cambio.
	// Ver unpoco más abajo cómo.
	// TableModelEvent evento = new TableModelEvent (this, fila, fila,
	// columna);
	// avisaSuscriptores (evento);
	System.out.println("MODELTABLAHISTORICO saliendo en setvalueat");
    }

    public Object getValueAt(int fila, int columna) {
	System.out.println("MODELTABLAHISTORICO entrando en getvalueat");
	// Obtenemos la historia de la fila indicada
	Historico aux = (Historico) ModeloTablaHistorico.Datos.get(fila);

	switch (columna) {
	
	case 0:
	    return aux.getFecha();
	case 1:
	    return aux.getUsuario();
	case 2:
	    return aux.getMedicamento();
	case 3:
	    return aux.getCodNac();
	case 4:
	    return aux.getIdCorte();
	case 5:
	    return aux.getEvento();
	}
	return null;
    }

    public Historico getHistorico(int fila) {
	// Obtenemos la persona de la fila indicada
	Historico aux = (Historico) ModeloTablaHistorico.Datos.get(fila);
	return aux;
    }

    public void anadeHistorico(Historico nuevoHist) {
	// Añade el historico
		boolean existe = false;
		
		for (int i = 0; i < ModeloTablaHistorico.Datos.size(); i++) {
		    if (nuevoHist.getId() == ModeloTablaHistorico.Datos.get(i).getId()) {
			existe = true;
			break;
		    }
		}

		if (!existe) {
		    ModeloTablaHistorico.Datos.add(nuevoHist);
		    // Avisa a los suscriptores creando un TableModelEvent...
		    TableModelEvent evento;
		    evento = new TableModelEvent(this, this.getRowCount() - 1,
			    this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
			    TableModelEvent.UPDATE);

		    // ... y avisando a los suscriptores
		    avisaSuscriptores(evento);
		}

    }

    //NADIE LLAMA A ESTOS DOS !!!
    public void addTableModelListener(TableModelListener l) {
	suscriptores.add(l);
    }
    public void removeTableModelListener(TableModelListener l) {
	suscriptores.remove(l);
    }

    @Override
    public int getRowCount() {
	return ModeloTablaHistorico.Datos.size();
    }

    @Override
    public int getColumnCount() {
	// TODO Auto-generated method stub
	return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
	// Devuelve el nombre de cada columna. Este texto aparecerá en la
	// cabecera de la tabla.
	switch (columnIndex) {
	case 0:
	    return "Fecha y hora";
	case 1:
	    return "Usuario";
	case 2:
	    return "Medicamento";
	case 3:
	    return "Codigo Nacional";
	case 4:
	    return "Patron Corte";
	case 5:
	    return "Evento";
	default:
	    return null;
	}
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	// Devuelve la clase que hay en cada columna.
	switch (columnIndex) {
	case 0:
	    return String.class;
	case 1:
	    return String.class;
	case 2:
	    return String.class;
	case 3:
	    return Integer.class;
	case 4:
	    return Integer.class;
	case 5:
	    return String.class;
	default:
	    // Devuelve una clase Object por defecto.
	    return Object.class;
	}
    }
    
//    @Override
//    public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
//    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	return false;
    }

    /**
     * Pasa a los suscriptores el evento.
     */
    private void avisaSuscriptores(TableModelEvent evento) {
	int i;

	// Bucle para todos los suscriptores en la lista, se llama al metodo
	// tableChanged() de los mismos, pasándole el evento.
	for (i = 0; i < suscriptores.size(); i++)
	    ((TableModelListener) suscriptores.get(i)).tableChanged(evento);
    }

}
