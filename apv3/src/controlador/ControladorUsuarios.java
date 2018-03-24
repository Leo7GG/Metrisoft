package controlador;

import com.sun.prism.impl.Disposer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import modelo.DAOEmpleado;
import modelo.DAOUsuarios;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javax.swing.text.html.parser.Parser;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorUsuarios implements Initializable{
    @FXML TextField txtUsuario, txtContrasenia, txtContraseniaVisible;
    @FXML Button btnNuevo, btnEliminar,btnModificar, btnCancelar, btnGuardar, btnVer;
    @FXML ComboBox<String> cbNivel, cbEmpleados;
    @FXML Label lblMensaje;
    @FXML CheckBox ckbInactivos;
    private ObservableList<String> puestos, empleados;
    private ObservableList<Integer> empleadosId;
    private DAOUsuarios Usuario;
    private DAOEmpleado Empleado;
    private ObservableList<DAOUsuarios> lista;
    private ObservableList<DAOEmpleado> listaE;
    @FXML TableView<DAOUsuarios> tvUsuarios;
    private static boolean caso = false;
    private ControladorVentanas instancia;
    private DAOUsuarios usuario;
    //Validaciones
    ValidationSupport soporte = new ValidationSupport();

    public ControladorUsuarios() {
        // TODO Auto-generated constructor stub
        this.Usuario = new DAOUsuarios();
        this.Empleado = new DAOEmpleado();
        instancia = ControladorVentanas.getInstancia();
        usuario=(DAOUsuarios) instancia.getPrimaryStage().getUserData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empleados = FXCollections.observableArrayList();
        empleadosId = FXCollections.observableArrayList();
        listaE= Empleado.consultar("select * from empleado where estatus='1' ");
        lista= Usuario.consultar("select * from usuarios where estatus='1' and id_us <>"+usuario.getUsuarioid());

        for (int i = 0; i< listaE.size(); i++){
            empleados.add(listaE.get(i).getNombre());
            empleadosId.add(listaE.get(i).getidEmpleados());
        }
        cbEmpleados.setItems(empleados);

        puestos = FXCollections.observableArrayList();
        puestos.add("administrador");
        puestos.add("usuario");
        puestos.add("invitado");
        cbNivel.setItems(puestos);

        tvUsuarios.setItems(lista);

        cbNivel.getSelectionModel().select(1);//Default
        clickCancelar();
        //Validaciones
        validar();
        verContrasenia();
    }

    @FXML public void clickNuevo(){
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        txtUsuario.setDisable(false);
        txtContrasenia.setDisable(false);
        txtContraseniaVisible.setDisable(false);
        cbNivel.setDisable(false);
        btnVer.setDisable(false);
        cbEmpleados.setDisable(false);

    }

    @FXML public void clickCancelar(){
        btnGuardar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);

        //Habilitar
        btnNuevo.setDisable(false);
        txtUsuario.clear();
        txtContrasenia.clear();
        txtUsuario.setDisable(true);
        txtContrasenia.setDisable(true);
        txtContraseniaVisible.setDisable(true);
        cbNivel.setDisable(true);
        btnVer.setDisable(true);
        cbEmpleados.setDisable(true);
    }

    @FXML public void clickModificar(){
        if(txtUsuario.getText().trim().isEmpty() ||
                txtContrasenia.getText().trim().isEmpty() ){
            lblMensaje.setText("Todos los campos son obligatrios");
        }
        else{
            //Asignar los datos a empleado
            this.Usuario.setAlias(txtUsuario.getText());
            this.Usuario.setContrasenia(txtContrasenia.getText());
            this.Usuario.setNivel(cbNivel.getSelectionModel().getSelectedItem());
            this.Usuario.setIdempleado(empleadosId.get(cbEmpleados.getSelectionModel().getSelectedIndex()));
            if(Usuario.modificar()){
                lblMensaje.setText("Se ha modificado correctamente el registro.");
                clickCancelar();
                //Limpiar cajas

                //Bloquear cajas

                //Deshabilitar
                btnEliminar.setDisable(true);
                btnModificar.setDisable(true);
                btnCancelar.setDisable(true);
                //Habilitar
                btnNuevo.setDisable(false);
                //Actualizar el TableView
            }
            else{
                lblMensaje.setText("Ha ocurrido un error inesperado. Consulte a su administrador.");
            }
        }
    }

    @FXML public void clickGuardar(){
        try {
            //Validar que las cajas no esten vacias
            if(txtContrasenia.getText().trim().isEmpty() ||
                    txtUsuario.getText().trim().isEmpty()){
                lblMensaje.setText("Todos los campos son obligatrios");
            }
            else{
                //No están vacios los TextField
                Usuario.setAlias(txtUsuario.getText());
                Usuario.setContrasenia(txtContrasenia.getText());
                Usuario.setNivel(cbNivel.getSelectionModel().getSelectedItem());
                Usuario.setIdempleado(empleadosId.get(cbEmpleados.getSelectionModel().getSelectedIndex()));
                //Se recupera la fecha del DatePicker

                //Insertar
                if(Usuario.insertar()==true){
                    lblMensaje.setText("Datos insertados correctamente");
                    lista.clear();
                    lista=Usuario.consultar("select * from usuarios where estatus='1' and id_us <>"+usuario.getUsuarioid());
                    tvUsuarios.setItems(lista);
                    tvUsuarios.refresh();
                    //lista= bibliotecario.consultar("select * from bibliotecario");
                    //limpiar();
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

    @FXML public void clickTableView(){
        if(tvUsuarios.getSelectionModel().getSelectedItem()!=null){
            //Recuperar el elemento al que se dio click en el TableView
            Usuario = tvUsuarios.getSelectionModel().getSelectedItem();
            txtUsuario.setText(Usuario.getAlias());
            txtContrasenia.setText(Usuario.getContrasenia());
            cbNivel.getSelectionModel().select(Usuario.getNivel());

            for (int i = 0; i < empleadosId.size(); i++) {
                if (empleadosId.get(i) == Usuario.getIdempleado()) {
                    cbEmpleados.getSelectionModel().select(i);
                }
            }
            lblMensaje.setText("Cargados datos de: " +
                    Usuario.getAlias());
            //Habilitar
            btnGuardar.setDisable(true);
            btnEliminar.setDisable(false);
            btnModificar.setDisable(false);
            btnCancelar.setDisable(false);
            btnNuevo.setDisable(true);
            txtContraseniaVisible.setDisable(false);
            txtContrasenia.setDisable(false);
            txtUsuario.setDisable(false);
            cbNivel.setDisable(false);
            cbEmpleados.setDisable(false);
        }
        else
        {
            lblMensaje.setText("No se ha seleccionado un elemento.");
        }
    }

    public void verContrasenia(){
        if (caso){
            txtContrasenia.managedProperty().setValue(false);
            txtContrasenia.visibleProperty().setValue(false);
            txtContraseniaVisible.managedProperty().setValue(true);
            txtContraseniaVisible.visibleProperty().setValue(true);
            txtContraseniaVisible.textProperty().bindBidirectional(txtContrasenia.textProperty());
            caso = false;
        } else {
            txtContrasenia.managedProperty().setValue(true);
            txtContrasenia.visibleProperty().setValue(true);
            txtContraseniaVisible.managedProperty().setValue(false);
            txtContraseniaVisible.visibleProperty().setValue(false);
            caso = true;
        }

    }

    @FXML public void clickEliminar(){
        if(Usuario.getUsuarioid()>0){
            Usuario.eliminar();
            //Actualizar los elementos en el tableView
            tvUsuarios.getItems().clear();
            tvUsuarios.setItems(Usuario.
                    consultar("select * from usuarios where estatus='1'"));
        }
        else{
            lblMensaje.setText("Debe seleccionar un usuario!");
        }
    }

    //Validaciones
    public void validar(){
        soporte.registerValidator(txtUsuario,true, Validator.createEmptyValidator("El nombre es requerido."));
        //soporte.registerValidator(txtUsuario, Validator.createRegexValidator("Solo se permiten letras","[a-zA-Z]", Severity.ERROR));
        soporte.registerValidator(txtContrasenia, true, Validator.createEmptyValidator("La contraseña es requerida."));
        soporte.registerValidator(txtContraseniaVisible, true, Validator.createEmptyValidator("La contraseña es requerida."));

    }

    @FXML public void clickInactivos(){
        try {
            tvUsuarios.getItems().clear();//Limpiar los datos de la tabla
            if(ckbInactivos.isSelected()==true){
                //Si esta seleccionado se muestran los inactivos
                lista = Usuario.consultar("select * from usuarios where estatus='0' and id_us <>"+usuario.getUsuarioid());
                @SuppressWarnings("rawtypes")
                TableColumn columnaRestaurar =
                        new TableColumn<>();
                tvUsuarios.getColumns().add(0,columnaRestaurar);
                columnaRestaurar.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
                            @Override
                            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> param) {
                                return new SimpleBooleanProperty(param.getValue()!=null);
                            }
                        });
                columnaRestaurar.setCellFactory(
                        new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record,Boolean>>() {
                            @Override
                            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> param) {
                                return new BotonActivar();
                            }
                        });
            }
            else{
                //Si esta desactivado se muestran los activos
                if(tvUsuarios.getColumns().size()>2){
                    tvUsuarios.getColumns().remove(0);
                }
                lista = Usuario.consultar("select * from usuarios where estatus='1' and id_us <>"+usuario.getUsuarioid());
            }
            tvUsuarios.setItems(lista); //Asignar la lista actualizada a la tabla
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class BotonActivar extends TableCell<Disposer.Record, Boolean> {
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
                    Usuario = (DAOUsuarios) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
                    if(Usuario.reactivar()==true){
                        lista = Usuario.consultar("select * from usuarios where estatus='0' and id_us <>"+usuario.getUsuarioid());
                        tvUsuarios.setItems(lista);
                        tvUsuarios.refresh();
                        txtContrasenia.clear();
                        txtContraseniaVisible.clear();
                        txtUsuario.clear();
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
