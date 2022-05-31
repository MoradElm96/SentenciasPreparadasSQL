/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio3Ordinario;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*Ejecutar el fichero tienda.sql, que crea la base de datos Tienda, compuesta de las tablas
 Productos y Ventas.
 Hacer un programa con SENTENCIAS PREPARADAS que actualice la tabla Productos, mediante
 el fichero datos.dat. El fichero tiene la siguiente estructura:
 • El primer campo es una cadena que contendrá una A, una B o una M (alta, baja o
 modificación) y dependiendo de la operación tendrá los siguientes campos.
 • Si es una baja solo aparecerá el código del producto a dar de baja.
 • Si es un alta, vendrán todos los datos que forman parte de la tabla productos que es donde
 se van a insertar los datos.
 • Y si es una modificación, vendrá el código del producto y el porcentaje que se subirá el
 precio.
 Se deben controlar los posibles errores.
 Finalmente, sacar un informe en el que aparezca:
 Cod_prod Nombre_Producto Total_Venta(unidades vendidas * precio)*/
/**
 *
 * @author Morad
 */
public class Principal {
    //primero corremos el script de la base de datos tienda

    public static void main(String[] args) throws FileNotFoundException {

        try {
            File f = new File("datos.dat");
            //para poder leer el fichero binario
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);

            //conexion a la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/Tienda";
            String user = "root";
            String password = "";
            
            
            

            Connection con = DriverManager.getConnection(url, user, password);//importar con sql

            String cadena = "select * from productos where CodProducto=?";
            //select en sentencia preparada insertar
            PreparedStatement psSelect = con.prepareStatement(cadena);

            cadena = "insert into productos values(?,?,?,?,?)";
            PreparedStatement psInsertar = con.prepareStatement(cadena);

            cadena = "update productos set PrecioUnitario=PrecioUnitario+PrecioUnitario*?/100 + where CodProducto=?";
            PreparedStatement psUpdate = con.prepareStatement(cadena);

            cadena = "delete from productos where CodProducto=?";
            PreparedStatement psDelete = con.prepareStatement(cadena);

            try {

                String opcion;
                while (true) {
                    //aqui se lee el contenido del fichero y dependiendo de lo que tenga hacemos un switch
                    opcion = dis.readUTF();
                    switch (opcion) {
                        case "A":
                            DarDeAlta(dis, psSelect, psInsertar);
                            break;
                        case "B":
                            DarBaja(dis, psDelete);
                            break;
                        case "M":
                            Modificar(dis, psUpdate);
                            break;
                    }

                }

            } catch (EOFException e) {
            }

            //cerramos
            dis.close();
            fis.close();

            //aqui el informe
            Statement st = con.createStatement();
            cadena = "select CodProducto,sum(unidadesvendidas) from ventas group by CodProducto";

            ResultSet rs = st.executeQuery(cadena);
            ResultSet rs1;

            while (rs.next()) {
                psSelect.setString(1, rs.getString(1));//la columna
                rs1 = psSelect.executeQuery();
                rs1.next();
                System.out.println(rs1.getString(1) + " " + rs1.getString(2) + " " + (rs1.getInt(4) * rs.getInt(2)));

            }

            st.close();
            con.close();

        } catch (IOException e) {
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }

    }

    static void DarDeAlta(DataInputStream dis, PreparedStatement psSelect, PreparedStatement psInsertar) throws IOException, SQLException {

        String CodProducto = dis.readUTF();
        String Nombre = dis.readUTF();
        String LineaProducto = dis.readUTF();
        int PrecioUnitario = dis.readInt();
        int Stock = dis.readInt();

        //el numero es la poscion de la interrogacion
        psSelect.setString(1, CodProducto);//para cada interrogacion una sentencia con el set del tipo de dato adecuado
        ResultSet rs = psSelect.executeQuery();

        if (rs.next()) {
            System.out.println("NO SE PUEDE DAR DE ALTA PUES YA EXISTE");
        } else {
            psInsertar.setString(1, CodProducto);
            psInsertar.setString(2, Nombre);
            psInsertar.setString(3, LineaProducto);
            psInsertar.setInt(4, PrecioUnitario);
            psInsertar.setInt(5, Stock);

            System.out.println("alta realizada");
        }
    }

    static void DarBaja(DataInputStream dis, PreparedStatement psDelete) throws IOException, SQLException {
        String CodProducto = dis.readUTF();
        psDelete.setString(1, CodProducto);

        if (psDelete.executeUpdate() == 0) {
            System.out.println("NO SE HA DADO DE BAJA PUES NO EXISTE EL PRODUCTO");
        } else {
            System.out.println("baja realizada");
        }

    }

    static void Modificar(DataInputStream dis, PreparedStatement psUpdate) throws IOException, SQLException {

        String CodProducto = dis.readUTF();
        int porcentaje = dis.readInt();

        psUpdate.setInt(1, porcentaje);
        psUpdate.setString(2, CodProducto);
        if (psUpdate.executeUpdate() == 0) {
            System.out.println("NO SE HA ACTUALIZADO PUES NO EXISTE EL PRODUCTO");
        } else {
            System.out.println("modificacion realizada");
        }

    }

}
