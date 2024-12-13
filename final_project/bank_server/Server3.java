import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class sever3 {
    private static final int SERVER_PORT = 8080;
    private static final String RESPONSE_MESSAGE = "File received successfully";
    private static final String OUTPUT_FILE = "C:\\Users\\codbs\\Documents\\server\\server.txt";

    public static void main(String[] args) {
        System.out.println("Starting server...");
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            printServerDetails(serverSocket);

            while (true) {
                System.out.println("Waiting for client connection...");
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    // 파일 수신 처리
                    receiveFile(clientSocket);

                    System.out.println("File received and saved successfully.");
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    /**
     * 서버 IP와 포트 정보를 출력합니다.
     */
    private static void printServerDetails(ServerSocket serverSocket) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address.isSiteLocalAddress() && address instanceof Inet4Address) {
                        System.out.println("Internal IP Address: " + address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error retrieving network details: " + e.getMessage());
        }
        System.out.println("Server is listening on port: " + serverSocket.getLocalPort());
    }

    /**
     * 클라이언트로부터 파일을 수신합니다.
     */
    private static void receiveFile(Socket socket) {
        try (
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
            OutputStream os = socket.getOutputStream()
        ) {
            System.out.println("Receiving file...");

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            // 파일 저장 완료
            System.out.println("File saved to: " + OUTPUT_FILE);

            // 클라이언트에 응답 메시지 전송
            os.write(RESPONSE_MESSAGE.getBytes());
            os.flush();
        } catch (IOException e) {
            System.err.println("Error receiving file: " + e.getMessage());
        }
    }
}
