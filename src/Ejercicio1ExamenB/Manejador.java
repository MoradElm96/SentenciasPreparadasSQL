/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio1ExamenB;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Morad
 */
public class Manejador extends DefaultHandler{
    
    ArrayList<Suministro> listaSuministro = new ArrayList<>();
    Suministro suministro;
    
    StringBuilder sb = new StringBuilder();
    
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       
        switch(qName){
            
            case "numprov": suministro.setNumprov(sb.toString());
                break;
            case "numparte": suministro.setNumparte(sb.toString());
                break;
            case "numproyecto": suministro.setNumproyecto(sb.toString());
                break;
            case "cantidad": suministro.setCantidad(Integer.parseInt(sb.toString()));
                break;
            case "operacion": suministro.setOperacion(sb.toString());
                break;
            
        }
        
        
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      
        switch(qName){
            
            case "suministra":
                suministro = new Suministro();
                        
                listaSuministro.add(suministro);
                
                
                
                break;
            case "numprov":
            case "numparte":
            case "numproyecto":
            case "cantidad":
            case "operacion":
                sb.delete(0, sb.length());
                break;
           
            
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    public  ArrayList<Suministro> obtenerLista(){
        
        return listaSuministro;
    }
    
}
