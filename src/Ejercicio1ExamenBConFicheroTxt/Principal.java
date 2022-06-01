/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio1ExamenBConFicheroTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Alumno
 */
public class Principal {
    public static void main(String[] args) {
        try {
        File f = new File("suministros.txt");
        
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        
        String suministro = br.readLine();
        
        while(suministro!=null){
        
            String [] valores = suministro.split(" ");
            suministro= br.readLine();
            
            System.out.println(suministro);
                    }
            br.close();
            fr.close();
            
        }catch(Exception e){}
        
    }
}
