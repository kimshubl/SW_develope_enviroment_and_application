import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class server3 extends Thread {
    
    final static int SERVER_PORT = 8080;
    final static String RESPONSE_MESSAGE = "File received successfully";

    public static void main(String[] args) {
        
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            while (true) {
                System.out.println("Waiting for client connection...");
                
                // 출력: 서버의 IP 주소와 포트 정보
                printServerDetails(serverSocket);

                // 클라이언트 연결 대기
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                
                // 파일 수신
                receiveFile(socket);

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 서버 IP와 포트 정보를 출력하는 메서드
    private static void printServerDetails(ServerSocket serverSocket) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress() && addr instanceof java.net.Inet4Address) {
                        System.out.println("Internal IP Address: " + addr.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Server listening on port: " + serverSocket.getLocalPort());
    }

    // 파일 수신 메서드
    private static void receiveFile(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // 파일 저장 경로
            String outputFile = "received_file.txt";
            FileOutputStream fos = new FileOutputStream(outputFile);

            System.out.println("Receiving file...");

            // 데이터를 읽어서 파일로 저장
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("File saved to: " + outputFile);

            // 클라이언트에 응답 메시지 전송
            os.write(RESPONSE_MESSAGE.getBytes());
            os.flush();

            fos.close();
            is.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
