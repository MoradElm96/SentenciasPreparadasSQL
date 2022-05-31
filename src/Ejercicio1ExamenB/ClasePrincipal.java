/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio1ExamenB;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
/*Sea el fichero suministros.txt (suministros.xml) que ya está creado y que contiene la
 siguiente información:
 numprov numparte numproyecto cantidad operacion

 donde el campo operación puede tener una s o una r, y el script Construccion.sql que
 crea la base de datos Construccion, formada por las tablas Proveedor, Parte, Proyecto
 y Suministra.
 Utilizando sentencias preparadas, actualizar la tabla Suministra mediante el fichero
 de manera que:
 • Si la operación es una s y el registro no existe, se insertará en la tabla.
 • Si la operación es una s y el registro si existe se sumará la cantidad a la que ya hay
 en la tabla.
 • Si la operación es una r y el registro no existe, se informará al usuario que no es
 posible realizar la operación.
 • Si la operación es una r y el registro si existe se restará la cantidad a la de la tabla.
 Si da un resultado negativo, se dejará la cantidad a 0 indicando que hay unidades
 pendientes.
 Una vez actualizada la tabla, se eliminarán todos los registros con cantidad 0.
 Finalmente, mostrar cómo queda la tabla Suministra.*/
/**
 *
 * @author Morad
 */
public class ClasePrincipal {
    
    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
        try {
            
            File f= new File("suministros.xml");
            Suministro suministro;
            
            SAXParserFactory spf = SAXParserFactory.newInstance();
            
            SAXParser sp = spf.newSAXParser();
            
            Manejador m = new Manejador();
            
            sp.parse(f, m);
            
            ArrayList<Suministro> lista = m.obtenerLista();
            
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/Construccion";
            String user ="root";
            String password = "";
            
            
            Connection con = DriverManager.getConnection(url,user, password);
            
            
            //comprobar si existe
            String cadena = "select count(*) from suministra where numprov=? and numparte=? and numproyecto=?";
            PreparedStatement psComprobarSiExiste = con.prepareStatement(cadena);
          
            cadena = "insert into suministra values (?,?,?,?)";
            PreparedStatement psInsert = con.prepareStatement(cadena);
            
            cadena = "select cantidad from suministra where numprov=? and numparte=? and numproyecto=?";
            PreparedStatement psComprobarCantidad = con.prepareStatement(cadena);
           
           /*Si la operación es una s y el registro si existe se sumará la cantidad a la que ya hay
           en la tabla*/
           
            cadena = "update suministra set cantidad=cantidad+? where numprov=? and numparte=? and numproyecto=?"; 
            PreparedStatement psSumarCantidad = con.prepareStatement(cadena);
            //Si la operación es una r y el registro si existe se restará la cantidad a la de la tabla
            
            cadena = "update suministra set cantidad= cantidad-? where numprov=? and numparte=? and numproyecto=?";
            PreparedStatement psRestarCantidad = con.prepareStatement(cadena);
            
            //Una vez actualizada la tabla, se eliminarán todos los registros con cantidad 0
            cadena = "delete from suministra where cantidad=0";
            PreparedStatement psEliminarCantidadCero = con.prepareStatement(cadena);
           
            //Finalmente, mostrar cómo queda la tabla Suministra.
            cadena = "select * from suministra";
            PreparedStatement psTablaSuministra = con.prepareStatement(cadena);
            
            ResultSet rs = null;
            ResultSet rsCantidad= null;
            
            
            System.out.println("Contenido del xml");
            
            for (Suministro suministro1 : lista) {
               
                System.out.println(suministro1);
                
                psComprobarSiExiste.setString(1, suministro1.getNumprov());
                psComprobarSiExiste.setString(2, suministro1.getNumparte());
                psComprobarSiExiste.setString(3, suministro1.getNumproyecto());
                //ejecutamos la sentencia
                
                rs= psComprobarSiExiste.executeQuery();
                rs.next();
                if(suministro1.getOperacion().equalsIgnoreCase("s")){
                    if(rs.getInt(1)==0){//si no existe
                        psInsert.setString(1,suministro1.getNumprov() );
                        psInsert.setString(2, suministro1.getNumparte());
                        psInsert.setString(3, suministro1.getNumproyecto());
                        psInsert.setInt(4, suministro1.getCantidad());
                        
                        System.out.println("Creando nuevo registro...");
                        psInsert.execute();//para ejecutar sentencia
                    } else{
                        psSumarCantidad.setInt(1, suministro1.getCantidad());
                        psSumarCantidad.setString(2, suministro1.getNumprov());
                        psSumarCantidad.setString(3, suministro1.getNumparte());
                        psSumarCantidad.setString(4,suministro1.getNumproyecto());
                        System.out.println("Sumando cantidad al registro...");
                        psSumarCantidad.executeQuery();
                        
                    }
                    
                } else {
                    if(rs.getInt(1) == 0){
                        System.out.println("El registro no existe, no es posible realizar la operacion");
                    }else{
                        psComprobarCantidad.setString(1, suministro1.getNumprov());
                        psComprobarCantidad.setString(2, suministro1.getNumparte());
                        psComprobarCantidad.setString(3, suministro1.getNumproyecto());
                        rsCantidad = psComprobarCantidad.executeQuery();
                        rsCantidad.next();
                        if(suministro1.getCantidad()< rsCantidad.getInt(1)){
                            psRestarCantidad.setInt(1, suministro1.getCantidad());
                            psRestarCantidad.setString(2, suministro1.getNumprov());
                            psRestarCantidad.setString(3, suministro1.getNumparte());
                            psRestarCantidad.setString(4,suministro1.getNumproyecto());
                            System.out.println("Restando cantidad al registro...");
                            psRestarCantidad.executeQuery();
                            
                        } else {
                            psRestarCantidad.setInt(1,rsCantidad.getInt(1));
                            psRestarCantidad.setString(2, suministro1.getNumprov());
                            psRestarCantidad.setString(3,suministro1.getNumparte());
                            psRestarCantidad.setString(4,suministro1.getNumproyecto());
                            System.out.println("Cantidad = 0, el registro sera borrado... ");
                            psRestarCantidad.executeQuery();
                        }
                    }
                }
            }
            
            psEliminarCantidadCero.executeQuery();
            System.out.println("TABLA SUMINISTRA");
            
            ResultSet rsMostrar = psTablaSuministra.executeQuery();
            while(rsMostrar.next()){
            System.out.println("NumProv = "+rsMostrar.getString(1)+ " | NumParte = "+rsMostrar.getString(2)+" | NumProyecto = "+rsMostrar.getString(3)+" | Cantidad = " + rsMostrar.getInt(4));

            }
            
        } catch (ClassNotFoundException ex) {
            
        }catch (SQLException ex) {
               
       }
        
        //pendiente de comprobar
        
        
    }
    
}
