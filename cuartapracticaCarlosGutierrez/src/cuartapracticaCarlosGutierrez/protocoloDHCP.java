package cuartapracticaCarlosGutierrez;

import java.io.DataInputStream;
import java.nio.ByteBuffer;
import java.util.Random;

import javax.swing.plaf.basic.BasicFormattedTextFieldUI;

public class protocoloDHCP  
	{
	private byte HARDWARE_TYPE = 1; //RED EHTERNET DE 10MB; 
	private byte DHCP_HOPS = 0; //Dada por la hoja de la práctica
	private byte MESSAGE_TYPE = 1; //del cliente al servidor;
	private byte HARDWARE_ADDRESS_LENGTH = 6; //Longitud dada en la hoja de la práctica;
	
	//Datos para el protocolo 53
	private byte longitud_tipo_mensaje = 1;
	private byte codigo_tipo_mensaje = 53;
	private byte bootrequest = 1;
	
	//Datos para el protocolo 51
	private byte longitud_tiempo_cesion = 4;
	private byte codigo_tiempo_cesion = 51;
	private int	tiempo_de_cesion = 60000;
	
	//Datos para el protocolo 58
	 private byte longitud_tiempo_renovacion = 4;
	 private byte codigo_tiempo_renovacion = 58;
	 private int tiempo_de_renovacion = tiempo_de_cesion / 2;
	 
	 //Datos para el protcolo 1
	 private byte longitud_mascara = 4;
	 private byte codigo_mascara = 1;
	 private byte mascara [] = {(byte)255,(byte) 255, (byte) 255, 0};
	 
	 //Datos para el protocolo 6
	 private byte codigo_servidores_DNS = 6;
	 private byte longitud_servidor_DNS = 4;
	 private byte servidor_DNS [] = {8, 8, 8, 8};
	 
	 //Datos para el protocolo 50
	 private byte codigo_request_ip = 50;
	 private byte longitud_request_ip = 4;
	 private byte ip [] = {(byte) 192, (byte) 168, 1, (byte) 120};
	 
	 //Datos para el protocolo 54
	 private byte codigo_identificacion_servidor = 50;
	 private byte longitud_identificacion_servidor = 4;
	 private byte dir_router [ ]= {(byte) 192, (byte)168, 1, 1};
	 
	 private byte [] direccion_ip_cliente = new byte[4];
	 private byte [] direccion_ip_ofrecida = new byte[4];
	 private byte [] siguiente_direccion_ip_servidor = new byte[4];
	 private byte [] direccion_IP_relay = new byte[4];
	 private byte []  mac_del_cliente = new byte [16];

	 private byte[] cabezera_dhcp;
	 private int LONGITUD_DEL_MENSAJE = 236;
	 private int transaction_id = 0;// número aleatorio mandado por el cliente
   
	 private short secs = 0;
	 private short flags = 0; 
    
	 private static byte[] MAGIC_COOKIE = new byte[] {63, 82, 52, 63};
    
	 private byte END = (byte)255;
	 
    public protocoloDHCP() 
    {
    	Random ramRandom = new Random();
    	transaction_id = ramRandom.nextInt();
    	cabecera_dhcp();
	}
	
    private void cabecera_dhcp() 
    {
    	cabezera_dhcp = new byte[LONGITUD_DEL_MENSAJE];
    	ByteBuffer buffer = ByteBuffer.wrap(cabezera_dhcp);
    	//Establecemos el tipo de paquete 
    	buffer.put(MESSAGE_TYPE);
    	//Establecer el tipo de red 
    	buffer.put(HARDWARE_TYPE);
    	 // Establecer la longitud de la dirección de hardware
    	buffer.put(HARDWARE_ADDRESS_LENGTH);
    	// Establecer el número de saltos de paquetes
    	buffer.put(DHCP_HOPS);
    	 // Establecer el id de sesión
    	buffer.putInt(transaction_id);
    	 // Establecer el tiempo de espera
    	buffer.putShort(secs);
    	//Establece la bandera
    	buffer.putShort(flags);
    	//Establece la dirección IP del cliente
    	buffer.put(direccion_ip_cliente);
    	//Establece la dirección IP que ofrece el servidor al cliente
    	buffer.put(direccion_ip_ofrecida);
    	//Dirección IP del siguiente servidor DHCP
    	buffer.put(siguiente_direccion_ip_servidor);
    	//Direccion IP del realy agent
    	buffer.put(direccion_IP_relay);
    	//DIrección MAC del cliente
    	buffer.put(mac_del_cliente);
    	// Establecer el campo  file 
    	byte[] file = new byte[128];
    	buffer.put(file);
    }

    private void crear_parte_opciones() 
    {
    	//Opcion 53 tipo de mensaje
    	byte [] tipo_de_mensaje = new byte [4];
    	ByteBuffer buffer = ByteBuffer.wrap(tipo_de_mensaje);
    	buffer.put(codigo_tipo_mensaje);
    	buffer.put(longitud_tipo_mensaje);
    	buffer.put(bootrequest);
    	
    	//Opcion 1 máscara
    	byte [] mascara_red = new byte[4];
    	buffer = ByteBuffer.wrap(mascara_red);
    	buffer.put(codigo_mascara);
    	buffer.put(longitud_mascara);
    	buffer.put(mascara);
    	
    	//Opcion 3 Router
    	byte [] router = new byte[4];
    	buffer = ByteBuffer.wrap(router);
    	
    	
    	
    	//Opcion 6 DNS
    	byte [] servidor_DNS = new byte [4];
    	buffer = ByteBuffer.wrap(servidor_DNS);
    	buffer.put(codigo_servidores_DNS);
    	buffer.put(longitud_servidor_DNS);
    	buffer.put(servidor_DNS);

    	//Opcion 50 requested IP
    	byte [] requested_IP = new byte [4];
    	buffer = ByteBuffer.wrap(requested_IP);
    	buffer.put(codigo_request_ip);
    	buffer.put(longitud_request_ip);
    	buffer.put(ip);
    	
    	//Opcion 51 tiempo de cesión
    	byte[] tiempo_de_cesion = new byte[4];
    	buffer = ByteBuffer.wrap(tiempo_de_cesion);
    	buffer.put(codigo_tiempo_cesion);
    	buffer.put(longitud_tiempo_cesion);
    	buffer.putInt(this.tiempo_de_cesion);
    	
    	//Opcion 58 tiempo de renovacion
    	byte[] tiempo_de_renovacion = new byte[4];
    	buffer = ByteBuffer.wrap(tiempo_de_renovacion);
     	buffer.putInt(codigo_tiempo_renovacion);
    	buffer.putInt(longitud_tiempo_renovacion);
    	buffer.putInt(this.tiempo_de_renovacion);
    	
    	//Opcion 54 Identificador del servidor
    	byte [] identificador_del_servidor = new byte [4];
    	buffer = ByteBuffer.wrap(identificador_del_servidor);
    	buffer.put(codigo_identificacion_servidor);
    	buffer.put(longitud_identificacion_servidor);
    	buffer.put(dir_router);
   
    	byte [] end = new byte [1];
    	buffer = ByteBuffer.wrap(end);
    	buffer.put(END);
    	
    }
    
    
    
    
    
    /*
    public void dhcpDiscovery() 
    {
    	byte[] dhcpDiscBuffer = new byte[cabezera_dhcp.length + MAGIC_COOKIE.length + dhcp_options_part.length];
    	ByteBuffer buffer = ByteBuffer.wrap(dhcpDiscBuffer);
    	buffer.put(dhcp_first_part);
    	buffer.put(MAGIC_COOKIE);
    	buffer.put(dhcp_options_part);
    	
    	byte[] udpHeader = createUDPHeader(dhcpDiscBuffer);
    	byte[] ipHeader = createIP4Header(udpHeader.length);
    	
    	byte[] dhcpPacket = new byte[ udpHeader.length + ipHeader.length];
    	buffer = ByteBuffer.wrap(dhcpPacket);
    	buffer.put(ipHeader);
    	buffer.put(udpHeader);
    	// Transmite el mensaje
    	ProtocolManager.getInstance().broadcastData(dhcpPacket);
    }
    
    /*
    try 
    {
    	protocoloDHCP dhcpApp = new protocoloDHCP();
		dhcpApp.dhcpDiscovery();
	 } 
	 catch(Exception e) 
	 {
		   e.printStackTrace();
	 }*/
    
}


