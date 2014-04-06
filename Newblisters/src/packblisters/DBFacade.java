package packblisters;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class DBFacade implements TableModelListener {

    // Objetos a tratar de la BBDD
    private Connection conexion = null;
    private PreparedStatement sentenciapre = null;
    private ResultSet resultados = null;
    private JOptionPane panel;

    private Connection conectar() throws ClassNotFoundException, SQLException {

	Class.forName(Messages.getString("DBFacade.DBDriverClass")); // Aki iría el driver //$NON-NLS-1$

	// Conexion con la BBDD
	Connection c = DriverManager.getConnection(Messages
		.getString("DBFacade.Connection")); //$NON-NLS-1$
	return c;
    }

    private String leerBBDD(String nomusuario) throws Exception {

	conexion = conectar();
	// Sentencia preparada
	sentenciapre = conexion
		.prepareStatement("select password from usuarios  WHERE nombre=?");
	sentenciapre.setString(1, nomusuario);
	resultados = sentenciapre.executeQuery();
	resultados.next();
	return resultados.getString("password"); // Nombre de campo a recoger de
						 // la BBDD

    }

    public boolean comprobarLogin(String user, char[] pass) {

	// Aki comprobaríamos con la BBDD

	try {
	    String contraseña = leerBBDD(user);
	    String pwd = new String(pass);
	    return (contraseña.contentEquals(pwd));

	} catch (Exception e) {
	   
	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorLogin")); //$NON-NLS-1$
	    return false;

	} finally // CErrar conexion con BBDD
	{
	    try {
		conexion.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void getMedicamentos(DefaultListModel<Medicamento> modelolista) {
	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion
		    .prepareStatement("select * from medicamentos;");
	    resultados = sentenciapre.executeQuery();

	    while (resultados.next()) {
		Medicamento medicamento = new Medicamento();

		medicamento.setId(resultados.getInt("medicamentos_id"));
		medicamento.setNombre(resultados.getString("nombre"));
		medicamento.setCodnac(resultados.getInt("codnac"));
		medicamento.setCodbar(resultados.getLong("codbar"));
		medicamento.setRutaimg(resultados.getString("rutaimg"));
		medicamento.setIdcorte(resultados.getInt("idcorte"));

		modelolista.addElement(medicamento);
	    }
	} catch (Exception e) {

	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorMedicamento")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
	    try {
		conexion.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void getRecorteMedicamento(Medicamento med) {
	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion
		    .prepareStatement("select * from cortes WHERE idcorte=?;");
	    sentenciapre.setInt(1, med.getIdcorte());
	    resultados = sentenciapre.executeQuery();

	    while (resultados.next()) {
		Corte c = new Corte();

		c.setId(resultados.getInt("idcorte"));
		c.setGcode(resultados.getString("gcode"));
		med.setMicorte(c);
	    }

	} catch (Exception e) {

	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorRecorte")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
	    try {
		conexion.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    //
    // Para la tabla !!
    //
    public void getMedicamentos(ModeloTablaMeds modelotablao) {

	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion.prepareStatement("select * from medicamentos;");
	    resultados = sentenciapre.executeQuery();

	    // Vector<Medicamento> vmed;
	    // Vector<String> idcolumns = new Vector<String>();

	    // COLUMNAS
	    // idcolumns.add("ID");
	    // idcolumns.add("Nombre");
	    // idcolumns.add("Código Nacional");
	    // idcolumns.add("Ruta");
	    // idcolumns.add("Corte");
	    // modelotabla.setColumnIdentifiers(idcolumns);

	    // FILAS
	    while (resultados.next()) {
		Medicamento medicamento = new Medicamento();

		medicamento.setId(resultados.getInt("medicamentos_id"));
		medicamento.setNombre(resultados.getString("nombre"));
		medicamento.setCodnac(resultados.getInt("codnac"));
		medicamento.setCodbar(resultados.getLong("codbar"));
		medicamento.setRutaimg(resultados.getString("rutaimg"));
		medicamento.setIdcorte(resultados.getInt("idcorte"));
		System.out.println(medicamento);
		modelotablao.anademed(medicamento);

	    }

	} catch (Exception e) {

	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorMedicamntosTabla")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
		 //if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
	     if (sentenciapre != null) try { sentenciapre.close(); } catch (SQLException logOrIgnore) {}
	     if (conexion != null) try { conexion.close(); } catch (SQLException logOrIgnore) {}
	}
    }

    public void getIdCorte(DefaultComboBoxModel modeloCombo) {
	
	try {
	    conexion = conectar();
	    // Sentencia preparada   
	    sentenciapre = conexion.prepareStatement("select idcorte from cortes;");
	    resultados = sentenciapre.executeQuery();

	    while (resultados.next()) {
	    	modeloCombo.addElement(resultados.getInt("idcorte"));
	    }

	} catch (Exception e) {

	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println(Messages
		    .getString("DBFacade.ErrorMedicamntosTabla")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
	    try {
		conexion.close();

	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public int insertarIdCorte(String gcode) {
	
	int i=0;
	
	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion
		    .prepareStatement("insert into cortes (gcode)"
			    + "	VALUES (?)", Statement.RETURN_GENERATED_KEYS);
	    
	
		sentenciapre.setString(1, gcode);
		sentenciapre.executeUpdate();
		
		ResultSet res = sentenciapre.getGeneratedKeys();
		res.last();
		i = res.getInt(1);
		
		

	} catch (Exception e) {

	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorID")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
	    try {
		sentenciapre.close(); // NOS FALTA EN TODO
		conexion.close();
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return i;
    }

    public void getUsuarios(ModeloTablaUsr modelotablao) {
	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion.prepareStatement("select * from usuarios;");
	    resultados = sentenciapre.executeQuery();

	    // FILAS
	    while (resultados.next()) {
		Usuario usr = new Usuario();

		usr.setId(resultados.getInt("id"));
		usr.setNombre(resultados.getString("nombre"));
		usr.setRoot(resultados.getBoolean("root"));

		modelotablao.anadeUsr(usr);

	    }

	} catch (Exception e) {

	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println(Messages
		    .getString("DBFacade.ErrorMedicamntosTabla")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
	    try {
		conexion.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

    public Usuario getUsr(String nombre) {

	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion
		    .prepareStatement("select * from usuarios WHERE nombre=?;");
	    sentenciapre.setString(1, nombre);
	    resultados = sentenciapre.executeQuery();

	    VLogin.UsuarioLogueado = new Usuario();
	    while (resultados.next()) {

		VLogin.UsuarioLogueado.setId(resultados.getInt("id"));
		VLogin.UsuarioLogueado
			.setNombre(resultados.getString("nombre"));
		VLogin.UsuarioLogueado.setRoot(resultados.getBoolean("root"));
		System.out.println("getUsr: "
			+ VLogin.UsuarioLogueado.toString());
	    }

	} catch (Exception e) {
	    VLogin.UsuarioLogueado = null;
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorUsuario")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
	    try {
		conexion.close();

	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	return VLogin.UsuarioLogueado;
    }

    public void getHistorico(ModeloTablaHistorico modelotablao) {
	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion
		    .prepareStatement("select * from historico;");
	    resultados = sentenciapre.executeQuery();

	    // Vector<Medicamento> vmed;
	    // Vector<String> idcolumns = new Vector<String>();

	    // COLUMNAS
	    // idcolumns.add("ID");
	    // idcolumns.add("Nombre");
	    // idcolumns.add("Código Nacional");
	    // idcolumns.add("Ruta");
	    // idcolumns.add("Corte");
	    // modelotabla.setColumnIdentifiers(idcolumns);

	    // FILAS
	    while (resultados.next()) {
		Historico hist = new Historico();

		hist.setId(resultados.getInt("id"));
		hist.setFecha(resultados.getTimestamp("fecha").toString());
		hist.setUsuario(resultados.getString("usuario"));
		hist.setMedicamento(resultados.getString("medicamento"));
		hist.setCodNac(resultados.getInt("codigonacional"));
		hist.setIdCorte(resultados.getInt("idcorte"));
		hist.setEvento(resultados.getString("evento"));

		modelotablao.anadeHistorico(hist);

	    }

	} catch (Exception e) {

	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorHistorico")); //$NON-NLS-1$

	} finally // Cerrar conexion con BBDD
	{
	    try {
		conexion.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

    // Escuchador de eventos de cambio en el modelo de la tabla
    @Override
    public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	if (e.getType() == TableModelEvent.UPDATE) {
	    System.out.println("DFacade ha escuchado cambio en el modelo");
	    Usuario aux = ((ModeloTablaUsr) e.getSource()).getUsr(e
		    .getLastRow());

	    System.out.println(aux.getNombre());
	}

    }

    public void insertarHistorico(String usuario, String nombre, int codnac,
	    int idcorte, String evento, String incidencia) {

	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion
		    .prepareStatement("insert into historico (usuario, medicamento, codigonacional, idcorte, evento)"
			    + "	VALUES (?,?,?,?,?,?)");
	    sentenciapre.setString(1, usuario);
	    sentenciapre.setString(2, nombre);
	    sentenciapre.setInt(3, codnac);
	    sentenciapre.setInt(4, idcorte);
	    sentenciapre.setString(5, evento);
	    sentenciapre.setString(6, incidencia);

	    sentenciapre.executeUpdate();

	} catch (Exception e) {

	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorHistorico")); //$NON-NLS-1$

	} finally // CErrar conexion con BBDD
	{
	    try {
		sentenciapre.close(); // NOS FALTA EN TODO
		conexion.close();

	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }
    
    public void insertarMed(Medicamento med) 
    {

	try {
	    conexion = conectar();
	    // Sentencia preparada
	    sentenciapre = conexion
		    .prepareStatement("insert into medicamentos (nombre, codnac, codbar, rutaimg, idcorte)"
			    + "	VALUES (?,?,?,?,?)");
		sentenciapre.setString(1, med.getNombre());
		sentenciapre.setInt(2, med.getCodnac());
		sentenciapre.setLong(3, med.getCodbar());
		sentenciapre.setString(4, med.getRutaimg());
		sentenciapre.setInt(5, med.getIdcorte());
		
	    int filasAfectadas= sentenciapre.executeUpdate();
        if (filasAfectadas == 0) {
            throw new SQLException("Creacion de medicamento fallida, no se modificó ninguna fila.");
        }
        ResultSet generatedKeys = sentenciapre.getGeneratedKeys();
        if (generatedKeys.next()) {
            med.setId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creacion de medicamento fallida, no se obtuvo la clave generada.");
        }
	    
	} catch (SQLException | ClassNotFoundException e) {

	    e.printStackTrace();
	    System.out.println(Messages.getString("DBFacade.ErrorNuevoMed")); //$NON-NLS-1$

	} finally // Cerrar conexion con BBDD
	{
	     if (sentenciapre != null) try { sentenciapre.close(); } catch (SQLException logOrIgnore) {}
	     if (conexion != null) try { conexion.close(); } catch (SQLException logOrIgnore) {}
	}
    }

    public void borrarMed(String nombre) {
		try {
			conexion = conectar();
			// Sentencia preparada
			sentenciapre = conexion.prepareStatement("delete from medicamentos where nombre=?");
			sentenciapre.setString(1, nombre);

			sentenciapre.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(Messages.getString("DBFacade.ErrorBorraMed")); //$NON-NLS-1$

		} finally // Cerrar conexion con BBDD
		{			
		     if (sentenciapre != null) try { sentenciapre.close(); } catch (SQLException logOrIgnore) {}
		     if (conexion != null) try { conexion.close(); } catch (SQLException logOrIgnore) {}
		}
	}

    public void modificarMed(Medicamento med) {
		ResultSet generatedKeys = null;
		try {
			conexion = conectar();
			// Sentencia preparada
			sentenciapre = conexion
					.prepareStatement("UPDATE `medicamentos` SET " +
							"`nombre`=?, `codnac`=?,`codbar`=?, `rutaimg`=?,  `idcorte`=? WHERE `medicamentos_id`=?", Statement.RETURN_GENERATED_KEYS);
			sentenciapre.setString(1, med.getNombre());
			sentenciapre.setInt(2, med.getCodnac());
			sentenciapre.setLong(3, med.getCodbar());
			sentenciapre.setString(4, med.getRutaimg());
			sentenciapre.setInt(5, med.getIdcorte());
			sentenciapre.setInt(6, med.getId());
			
			int filasAfectadas= sentenciapre.executeUpdate();
			 
		        if (filasAfectadas == 0) {
		            throw new SQLException("Modificación de medicamento fallida, no se modificó ninguna fila.");
		        }

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(Messages.getString("DBFacade.ErrorModMed")); //$NON-NLS-1$

		} finally // Cerrar conexion con BBDD
		{
			 if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
		     if (sentenciapre != null) try { sentenciapre.close(); } catch (SQLException logOrIgnore) {}
		     if (conexion != null) try { conexion.close(); } catch (SQLException logOrIgnore) {}
		}
		
	}
    
    
	public void insertarUsr(Usuario user, char[] password) {
		ResultSet generatedKeys = null;
		try {
			conexion = conectar();
			// Sentencia preparada
			sentenciapre = conexion
					.prepareStatement("insert into usuarios (nombre, password, root)"
							+ "	VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			sentenciapre.setString(1, user.getNombre());
			sentenciapre.setString(2, String.valueOf(password));
			sentenciapre.setBoolean(3, user.getRoot());
			//seguridad de contraseña
			 Arrays.fill(password, '0');
			
			int filasAfectadas= sentenciapre.executeUpdate();
		        if (filasAfectadas == 0) {
		            throw new SQLException("Creacion de usuario fallida, no se modificó ninguna fila.");
		        }
		        generatedKeys = sentenciapre.getGeneratedKeys();
		        if (generatedKeys.next()) {
		            user.setId(generatedKeys.getInt(1));
		        } else {
		            throw new SQLException("Creacion de usuario fallida, no se obtuvo la clave generada.");
		        }

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(Messages.getString("DBFacade.ErrorNuevoUsr")); //$NON-NLS-1$

		} finally // CErrar conexion con BBDD
		{
			 if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
		     if (sentenciapre != null) try { sentenciapre.close(); } catch (SQLException logOrIgnore) {}
		     if (conexion != null) try { conexion.close(); } catch (SQLException logOrIgnore) {}
		}
	}
	

	public void borrarUsr(String nombre) {
		try {
			conexion = conectar();
			// Sentencia preparada
			sentenciapre = conexion
					.prepareStatement("delete from usuarios where nombre=?");
			sentenciapre.setString(1, nombre);

			sentenciapre.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(Messages.getString("DBFacade.ErrorBorraUsr")); //$NON-NLS-1$

		} finally // Cerrar conexion con BBDD
		{
			try {
				sentenciapre.close(); // NOS FALTA EN TODO
				conexion.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public void modificarUsr(Usuario user, char[] password) {
		ResultSet generatedKeys = null;
		try {
			conexion = conectar();
			// Sentencia preparada
			sentenciapre = conexion
					.prepareStatement("UPDATE `usuarios` SET " +
							"`password`=?, `root`=? WHERE `id`=? and `nombre`=?", Statement.RETURN_GENERATED_KEYS);
			sentenciapre.setString(1, String.valueOf(password));
			sentenciapre.setBoolean(2, user.getRoot());
			sentenciapre.setInt(3, user.getId());
			sentenciapre.setString(4, user.getNombre());
			
			
			int filasAfectadas= sentenciapre.executeUpdate();
			
			//seguridad de contraseña
			 Arrays.fill(password, '0');
			 
		        if (filasAfectadas == 0) {
		            throw new SQLException("Modificación de usuario fallida, no se modificó ninguna fila.");
		        }
		        generatedKeys = sentenciapre.getGeneratedKeys();
		        if (generatedKeys.next()) {
		            user.setId(generatedKeys.getInt(1));
		        } else {
		            throw new SQLException("Modificación de usuario fallida, no se obtuvo la clave generada.");
		        }

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(Messages.getString("DBFacade.ErrorNuevoUsr")); //$NON-NLS-1$

		} finally // Cerrar conexion con BBDD
		{
			 if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
		     if (sentenciapre != null) try { sentenciapre.close(); } catch (SQLException logOrIgnore) {}
		     if (conexion != null) try { conexion.close(); } catch (SQLException logOrIgnore) {}
		}
		
	}

	}
