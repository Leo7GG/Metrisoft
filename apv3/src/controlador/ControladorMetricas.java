package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import modelo.*;
import org.apache.commons.digester.annotations.rules.FactoryCreate;

import javax.swing.text.html.ListView;

public class ControladorMetricas  implements Initializable{
	/*
	 * Botones y cuadro de textos
	 */
	@FXML private Button btnReporte, btnNuevo, btnCancelar, btnEliminar, btnModificar, btnGuardar, btnCerrarR;
    @FXML private Label lblMensaje,lblM1,lblM2,lblM3,lblM4,lblMensajeInterno;
    @FXML private ComboBox cbMetricas;
	private ObservableList<String> met;
	private ControladorVentanas instancia;

     /*
     * Columnas para la tabla

     */
    @FXML private TableColumn<DAORegistroProyecto,String> clNombre;
	@FXML private TableColumn<DAORegistroProyecto,Date> clInicio;
	@FXML private TableColumn<DAORegistroProyecto,Date> clFinal;
	@FXML private TableColumn<DAORegistroProyecto,DAOCliente> clCliente;
	@FXML private TableColumn<DAORegistroProyecto,DAOEmpleado> clLider;
	 /*
     * Tabla principal y combobox de opciones
     */
    @FXML private TableView<DAORegistroProyecto> tvProyectos;
    //Listas de colecciones de datos
    private ObservableList<DAORegistroProyecto> listaProyecto;
    //Variables de conexion
    private DAOConexion con;
	private DAOMetrica metrica;
	private ObservableList<DAOMetrica> listaM;
	private DAORegistroProyecto proyecto;
    
    public ControladorMetricas() throws SQLException {
		// TODO Auto-generated constructor stub
    	con = new DAOConexion();
		instancia = ControladorVentanas.getInstancia();
    	con.establerConexion();
		this.metrica = new DAOMetrica();
    	proyecto = new DAORegistroProyecto();
    	listaProyecto = FXCollections.observableArrayList();
    }
    @Override
	public void initialize(URL location, ResourceBundle resource) {
		// TODO Auto-generated method stub
		llenartabla();
		enlazarColumnas();
		bloquear();
		met = FXCollections.observableArrayList();
		listaM = metrica.consultar("select * from metrica");
		for (int i = 0; i< listaM.size(); i++){
			met.add(listaM.get(i).getNombremetrica());
		}
		cbMetricas.setItems(met);
		lblMensajeInterno.setText("Nueva Metrica:");
	}
    //Metodo para habilitar la casillas
    @FXML void clickNuevo() {
    	habilitar();
    }
    //Metodo para eliminar un registro de la tabla 
    @FXML void clickEliminar() {
    
    }
    //Metodo para modificar un  elemnto de la lista
    @FXML void clickModificar() {

    	
    }
    //Metodo que guarda el nuevo elemento	
    @FXML public void clickGuardar(){}
    	
    @FXML public void clickReporte() throws IOException{
		
	}
    @FXML public void cargarReporte() throws IOException{
  		
  	}
    
    @FXML void clickCancelar() {
    	
    	}
    public void llenartabla(){
    	proyecto.consultarInformacionProyectos(con.getConexion(), listaProyecto);
      	
    	tvProyectos.setItems(listaProyecto);
    }
    public void enlazarColumnas(){
    	clNombre.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, String>("nombre"));
    	clInicio.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, Date>("inicio"));
    	clFinal.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, Date>("fin"));
    	clCliente.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, DAOCliente>("clienteAlta"));
    	clLider.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, DAOEmpleado>("empleadoAlta"));
    }
	@FXML public void clickTableView(){
		lblMensajeInterno.setText("Nueva Metrica:");
	}
	@FXML public void clickCb(){
		switch (listaM.get(cbMetricas.getSelectionModel().getSelectedIndex()).getIdmetrica()){
			case 1:
				lblM1.setText("Errores");
				lblM2.setText("KLDC");
				break;
			case 2:
				lblM1.setText("Paguinas");
				lblM2.setText("KLDC");
				break;
			case 3:
				lblM1.setText("KLDC");
				lblM2.setText("Personas");
				break;
			case 4:
				lblM1.setText("EI");
				lblM2.setText("EI+1");
				break;
			default:
				break;
		}
	}
	@FXML public void btnAsociar(){
		//int idProyectoA = tvProyectos.getSelectionModel().getSelectedItem().getIdProyecto();
		//int idMetricaA = listaM.get(cbMetricas.getSelectionModel().getSelectedIndex()).getIdmetrica();


	}
	@FXML public void clicMedida(){
		instancia.asignarModal("../vista/medidas.fxml","Medidas");
	}
	public void limpiar(){
		
	}
	public void bloquear(){
		
	}
	public void habilitar(){
		
		
		
	}

	public void habilitarSeleccion(){
		
		
		
	}
	
}


