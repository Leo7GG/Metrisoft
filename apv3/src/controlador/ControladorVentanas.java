package controlador;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.DAOUsuarios;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class ControladorVentanas {
    private static ControladorVentanas instancia;
    private Stage primaryStage;
    private Stage escenario2;
    private Stage taskUpdateStage;
    private Scene escena;
    private BorderPane contenedorMenu;
    private BorderPane subcontenedor;
    private ProgressIndicator progress;
    private ArrayList<Stage> stages;
    //Constructor privado
    private ControladorVentanas() {
        //Solo 1 instancia
        progress= new ProgressIndicator();
    }

    //Recuperar la instancia
    public static ControladorVentanas getInstancia() {
        if (instancia == null) {
            instancia = new ControladorVentanas();
        }
        return instancia;
    }

    //Asignar el escenario principal
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //Asignar Menu
    public void asignarMenu(String ruta, String titulo, DAOUsuarios usuario) {
        try {
            primaryStage.setUserData(usuario);
            FXMLLoader parent = new FXMLLoader(getClass().getResource(ruta));
            contenedorMenu = (BorderPane) parent.load();
            escena = new Scene(contenedorMenu);
            primaryStage.setScene(escena);
            primaryStage.setTitle(titulo);
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            //primaryStage.getIcons().add(
                    //new Image("/vista/iconos/logo.png"));
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();//Imprime la traza del error
        }
    }

    //Vistas dentro del contenedor principal
    public void asignarVistas(String ruta) {
        try {
            FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
            subcontenedor = (BorderPane) interfaz.load();
            contenedorMenu.setCenter(subcontenedor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Vistas como modal
    public void asignarModal(String ruta, String titulo) {
        try {
            FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
            subcontenedor = (BorderPane) interfaz.load();
            subcontenedor.getChildren().add(progress);
            escenario2 = new Stage(); //Nuevo escenario(Stage)
            escena = new Scene(subcontenedor);
            //Ligando hoja de estilo
            escena.getStylesheets().add("vista/estilo/botones.css");
            escenario2.setScene(escena);
            escenario2.setTitle(titulo);
            escenario2.centerOnScreen();
            escenario2.initModality(Modality.WINDOW_MODAL);//Comportamiento modal
            escenario2.initOwner(primaryStage);//Asignar al propietario
            //escenario2.show();
            cargando();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //Vistas para mostrar el reporte
    public void getSubcontenedor(JasperPrint impreso){
    		SwingNode nodo = new SwingNode();
        nodo.setContent(new JRViewer(impreso));
        subcontenedor.setCenter(nodo);
        
    }
    

    //Método para recuperar el PrimaryStage
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //Método para cerrar la ventana de acceso
    public void cerrarAcceso(){
        escenario2.close();
    }


    public void cargando(){
        VBox updatePane = new VBox();
        updatePane.setPadding(new Insets(10));
        updatePane.setSpacing(5.0d);
        updatePane.getChildren().add(progress);
        updatePane.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.9);"+
                        "-fx-background-insets: 50;"
        );
        taskUpdateStage = new Stage(StageStyle.TRANSPARENT);
        escena = new Scene(updatePane);
        escena.setFill(Color.TRANSPARENT);
        taskUpdateStage.setScene(escena);
        taskUpdateStage.centerOnScreen();
        taskUpdateStage.show();
        Task<Void> tareas = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max=10;
                for(int i=1; i<=max; i++){
                    if(isCancelled()){
                        break;
                    }
                    updateProgress(i,max);
                    updateMessage("Cargando " + String.valueOf(i));
                    Thread.sleep(100);
                }
                return null;
            }
        } ;

        tareas.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                taskUpdateStage.hide();
                escenario2.show();
            }
        });
        progress.progressProperty().bind(tareas.progressProperty());
        //lblMensaje.textProperty().bind(tareas.messageProperty());
        taskUpdateStage.show();
        new Thread(tareas).start();
    }
   
	
}