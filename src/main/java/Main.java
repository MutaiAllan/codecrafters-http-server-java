import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.util.Objects;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    //
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    
    try {
      serverSocket = new ServerSocket(4221);
      serverSocket.setReuseAddress(true);
      clientSocket = serverSocket.accept(); // Wait for connection from client.
      DataOutputStream dataOutputStream =
          new DataOutputStream(clientSocket.getOutputStream());
      dataOutputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
      dataOutputStream.flush();
      System.out.println("accepted new connection");

      String[] request =
          in.readLine().split(" "); // Ignore the client input
      if (Objects.equals(request[1], "/")) {
        System.out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
      } else {
        System.out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
