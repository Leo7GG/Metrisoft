package controlador;

import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application {

	private ControladorVentanas ventanas;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ventanas = ControladorVentanas.getInstancia();
		ventanas.setPrimaryStage(primaryStage);
		ventanas.asignarModal("../vista/acceso.fxml", "Metrisoft");
	}

}
