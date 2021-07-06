package sample.conexion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClienteSocket {
    private Socket cliente;
    private InetAddress host;
    public String message = "";

    public void connectToServer(Object message){
        try{
            host = InetAddress.getLocalHost();
            cliente = new Socket(host, 8000);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            System.out.println(entrada.readLine());

            PrintStream salida = new PrintStream(cliente.getOutputStream());
            salida.println(message);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
