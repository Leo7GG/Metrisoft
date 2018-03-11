package controlador;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.DAOUsuarios;
import controlador.controlador.notificaciones.*;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;



/**
 * Created by mreinazarate on 23/03/17.
 */
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
                                ((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
                            }
                        });
            	
                usuarios.setAlias(txtAlias.getText());
                usuarios.setContrasenia(pfContrasenia.getText());
                DAOUsuarios temp = usuarios.validarCredencial();
                if(temp!=null){
                    cv.cerrarAcceso();
                    cv.asignarMenu("../vista/menu.fxml","Bienvenido " + temp.getAlias().toUpperCase(), temp);
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
    	//System.out.println("Hola Mundo desde link");
    	cv.asignarModal("../vista/configura.fxml","Configuración");
    }

}
