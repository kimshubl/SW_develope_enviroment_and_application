package test3;

import java.io.*;
import java.net.Socket;

public class Clientwan {

    final static String SERVER_IP = "192.168.172.19"; // 서버 IP (필요 시 변경)
    final static int SERVER_PORT = 8080;         // 서버 포트
    final static String FILE_TO_SEND = "C:\\Users\\minch\\eclipse-workspace\\bank\\users.txt"; // 전송할 파일 경로

    public static void main() {
        Socket socket = null;

        try {
            // 서버에 연결
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);

            // 파일 전송
            sendFile(socket, FILE_TO_SEND);

            // 서버 응답 받기
            InputStream is = socket.getInputStream();
            byte[] responseBuffer = new byte[1024];
            int n = is.read(responseBuffer);
            String serverResponse = new String(responseBuffer, 0, n);
            System.out.println("Server response: " + serverResponse);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 소켓 닫기
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 파일을 서버로 전송하는 메서드
    private static void sendFile(Socket socket, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("File not found: " + filePath);
                return;
            }

            // 파일을 읽기 위한 스트림
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = socket.getOutputStream();

            System.out.println("Sending file: " + file.getName());

            // 파일 내용을 서버로 전송
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();

            System.out.println("File sent successfully.");

            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

