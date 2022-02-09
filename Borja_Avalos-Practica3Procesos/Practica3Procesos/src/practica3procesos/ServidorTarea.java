
package practica3procesos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTarea {
    
    private static int idTarea = 0; 
   
    public static void main(String[] args) {        
        //Puerto del servidor
        int puerto = 30500;
        //Puerto del cliente
        int puertoCliente = 30501;
        byte[] peticion = new byte[1];
        //Se generan los canales de comunicacion
        DatagramSocket recibir = getSocketRecibir(puerto);        
        DatagramPacket paqueteRecibir = new DatagramPacket(peticion, peticion.length);
        DatagramSocket enviar = getSocketEnviar();
        
        boolean salir = false;
        //Recibira peticiones(1) hasta que se le indique que debe terminar(0)
        while (!salir) { 
            //Recibe la orden 
            int orden = recibirPaquete(paqueteRecibir, recibir);
            //Comprueba si debe generar una nueva tarea
            if(orden == 1){
                //Genera la tarea y la envia
                byte[] respuesta = generarTarea();
                DatagramPacket paqueteEnviar = new DatagramPacket(respuesta,
                        respuesta.length, paqueteRecibir.getAddress(), puertoCliente);
                enviarMensaje(paqueteEnviar, enviar);
            //Si no termina el bucle y muere
            }else
                salir = true;
        }
    }
    
    private static DatagramSocket getSocketRecibir(int puerto){
        DatagramSocket s = null;
        try {
            s = new DatagramSocket(puerto);
        } catch (SocketException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    //Recibe la peticion del cliente y la transforma a int. Por protocolo solo recibira un byte
    private static int recibirPaquete(DatagramPacket paquete, DatagramSocket socket){
        int respuesta = -1;
        try {
            socket.receive(paquete);
            byte[] recibido = paquete.getData();
            respuesta = Integer.parseInt(new String(recibido));
        } catch (IOException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    //Genera una nueva tarea y la devuelve convertida a un byte[]
    private static byte [] generarTarea(){
        int duracion = (int)Math.round((Math.random()*2)+1); 
        ++idTarea;        
        String mensaje = duracion +","+idTarea;
        return mensaje.getBytes();
        
    }
    
    private static DatagramSocket getSocketEnviar(){
        DatagramSocket s = null;
        try {
            s = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    private static void enviarMensaje(DatagramPacket paquete, DatagramSocket socket){
        try {
            socket.send(paquete);
        } catch (IOException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
