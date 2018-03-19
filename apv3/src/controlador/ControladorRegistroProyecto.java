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
import modelo.DAOCliente;
import modelo.DAOConexion;
import modelo.DAOEmpleado;
import modelo.DAORegistroProyecto;
import modelo.DAOReportes;

public class ControladorRegistroProyecto  implements Initializable{
	/*
	 * Botones y cuadro de textos
	 */
    @FXML private TextArea txaObjetivos, txaDescripcion, txaHer, txaOtros;
    @FXML private Button btnReporte, btnNuevo, btnCancelar, btnEliminar, btnModificar, btnGuardar, btnCerrarR;
    @FXML private Label lblMensaje;
    @FXML private TextField txtTarifa, txtNombre, txtId, txtBusqueda;
    @FXML private DatePicker dtInicio,  dtFin;
    @FXML private GridPane gridfField;
    @FXML private CheckBox ckbInactivos;
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
    @FXML private ComboBox<String> cbCiclo;
    @FXML private ComboBox<DAOEmpleado> cbLider;
    @FXML private ComboBox<DAOCliente> cbCliente;
    //Listas de colecciones de datos
    private ObservableList<DAORegistroProyecto> listaProyecto;
    private ObservableList<DAOCliente> listaCliente;
    private ObservableList<DAOEmpleado> listaEmplaedo;
    private ObservableList<String> listaMetricas;
    private ObservableList<String> listaCiclo;
    private FilteredList<DAORegistroProyecto> listaBusqueda ;
    //Variables de conexion
    private DAOConexion con;
    private DAORegistroProyecto proyecto;
    private ControladorVentanas instancia;
    private DAOReportes reporteador;
    public ControladorRegistroProyecto() throws SQLException {
		// TODO Auto-generated constructor stub
    	con = new DAOConexion();
    	con.establerConexion();
    	this.reporteador = new DAOReportes();
		instancia = ControladorVentanas.getInstancia();
    	proyecto = new DAORegistroProyecto();
    	listaCiclo = FXCollections.observableArrayList();
    	listaCliente = FXCollections.observableArrayList();
    	listaEmplaedo = FXCollections.observableArrayList();
    	listaMetricas = FXCollections.observableArrayList();
    	listaProyecto = FXCollections.observableArrayList();
    }
    @Override
	public void initialize(URL location, ResourceBundle resource) {
		// TODO Auto-generated method stub
		llenarCombobox();
		enlazarColumnas();
		clickTableProyecto();
		bloquear();
		listaBusqueda = new FilteredList<DAORegistroProyecto>(listaProyecto);
	}
    //Metodo para habilitar la casillas
    @FXML void clickNuevo() {
    	
    	habilitar();

    }
    //Metodo para eliminar un registro de la tabla 
    @FXML void clickEliminar() {
    	int delete = tvProyectos.getSelectionModel().
    			getSelectedItem().eliminarProyecto(con.getConexion());
    			if(delete == 1){
    				listaProyecto.remove(tvProyectos.getSelectionModel().getSelectedIndex());
    				tvProyectos.getSelectionModel().clearSelection();
    				tvProyectos.refresh();
    				lblMensaje.setText("Eliminado correctamente");
    				limpiar();
    				bloquear();
    				ckbInactivos.setDisable(false);
    			}
    }
    //Metodo para modificar un  elemnto de la lista
    @FXML void clickModificar() {

    	try {
    		if (txtNombre.getText().trim().isEmpty()|| txaDescripcion.getText().trim().isEmpty()|| txaHer.getText().trim().isEmpty()|| txaOtros.getText().trim().isEmpty()||
					txtTarifa.getText().trim().isEmpty()||txaObjetivos.getText().trim().isEmpty()) {
    			lblMensaje.setText("Requiere llenar todos los campos");
			}else{
				DAORegistroProyecto p = new DAORegistroProyecto(
						Integer.valueOf(txtId.getText()),
						Integer.valueOf(txtTarifa.getText()), 
						txtNombre.getText(), 
						txaDescripcion.getText(), 
						txaObjetivos.getText(), 
						cbCiclo.getSelectionModel().getSelectedItem(),
						txaHer.getText(), 
						txaOtros.getText(), 
						Date.valueOf(dtInicio.getValue()),
						Date.valueOf(dtFin.getValue()), 
						cbLider.getSelectionModel().getSelectedItem(), 
						cbCliente.getSelectionModel().getSelectedItem());
		    	con.establerConexion();
				int r = p.modificarProyecto(con.getConexion());
		    	if (r == 1){
		    		listaProyecto.set(tvProyectos.getSelectionModel().getSelectedIndex(), p);
		    		lblMensaje.setText("Modificado exitosamente");
		    		limpiar();
		    		bloquear();
		    		
		    	}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
     	
    }
    //Metodo que guarda el nuevo elemento	
    @FXML public void clickGuardar(){
    	btnCancelar.setDisable(false);
		try {
			if (txtNombre.getText().trim().isEmpty()|| txaDescripcion.getText().trim().isEmpty()||
					txaHer.getText().trim().isEmpty()|| txaOtros.getText().trim().isEmpty()||
					txtTarifa.getText().trim().isEmpty()||txaObjetivos.getText().trim().isEmpty()) {
    			lblMensaje.setText("Requiere llenar todos los campos");
			}else{
				DAORegistroProyecto p = new DAORegistroProyecto(
						Integer.valueOf(txtTarifa.getText()), 
						txtNombre.getText(), 
						txaDescripcion.getText(), 
						txaObjetivos.getText(), 
						cbCiclo.getSelectionModel().getSelectedItem(),
						txaHer.getText(), 
						txaOtros.getText(), 
						Date.valueOf(dtInicio.getValue()),
						Date.valueOf(dtFin.getValue()), 
						cbLider.getSelectionModel().getSelectedItem(), 
						cbCliente.getSelectionModel().getSelectedItem());
				
				int r = p.guardarNuevoProyecto(con.getConexion());
				if(r == 1){
					listaProyecto.add(p);
					lblMensaje.setText("Registro guardado");
					limpiar();
					bloquear();
					con.getConexion().close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
    @FXML public void clickReporte() throws IOException{
		try {
			ControladorVentanas cv = ControladorVentanas.getInstancia();
			cv.getSubcontenedor(reporteador.loadReporteProyecto());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @FXML public void cargarReporte() throws IOException{
  		try {
  			ControladorVentanas cv = ControladorVentanas.getInstancia();
  			cv.getSubcontenedor(reporteador.loadReporteProyecto());

  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
    
    //Metodo para cancelar la selecccion de un  de la tabla
    @FXML void clickCancelar() {
    	limpiar();
    	bloquear();
    	clickTableProyecto();
    	tvProyectos.refresh();
    	ckbInactivos.setDisable(false);
    	}
    public void llenarCombobox(){
    	DAOCliente.consultarInformacionCliente(con.getConexion(), listaCliente);
    	DAOEmpleado.consultarInformacionEmplado(con.getConexion(), listaEmplaedo);
    	DAORegistroProyecto.consultarInformacionProyectos(con.getConexion(), listaProyecto);
    	listaCiclo.add("XP");
    	listaCiclo.add("AGIL");
    	listaCiclo.add("XTREME");
    	listaCiclo.add("CASCADA");
    	
    	
    	
    	cbCiclo.setItems(listaCiclo);
    	cbLider.setItems(listaEmplaedo);
    	cbCliente.setItems(listaCliente);
    	tvProyectos.setItems(listaProyecto);
    }
    public void enlazarColumnas(){
    	clNombre.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, String>("nombre"));
    	clInicio.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, Date>("inicio"));
    	clFinal.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, Date>("fin"));
    	clCliente.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, DAOCliente>("clienteAlta"));
    	clLider.setCellValueFactory(new PropertyValueFactory<DAORegistroProyecto, DAOEmpleado>("empleadoAlta"));
    }
	public void clickTableProyecto(){
		tvProyectos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DAORegistroProyecto>() {
			@Override
			public void changed(ObservableValue<? extends DAORegistroProyecto> arg0, DAORegistroProyecto anterior,
					DAORegistroProyecto seleccion) {
				// TODO Auto-generated method stub
				if(seleccion != null){
					cbCiclo.setValue(seleccion.getCiclo());
					cbCliente.setValue(seleccion.getClienteAlta());
					cbLider.setValue(seleccion.getEmpleadoAlta());
					txtId.setText(String.valueOf(seleccion.getIdProyecto()));
					txtNombre.setText(seleccion.getNombre());
					txtTarifa.setText(String.valueOf(seleccion.getTarifa()));
					txaDescripcion.setText(seleccion.getDescr());
					txaHer.setText(seleccion.getHerramienta());
					txaObjetivos.setText(seleccion.getObjetivo());
					txaOtros.setText(seleccion.getOtros());
					dtInicio.setValue(seleccion.getInicio().toLocalDate());
					dtFin.setValue(seleccion.getFin().toLocalDate());
					habilitarSeleccion();
					tvProyectos.setDisable(true);
					ckbInactivos.setDisable(true);
					lblMensaje.setText("");
				}
				
			}
		});
	}
	public void clickTableProyectoEliminados(){
		tvProyectos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DAORegistroProyecto>() {
			@Override
			public void changed(ObservableValue<? extends DAORegistroProyecto> arg0, DAORegistroProyecto anterior,
					DAORegistroProyecto seleccion) {
				// TODO Auto-generated method stub
				if(seleccion != null){
					limpiar();
					tvProyectos.setDisable(false);
					btnEliminar.setDisable(true);
					btnModificar.setDisable(true);
					btnCancelar.setDisable(false);
					gridfField.setDisable(false);
					}
				
			}
		});
	}
	public void limpiar(){
		txtId.clear();
		txtNombre.clear();
		txtTarifa.clear();
		txaDescripcion.clear();
		txaHer.clear();
		txaObjetivos.clear();
		txaOtros.clear();
		dtFin.setValue(LocalDate.now());
		dtInicio.setValue(LocalDate.now());
		cbCiclo.getSelectionModel().clearSelection();
		cbCliente.getSelectionModel().clearSelection();
		cbLider.getSelectionModel().clearSelection();
	}
	public void bloquear(){
		tvProyectos.setDisable(false);
		btnCancelar.setDisable(true);
		btnEliminar.setDisable(true);
		btnModificar.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnReporte.setDisable(false);
		txaDescripcion.setDisable(true);
		txaHer.setDisable(true);
		txaObjetivos.setDisable(true);
		txaOtros.setDisable(true);
		txtNombre.setDisable(true);
		txtTarifa.setDisable(true);
		dtFin.setDisable(true);
		dtInicio.setDisable(true);
		cbCiclo.setDisable(true);
		cbLider.setDisable(true);
		cbCliente.setDisable(true);
	}
	public void habilitar(){
		tvProyectos.setDisable(true);
		btnCancelar.setDisable(false);
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		btnModificar.setDisable(true);
		btnNuevo.setDisable(true);
		btnReporte.setDisable(false);
		txaDescripcion.setDisable(false);
		txaHer.setDisable(false);
		txaObjetivos.setDisable(false);
		txaOtros.setDisable(false);
		txtNombre.setDisable(false);
		txtTarifa.setDisable(false);
		dtFin.setDisable(false);
		dtInicio.setDisable(false);
		cbCiclo.setDisable(false);
		cbLider.setDisable(false);
		cbCliente.setDisable(false);
		
	}
	public void habilitarSeleccion(){
		tvProyectos.setDisable(false);
		btnCancelar.setDisable(false);
		btnEliminar.setDisable(false);
		btnGuardar.setDisable(true);
		btnModificar.setDisable(false);
		btnNuevo.setDisable(true);
		btnReporte.setDisable(true);
		txaDescripcion.setDisable(false);
		txaHer.setDisable(false);
		txaObjetivos.setDisable(false);
		txaOtros.setDisable(false);
		txtNombre.setDisable(false);
		txtTarifa.setDisable(false);
		dtFin.setDisable(false);
		dtInicio.setDisable(true);
		cbCiclo.setDisable(false);
		cbLider.setDisable(false);
		cbCliente.setDisable(false);
		
	}
	@SuppressWarnings("unused")
	private class BotonActivar extends TableCell<Record, Boolean>{
		Button cellButton;
		Image imagen;
		ImageView contenedor;
		public BotonActivar() {
			// TODO Auto-generated constructor stub
			imagen = new Image("vista/iconos/refresh.png");
			contenedor = new ImageView(imagen);
			contenedor.setFitWidth(15);
			contenedor.setFitWidth(15);
			cellButton = new Button("", contenedor);
			cellButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent t) {
					// TODO Auto-generated method stub
					proyecto = (DAORegistroProyecto) BotonActivar.
							this.getTableView().getItems().get(BotonActivar.this.getIndex());
				
					if(proyecto.reactivar()==true){
						tvProyectos.getItems().clear();
						DAORegistroProyecto.consultarInformacionProyectosEliminados(con.getConexion(), listaProyecto);
						tvProyectos.setItems(listaProyecto);
						
					}
				}
			});
		}
		@Override
		protected void updateItem(Boolean t, boolean empty){
			super.updateItem( t, empty);
			if(!empty){
			setGraphic(cellButton);
			}
			
		}
	}
@SuppressWarnings("unchecked")
	@FXML public void clickInactivos(){
	try {
		tvProyectos.getItems().clear();
		if(ckbInactivos.isSelected()==true){
			DAORegistroProyecto.consultarInformacionProyectosEliminados(con.getConexion(), listaProyecto);
			clickTableProyectoEliminados();
			TableColumn columnaRestaurar = new TableColumn<>();
			tvProyectos.getColumns().add(0, columnaRestaurar);
			columnaRestaurar.setCellFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
					ObservableValue<Boolean>>() {

				@Override
				public ObservableValue<Boolean> call(CellDataFeatures<Record, Boolean> param) {
					// TODO Auto-generated method stub
					return new SimpleBooleanProperty(param.getValue()!=null);
				}
			});
			columnaRestaurar.setCellFactory(new Callback<TableColumn<Record, Boolean>,
					TableCell<Record, Boolean>>() {

				@Override
				public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> param) {
					// TODO Auto-generated method stub
					return new BotonActivar();
				}
			});
		}else{
			if(tvProyectos.getColumns().size()>5){
				tvProyectos.getColumns().remove(0);
				
				clickTableProyecto();
				gridfField.setDisable(false);
				btnNuevo.setDisable(false);
				tvProyectos.refresh();
				btnCancelar.setDisable(true);
			}
			DAORegistroProyecto.consultarInformacionProyectos(con.getConexion(), listaProyecto);
		}
		tvProyectos.setItems(listaProyecto);
	} catch (Exception e) {
		// TODO: handle exception
	}
}
	@FXML public void textChange_busqueda(){
	if (txtBusqueda.getText().trim().isEmpty()){
		tvProyectos.refresh();
		tvProyectos.setItems(listaProyecto);
	} else {
		try {
			listaBusqueda.setPredicate(DAORegistroProyecto->{
			if (DAORegistroProyecto.getNombre().
					toLowerCase().contains(txtBusqueda.getText().toLowerCase())){
				return true;
			} else {
				return false;
				}
			});
			listaBusqueda.setPredicate(DAOCliente->{
				if (DAOCliente.getNombre().
						toLowerCase().contains(txtBusqueda.getText().toLowerCase())){
					return true;
				} else {
					return false;
					}
				});
			tvProyectos.refresh();
			tvProyectos.setItems(listaBusqueda);
			lblMensaje.setText("Se encontraron " + listaBusqueda.size() + " registros.");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
}

