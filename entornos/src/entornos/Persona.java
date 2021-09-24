package entornos;

public class Persona{ 
 	String numeroDeTelefono; 
 
 	public Persona(String numeroDeTelefono){ 
 	 	Super(); 
 	 	this.numeroDeTelefono=numeroDeTelefono; 
 	} 
 	public String getNumeroDeTelefono(){  	 	return numeroDeTelefono; 
 	} 
 	public void setNumeroDeTelefono(String numeroDeTelefono){ 
 	 	this.numeroDeTelefono=numeroDeTelefono; 
 	} 
} 
 
 
public class Profesor extends Persona{ 
 	String str;  	int edad; 
 	String numeroDeTelefono; 
 	 
 	public Profesor(String numeroDeTelefono){  	 	super(numeroDeTelefono); 
 	} 
 	public void printInformacionPersonal(){ 
 	 	System.out.println("Nombre:  " + str); 
 	 	System.out.println("Edad:  " + edad); 
 	 	System.out.println("Telefono:  "+ numeroDeTelefono); 
 	} 
 
 public void printTodaLaInformacion(){   System.out.println("Profesor:  "); 
 	 	System.out.println("Nombre:  " + str); 
 	 	System.out.println("Edad:  " + edad); 
 	 	System.out.println("Telefono:  " + this.numeroDeTelefono); 
 	 	} 
 	} 
