package pyOca;

class CJugador{
	
	int piOrdenDelJugador; 
	String psNombre; 
	int piPosicion;
	
	
CJugador(int iiOrdenDelJugador){piOrdenDelJugador= iiOrdenDelJugador; }//CJugador()

void mvInicializar(){
        piPosicion = 0;
        System.out.print("Nombre Del Jugador "+ piOrdenDelJugador+": "); psNombre= CMainOca.poTeclado.nextLine();
    }//mvInicializar
}//CJugador
