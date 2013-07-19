package packblisters;

import java.util.LinkedList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

class ModeloTablaUsr extends AbstractTableModel implements TableModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static LinkedList<Usuario> datos = new LinkedList<Usuario>();
    private LinkedList<TableModelListener> suscriptores = new LinkedList<TableModelListener>();
    private static Usuario aux;

    public void setValueAt(Object dato, int fila, int columna) {
	// Obtenemos la persona de la fila indicada
	aux = (Usuario) datos.get(fila);

	switch (columna) {
	// Nos pasan el apellido.
	case 0:
	    aux.setNombre((String) dato);
	    break;
	// Nos pasan la edad.
	case 1:
	    aux.setRoot((boolean) dato);
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
	Usuario aux = (Usuario) datos.get(fila);
	switch (columna) {
	// Nos piden el apellido
	case 0:
	    return aux.getNombre();

	    // Nos piden la edad.
	case 1:
	    return new Boolean(aux.getRoot());

	}
	return null;
    }

    public Usuario getUsr(int fila) {
	// Obtenemos la persona de la fila indicada
	Usuario aux = (Usuario) datos.get(fila);
	return aux;
    }

    public void anadeUsr(Usuario nuevoUsr) {
	// Añade la persona al modelo
	boolean existe = false;
	for (int i = 0; i < datos.size(); i++) {
	    if ((nuevoUsr.getId() == datos.get(i).getId())&& nuevoUsr.getId()!=0) {
		existe = true;
		break;
	    }
	}

	if (!existe) {
	    datos.add(nuevoUsr);
	    // Avisa a los suscriptores creando un TableModelEvent...
	    TableModelEvent evento;
	    evento = new TableModelEvent(this, this.getRowCount() - 1,
		    this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
		    TableModelEvent.INSERT);

	    // ... y avisando a los suscriptores
	    avisaSuscriptores(evento);
	}

    }

    public void borraUsr(int fila) {
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

	return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
	// Devuelve el nombre de cada columna. Este texto aparecerá en la
	// cabecera de la tabla.
	switch (columnIndex) {
	case 0:
	    return "Nombre";
	case 1:
	    return "Administrador";
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
	    return Boolean.class;
	default:
	    // Devuelve una clase Object por defecto.
	    return Object.class;
	}
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

	return true;
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
