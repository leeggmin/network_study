package day0410;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServerEx {

    private static ServerSocket serverSocket;

    public static void main(String[] args) {

        System.out.println("-------------------------------------------");
        System.out.println("서버를 종료하려면 q 또는 q를 입력하고 Enter를 입력하세요");
        System.out.println("-------------------------------------------");

        //TCP 서버 시작
        startServer();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String key = scanner.nextLine();

            if (key.toLowerCase().equals("q")) {
                System.out.println("[서버] 종료");
                break;
            }

            scanner.close();

            stopServer();
        }
    }

    private static void stopServer() {
        //ServerSocket을 닫고 Port 언바인딩
        try {
            serverSocket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void startServer() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                //ServerSocket 생성 및 Port 바인딩

                try {
                    serverSocket = new ServerSocket(50001);
                    System.out.println("[서버] 시작됨");

                    while (true) {
                        System.out.println("\n[서버] 연결 요청을 기다림\n");

                        //연결수락
                        Socket socket = serverSocket.accept();

                        //연결된 클라이언트 정보 받기
                        InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();

                        String clientIp = isa.getHostString();
                        System.out.println("[서버]"+clientIp+"의 연결 요청을 수락함");

//                        //데이터 받기
//                        InputStream is = socket.getInputStream();
//                        byte[] bytes = new byte[1024];
//                        int readByteCount = is.read(bytes);
//                        String message = new String(bytes, 0, readByteCount, "UTF-8");
//
//                        //데이터 보내기
//                        OutputStream os = socket.getOutputStream();
//                        bytes = message.getBytes("UTF-8");
//                        os.write(bytes);
//                        os.flush();
//                        System.out.println("[서버] 받은 데이터를 다시 보냄:"+message);

                        //데이터 받기
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        String message = dis.readUTF();

                        //데이터 보내기
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(message);
                        dos.flush();
                        System.out.println("[서버] 받은 데이터를 다시 보냄:"+message);

                        //연결 끊기
                        socket.close();
                        System.out.println("[서버]"+clientIp+"의 연결을 끊음.");
                    }
                } catch (IOException e) {
                    System.out.println("[서버]"+e.getMessage());
                }
            }
        };

        thread.start();
    }
}