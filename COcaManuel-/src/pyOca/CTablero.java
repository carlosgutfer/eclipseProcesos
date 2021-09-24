package pyOca;

public class CTablero{ int piTotalDeCasillas; int [] paiCasilla;

CTablero(int iiTotalDeCasillas) {piTotalDeCasillas= iiTotalDeCasillas; }//CTablero

public final int CASILLA_NORMAL    = 0;
public final int CASILLA_INICIAL =  1;
public final int CASILLA_FINAL     = 2;    
public final int CASILLA_MUERTE    = 3;
public final int CASILLA_CIELO     = 4;
public final int CASILLA_OCA    = 5;

void mvInicializar(){
        paiCasilla = new int[piTotalDeCasillas+1];
        for(int i=1; i<paiCasilla.length; i++) paiCasilla[i]= CASILLA_NORMAL;
        paiCasilla[1] = CASILLA_INICIAL;    
        paiCasilla[30]= CASILLA_FINAL;
        for (int i=5; i<paiCasilla.length; i=i+5) paiCasilla[i]= CASILLA_OCA;

        System.out.print("Dime la casilla de MUERTE: ");
        paiCasilla[ CMainOca.poTeclado.nextInt() ]= CASILLA_MUERTE; CMainOca.poTeclado.nextLine();
        System.out.print("Dime la casilla de CIELO : ");
        paiCasilla[ CMainOca.poTeclado.nextInt() ]= CASILLA_CIELO;  CMainOca.poTeclado.nextLine();
}//mvInicializar

int miValorDeLaCasilla(int iiCasilla){ return paiCasilla[iiCasilla];}//miValorDeLaCasilla()

int miCasillaSiguienteOca(int iiCasilla) { if (iiCasilla + 5 >= 30) return iiCasilla; else return (iiCasilla+5); }//miCasillaSiguienteOca()    
    
int miCasillaSiguiente(int iiCasillaActual, int iiDado) {
    int wiNuevaPosicion = iiCasillaActual + iiDado;    
    int wiTipoDeCasilla = paiCasilla[wiNuevaPosicion];
      
        if ( wiNuevaPosicion < 30) {
            if (wiTipoDeCasilla== CASILLA_INICIAL)
                return  wiNuevaPosicion;                                 
            if (wiTipoDeCasilla== CASILLA_NORMAL)
                return  wiNuevaPosicion;                                 
            if (wiTipoDeCasilla== CASILLA_MUERTE)
                return 1;
            if (wiTipoDeCasilla== CASILLA_CIELO)
                return 30;
            if (wiTipoDeCasilla== CASILLA_OCA)
                return miCasillaSiguienteOca(wiNuevaPosicion);                
        }else{
            int wiExceso = wiNuevaPosicion - 30;
             wiNuevaPosicion = 30 - wiExceso;
        }
     return wiNuevaPosicion;
}//miCasillaSiguienteV2()
}//CTablero
