package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.*;

public class DAOMetricaProyecto {

	  	private IntegerProperty idProyecto;
		private IntegerProperty tarifa;
		private IntegerProperty idlider;
		private IntegerProperty idcliente;
		private StringProperty nombre;
		private StringProperty descr;
		private StringProperty objetivo;
		private StringProperty ciclo;
		private StringProperty herramienta;
		private StringProperty otros;
		private Date inicio, fin;
		private DAOEmpleado empleadoAlta;
		private DAOCliente clienteAlta;

		private static DAOConexion conexion;
		private PreparedStatement comando;


		/**
		 * @param tarifa
		 * @param nombre
		 * @param clave
		 * @param descr
		 * @param objetivo
		 * @param ciclo
		 * @param metrica
		 * @param herramienta
		 * @param otros
		 * @param inicio
		 * @param fin
		 * @param empleadoAlta
		 * @param clienteAlta
		 */
		public DAOMetricaProyecto() {
			// TODO Auto-generated constructor stub
			conexion = new DAOConexion();
		}
		public DAOMetricaProyecto(Integer tarifa, String nombre, String descr,
                                  String objetivo, String ciclo, String herramienta,
                                  String otros, Date inicio, Date fin, DAOEmpleado empleadoAlta,
                                  DAOCliente clienteAlta) {
			super();
			this.tarifa = new SimpleIntegerProperty(tarifa);
			this.nombre = new SimpleStringProperty(nombre);
			this.descr = new SimpleStringProperty(descr);
			this.objetivo = new SimpleStringProperty(objetivo);
			this.ciclo = new SimpleStringProperty(ciclo);
			this.herramienta = new SimpleStringProperty(herramienta);
			this.otros = new SimpleStringProperty(otros);
			this.inicio = inicio;
			this.fin = fin;
			this.empleadoAlta = empleadoAlta;
			this.clienteAlta = clienteAlta;
		}
		public DAOMetricaProyecto(Integer id, Integer tarifa, String nombre,
                                  String descr, String objetivo, String ciclo,
                                  String herramienta, String otros, Date inicio, Date fin, DAOEmpleado empleadoAlta,
                                  DAOCliente clienteAlta) {
			this.idProyecto = new SimpleIntegerProperty(id);
			this.tarifa = new SimpleIntegerProperty(tarifa);
			this.nombre = new SimpleStringProperty(nombre);
			this.descr = new SimpleStringProperty(descr);
			this.objetivo = new SimpleStringProperty(objetivo);
			this.ciclo = new SimpleStringProperty(ciclo);
			this.herramienta = new SimpleStringProperty(herramienta);
			this.otros = new SimpleStringProperty(otros);
			this.inicio = inicio;
			this.fin = fin;
			this.empleadoAlta = empleadoAlta;
			this.clienteAlta = clienteAlta;
		}
		//Metodos get y sett para cada una de la comnas que mas adelante se corresponderan con cada una de las filas

		public Integer getIdProyecto() {
			return idProyecto.get();
		}
		public void setIdProyecto(Integer idProyecto) {
			this.idProyecto = new SimpleIntegerProperty(idProyecto);
		}
		public Integer getTarifa() {
			return tarifa.get();
		}
		public void setTarifa(Integer tarifa) {
			this.tarifa = new SimpleIntegerProperty(tarifa);
		}
		public Integer getIdlider() {
			return idlider.get();
		}
		public void setIdlider(Integer idlider) {
			this.idlider = new SimpleIntegerProperty(idlider);;
		}
		public Integer getIdcliente() {
			return idcliente.get();
		}
		public void setIdcliente(Integer idcliente) {
			this.idcliente = new SimpleIntegerProperty(idcliente);;
		}
		public String getNombre() {
			return nombre.get();
		}
		public void setNombre(String nombre) {
			this.nombre = new SimpleStringProperty(nombre);
		}
		
		public String getDescr() {
			return descr.get();
		}
		public void setDescr(String descr) {
			this.descr = new SimpleStringProperty(descr);;
		}
		public String getObjetivo() {
			return objetivo.get();
		}
		public void setObjetivo(String objetivo) {
			this.objetivo = new SimpleStringProperty(objetivo);;
		}
		public String getCiclo() {
			return ciclo.get();
		}
		public void setCiclo(String ciclo) {
			this.ciclo = new SimpleStringProperty(ciclo);;
		}
		
		public String getHerramienta() {
			return herramienta.get();
		}
		public void setHerramienta(String herramienta) {
			this.herramienta = new SimpleStringProperty(herramienta);;
		}
		public String getOtros() {
			return otros.get();
		}
		public void setOtros(String otros) {
			this.otros = new SimpleStringProperty(otros);;
		}
		public Date getInicio() {
			return inicio;
		}
		public void setInicio(Date inicio) {
			this.inicio = inicio;
		}
		public Date getFin() {
			return fin;
		}
		public void setFin(Date fin) {
			this.fin = fin;
		}
		public DAOEmpleado getEmpleadoAlta() {
			return empleadoAlta;
		}
		public void setEmpleadoAlta(DAOEmpleado empleadoAlta) {
			this.empleadoAlta = empleadoAlta;
		}
		public DAOCliente getClienteAlta() {
			return clienteAlta;
		}
		public void setClienteAlta(DAOCliente clienteAlta) {
			this.clienteAlta = clienteAlta;
		}
		
		public IntegerProperty idProyectoProperty(){
			return idProyecto;
		}
		public IntegerProperty tarifaProperty(){
			return idProyecto;
		}
		public StringProperty nombreProperty(){
			return nombre;
		}
	
		public StringProperty descripcionProperty(){
			return descr;
		}
		public StringProperty objectivoProperty(){
			return objetivo;
		}
		public StringProperty herramientasProperty(){
			return herramienta;
		}
		public StringProperty otrosProperty(){
			return otros;
		}
		public StringProperty cicloProperty(){
			return ciclo;
		}
		
		
		
		//Metodo que sirve para guardar un nuevo registro en la base de datos con sus correspondientes relaciones
		public int guardarNuevoProyecto(Connection conexion){
			try {
				PreparedStatement sqlInsertar = conexion.prepareStatement("INSERT INTO proyecto(nombre, "
						+ "descr, objetivo, inicio, "
						+ "final, ciclo, herramienta, tarifa, otros, idcliente, idlider)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				sqlInsertar.setString(1, nombre.get());
				sqlInsertar.setString(2, descr.get());
				sqlInsertar.setString(3, objetivo.get());
				sqlInsertar.setDate(4, inicio);
				sqlInsertar.setDate(5, fin);
				sqlInsertar.setString(6, ciclo.get());
				sqlInsertar.setString(7, herramienta.get());
				sqlInsertar.setInt(8, tarifa.get());
				sqlInsertar.setString(9, otros.get());
				sqlInsertar.setInt(10, clienteAlta.getCliente_id_proyecto());
				sqlInsertar.setInt(11, empleadoAlta.getEmpleado_id_proyecto());
				
				return sqlInsertar.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
		
			
		}
		
		public int modificarProyecto(Connection conexion){
			try {
				PreparedStatement slqModificar = 
						conexion.prepareStatement("UPDATE proyecto SET "
						+ "nombre = ?, "
						+ "descr = ? ,"
						+ "objetivo = ?, "
						+ "inicio = ?, "
						+ "final = ?, "
						+ "ciclo = ?, "
						+ "herramienta =?, "
						+ "tarifa = ?, "
						+ "otros = ?, "
						+ "idcliente = ?, "
						+ "idlider = ? "
						+ "WHERE idproyecto = ?");
				slqModificar.setString(1, nombre.get());
				slqModificar.setString(2, descr.get());
				slqModificar.setString(3, objetivo.get());
				slqModificar.setDate(4, inicio);
				slqModificar.setDate(5, fin);
				slqModificar.setString(6, ciclo.get());
				slqModificar.setString(7, herramienta.get());
				slqModificar.setInt(8, tarifa.get());
				slqModificar.setString(9, otros.get());
				slqModificar.setInt(10, clienteAlta.getCliente_id_proyecto());
				slqModificar.setInt(11, empleadoAlta.getEmpleado_id_proyecto());
				slqModificar.setInt(12, idProyecto.get());
				return slqModificar.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
			
			
		}
		
		//Metodo para eliminar un proyecto correspondinte al id que el usuario haya seleccionado
		public int eliminarProyecto(Connection conexion){
			try {
				PreparedStatement sqlEliminar = conexion.prepareStatement("UPDATE proyecto SET estatus = '0' WHERE idProyecto = ?");
				sqlEliminar.setInt(1, idProyecto.get());
				return sqlEliminar.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
		
			
		}
		
		//Metodo para onsultar los datos que estan guardados en la BBDD, lo hacemos statico para evitar varias llamadas
		public static void consultarInformacionProyectos(Connection con, ObservableList<DAOMetricaProyecto> listaProyecto){
				try {
					
					Statement st = con.createStatement();
					String sqlSelect="SELECT DISTINCT  "
							+ "p.idproyecto, "
							+ "p.nombre, "
							+ "p.inicio, p.final, "
							+ "p.tarifa, p.ciclo, p.herramienta, p.descr, p.objetivo, p.otros, p.estatus, "
							+ "c.nombre as nombrecliente, c.idcliente, e.nombre as nombreempleado, e.idempleado "
							+ "FROM proyecto p, "
							+ "cliente c, empleado e "
							+ "WHERE p.idcliente = c.idcliente "
							+ "AND p.idlider = e.idempleado AND p.estatus ='1'";
					ResultSet rs = st.executeQuery(sqlSelect);
					while (rs.next()) {
						listaProyecto.add(new DAOMetricaProyecto(
								rs.getInt("idproyecto"),
								rs.getInt("tarifa"),
								rs.getString("nombre"),
								rs.getString("descr"),
								rs.getString("objetivo"), 
								rs.getString("ciclo"),
								rs.getString("herramienta"), 
								rs.getString("otros"),
								rs.getDate("inicio"),
								rs.getDate("final"), 
								new DAOEmpleado(rs.getInt("idempleado"),
												rs.getString("nombreempleado")),
								new DAOCliente(rs.getInt("idcliente"),
												rs.getString("nombrecliente"))));

					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		}
		public static void consultarInformacionProyectosEliminados(Connection con, ObservableList<DAOMetricaProyecto> listaProyecto){
			try {
				
				Statement st = con.createStatement();
				String sqlSelect="SELECT DISTINCT  "
						+ "p.idproyecto, "
						+ "p.nombre, "
						+ "p.inicio, p.final, "
						+ "p.tarifa, p.ciclo, p.herramienta, p.descr, p.objetivo, p.otros, p.estatus, "
						+ "c.nombre as nombrecliente, c.idcliente, e.nombre as nombreempleado, e.idempleado "
						+ "FROM proyecto p, "
						+ "cliente c, empleado e "
						+ "WHERE p.idcliente = c.idcliente "
						+ "AND p.idlider = e.idempleado AND p.estatus ='0'";
				ResultSet rs = st.executeQuery(sqlSelect);
				while (rs.next()) {
					listaProyecto.add(new DAOMetricaProyecto(
							rs.getInt("idproyecto"),
							rs.getInt("tarifa"),
							rs.getString("nombre"),
							rs.getString("descr"),
							rs.getString("objetivo"), 
							rs.getString("ciclo"),
							rs.getString("herramienta"), 
							rs.getString("otros"),
							rs.getDate("inicio"),
							rs.getDate("final"), 
							new DAOEmpleado(rs.getInt("idempleado"),
											rs.getString("nombreempleado")),
							new DAOCliente(rs.getInt("idcliente"),
											rs.getString("nombrecliente"))));

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
		public int reactiva(Connection cone){
			try {
				PreparedStatement restaurar = cone.
						prepareStatement("Update proyecto SET estatus = '1' WHERE idproyecto=?");
					restaurar.setInt(1, idProyecto.get());
					return restaurar.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
			
		}
		
		public boolean reactivar(){
			PreparedStatement comando;
			try {
				if(conexion.conectar()){
					String sql ="UPDATE proyecto SET estatus = '1' WHERE idproyecto=?";
					comando = conexion.getConexion().
					prepareStatement(sql);
					comando.setInt(1, this.idProyecto.get());
					comando.execute();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
				// TODO: handle exception
			}
			finally{
				conexion.desconectar();
			}
		}
		
}
