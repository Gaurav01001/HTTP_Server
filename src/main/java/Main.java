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

  String requestLine = in.readLine();//to read each line of http request 
  

  System.out.println("Requested: " + requestLine);
  
  String path="";//creates an empty string for the url path the client makes a request.
if (requestLine != null) { 
    String[] parts = requestLine.split(" ");
    path = parts[1];
}
//Read Header 

String line;
String userAgent = " ";
while((line=in.readLine()) != null && !line.isEmpty()){
  
  if(line.startsWith("User-Agent:")){
    userAgent = line.substring("User-Agent:".length()).trim();
    

  }
  System.out.println("HEADER: " + line);
}

//Routing
if (path.startsWith("/echo/")) {
    String echoStr = path.substring("/echo/".length());
  byte[] body = echoStr.getBytes();
String resp =
    "HTTP/1.1 200 OK\r\n" +
    "Content-Type: text/plain\r\n" +
    "Content-Length: " + echoStr.getBytes().length + "\r\n" +
    "\r\n" ;

  out.write(resp.getBytes());
  out.write(body);

}
else if (path.equals("/user-agent"))

{
  byte[] body = userAgent.getBytes();
  String resp =
  "HTTP/1.1 200 OK\r\n" +
  "Content-Type: text/plain\r\n" + 
  "Content-Length: " + body.length + "\r\n"
  + "\r\n";
  out.write(resp.getBytes());
  out.write(body);
}

else {
    out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
}

  clientsSocket.close();
}
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
//Completed Read header 

