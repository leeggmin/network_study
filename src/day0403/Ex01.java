package day0403;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ex01 {
    public static void main(String[] args) {
        try {
            InetAddress local = InetAddress.getLocalHost();
            System.out.println("내컴퓨터 IP 주소 : " + local.getHostAddress());

            InetAddress[] ipArr = InetAddress.getAllByName("www.naver.com");
            for (InetAddress remote :ipArr) {
                System.out.println("www.naver.com IP 주소 : " + remote.getHostAddress());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}