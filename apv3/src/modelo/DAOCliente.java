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

public class DAOCliente {
	private Integer idCiente;
	private String nombre, paterno, materno, domicilio, ciudad,
	empresa, cp, telefono, correo;
	private IntegerProperty cliente_id_proyecto;
	private StringProperty nombreCliente;
	//Base de Datos
	private static DAOConexion con;//Variable para conectar a la bd
	private static PreparedStatement comando;  //Operaciones a la BD
	private ObservableList<DAOCliente> lista; //Almacena los registro de la tabla
	
	//Constructor
	public DAOCliente() {
		// TODO Auto-generated constructor stub
		this.idCiente=0;
		this.nombre=this.paterno=this.materno=this.domicilio="";
		this.ciudad=this.empresa=this.cp=this.telefono="";
		this.con= new DAOConexion();
		this.lista = FXCollections.observableArrayList();//Inicializar lista
	}
	public DAOCliente(Integer cliente_id_proyecto, String nombreCliente) {
		super();
		this.cliente_id_proyecto = new SimpleIntegerProperty(cliente_id_proyecto);
		this.nombreCliente = new SimpleStringProperty(nombreCliente);
	}
	//GET Y SET para las variables propertys
	public Integer getCliente_id_proyecto(){
		return cliente_id_proyecto.get();
	}
	public void setCliente_id_proyecto(Integer cliente_id_proyecto){
		this.cliente_id_proyecto = new SimpleIntegerProperty(cliente_id_proyecto);
	}
	public String getNombreCliente(){
		return nombreCliente.get();
	}
	public void setNombreCliente(String nombreCliente){
		this.nombreCliente = new SimpleStringProperty(nombreCliente);
	}
	public  IntegerProperty cliente_id_Property(){
		return cliente_id_proyecto ;
	}
	public StringProperty nombreClienteProperty(){
		return nombreCliente ;
		
	}
	//Getters and Setters
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setMaterno(String materno) {
		this.materno = materno;
	}
	public void setidCiente(Integer idCiente) {
		this.idCiente = idCiente;
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
	public String getEmpresa() {
		return empresa;
	}
	public String getNombre() {
		return nombre;
	}
	public String getMaterno() {
		return materno;
	}
	public Integer getidCiente() {
		return idCiente;
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
				sql="insert into cliente "
						+ "values (default,?,?,?,?,?,?,?,?,?,default)";
				comando =con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre);
				comando.setString(2, this.paterno);
				comando.setString(3, this.materno);
				comando.setString(4, this.domicilio);
				comando.setString(5, this.ciudad);
				comando.setString(6, this.empresa);
				comando.setString(7, this.cp);
				comando.setString(8, this.telefono);
				comando.setString(9, this.correo);
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
	public ObservableList<DAOCliente> consultar(String consulta){
		ResultSet temporal;
		try {
			con.conectar();
			comando = con.getConexion().prepareStatement(consulta);
			temporal =  comando.executeQuery();
			while(temporal.next()){
				DAOCliente b = new DAOCliente();
				b.idCiente= temporal.getInt("idcliente");
				b.nombre=temporal.getString("nombre");
				b.paterno = temporal.getString("paterno");
				b.materno=temporal.getString("materno");
				b.domicilio = temporal.getString("direccion");
				b.ciudad = temporal.getString("ciudad");
				b.empresa = temporal.getString("empresa");
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
			con.desconectar();
			///temporal.close();
		}
	}
	
	//Método para eliminar de manera temporal un Bibliotecario
	public boolean eliminar(){
		try {
			if(con.conectar()){
				String sql="update cliente set estatus='0' where idcliente=?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.idCiente);
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
				String sql = "update cliente set estatus='1' where idcliente=?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1,this.idCiente);
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
				sql="update cliente set nombre=?, paterno=?, materno=?, "
						+ "direccion=?, ciudad=?, empresa=?, cp=?, telefono=?, correo=?"
						+ "where idcliente=?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre);
				comando.setString(2, this.paterno);
				comando.setString(3, this.materno);
				comando.setString(4, this.domicilio);
				comando.setString(5, this.ciudad);
				comando.setString(6, this.empresa);
				comando.setString(7, this.cp);
				comando.setString(8, this.telefono);
				comando.setString(9, this.correo);
				comando.setInt(10, this.idCiente);//El ID del bibliotecario a modificar
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
	/*
	 * Metodo para traer informacion hacia el comboBox
	 */
	public static void consultarInformacionCliente(Connection con, ObservableList<DAOCliente> listaCliente) {
		try {
			String sqlSelect = "SELECT idcliente, nombre FROM cliente WHERE estatus = '1'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlSelect);
			while (rs.next()) {
				listaCliente.add(new DAOCliente(
						rs.getInt("idcliente"), 
						rs.getString("nombre")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombreCliente.get();
	}
	
}
