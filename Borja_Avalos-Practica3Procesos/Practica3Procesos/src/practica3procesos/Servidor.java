
package practica3procesos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    
    //Determina si se guardaran peticiones o no 
    private static boolean aceptarPeticiones = true;

    public static void main(String[] args) {
        //Guardara las peticiones recibidas
        ArrayList<Socket> sockets = new ArrayList<>();
        //Se inicializa el socket para escuchar
        ServerSocket sSocket = getSocket();
        
        Timer timer = new Timer();
        //Esta tarea permite programar su contenido para que se ejecute 
        //en un periodo determinado de tiempo
        TimerTask ts = new TimerTask() {
            @Override
            public void run() {
                //Cierra la recepcion de peticiones 
                aceptarPeticiones = false;
                System.out.println("Tiempo de recepcion agotado.");
                
                //Arranca un productor y los hilos necesarios para responder las peticiones 
                //recbidas. Ambos reciben una misma clase GestorDeDatos que dirigira las comunicaciones
                //entre ambos.
                GestorDeTareas gestor = new GestorDeTareas(sockets.size());
                Thread productor = new Thread(new ProductorTarea(gestor));
                productor.start();
                
                ArrayList<Thread> hilosServidor = new ArrayList<>();
                for (int i = 0; i < sockets.size(); i++) {
                    Thread hiloServidor = new Thread(new HiloServidor(sockets.get(i), gestor));
                    hiloServidor.setName("Trabajador "+ (i+1));
                    hiloServidor.start();
                    hilosServidor.add(hiloServidor);
                }
                
                //Se espera a que los hilos terminen
                for (Thread thread : hilosServidor) {
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Ronda terminada");
            }  
        };
        int contador = 0;
        //El servidor entra en un bucle infinito en el que escuchara peticiones
        while (true) {            
            contador++;
            Socket s = accepSocket(sSocket);
            //Cuando acepta la primera peticion aranca el temporizador 
            if(contador == 1)
                timer.schedule(ts, 10000);
            //Si esta dentro del tiempo guarda las comunicaciones 
            if(aceptarPeticiones)
                sockets.add(s);
        }
    }
    //Funciones para limpiar el codigo de try catch
    private static ServerSocket getSocket(){
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(30000);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  socket;
    }
    
    private static Socket accepSocket(ServerSocket ss){
        Socket socket = null;
        try {
            socket = ss.accept();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket;
    }
    
}
