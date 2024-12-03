
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	final static String SERVER_IP = "127.0.0.1"; 
	final static int SERVER_PORT = 1225;
	final static String MESSAGE_TO_SERVER = "Hi, Server";
	
	public static void main(String[] args) {
		
		Socket socket = null;
		
		try {
			/** 소켓통신 시작 */
			socket = new Socket(SERVER_IP,SERVER_PORT);
			System.out.println("socket 연결");
		
			/**	Client에서 Server로 보내기 위한 통로 */
			OutputStream os = socket.getOutputStream();
			/**	Server에서 보낸 값을 받기 위한 통로 */
			InputStream is = socket.getInputStream();
			
			os.write( MESSAGE_TO_SERVER.getBytes() );
			os.flush();
			
			byte[] data = new byte[16];
			int n = is.read(data);
			final String resultFromServer = new String(data,0,n);
			
			System.out.println(resultFromServer);
			
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
출처: https://smilek1225.tistory.com/11 [웃엉의 프로그래밍 타임라인:티스토리]