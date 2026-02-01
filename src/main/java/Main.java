import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // TODO: Uncomment the code below to pass the first stage
    
    try {
      ServerSocket serverSocket = new ServerSocket(4221);// create a tcp server binding it to port 4221
    
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);// basically allows reusing same port 
    while (true)
{      
  Socket clientsSocket = serverSocket.accept();
  BufferedReader in = new BufferedReader(new InputStreamReader(clientsSocket.getInputStream()));
  OutputStream out = clientsSocket.getOutputStream();

  String requestLine = in.readLine();
  System.out.println("Requested: " + requestLine);
  String path=" ";

  if(requestLine != null){
    String[] parts = requestLine.split("");
    path = parts[1];
  }
  if(path.equals("/")){
    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
  }else{
    out.write("HTTP/1.1 400 Not Found\r\n\r\n".getBytes());
  }
  clientsSocket.close();
}
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}


