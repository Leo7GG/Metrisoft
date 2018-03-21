package controlador;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.DAOUsuarios;
import controlador.controlador.notificaciones.*;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorAcceso implements Initializable{
    //Controles
    @FXML TextField txtAlias;
    @FXML PasswordField pfContrasenia;
    
    //Atributos
    private DAOUsuarios usuarios;
    private ControladorVentanas cv;
    Notification info;
    
    public ControladorAcceso(){
    	
        this.usuarios= new DAOUsuarios();
        cv = ControladorVentanas.getInstancia();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	Platform.runLater(new Runnable()
		{
		    @Override
		    public void run()
		    {
		    	txtAlias.requestFocus();
		    }
		});
		
		txtAlias.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					clickValidar();
				}
			}
			
		});
		
		pfContrasenia.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					clickValidar();
				}
			}
			
		});
	}

    @FXML public void clickValidar(){
        try{
            if(txtAlias.getText().trim().isEmpty() || pfContrasenia.getText().trim().isEmpty()){
                //Campos oblilgatorios
                Notification.Notifier.INSTANCE.notifyError("Metrisoft", "Existen campos vacios.");

            }
            else{
            	
            	txtAlias.textProperty().addListener(
                        (observable, oldValue, newValue)->{
                            if(!newValue.matches("[a-zA-Z0-9\\\\._\\\\-]{0,20}") || newValue.length()>20){
                                ((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
                            }
                            else{
                                ((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es vÃ¡lido.
                            }
                        });
            	
                usuarios.setAlias(txtAlias.getText());
                usuarios.setContrasenia(pfContrasenia.getText());
                DAOUsuarios temp = usuarios.validarCredencial();
                if(temp!=null){
                    cv.cerrarAcceso();
                    cv.asignarMenu("../vista/menu.fxml","Bienvenido(a): " + temp.getAlias().toUpperCase(), temp);
                }
                else{
                    Notification.Notifier.INSTANCE.notifyError("Metrisoft", "Credenciales no válidas.");
                    txtAlias.clear();
                    pfContrasenia.clear();
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    @FXML public void clickConfigurar(){
    	cv.asignarModal("../vista/configura.fxml","Configuración");
    }

}
