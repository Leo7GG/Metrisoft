package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.DAOConexion;
import modelo.DAOUsuarios;


public class ControladorMenu implements Initializable {
	
	private ControladorVentanas instancia;
	private DAOConexion con;

	//Controlador
	@FXML Label lblMensaje, lblHora;
	@FXML Button btnEmpleados,btnUsuarios, btnProyectos, btnClientes;
	
	public ControladorMenu(){
		instancia = ControladorVentanas.getInstancia();
		con = new DAOConexion();
	}

	//Método asociado al botón Autores
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Recuperar información del usuario Logeado
		DAOUsuarios usuario=(DAOUsuarios) instancia.getPrimaryStage().getUserData();
		lblMensaje.setText("Usuario: " + usuario.getAlias());
		lblHora.setText("Hora de acceso: " + (new Date()).toString());
		switch (usuario.getNivel()){
			case "administrador":
				btnUsuarios.setDisable(false);
				btnEmpleados.setDisable(false);
				btnClientes.setDisable(false);
				btnProyectos.setDisable(false);
				break;
			case "mostrador":
				btnUsuarios.setDisable(false);
				btnClientes.setDisable(true);
				break;
			default:
				btnProyectos.setDisable(false);
				break;
		}
	}
	//Método asociado al botón Bibliotecario

	//@FXML public void clickReporte() throws IOException{
	//	try {
	//		ControladorVentanas cv = ControladorVentanas.getInstancia();
	//		cv.asignarModal("../vista/reporte.fxml", "Módulo de reportes");
//.
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	//Método asociado al botón Libros
	@FXML public void clickUsuarios(){
		instancia.asignarModal("../vista/usuarios.fxml","Usuarios");
	}
	@FXML public void clicEmpleado(){
		instancia.asignarModal("../vista/Empleado.fxml","Empleado");
	}
	@FXML public void clicCliente(){
		instancia.asignarModal("../vista/clientes.fxml","Clientes");
	}
	@FXML public void clicProyecto(){
		instancia.asignarModal("../vista/nuevoProyecto.fxml","Proyecto");
	}
	@FXML public void clicMedida(){

		instancia.asignarModal("../vista/Metricas.fxml","Medidas");

	}
}
