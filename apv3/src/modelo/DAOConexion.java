	package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import controlador.ControladorErrores;

public class DAOConexion {
	//Clase para conectar a la base de datos
	private String servidor,usuario,contrasenia,puerto,baseDatos;
	private Connection miConexion;
	private ControladorErrores ce;
	
	public DAOConexion(){
		this.servidor="localhost";//127.0.0.1
		this.usuario="postgres";
		this.contrasenia="97499510";
		this.puerto="5432";
		this.baseDatos="Metrisoft";
		this.ce= new ControladorErrores();
	}
	
	public boolean conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			miConexion= DriverManager.
					getConnection("jdbc:postgresql://"+servidor+":"+puerto+"/"+baseDatos, 
							usuario, contrasenia);
			System.out.println("Conectado a: " + miConexion.getCatalog());
			return true;
			
		} catch (Exception ex) {
			ce.imprimirBitacora(ex.getMessage(), "DAOConexion-Conectar");
			//ex.printStackTrace();
			return false;
		}
	}
	///Método para cerrar la conexión
	public boolean desconectar(){
		try {
			miConexion.close();
			System.out.println("Conexión cerrada");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//Método para recuperar la conexión
	public Connection getConexion(){
		return miConexion;
	}
	public void establerConexion() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
			miConexion= DriverManager.
					getConnection("jdbc:postgresql://"+servidor+":"+puerto+"/"+baseDatos, 
							usuario, contrasenia);
			System.out.println("Conectado a: " + miConexion.getCatalog());
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void cerrarConexion(){
		try {
			miConexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
