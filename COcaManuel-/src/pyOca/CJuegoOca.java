package pyOca;

class CJuegoOca{ 
	int piNumJugadores; 
	CJugador [] paoJugador;  
	CTablero poTablero; 
	int piTurno; 
	boolean pbFinalDePartida;  
	CTablero newTablero;
CJuegoOca(int iiNumJugadores){ piNumJugadores = iiNumJugadores;}//CJuegoOca()

public void mvInicializar(){
    paoJugador = new CJugador[piNumJugadores];
    for (int i=0; i<paoJugador.length; i++){ paoJugador[i]= new CJugador(i+1); paoJugador[i].mvInicializar();}    
    
    poTablero = new CTablero(30); poTablero.mvInicializar();        
}//mvInicializar()

public void mvJugarUnaPartida(){
            mvInicializar();  
            pbFinalDePartida= false;
            piTurno= 0; // el primer turno es el cero para coincidir con el array               

    while(true){
        System.out.print("\nValor del dado de "+paoJugador[piTurno].psNombre+": ");  
        int wiDado = CMainOca.poTeclado.nextInt(); CMainOca.poTeclado.nextLine();
        
        int wiNuevaPosicion = paoJugador[piTurno].piPosicion + wiDado;    
        int wiTipoDeCasilla = poTablero.paiCasilla[wiNuevaPosicion];
        
        if ( wiNuevaPosicion < 30) {
                        if (wiTipoDeCasilla== poTablero.CASILLA_INICIAL)
                            paoJugador[piTurno].piPosicion = wiNuevaPosicion;                                 
                        if (wiTipoDeCasilla== poTablero.CASILLA_NORMAL)
                            paoJugador[piTurno].piPosicion = wiNuevaPosicion;                                 
                        if (wiTipoDeCasilla== poTablero.CASILLA_MUERTE)
                            paoJugador[piTurno].piPosicion = 1;
                        if (wiTipoDeCasilla== poTablero.CASILLA_CIELO)
                            paoJugador[piTurno].piPosicion = 30;
                        if (wiTipoDeCasilla== poTablero.CASILLA_OCA)
                            paoJugador[piTurno].piPosicion = poTablero.miCasillaSiguienteOca(wiNuevaPosicion);                
        }else
                        /** repetir dado **/;
        if (paoJugador[piTurno].piPosicion == 30) {pbFinalDePartida= true; break;}            
        mvMostrarPosicionJugadores();
                    if (piTurno==3) piTurno=0; else piTurno++;
    }//while(true)
    System.out.println("Fin de la partida. Ha ganado: "+paoJugador[piTurno].psNombre);
}//mvJugarUnaPartida

public void mvJugarUnaPartidaV2(){
            mvInicializar();  
            pbFinalDePartida= false;
            piTurno= 0; // el primer turno es el cero para coincidir con el array               

    while(true){
        System.out.print("\nValor del dado de "+paoJugador[piTurno].psNombre+": ");  
        int wiDado = CMainOca.poTeclado.nextInt(); CMainOca.poTeclado.nextLine();
        
        paoJugador[piTurno].piPosicion = newTablero.miCasillaSiguiente(paoJugador[piTurno].piPosicion, wiDado);            

        if (paoJugador[piTurno].piPosicion == 30) {pbFinalDePartida= true; break;}            
        mvMostrarPosicionJugadores();
        if (piTurno==3) piTurno=0; else piTurno++;
    }//while(true)
    System.out.println("Fin de la partida. Ha ganado: "+paoJugador[piTurno].psNombre);
}//mvJugarUnaPartidaV2    

void mvMostrarPosicionJugadores(){
        System.out.print("\nPosiciones: ");
        for (CJugador paoJugador1 : paoJugador) System.out.print(paoJugador1.piPosicion + "-");    
}//mvMostrarPosicionJugadores()
}//CJuegoOca

