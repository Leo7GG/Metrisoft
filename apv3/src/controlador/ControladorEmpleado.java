package controlador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//import modelo.DAOEmpleado;
import modelo.*;


public class ControladorEmpleado implements Initializable {
	@FXML TextField txtNombre, txtAP, txtAM, txtDomicilio,
	txtCiudad, txtCP, txtTelefono, txtCorreo;
	@FXML TableView<DAOEmpleado> tvempleado;
	@FXML Button btnNuevo, btnGuardar, btnCancelar, btnEliminar, btnModificar;
	@FXML Label lblMensaje;
	@FXML CheckBox ckbInactivos;
	@FXML TextField txtBuscador;
	//Atributos
	private ObservableList<DAOEmpleado> lista;
	private DAOEmpleado empleado;
	private DAOReportes reporteador;
	private ControladorVentanas instancia;	//Lista para realizar bÃºsquedas
	private FilteredList<DAOEmpleado> listaBusqueda;
	
	private static final int NOMBRE = 50;
	private static final int APELLIDO = 50;
	private static final int DOMICILIO = 100;
	private static final int CIUDAD = 50;
	private static final int PAIS = 50;
	private static final int CP = 5;
//	private static final int CORREO = 100;
	private static final int TELEFONO = 10;
	
	//Constructor
	public ControladorEmpleado() {
		// TODO Auto-generated constructor stub
		this.empleado = new DAOEmpleado();
		this.reporteador = new DAOReportes();
		instancia = ControladorVentanas.getInstancia();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		
		//Inicializar listas
		lista=empleado.consultar("select * from empleado where estatus='1'");
		tvempleado.setItems(lista);
		listaBusqueda = new FilteredList<DAOEmpleado>(lista);
		tvempleado.setDisable(false);//Habilitar table View
		//Validaciones
		validacionTexto();
		//Activar el checkbox
		ckbInactivos.setDisable(false);
	}
	 @FXML public void clickReporte() throws IOException{
			try {
				ControladorVentanas cv = ControladorVentanas.getInstancia();
				cv.getSubcontenedor(reporteador.loadReporteEmpleados());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
					txtCP.getText().trim().isEmpty() ||
					txtTelefono.getText().trim().isEmpty() ||
					txtCorreo.getText().trim().isEmpty()){
				lblMensaje.setText("Todos los campos son obligatrios");
			}
			else{
				//No estÃ¡n vacios los TextField
				empleado.setNombre(txtNombre.getText());
				empleado.setPaterno(txtAP.getText());
				empleado.setMaterno(txtAM.getText());
				empleado.setDomicilio(txtDomicilio.getText());
				empleado.setCiudad(txtCiudad.getText());
				empleado.setCp(txtCP.getText());
				empleado.setTelefono(txtTelefono.getText());
				empleado.setCorreo(txtCorreo.getText());
						//Insertar
				if(empleado.insertar()==true){
					lblMensaje.setText("Datos insertados correctamente");
					lista= empleado.consultar("select * from empleado where estatus='1'");
					limpiar();
					bloquear();
					tvempleado.getItems().clear();
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
	
	//Click TableView
	@FXML public void clickTableView(){
		if(tvempleado.getSelectionModel().getSelectedItem()!=null){
			//Recuperar el elemento al que se dio click en el TableView
			empleado = tvempleado.getSelectionModel().getSelectedItem();
			txtNombre.setText(empleado.getNombre());
			txtAP.setText(empleado.getPaterno());
			txtAM.setText(empleado.getMaterno());
			txtCiudad.setText(empleado.getCiudad());
			txtCorreo.setText(empleado.getCorreo());
			txtDomicilio.setText(empleado.getDomicilio());
			txtCP.setText(empleado.getCp());
			txtTelefono.setText(empleado.getTelefono());
			lblMensaje.setText("Cargados datos de: " + 
					empleado.getNombre() + " " + empleado.getPaterno());
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
		if(empleado.getidEmpleados()>0){
			empleado.eliminar();
			//Actualizar los elementos en el tableView
			tvempleado.getItems().clear();
			tvempleado.setItems(empleado.
					consultar("select * from empleado where estatus='1' "));
			limpiar();
			bloquear();
		}
		else{
			lblMensaje.setText("Debe seleccionar un empleado!");
		}
	}
	
	//Click Inactivos

	@FXML public void clickInactivos(){ 
		try {
			tvempleado.getItems().clear();//Limpiar los datos de la tabla
			if(ckbInactivos.isSelected()==true){
				//Si esta seleccionado se muestran los inactivos
				lista = empleado.consultar("select * from empleado where estatus='0'");
				listaBusqueda= new FilteredList<DAOEmpleado>(lista);
				//Agregar una columna al tableView para restaurar el dato inactivo
				@SuppressWarnings("rawtypes")
				TableColumn columnaRestaurar =
						new TableColumn<>();
				tvempleado.getColumns().add(0,columnaRestaurar);
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
				if(tvempleado.getColumns().size()>6){
					tvempleado.getColumns().remove(0);
				}
				lista = empleado.consultar("select * from empleado where estatus='1'");
				listaBusqueda= new FilteredList<DAOEmpleado>(lista);
			}
			tvempleado.setItems(lista); //Asignar la lista actualizada a la tabla
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//Validaciones
			public void validacionTexto(){
				txtNombre.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event){
						char l = event.getCharacter().charAt(0);
						
						if(!Character.isLetter(l) && !Character.isSpaceChar(l) && l != 8 && l != 127){
								event.consume();
								lblMensaje.setText("Solo se permiten letras (A-Z) y espacios");					
						}
						else{
							lblMensaje.setText("Mensaje*");
							if(txtNombre.getText().length() == NOMBRE){
								event.consume();
								lblMensaje.setText("Se ha alcanzado el número máximo de caracteres");
							}
						}
					}
				});
				txtAP.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event){
						char l = event.getCharacter().charAt(0);
						
						if(!Character.isLetter(l) && !Character.isSpaceChar(l) && l != 8 && l != 127){
								event.consume();
								lblMensaje.setText("Solo se permiten letras (A-Z) y espacios");					
						}
						else{
							lblMensaje.setText("Mensaje*");
							if(txtAP.getText().length() == APELLIDO){
								event.consume();
								lblMensaje.setText("Se ha alcanzado el número máximo de caracteres");
							}
						}
					}
				});
				txtAM.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event){
						char l = event.getCharacter().charAt(0);
						
						if(!Character.isLetter(l) && !Character.isSpaceChar(l) && l != 8 && l != 127){
								event.consume();
								lblMensaje.setText("Solo se permiten letras (A-Z) y espacios");					
						}
						else{
							lblMensaje.setText("Mensaje*");
							if(txtAM.getText().length() == APELLIDO){
								event.consume();
								lblMensaje.setText("Se ha alcanzado el número máximo de caracteres");
							}
						}
					}
				});
				txtCiudad.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event){
						char l = event.getCharacter().charAt(0);
						
						if(!Character.isLetter(l) && !Character.isSpaceChar(l) && l != 8 && l != 127){
								event.consume();
								lblMensaje.setText("Solo se permiten letras (A-Z) y espacios");					
						}
						else{
							lblMensaje.setText("Mensaje*");
							if(txtCiudad.getText().length() == CIUDAD){
								event.consume();
								lblMensaje.setText("Se ha alcanzado el número máximo de caracteres");
							}
						}
					}
				});		
				txtCP.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event){
						char l = event.getCharacter().charAt(0);
						
						if(!Character.isDigit(l) && l != 8 && l != 127){
								event.consume();
								lblMensaje.setText("Solo se permiten numeros (0-9)");					
						}
						else{
							lblMensaje.setText("Mensaje*");
							if(txtCP.getText().length() == CP){
								event.consume();
								lblMensaje.setText("Se ha alcanzado el número máximo de caracteres");
							}
						}
					}
				});
				txtDomicilio.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event){
						char l = event.getCharacter().charAt(0);
						
						if(!Character.isLetter(l) && !Character.isSpaceChar(l) && !Character.isDigit(l) && l != 8 && l != 127){
								event.consume();
								lblMensaje.setText("Solo se permiten letras (A-Z), numeros (0-9) y espacios");					
						}
						else{
							lblMensaje.setText("Mensaje*");
							if(txtDomicilio.getText().length() == DOMICILIO){
								event.consume();
								lblMensaje.setText("Se ha alcanzado el número máximo de caracteres");
							}
						}
					}
				});
				txtTelefono.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event){
						char l = event.getCharacter().charAt(0);
						
						if(!Character.isDigit(l) && l != 8 && l != 127){
								event.consume();
								lblMensaje.setText("Solo se permiten numeros (0-9)");					
						}
						else{
							lblMensaje.setText("Mensaje*");
							if(txtTelefono.getText().length() == TELEFONO){
								event.consume();
								lblMensaje.setText("Se ha alcanzado el número máximo de caracteres");
							}
						}
					}
				});
				txtCorreo.setOnKeyTyped(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event) {
						boolean status = emailValidacion(txtCorreo.getText());
						if (status) {
							lblMensaje.setText("Mensaje*");
							
						} else {
							lblMensaje.setText("El correo no es valido");
						}
					}			
				});
			}
			
			private static boolean emailValidacion(String email){
				boolean status = false;
				String patron = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$";
				Pattern pattern = Pattern.compile(patron);
				Matcher matcher = pattern.matcher(email);
				if(matcher.matches()){
					status = true;
				}
				else{
					status = false;
				}
				return status;
			}
	
	//Caja de texto para realizar bÃºsquedas
	@FXML public void textChange_busqueda(){

		if(txtBuscador.getText().trim().isEmpty()){ //La caja esta vacia
			tvempleado.refresh(); //Refrescar
			tvempleado.setItems(lista); //Se le asignan todos los datos
			lblMensaje.setText("Cargados todos los registros.");
		}
		else{
			try {
				listaBusqueda.setPredicate(DAOEmpleado->{
					if(DAOEmpleado.getNombre().
						toLowerCase().contains(txtBuscador.getText().toLowerCase())){
						return true;
					}
					else if(DAOEmpleado.getPaterno().toLowerCase().
							contains(txtBuscador.getText().toLowerCase())){
						return true;
					}
					else{
						return false;
					}
				});
				//tvempleado.refresh(); //Refrescar
				tvempleado.setItems(listaBusqueda);
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
				txtCP.getText().trim().isEmpty() ||
				txtTelefono.getText().trim().isEmpty() ||
				txtCorreo.getText().trim().isEmpty()){
			lblMensaje.setText("Todos los campos son obligatrios");
		}
		else{
			//Asignar los datos a empleado
			this.empleado.setNombre(txtNombre.getText());
			this.empleado.setPaterno(txtAP.getText());
			this.empleado.setMaterno(txtAM.getText());
			this.empleado.setCiudad(txtCiudad.getText());
			this.empleado.setCorreo(txtCorreo.getText());
			this.empleado.setCp(txtCP.getText());
			this.empleado.setDomicilio(txtDomicilio.getText());
			this.empleado.setTelefono(txtTelefono.getText());
			if(empleado.modificar()){
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

	//MÃ©todo para limpiar
	public void limpiar(){
		//Limpiar los textfields
		txtAM.clear();
		txtAP.clear();
		txtCiudad.clear();
		txtCorreo.clear();
		txtCP.clear();
		txtDomicilio.clear();
		txtNombre.clear();
		txtTelefono.clear();
	}
	
	//MÃ©todo para bloquear
	public void bloquear(){
		txtAM.setDisable(true);
		txtAP.setDisable(true);
		txtCiudad.setDisable(true);
		txtCorreo.setDisable(true);
		txtCP.setDisable(true);
		txtDomicilio.setDisable(true);
		txtNombre.setDisable(true);
		txtTelefono.setDisable(true);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		btnEliminar.setDisable(true);
		btnModificar.setDisable(true);
		btnNuevo.setDisable(false);
	}
	
	//MÃ©todo para actualizar el TableView
	public void actualizarTableView(){
		lista=empleado.consultar("select * from empleado where estatus='1'");
		tvempleado.setItems(lista);
		listaBusqueda = new FilteredList<DAOEmpleado>(lista);
	}

	//Clase anÃ³nima
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
	            	empleado = (DAOEmpleado) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
	           		if(empleado.reactivar()==true){
						lista = empleado.consultar("select * from empleado where estatus='0'");
						tvempleado.setItems(lista);
						tvempleado.refresh();
					}
	            }
	        });
	    }
	    //MOSTRAR EL BOTÃ“N, SOLO CUANDO LA FILA NO SE ENCUENTRA VACIA.
	    @Override
	    protected void updateItem(Boolean t, boolean empty) {
	        super.updateItem(t, empty);
	        if(!empty){ //SI LA FILA NO ESTA VACIA, SE MUESTRA EL BOTÃ“N.
	            setGraphic(cellButton);
	        }
	    }
	}
	
}