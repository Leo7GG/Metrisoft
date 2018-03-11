 package controlador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.sun.prism.impl.Disposer.Record;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
//import modelo.DAOCliente;
import modelo.*;


public class ControladorCliente implements Initializable {
	@FXML TextField txtNombre, txtAP, txtAM, txtDomicilio,
	txtCiudad, txtPais, txtCP, txtTelefono, txtCorreo;
	@FXML TableView<DAOCliente> tvcliente;
	@FXML Button btnNuevo, btnGuardar, btnCancelar, btnEliminar, btnModificar;
	@FXML Label lblMensaje;
	@FXML CheckBox ckbInactivos;
	@FXML TextField txtBuscador;
	private DAOReportes reporteador;
	private ControladorVentanas instancia;
	
	//Atributos
	private ObservableList<DAOCliente> lista;
	private DAOCliente cliente;

	//Lista para realizar búsquedas
	private FilteredList<DAOCliente> listaBusqueda;
	
	//Constructor
	public ControladorCliente() {
		// TODO Auto-generated constructor stub
		this.cliente = new DAOCliente();
		this.reporteador = new DAOReportes();
		instancia = ControladorVentanas.getInstancia();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		
		//Inicializar listas
		lista=cliente.consultar("select * from cliente where estatus='1'");
		tvcliente.setItems(lista);
		listaBusqueda = new FilteredList<DAOCliente>(lista);
		tvcliente.setDisable(false);//Habilitar table View
		//Validaciones
		validacionTexto();
		//Activar el checkbox
		ckbInactivos.setDisable(false);
	}
	
	//Click Nuevo
	@FXML public void clickNuevo(){
		//Habilitar
		txtAM.setDisable(false);
		txtAP.setDisable(false);
		txtCiudad.setDisable(false);
		txtCorreo.setDisable(false);
		txtCP.setDisable(false);
		txtDomicilio.setDisable(false);
		txtNombre.setDisable(false);
		txtPais.setDisable(false);
		txtTelefono.setDisable(false);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		//Deshabilitar
		btnNuevo.setDisable(true);
	}
	
	//Click para cancelar
	@FXML public void clickCancelar(){
		//Deshabilitar
		txtAM.setDisable(true);
		txtAP.setDisable(true);
		txtCiudad.setDisable(true);
		txtCorreo.setDisable(true);
		txtCP.setDisable(true);
		txtDomicilio.setDisable(true);
		txtNombre.setDisable(true);
		txtPais.setDisable(true);
		txtTelefono.setDisable(true);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		btnEliminar.setDisable(true);
		btnModificar.setDisable(true);
		//Habilitar
		btnNuevo.setDisable(false);	
		limpiar();
		
	}
	
	
	//Click para guardar
	@FXML public void clickGuardar(){
		
		try {
			//Validar que las cajas no esten vacias
			if(txtAM.getText().trim().isEmpty() || 
					txtNombre.getText().trim().isEmpty() ||
					txtAP.getText().trim().isEmpty() ||
					txtDomicilio.getText().trim().isEmpty() ||
					txtCiudad.getText().trim().isEmpty() ||
					txtPais.getText().trim().isEmpty() ||
					txtCP.getText().trim().isEmpty() ||
					txtTelefono.getText().trim().isEmpty() ||
					txtCorreo.getText().trim().isEmpty() ){
				lblMensaje.setText("Todos los campos son obligatrios");
			}
			else{
				//No están vacios los TextField
				cliente.setNombre(txtNombre.getText());
				cliente.setPaterno(txtAP.getText());
				cliente.setMaterno(txtAM.getText());
				cliente.setDomicilio(txtDomicilio.getText());
				cliente.setCiudad(txtCiudad.getText());
				cliente.setEmpresa(txtPais.getText());
				cliente.setCp(txtCP.getText());
				cliente.setTelefono(txtTelefono.getText());
				cliente.setCorreo(txtCorreo.getText());
						//Insertar
				if(cliente.insertar()==true){
					lblMensaje.setText("Datos insertados correctamente");
					lista= cliente.consultar("select * from cliente where estatus='1'");
					limpiar();
					bloquear();
					tvcliente.getItems().clear();
					actualizarTableView();
					
					//Bloquear
					clickCancelar();
				}
				else{
					lblMensaje.setText("Ha ocurrido un error. Consulte a su proveedor");
				}
			}
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
	}
	  /*@FXML public void clickReporte()throws IOException, URISyntaxException{
	    	try {
	    		instancia.asignarReporte(reporteador.loadReporteCliente(), "Reporte");
	    		//reporteador.loadReporteProducto();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }*/
	  @FXML public void clickReporte() throws IOException{
			try {
				ControladorVentanas cv = ControladorVentanas.getInstancia();
				cv.getSubcontenedor(reporteador.loadReporteCliente());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	//Click TableView
	@FXML public void clickTableView(){
		if(tvcliente.getSelectionModel().getSelectedItem()!=null){
			//Recuperar el elemento al que se dio click en el TableView
			cliente = tvcliente.getSelectionModel().getSelectedItem();
			txtNombre.setText(cliente.getNombre());
			txtAP.setText(cliente.getPaterno());
			txtAM.setText(cliente.getMaterno());
			txtCiudad.setText(cliente.getCiudad());
			txtCorreo.setText(cliente.getCorreo());
			txtDomicilio.setText(cliente.getDomicilio());
			txtCP.setText(cliente.getCp());
			txtPais.setText(cliente.getEmpresa());
			txtTelefono.setText(cliente.getTelefono());
			lblMensaje.setText("Cargados datos de: " + 
					cliente.getNombre() + " " + cliente.getPaterno());
			//Habilitar
			btnEliminar.setDisable(false);
			btnModificar.setDisable(false);
			btnCancelar.setDisable(false);
			txtAM.setDisable(false);
			txtAP.setDisable(false);
			txtCiudad.setDisable(false);
			txtCorreo.setDisable(false);
			txtCP.setDisable(false);
			txtDomicilio.setDisable(false);
			txtNombre.setDisable(false);
			txtPais.setDisable(false);
			txtTelefono.setDisable(false);
			//Deshabilitar
			btnNuevo.setDisable(true);
		}
		else
		{
			lblMensaje.setText("No se ha seleccionado un elemento.");
		}
	}
	
	//Click Eliminar
	@FXML public void clickEliminar(){
		if(cliente.getidCiente()>0){
			cliente.eliminar();
			//Actualizar los elementos en el tableView
			tvcliente.getItems().clear();
			tvcliente.setItems(cliente.
					consultar("select * from cliente where estatus='1' "));
			limpiar();
			bloquear();
		}
		else{
			lblMensaje.setText("Debe seleccionar un cliente!");
		}
	}
	
	//Click Inactivos

	@FXML public void clickInactivos(){ 
		try {
			tvcliente.getItems().clear();//Limpiar los datos de la tabla
			if(ckbInactivos.isSelected()==true){
				//Si esta seleccionado se muestran los inactivos
				lista = cliente.consultar("select * from cliente where estatus='0'");
				listaBusqueda= new FilteredList<DAOCliente>(lista);
				//Agregar una columna al tableView para restaurar el dato inactivo
				@SuppressWarnings("rawtypes")
				TableColumn columnaRestaurar =
						new TableColumn<>();
				tvcliente.getColumns().add(0,columnaRestaurar);
				columnaRestaurar.setCellValueFactory(
						new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {
							@Override
							public ObservableValue<Boolean> call(CellDataFeatures<Record, Boolean> param) {
								return new SimpleBooleanProperty(param.getValue()!=null);
							}
						});
				columnaRestaurar.setCellFactory(
						new Callback<TableColumn<Record, Boolean>, TableCell<Record,Boolean>>() {
							@Override
							public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> param) {
								return new BotonActivar();
							}
						});
			}
			else{
				//Si esta desactivado se muestran los activos
				if(tvcliente.getColumns().size()>6){
					tvcliente.getColumns().remove(0);
				}
				lista = cliente.consultar("select * from cliente where estatus='1'");
				listaBusqueda= new FilteredList<DAOCliente>(lista);
			}
			tvcliente.setItems(lista); //Asignar la lista actualizada a la tabla
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//Validaciones
	public void validacionTexto(){
		txtNombre.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("[a-zA-Z]{0,20}") || newValue.length()>20){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
		txtAM.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("[a-zA-Z]{0,50}") || newValue.length()>50){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
		txtAP.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("[a-zA-Z]{0,50}") || newValue.length()>50){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
		txtCiudad.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("[a-zA-Z]{0,50}") || newValue.length()>50){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
		/*txtCorreo.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") || newValue.length()>50){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});*/
		
		txtCP.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("\\d{0,5}") || newValue.length()>5){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
		txtDomicilio.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("[a-zA-Z\\s0-9]{0,50}") || newValue.length()>50){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
		txtPais.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("[a-zA-Z]{0,50}") || newValue.length()>50){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
		txtTelefono.textProperty().addListener(
				(observable, oldValue, newValue)->{
					if(!newValue.matches("\\d{0,10}") || newValue.length()>12){
						((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior.
					}
					else{
						((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
					}
				});
	}
	
	//Caja de texto para realizar búsquedas
	@FXML public void textChange_busqueda(){

		if(txtBuscador.getText().trim().isEmpty()){ //La caja esta vacia
			tvcliente.refresh(); //Refrescar
			tvcliente.setItems(lista); //Se le asignan todos los datos
			lblMensaje.setText("Cargados todos los registros.");
		}
		else{
			try {
				listaBusqueda.setPredicate(DAOCliente->{
					if(DAOCliente.getNombre().
						toLowerCase().contains(txtBuscador.getText().toLowerCase())){
						return true;
					}
					else if(DAOCliente.getPaterno().toLowerCase().
							contains(txtBuscador.getText().toLowerCase())){
						return true;
					}
					else{
						return false;
					}
				});
				//tvcliente.refresh(); //Refrescar
				tvcliente.setItems(listaBusqueda);
				lblMensaje.setText("Se encontraron " + listaBusqueda.size() + " registros.");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@FXML public void clickModificar(){
		if(txtAM.getText().trim().isEmpty() || 
				txtNombre.getText().trim().isEmpty() ||
				txtAP.getText().trim().isEmpty() ||
				txtDomicilio.getText().trim().isEmpty() ||
				txtCiudad.getText().trim().isEmpty() ||
				txtPais.getText().trim().isEmpty() ||
				txtCP.getText().trim().isEmpty() ||
				txtTelefono.getText().trim().isEmpty() ||
				txtCorreo.getText().trim().isEmpty()){
			lblMensaje.setText("Todos los campos son obligatrios");
		}
		else{
			//Asignar los datos a cliente
			this.cliente.setNombre(txtNombre.getText());
			this.cliente.setPaterno(txtAP.getText());
			this.cliente.setMaterno(txtAM.getText());
			this.cliente.setCiudad(txtCiudad.getText());
			this.cliente.setCorreo(txtCorreo.getText());
			this.cliente.setCp(txtCP.getText());
			this.cliente.setDomicilio(txtDomicilio.getText());
			this.cliente.setEmpresa(txtPais.getText());
			this.cliente.setTelefono(txtTelefono.getText());
			if(cliente.modificar()){
				lblMensaje.setText("Se ha modificado correctamente el registro.");
				//Limpiar cajas
				limpiar();
				//Bloquear cajas
				bloquear();
				//Deshabilitar
				btnEliminar.setDisable(true);
				btnModificar.setDisable(true);
				btnCancelar.setDisable(true);
				//Habilitar
				btnNuevo.setDisable(false);
				//Actualizar el TableView
				actualizarTableView();
			}
			else{
				lblMensaje.setText("Ha ocurrido un error inesperado. Consulte a su administrador.");
			}
		}
	}

	//Método para limpiar
	public void limpiar(){
		//Limpiar los textfields
		txtAM.clear();
		txtAP.clear();
		txtCiudad.clear();
		txtCorreo.clear();
		txtCP.clear();
		txtDomicilio.clear();
		txtNombre.clear();
		txtPais.clear();
		txtTelefono.clear();
	}
	
	//Método para bloquear
	public void bloquear(){
		txtAM.setDisable(true);
		txtAP.setDisable(true);
		txtCiudad.setDisable(true);
		txtCorreo.setDisable(true);
		txtCP.setDisable(true);
		txtDomicilio.setDisable(true);
		txtNombre.setDisable(true);
		txtPais.setDisable(true);
		txtTelefono.setDisable(true);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
	}
	
	//Método para actualizar el TableView
	public void actualizarTableView(){
		lista=cliente.consultar("select * from cliente where estatus='1'");
		tvcliente.setItems(lista);
		listaBusqueda = new FilteredList<DAOCliente>(lista);
	}

	//Clase anónima
	private class BotonActivar extends TableCell<Record, Boolean> {
		Button cellButton;
		Image imagen;
		ImageView contenedor;
	    
	    BotonActivar(){
			imagen=new Image("vista/iconos/refresh.png");
			contenedor = new ImageView(imagen);
			contenedor.setFitWidth(15);
			contenedor.setFitHeight(15);
	    	cellButton =new Button("", contenedor);

	        cellButton.setOnAction(new EventHandler<ActionEvent>(){
	            @Override
	            public void handle(ActionEvent t) {
	            	cliente = (DAOCliente) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
	           		if(cliente.reactivar()==true){
						lista = cliente.consultar("select * from cliente where estatus='0'");
						tvcliente.setItems(lista);
						tvcliente.refresh();
					}
	            }
	        });
	    }
	    //MOSTRAR EL BOTÓN, SOLO CUANDO LA FILA NO SE ENCUENTRA VACIA.
	    @Override
	    protected void updateItem(Boolean t, boolean empty) {
	        super.updateItem(t, empty);
	        if(!empty){ //SI LA FILA NO ESTA VACIA, SE MUESTRA EL BOTÓN.
	            setGraphic(cellButton);
	        }
	    }
	}
	
}