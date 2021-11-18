package productor_consumidor;

public class Main 
{
	public static void main(String[] args) {
		Datos datos = new Datos();
		
		Thread productor = new Thread(new HiloProductor(datos));
		Thread consumidor = new Thread(new HiloConsumidor(datos));
		
		productor.start();
		consumidor.start();
	}
}
