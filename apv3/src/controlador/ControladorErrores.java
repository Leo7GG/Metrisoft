package controlador;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorErrores {
	private DateFormat dateFormat;
	private Date fecha;
	
	public ControladorErrores() {
		// TODO Auto-generated constructor stub
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		fecha = new Date();
	}
	
	public void imprimirBitacora(String mensaje, String clase){
		FileWriter fw = null;
		BufferedWriter bw= null;
		try {
			File archivo = new File("D:\\log.txt");
			fw = new FileWriter(archivo,true);
			bw = new BufferedWriter(fw);
			bw.write("*************************");
			bw.newLine();
			bw.write("Error ubicado en:" + clase);
			bw.newLine();
			bw.write("Fecha: " + dateFormat.format(fecha));
			bw.newLine();
			bw.write("Descripcion: " +mensaje);
			bw.newLine();
			bw.write("*************************");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
