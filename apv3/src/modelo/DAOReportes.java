package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DAOReportes {
	DAOConexion con;
	
	
	public DAOReportes(){
		con = new DAOConexion();
		;
		
	}
	public JasperPrint loadReporteCliente()throws JRException, IOException, URISyntaxException{
		JasperPrint impreso=null;
		try {
			if(con.conectar()){
				InputStream input = new FileInputStream(new File(getClass().getResource("/vista/reportes/Metrisoft_Cliente.jrxml").toURI()));
				JasperDesign disenio = JRXmlLoader.load(input);
				JasperReport reporte = JasperCompileManager.compileReport(disenio);
				impreso = JasperFillManager.fillReport(reporte, null, con.getConexion());
				input.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			con.desconectar();
		}
		return impreso;
	}
	public JasperPrint loadReporteEmpleados()throws JRException, IOException, URISyntaxException{
		JasperPrint impreso=null;
		try {
			if(con.conectar()){
				InputStream input = new FileInputStream(new File(getClass().getResource("/vista/reportes/Metrisoft_Empleado.jrxml").toURI()));
				JasperDesign disenio = JRXmlLoader.load(input);
				JasperReport reporte = JasperCompileManager.compileReport(disenio);
				impreso = JasperFillManager.fillReport(reporte, null, con.getConexion());
				input.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			con.desconectar();
		}
		return impreso;
	}
	public JasperPrint loadReporteProyecto()throws JRException, IOException, URISyntaxException{
		JasperPrint impreso=null;
		try {
			if(con.conectar()){
				InputStream input = new FileInputStream(new File(getClass().getResource("/vista/reportes/Metrisoft_Proyecto.jrxml").toURI()));
				JasperDesign disenio = JRXmlLoader.load(input);
				JasperReport reporte = JasperCompileManager.compileReport(disenio);
				impreso = JasperFillManager.fillReport(reporte, null, con.getConexion());
				input.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			con.desconectar();
		}
		return impreso;
	}
	
}