package day0508;

import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    //필드
    ServerSocket serverSocket;
    ExecutorService threadPool = Executors.newFixedThreadPool(100);
    Map<String, SocketClient> chatRoom = Collections.synchronizedMap(new HashMap<>());

    //메소드: 서버 시작
    public void start() throws IOException {

        serverSocket = new ServerSocket(50001);
        System.out.println("[서버] 시작 됨");

        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    SocketClient sc = new SocketClient(ChatServer.this, socket);
                }

            } catch (Exception e) {
            }
        });
        thread.start();
    }
    //메소드 : 클라이언트 연결 시 SocketClient  생성 및 추가
    public void addSocketClient(SocketClient socketClient) {
        String key = socketClient.chatName+"@"+socketClient.clientIp;
        chatRoom.put(key,socketClient);
        System.out.println("입장 : " + key);
        System.out.println("현재 채팅자 수:"+chatRoom.size()+"\n");
    }

    //메소드: 클라이언트 연결 종료 시 SocketClient  제거
    public void removeSocketClient(SocketClient socketClient) {
        String key = socketClient.chatName+"@"+socketClient.clientIp;
        chatRoom.remove(key);
        System.out.println("나감: "+key);
        System.out.println("현재 채팅자 수:"+chatRoom.size()+"\n");
    }

    //메소드: 모든 클라이언트에게 메시지 보냄
    public void sendToAll(SocketClient sender, String message) {
        JSONObject root = new JSONObject();
        root.put("clientIp",sender.clientIp);
        root.put("chatName",sender.chatName);
        root.put("message",message);
        String json = root.toString();

        Collection<SocketClient> socketClients = chatRoom.values();
        for(SocketClient sc:socketClients) {
            if (sc == sender) continue;
            sc.send(json);
        }
    }

    //메소드: 서버 종료
    public void stop() {
        try{
            serverSocket.close();
            threadPool.shutdownNow();
            chatRoom.values().stream().forEach(socketClient -> socketClient.close());
            System.out.println("[서버] 종료됨");

        }catch (IOException e){}
    }

    public static void main(String[] args) {
        try{
            ChatServer chatServer = new ChatServer();
            chatServer.start();

            System.out.println("-------------------------------------------");
            System.out.println("서버를 종료하려면 q를 입력하고 Enter");
            System.out.println("-------------------------------------------");

            //키보드 입력
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String key = scanner.nextLine();
                if(key.equals("q")) break;

            }
            scanner.close();

            //TCP 서버 종료
            chatServer.stop();

        }catch (IOException e) {
            System.out.println("[서버]"+e.getMessage());
        }
    }
}
