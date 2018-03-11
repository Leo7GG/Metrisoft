package modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by mreinazarate on 23/03/17.
 */
public class DAOUsuarios {
    private int usuarioid;
    private String alias, contrasenia, nivel;
    private DAOConexion con;
    private PreparedStatement comando;
    private ObservableList<DAOUsuarios> lista; //Almacena los registro de la tabla

    public DAOUsuarios(){
        this.usuarioid=0;
        this.alias="";
        this.contrasenia="";
        this.nivel="";
        this.con= new DAOConexion();
        this.lista = FXCollections.observableArrayList();//Inicializar lista
    }

    public int getUsuarioid() {
        return usuarioid;
    }

    public String getAlias() {
        return alias;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getNivel() {
        return nivel;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }

    public DAOUsuarios validarCredencial(){
        DAOUsuarios usuario=null;
        ResultSet rs=null;
        try{
            if(con.conectar()) {
                String sql = "select * from usuarios where alias='" + this.alias + "' and contrasenia='" + this.contrasenia + "'";
                comando = con.getConexion().prepareStatement(sql);
                rs = comando.executeQuery();
                while(rs.next()){
                    usuario= new DAOUsuarios();
                    usuario.alias=rs.getString("alias");
                    usuario.nivel=rs.getString("nivel");
                    usuario.usuarioid = rs.getInt("id_us");
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            con.desconectar();
        }
        return usuario;
    }

    public boolean insertar(){
        try {
            //Todo lo que puede generar error
            String sql="";
            if(con.conectar()==true){
                sql="insert into usuarios "
                        + "values (default,?,?,?,TRUE)";
                comando =con.getConexion().prepareStatement(sql);
                comando.setString(1, this.alias);
                comando.setString(2, this.nivel);
                comando.setString(3, this.contrasenia);
                //Ejecutate
                comando.execute();
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception ex) {
            //Código para trabajer el error
            ex.printStackTrace();
            return false;
        }
        finally{
            //Código que se tiene que ejecutar
            //Exista o no un error
            con.desconectar();
        }
    }
    public ObservableList<DAOUsuarios> consultar(String consulta){
        ResultSet temporal;
        try {
            con.conectar();
            comando = con.getConexion().prepareStatement(consulta);
            temporal =  comando.executeQuery();
            while(temporal.next()){
                DAOUsuarios b = new DAOUsuarios();
                b.usuarioid= temporal.getInt("id_us");
                b.alias=temporal.getString("alias");
                b.nivel = temporal.getString("nivel");
                b.contrasenia=temporal.getString("contrasenia");
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

    public boolean eliminar(){
        try {
            if(con.conectar()){
                String sql="update usuarios set estatus='0' where id_us=?";
                comando = con.getConexion().prepareStatement(sql);
                comando.setInt(1, this.usuarioid);
                comando.execute();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally{
            con.desconectar();
        }
    }

    public boolean reactivar(){
        try{
            if(con.conectar()){
                String sql = "update usuarios set estatus='1' where id_us=?";
                comando = con.getConexion().prepareStatement(sql);
                comando.setInt(1,this.usuarioid);
                comando.execute();
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            con.desconectar();
        }
    }



}
