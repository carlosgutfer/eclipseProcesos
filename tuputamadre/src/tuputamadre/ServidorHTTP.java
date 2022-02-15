/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuputamadre;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author carlo
 */
public class ServidorHTTP {

/*
 *
 * @author Administrador
 */

    public static void main(String[] args) 
    {

        ServerSocket ss = getServerSocket();
        Socket s;
       
        while(true)
        {
            try 
            {
                s = ss.accept();
                inicializar_hilo(s);
            } catch (IOException ex) {
            }
        }
  
    }

    private static void inicializar_hilo(Socket s) 
    {
        Hilo h = new Hilo(s);
        Thread t = new Thread(h);
        t.start();
        
     
    }
    
    
    private static ServerSocket getServerSocket(){
        ServerSocket s = null;

        try 
        {
             s = new ServerSocket(8081);
        } catch (IOException ex) {}
        return s;
    }

   
}
