
package practica3procesos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloServidor implements Runnable{

    private Socket socket;
    private GestorDeTareas gestor;
    //Se guardan las tareas validas
    private ArrayList<Tarea> tareasAceptadas = new ArrayList<>();

    //Recibe el socket por el que se comunicara y el Gestor para recibir tareas
    public HiloServidor(Socket socket, GestorDeTareas gestor) {
        this.socket = socket;
        this.gestor = gestor;
    }
    
    @Override
    public void run() {
        //Se establecen los canales de comunicacion
        ObjectOutputStream enviar = getWriter(socket);
        DataInputStream recibir = getDIS(socket);
        
        boolean salir = false;
        while(!salir) {
            //Pide una tarea, la envia y recibe el resultado
            Tarea tarea = gestor.consumirTarea();
            enviarTarea(enviar, tarea);
            boolean result = recibirResultado(recibir);
            //Si el resultado es valido la guarda 
            if(result){
                tareasAceptadas.add(tarea);
            }
            //Le comunica al gestor si ha podido usar la tarea o no 
            gestor.entregarResultado(result);
            //En caso de que el Trabajador no pueda aceptar mas tareas le envia una
            //tarea de duracion 0 para que este termine.
            if(!result && tarea.getDuracion() == 1){
                enviarTarea(enviar, new Tarea(0));
                salir = true;
            }
        }
        //Graba en el fichero indicado las tareas validas
        gestor.grabarFichero(tareasAceptadas);
        System.out.println("Server ends");
        
    }
    
    //Funcion para evitar try()catch()
    private static ObjectOutputStream getWriter(Socket s) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return oos;
    }
    
    //Funcion para evitar try()catch()
    private static void enviarTarea(ObjectOutputStream enviar, Tarea tarea){
        try {
            enviar.writeObject(tarea);
            enviar.flush();
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Funcion para evitar try()catch()
    private static DataInputStream getDIS(Socket s){
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dis;
    }
    
    //Recibe de el trabajador si la tarea ha sido valida
    private static boolean recibirResultado(DataInputStream dis){
        boolean result = false;
        try {
            result = dis.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
