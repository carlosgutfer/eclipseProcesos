package practica3procesos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorDeTareas {

    //Tarea actual con la que se esta trabajando
    private Tarea tarea;
    //Acumulador para la siguiente tarea
    private Tarea buffer = null;
    private int nHilos;
    //Contador de hilos que estan preparados para recibir una tarea
    private int contadorListos;
    //Indicador de que el turno ha terminado
    private boolean turnoTerminado;
    //Indicador de que el turno esta en marcha
    private boolean turnoEnMarcha;
    //Contador de hilos que han probado una tarea
    private int contadorEntrega;
    //Contador de turno
    private int turno = 0;

    public GestorDeTareas(int nHilos) {
        this.nHilos = nHilos;
        contadorListos = 0;
        turnoEnMarcha = false;
        turnoTerminado = false;
        contadorEntrega = 0;
    }
    
    //Se espera hasta que todos los hilos esten listos o que haya una tarea disponible.
    //Cuando es asi el ultimo en llegar despierta a un 
    //hilo aleatorio para que pruebe con una tarea.
    //Si es valida volvera a empezar el ciclo de nuevo y si no 
    //se despertara a otro hilo para que pruebe, asi hasta que haya un valido
    //o se acaaben los hilos y se genere una nueva tarea
    public synchronized Tarea consumirTarea(){        
        ++contadorListos;
        while (!turnoEnMarcha || buffer == null) {             
            if(contadorListos == nHilos){   
                //Si es el primer turno se usa la tarea que ya esta disponible
                if(turno != 0)
                    buffer = null;
                turno++;
                notifyAll();
                //Se inidica que comienza un turno
                turnoEnMarcha = true;
                turnoTerminado = false;
            }
            esperar();
        }        
        //Se para el turno ya que hay un hilo probando una tarea
        turnoEnMarcha = false;
                   
        contadorListos--;
        return tarea;
    }
    
    //Permite comprobar si se ha de terminar con una tarea o si se ha de despertar
    //a un nuevo hilo
    public synchronized void entregarResultado(boolean result){
        ++contadorEntrega;
        //Si la tarea ha sido valida o el turno ha terminado se idica que es asi
        //y se despiertan a los hilos
        if (result || contadorEntrega == nHilos) {            
            turnoTerminado = true;
            notifyAll();
        }
        //Si no se inidica que el turno debe seguir y se despierta un nuevo hilo
        else{
            turnoEnMarcha = true;            
            while (!turnoTerminado) { 
                notifyAll();
                esperar();
            }
        }
        //Cuando un hilo termina se le elimina de el contador de hilos y se despierta
        //al consumidor para en caso de que no queden mas hilos se termine
        if(!result && tarea.getDuracion() == 1){
            --nHilos;
            notifyAll();
        }
        --contadorEntrega;
    }
    
    //Entrega una nueva tarea cuando la anterior esta en uso. Devuelve un bool
    //para saber si debe seguir produciendo
    public synchronized boolean producirTarea(Tarea tarea){ 
        boolean salir = false;
        //Espera a que el buffer este vacio o a que ya no queden hilos
        while (buffer != null && nHilos!=0) {           
            esperar();
        }
        //Entrega la nueva tarea 
        this.buffer = tarea;
        this.tarea = tarea;
        //Si ya no quedan hilos termina
        if(nHilos == 0)
            salir = true; 
        
        notifyAll();
        return salir;
    }
    
    //Los hilos eperan. Permite limpiar el codigo de try catch
    private synchronized void esperar(){
        try {
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(GestorDeTareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Hilo por hilo escriben los resultados de cada trabajador
    public synchronized void grabarFichero(ArrayList<Tarea> tareas){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("resultado.txt", true));
            
            bw.write("\nTareas del trabajador: " + Thread.currentThread().getName()+ "\n\n");
            for (Tarea tarea : tareas) {
                bw.write(tarea.toString()+"\n");
            }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(GestorDeTareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
