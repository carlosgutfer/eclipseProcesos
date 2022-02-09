
package practica3procesos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trabajador {
    
    public static void main(String[] args) {
        //Tiempo disponible al inicio
        int tiempoDisponible = 40;
        //Se establecen los canales de comunicacion
        Socket s =getSocket();
        ObjectInputStream recibir = getInput(s);
        DataOutputStream dos = getDOS(s);
        
        boolean salir = false;
        while(!salir) {
            //Recibe una tarea y extrae su duracion
            Tarea task = recibirTarea(recibir);
            int duracion = task.getDuracion();
            boolean tareaValida;
            //Si la duracion es 0 termina
            if(duracion == 0){
                salir = true;
            //Si no comprueba que la tarea pueda ser utilizada
            }else{
                //Si es valida resta el tiempo a el tiempo disponible
                if(tiempoDisponible - duracion >= 0){
                    tiempoDisponible -= duracion;
                    tareaValida = true; 
                    
                }else{
                    tareaValida = false;
                }
                //Envia la respuesta con la validez de la tarea
                enviarResultado(dos, tareaValida);
            }            
        }        
    }    
    //Los siguientes metodos permiten inicializar objectos y reducir los 
    //try catch del codgio principal
    
    private static Socket getSocket(){
        Socket s = null;
        try {
            s = new Socket("127.0.0.1" , 30000);
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    private static ObjectInputStream getInput(Socket s){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ois;
    }
    
    private static Tarea recibirTarea(ObjectInputStream recibir){
        Tarea task = null;
        try {
            task =(Tarea) recibir.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return task;
    }
    
    private static DataOutputStream getDOS(Socket s){
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dos;
    }
    
    private static void enviarResultado(DataOutputStream dos, boolean result){
        try {
            dos.writeBoolean(result);
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
