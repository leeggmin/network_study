package day0424;

import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsServer {

    private static DatagramSocket datagramSocket;
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        System.out.println("-------------------------------------------");
        System.out.println("서버를 종료하려면 q 또는 q를 입력하고 Enter를 입력하세요");
        System.out.println("-------------------------------------------");

        startServer();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String key = scanner.nextLine();
            if (key.toLowerCase().equals("q")) {
                break;
            }
        }

        scanner.close();

        stopServer();
    }

    private static void stopServer() {
        datagramSocket.close();
        executorService.shutdownNow();
        System.out.println("[서버] 종료됨");
    }

    private static void startServer() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {

                    //DatagramSocket 생성 및 Port 바인딩
                    datagramSocket = new DatagramSocket(50001);
                    System.out.println("[서버] 시작됨");

                    while (true) {
                        //클라이언트가 구독하고 싶은 뉴스 주제 얻기
                        DatagramPacket receivePacket = new DatagramPacket(
                                new byte[1024], 1024
                        );
                        System.out.println("클라이언트의 희망뉴스 종류를 얻기 위해 대기중");
                        datagramSocket.receive(receivePacket);

                        String newsKind = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

                        //클라이언트의 IP와 Port 정보가 있는 SocketAddress 얻기
                        SocketAddress socketAddress = receivePacket.getSocketAddress();

                        //10개의 뉴스를 클라이언트에게 전송
                        for (int i=1; i<=10; i++) {
                            String data = newsKind + " : 뉴스" + i;
                            byte[] bytes = data.getBytes("UTF-8");
                            DatagramPacket sendPacket = new DatagramPacket(bytes, 0, bytes.length, socketAddress);
                            datagramSocket.send(sendPacket);

                            Thread.sleep(5000);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("[서버] : "+e.getMessage());
                }
            }

        };

        thread.start();
    }
}
