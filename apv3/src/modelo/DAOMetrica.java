package modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOMetrica {
    int idmetrica;
    String nombremetrica;

    private DAOConexion con;
    private PreparedStatement comando;
    private ObservableList<DAOMetrica> lista;

    public DAOMetrica() {
        this.con= new DAOConexion();
        idmetrica = 0;
        nombremetrica = "";
        this.lista = FXCollections.observableArrayList();//Inicializar lista
    }

    public DAOMetrica(int idmetrica, String nombremetrica) {
        this.idmetrica = idmetrica;
        this.nombremetrica = nombremetrica;
        this.con= new DAOConexion();
        this.lista = FXCollections.observableArrayList();//Inicializar lista
    }

    public int getIdmetrica() {
        return idmetrica;
    }

    public void setIdmetrica(int idmetrica) {
        this.idmetrica = idmetrica;
    }

    public String getNombremetrica() {
        return nombremetrica;
    }

    public void setNombremetrica(String nombremetrica) {
        this.nombremetrica = nombremetrica;
    }

    public ObservableList<DAOMetrica> consultar(String consulta){
        ResultSet temporal;
        try {
            con.conectar();
            comando = con.getConexion().prepareStatement(consulta);
            temporal =  comando.executeQuery();
            while(temporal.next()){
                DAOMetrica b = new DAOMetrica();
                b.idmetrica= temporal.getInt("idmetrica");
                b.nombremetrica=temporal.getString("nombremetrica");
                //temporal.getString(1).charAt(1);
                lista.add(b);
            }
            return lista;
        } catch (Exception ex) {
            ex.printStackTrace();
            return lista;
        }
        finally{
            con.desconectar();
            ///temporal.close();
        }
    }
}
