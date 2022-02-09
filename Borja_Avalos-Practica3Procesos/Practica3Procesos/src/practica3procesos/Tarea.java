
package practica3procesos;

import java.io.Serializable;
import java.util.Random;

public class Tarea implements Serializable{
    
    private int id;
    private int duracion;

    public Tarea(int id, int duracion) {
        this.id = id;
        this.duracion = duracion;
    }

    public Tarea(int duracion){
        this.duracion = duracion;
        this.id = 0;
    }
    

    @Override
    public String toString() {
        return "Tarea numero:" + " " + id + " duracion: " + duracion;
    }

    public int getDuracion() {
        return duracion;
    }
     
    
}
