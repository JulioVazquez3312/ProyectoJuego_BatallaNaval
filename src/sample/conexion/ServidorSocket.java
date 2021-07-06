package sample.conexion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {
    private ServerSocket servidor;
    private Socket cliente;
    public Boolean activate = false;
    public Integer barco = 0;
    public Integer message = 0;
    public String obj="";

    public String iniciarServidor(){
        try{
            activate = true;
            servidor = new ServerSocket(8000);
            cliente = servidor.accept();
            PrintStream escritura = new PrintStream(cliente.getOutputStream());
            escritura.println("");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            obj = entrada.readLine();

            cliente.close();
            activate = false;
            servidor.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return obj;
    }
}
