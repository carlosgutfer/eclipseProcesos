package practica3procesos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductorTarea implements Runnable{
    
    GestorDeTareas gestor;
    
    //Recibe un Gestor para poder entregar tareas producidas
    public ProductorTarea(GestorDeTareas gestor){
        this.gestor = gestor;
    }

    @Override
    public void run() {
        //Puerto por el que escucha
        int puerto = 30501;
        //Puerto al que envia
        int puertoServidor = 30500;
        
        //Genera los canales de comunicacion 
        DatagramSocket enviar = getSocketEnviar();
        DatagramSocket recibir = getSocketRecibir(puerto);
        
        Tarea tarea = null;
        DatagramPacket paqueteEnviar = null;
        boolean salir = false;
        //Entra en un bucle en el que pedira tareas hasta que se le indique que debe terminar
        while (!salir) {  
            //Le envia un 1 al servidor para indicarle que quiere una tarea
            paqueteEnviar = getPacketEnviar(generarPeticion(1), puertoServidor);
            enviarMensaje(paqueteEnviar, enviar);
            
            //Se genera un byte[] en el que se guardara la respuesta 
            byte[] respuesta = new byte[250];
            //Recibe una tarea del servidor y la entrega al gestor para que pueda ser consumida
            DatagramPacket paqueteRecibir = new DatagramPacket(respuesta, respuesta.length);
            tarea = recibirPaquete(paqueteRecibir, recibir);            
            salir = gestor.producirTarea(tarea);            
        }
        //Cuando ha terminado le inidica al servidor de tareas que debe morir
        paqueteEnviar = getPacketEnviar(generarPeticion(0), puertoServidor);
        enviarMensaje(paqueteEnviar, enviar);
        
        System.out.println("Producer ends");
    }
    
    //Genera un byte[] con el valor de el entero que se le pasa
    private static byte [] generarPeticion(int peticion){
        return String.valueOf(peticion).getBytes();
    }
    
    //Genera un socket para enviar y evita llenar el codigo de try catch
    private static DatagramSocket getSocketEnviar(){
        DatagramSocket s = null;
        try {
            s = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    //Envia el paquete y evita llenar el codigo de try catch
    private static void enviarMensaje(DatagramPacket paquete, DatagramSocket socket){
        try {
            socket.send(paquete);
        } catch (IOException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Genera el paquete que se le va a enviar al servidor
    private static DatagramPacket getPacketEnviar(byte [] buffer, int port){
        DatagramPacket p = null;
        try {
            p = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"), port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ProductorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    //Genera un socket para recibir y evita llenar el codigo de try catch
    private static DatagramSocket getSocketRecibir(int puerto){
        DatagramSocket s = null;
        try {
            s = new DatagramSocket(puerto);
        } catch (SocketException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    //Recibe una tarea del servidor en String, la formatea y genera una nueva tarea 
    private static Tarea recibirPaquete(DatagramPacket paquete, DatagramSocket socket){
        Tarea t = null;
        try {
            socket.receive(paquete);
            String respuesta = new String(paquete.getData()).trim();
            String[] parseado = respuesta.split(",");
            t = new Tarea(Integer.parseInt(parseado[1]), Integer.parseInt(parseado[0]));
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }
}
