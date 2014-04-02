package packblisters;

import java.util.LinkedList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

class ModeloTablaMeds extends AbstractTableModel implements TableModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static LinkedList<Medicamento> datos = new LinkedList<Medicamento>();
    private LinkedList<TableModelListener> suscriptores = new LinkedList<TableModelListener>();
    private static Medicamento aux;

    public void setValueAt(Object dato, int fila, int columna) {
	// Obtenemos la persona de la fila indicada
	aux = (Medicamento) datos.get(fila);

	switch (columna) {
	// Nos pasan el apellido.
	case 0:
	    aux.setNombre((String) dato);
	    break;
	// Nos pasan la edad.
	case 1:
	    aux.setCodnac((Integer) dato);
	    break;
	case 2:
	    aux.setCodbar((Long) dato);
	    break;

	}

	this.fireTableDataChanged();
	// Aquí hay que avisar a los sucriptores del cambio.
	// Ver unpoco más abajo cómo.
	// TableModelEvent evento = new TableModelEvent (this, fila, fila,
	// columna);
	// avisaSuscriptores (evento);
    }

    public Object getValueAt(int fila, int columna) {
	// Obtenemos la persona de la fila indicada
	Medicamento aux = (Medicamento) datos.get(fila);
	switch (columna) {
	// Nos piden el apellido
	case 0:
	    return aux.getNombre();

	    // Nos piden la edad.
	case 1:
	    return new Integer(aux.getCodnac());
	    
	case 2:
	    return new Long (aux.getCodbar());
	 
	}
	return null;
    }

    public Medicamento getmed(int fila) {
	// Obtenemos la persona de la fila indicada
	Medicamento aux = (Medicamento) datos.get(fila);
	return aux;
    }

    public void anademed(Medicamento nuevoMed) {
	// Añade la persona al modelo
	boolean existe = false;
	for (int i = 0; i < datos.size(); i++) {
	    if ((nuevoMed.getId() == datos.get(i).getId()) && nuevoMed.getId()!=0) {
		existe = true;
		break;
	    }
	}

	if (!existe) {
	    datos.add(nuevoMed);
	    // Avisa a los suscriptores creando un TableModelEvent...
	    TableModelEvent evento;
	    evento = new TableModelEvent(this, this.getRowCount() - 1,
		    this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
		    TableModelEvent.INSERT);

	    // ... y avisando a los suscriptores
	    avisaSuscriptores(evento);
	}

    }

    public void borramed(int fila) {
	// Se borra la fila
	datos.remove(fila);

	// Y se avisa a los suscriptores, creando un TableModelEvent...
	TableModelEvent evento = new TableModelEvent(this, fila, fila,
		TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

	// ... y pasándoselo a los suscriptores
	avisaSuscriptores(evento);
    }
    
    public void addTableModelListener(TableModelListener l) {
	suscriptores.add(l);
    }
    public void removeTableModelListener(TableModelListener l) {
	suscriptores.remove(l);
    }

    @Override
    public int getRowCount() {
	return datos.size();
    }

    @Override
    public int getColumnCount() {
	// TODO Auto-generated method stub
	return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
	// Devuelve el nombre de cada columna. Este texto aparecerá en la
	// cabecera de la tabla.
	switch (columnIndex) {
	case 0:
	    return "Nombre";
	case 1:
	    return "Código Nacional";
	case 2: 
	    return "Código de barras";
	default:
	    return null;
	}
    }

    @Override
    public Class getColumnClass(int columnIndex) {
	// Devuelve la clase que hay en cada columna.
	switch (columnIndex) {
	case 0:
	    // La columna uno contiene el apellido de la persona, que es
	    // un String
	    return String.class;
	case 1:
	    // La columna dos contine la edad de la persona, que es un
	    // Integer (no vale int, debe ser una clase)
	    return Integer.class;
	case 2:
	    return Long.class;
	    
	default:
	    // Devuelve una clase Object por defecto.
	    return Object.class;
	}
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	// TODO Auto-generated method stub
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
