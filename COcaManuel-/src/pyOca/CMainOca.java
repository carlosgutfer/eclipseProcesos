package pyOca;
import java.util.Scanner;

public class CMainOca {
    static  Scanner poTeclado = new Scanner(System.in);    
    static final int NUM_TOTAL_JUGADORES = 4;

    public static void main(String[] args) {
        CJuegoOca woJuegoOca = new CJuegoOca(NUM_TOTAL_JUGADORES);
        woJuegoOca.mvJugarUnaPartida();        
    }// main()    
} //CMainJuegoOca






