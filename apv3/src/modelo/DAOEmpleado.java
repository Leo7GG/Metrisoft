package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOEmpleado {
	private Integer idEmpleados;
	private String nombre, paterno, materno, domicilio, ciudad,
	 cp, telefono, correo;
	private IntegerProperty empleado_id_proyecto;
	private StringProperty nombreEmpleado;
	//Base de Datos
	private static DAOConexion con;//Variable para conectar a la bd
	private PreparedStatement comando;  //Operaciones a la BD
	private ObservableList<DAOEmpleado> lista; //Almacena los registro de la tabla
	
	//Constructor
	public DAOEmpleado() {
		// TODO Auto-generated constructor stub
		this.idEmpleados=0;
		this.nombre=this.paterno=this.materno=this.domicilio="";
		this.ciudad=this.cp=this.telefono="";
		this.con= new DAOConexion();
		this.lista = FXCollections.observableArrayList();//Inicializar lista
	}
	/**
	 * @param empleado_id_proyecto
	 * @param nombreEmpleado
	 */
	public DAOEmpleado(Integer empleado_id_proyecto, String nombreEmpleado) {
		super();
		this.empleado_id_proyecto = new SimpleIntegerProperty(empleado_id_proyecto);
		this.nombreEmpleado = new SimpleStringProperty(nombreEmpleado);
	}
	//Constructor sobrecargado para llamarlo con una instancia al DAORegistroProyecto
	/*
	 * Get y set para las variables property esto para evitar afectar las demas instancias
	 */
	public Integer getEmpleado_id_proyecto(){
		return empleado_id_proyecto.get();
	}
	public void setEmpleado_id_proyecto(Integer empleado_id_proyecto){
		this.empleado_id_proyecto = new SimpleIntegerProperty(empleado_id_proyecto);
	}
	public String getNombreEmpleado(){
		return nombreEmpleado.get();
	}
	public void setNombreEmpleado(String nombreEmpleado){
		this.nombreEmpleado = new SimpleStringProperty(nombreEmpleado);
	}
	public  IntegerProperty empleado_idProperty(){
		return empleado_id_proyecto;
	}
	public StringProperty nombreEmpleadoProperty(){
		return nombreEmpleado;
		
	}
	//Getters and Setters
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setMaterno(String materno) {
		this.materno = materno;
	}
	public void setidEmpleados(Integer idEmpleados) {
		this.idEmpleados = idEmpleados;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getTelefono() {
		return telefono;
	}
	
	public String getPaterno() {
		return paterno;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getMaterno() {
		return materno;
	}
	public Integer getidEmpleados() {
		return idEmpleados;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	public String getCp() {
		return cp;
	}
	public String getCorreo() {
		return correo;
	}
	public String getCiudad() {
		return ciudad;
	}
	
	//Métodos CRUD
	public boolean insertar(){
		try {
			//Todo lo que puede generar error
			String sql="";
			if(con.conectar()==true){
				sql="insert into empleado "
						+ "values (default,?,?,?,?,?,?,?,?,default)";
				comando =con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre);
				comando.setString(2, this.paterno);
				comando.setString(3, this.materno);
				comando.setString(4, this.domicilio);
				comando.setString(5, this.ciudad);
				comando.setString(6, this.cp);
				comando.setString(7, this.telefono);
				comando.setString(8, this.correo);
				//Ejecutate
				comando.execute();
				return true;
			}
			else{
				return false;
			}
		} 
		catch (Exception ex) {
			//Código para trabajer el error
			ex.printStackTrace();
			return false;
		}
		finally{
			//Código que se tiene que ejecutar 
			//Exista o no un error
			con.desconectar();
		}
	}
	
	//Métodos para recuperar el contenido de las listas
	public ObservableList<DAOEmpleado> consultar(String consulta){
		ResultSet temporal;
		try {
			con.conectar();
			comando = con.getConexion().prepareStatement(consulta);
			temporal =  comando.executeQuery();
			while(temporal.next()){
				DAOEmpleado b = new DAOEmpleado();
				b.idEmpleados= temporal.getInt("idempleado");
				b.nombre=temporal.getString("nombre");
				b.paterno = temporal.getString("paterno");
				b.materno=temporal.getString("materno");
				b.domicilio = temporal.getString("direccion");
				b.ciudad = temporal.getString("ciudad");
				b.cp = temporal.getString("cp");
				b.telefono = temporal.getString("telefono");
				b.correo = temporal.getString("correo");
				//temporal.getString(1).charAt(1);
				lista.add(b);
			}
			return lista;
		} catch (Exception ex) {
			ex.printStackTrace();
			return lista;
		}
		finally{

			///temporal.close();
		}
	}
	
	//Método para eliminar de manera temporal un Bibliotecario
	public boolean eliminar(){
		try {
			if(con.conectar()){
				String sql="update empleado set estatus='0' where idempleado=?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.idEmpleados);
				comando.execute();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	
 	//Método para reactivar un elemento dado de baja de manrea temporal
	public boolean reactivar(){
		try{
			if(con.conectar()){
				String sql = "update empleado set estatus='1' where idempleado=?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1,this.idEmpleados);
				comando.execute();
			}
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
		finally {
			con.desconectar();
		}
	}

	public boolean modificar(){
		String sql="";
		try {
			if(con.conectar()){
				sql="update empleado set nombre=?, paterno=?, materno=?, "
						+ "direccion=?, ciudad=?, cp=?, telefono=?, correo=?"
						+ "where idempleado=?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre);
				comando.setString(2, this.paterno);
				comando.setString(3, this.materno);
				comando.setString(4, this.domicilio);
				comando.setString(5, this.ciudad);
				comando.setString(6, this.cp);
				comando.setString(7, this.telefono);
				comando.setString(8, this.correo);
				comando.setInt(9, this.idEmpleados);//El ID del bibliotecario a modificar
				comando.execute();
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	
	//Este metodo se encargar� de llenar la informacion que necesitamos en el comboBox de la interfaz
	//de proyecto
	public static void consultarInformacionEmplado(Connection con, ObservableList<DAOEmpleado> listaEmpleado) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT idempleado, nombre FROM empleado WHERE estatus='1'");
			while (rs.next()) {
				listaEmpleado.add(new DAOEmpleado(
						rs.getInt("idempleado"), 
						rs.getString("nombre")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombreEmpleado.get();
	}
}
