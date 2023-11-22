package Engine.Developer.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Engine.Developer.Logger.Log;
import Engine.Developer.Logger.Logger;

import java.awt.Color;
import java.sql.*;

public class Database {
    private static final Database sDatabase = new Database();
	private Statement mConnection;

    private Database() {
		mConnection = null;
    }

    public static Database Instance() {
        return sDatabase;
    }

    private static Exception lastError = null;  // Información de último error SQL ocurrido
	
	/** Inicializa una BD SQLITE y devuelve una conexión con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexión con la base de datos indicada. Si hay algún error, se devuelve null
	 */
	public void InitConnection( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
		    Statement st = con.createStatement();      // (1) Solo para foreign keys
		    st.execute( "PRAGMA foreign_keys = ON" );  // (1)
		    st.close();                                // (1)
		    mConnection = st;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			e.printStackTrace();
			mConnection = null;
		}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public Statement usarBD() {
		try {
			mConnection.setQueryTimeout(30);  // poner timeout 30 msg
			return mConnection;
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			try {
				statement.executeUpdate("create table usuario " +
					// "(nick string "  // (2) Esto sería sin borrado en cascada ni relación de claves ajenas
					"(nick string PRIMARY KEY" // (1) Solo para foreign keys
					+ ", password string, nombre string, apellidos string" +
					", telefono integer, fechaultimologin bigint, tipo string" +
					", emails string)");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table partida " +
					"(usuario_nick string REFERENCES usuario(nick) ON DELETE CASCADE, fechapartida bigint, puntuacion integer)"); // (1) Solo para foreign keys
					// "(usuario_nick string, fechapartida bigint, puntuacion integer)"); // (2) Esto sería sin borrado en cascada
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			return statement;
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * UTILIZAR ESTE MÉTODO CON PRECAUCIÓN. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			statement.executeUpdate("drop table if exists usuario");
			statement.executeUpdate("drop table if exists partida");
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet Query(String con) {
		try {
			return mConnection.executeQuery(con);
		} catch (SQLException e) {
			Log v = Logger.Instance().GetLog("DataBases");

        	Logger.Instance().Log(v, "Statement failure. " + con, java.util.logging.Level.WARNING, 1, Color.YELLOW);
			return null;
		}
		
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexión abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 */
	public void cerrarBD() {

		try {
			mConnection.execute("INSERT INTO \"main\".\"PlayerSession\"(\"ID\",\"PlayTime\",\"Arrows\",\"Bombs\",\"FirstLevel\",\"LastLevel\") VALUES (2,'',0,0,'','');");

			if (mConnection!=null) mConnection.close();
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
		}
	}
	
	/** Devuelve la información de excepción del último error producido por cualquiera 
	 * de los métodos de gestión de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
}
