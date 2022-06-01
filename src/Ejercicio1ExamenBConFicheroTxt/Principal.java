/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio1ExamenBConFicheroTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Alumno
 */
public class Principal {

    public static void main(String[] args) {
       try{
          File f=new File("suministros.txt");
          FileReader fr=new FileReader(f);
          BufferedReader br=new BufferedReader(fr);
          Class.forName("com.mysql.jdbc.Driver");
          String url="jdbc:mysql://localhost/Construccion";
          String user="root";
          String password="";
          Connection con=DriverManager.getConnection(url, user, password);
          String cadena="update Suministra set cantidad=? "
              + "where numprov=? and numparte=? and numproyecto=?";
          PreparedStatement ps1=con.prepareStatement(cadena);
          cadena="delete from Suministra where cantidad=?";
          PreparedStatement ps2=con.prepareStatement(cadena);
          cadena="insert into Suministra values(?,?,?,?)";
          PreparedStatement ps3=con.prepareStatement(cadena);
          cadena="select cantidad from suministra"
             +" where numprov=? and numparte=? and numproyecto=?";
          PreparedStatement ps4=con.prepareStatement(cadena);
          
          Statement st=con.createStatement();
          ResultSet rs=st.executeQuery("select * from suministra");
          System.out.println("--------TABLA SIN ACTUALIZAR -----------");
          while(rs.next()){
           System.out.println(rs.getString(1)+" "+rs.getString(2)
                   +" "+rs.getString(3)+" "+rs.getInt(4));
          }
          
           
          
          
          String suministro=br.readLine();
          while(suministro!=null){
            String[] valores=suministro.split(" ");  
            Actualizar(ps1,ps4,ps3,valores);
            suministro=br.readLine();   
          }
          ps2.setInt(1,0);
          int bajas=ps2.executeUpdate();
          System.out.println("Se han eliminado "+bajas+" suministros");
                
          br.close();
          fr.close();
                  
          rs=st.executeQuery("select * from suministra");
          System.out.println("--------TABLA ACTUALIZADA -----------");
          while(rs.next()){
           System.out.println(rs.getString(1)+" "+rs.getString(2)
                   +" "+rs.getString(3)+" "+rs.getInt(4));
          }
          st.close();
          con.close();
     }catch(Exception ex){
           System.out.println(ex);
       }
    }
  
  public static void Actualizar(PreparedStatement ps,PreparedStatement ps1,
                   PreparedStatement ps2,String[] valor) throws SQLException{
      int cant=0;
      ps1.setString(1,valor[0]);
      ps1.setString(2,valor[1]);
      ps1.setString(3,valor[2]);
      ResultSet rs=ps1.executeQuery();
      if(rs.next()){
        cant=rs.getInt(1);
        switch(valor[4]){
          case "s": cant=cant+Integer.parseInt(valor[3]);
                    break;
          case "r": cant=cant-Integer.parseInt(valor[3]);
                    if(cant<0){
                      cant=0;
                      System.out.println("Hay unidades pendientes");
                    }
                    break;
        } 
        
        ps.setInt(1, cant);
        ps.setString(2,valor[0]);
        ps.setString(3,valor[1]);
        ps.setString(4,valor[2]);
        ps.executeUpdate();  
      
      }else{    //EL REGISTRO NO EXISTE
        switch(valor[4]){
          case "s": ps2.setString(1, valor[0]);
                    ps2.setString(2, valor[1]);
                    ps2.setString(3, valor[2]);
                    ps2.setString(4, valor[3]);
                    ps2.executeUpdate();
                    break;
          case "r": System.out.println("No es posible realizar la operacion");
                    break;
        }   
      }
 }
}
