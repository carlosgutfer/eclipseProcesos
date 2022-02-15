/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuputamadre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class Hilo implements  Runnable{

    private final Socket s;
    private boolean cerrado = true;
    public Hilo(Socket s) {
        this.s = s;
    }

    
    @Override
    public void run() 
    {

            BufferedReader  in = new BufferedReader (new InputStreamReader(getInput()));
            OutputStream  out = getOutPut();
            
            String cabecera;
            byte [] respuesta_bytes;
                while(cerrado)
                {
                    cabecera = leer_cabecera(in);   
                    if(cabecera != null)
                    {
                        respuesta_bytes = getDATA(cabecera);
                        enviar_mensaje(out, (cabecera.split(" "))[1], respuesta_bytes); 
                        cerrado = true;
                    }
                }
    }

    private OutputStream getOutPut() {
        OutputStream  out = null;
        try {
            out = s.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    private InputStream getInput()  {
        InputStream  in = null;
        try {
            in = s.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return in;
    }

    private String leer_cabecera(BufferedReader in)  {
            String cabecera = null;
        try 
        {
            cabecera = in.readLine();
            while(!in.readLine().equals("") && cabecera != null);
        } catch (SocketException ex) 
        {
            cerrado = false;
        } catch (IOException ex) {
        }catch (NullPointerException ex)
        {
            cerrado = false;
        }
        return cabecera;
    }

    private void enviar_mensaje(OutputStream out, String respuesta_index, byte[] respuesta_bytes)
    {
        String type;
        if(!respuesta_index.equals("/"))
        {
            respuesta_index = respuesta_index.split("\\.")[1];
            if(respuesta_index.contains("html")|| respuesta_index.contains("js") || respuesta_index.contains("css"))
                type = "text/" + respuesta_index;
            else if(respuesta_index.equals("png"))
                type = "image/" + respuesta_index;
            else 
                type = "icon/" + respuesta_index;
        }
        else
        {
            type = "text/html";
        }
        
        String respuesta_cabecera = "HTTP/1.1 200 OK \r\nContent-Type: " + type + " \r\nContent-Length: " + respuesta_bytes.length + "\r\n\r\n" ;
        try 
        {
            out.write(respuesta_cabecera.getBytes());
            out.write(respuesta_bytes);
            out.write("\r\n".getBytes());
            out.flush();
        } catch (IOException ex) {
            System.out.println("El navegador se ha cerrado");
            cerrado = false;
        }
    }

     private static byte [] getDATA(String siteindexhtml) 
    {
        byte[] bytes = null;
        String descomposicion [] = siteindexhtml.split(" ");
        String file;
        if(descomposicion[1].equals("/"))
            file = "index.html";
        else
            file = descomposicion[1].substring(1);
        try 
        {
            bytes = Files.readAllBytes(Paths.get("site\\" + file));
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytes;  
    }

   
   
    
}
