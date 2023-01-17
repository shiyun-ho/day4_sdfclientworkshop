package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 7000);

        //sends msg
        try(OutputStream os = socket.getOutputStream()) 
        {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            
            //reading data
            Console con = System.console();
            String readInput = "", receivedMessaged = "";

            try(InputStream is = socket.getInputStream()){
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);
               
                while (!readInput.equalsIgnoreCase("close")){
                readInput = con.readLine("Enter a command: "); 
                dos.writeUTF(readInput);
                dos.flush();

                receivedMessaged = dis.readUTF();
                System.out.println(receivedMessaged);
                }

                dis.close();
                bis.close();

            } catch (EOFException ex){
                socket.close();
            }

            

            //handle end of file exception
        } catch (EOFException ex) {
            socket.close();
        }
    }
}
