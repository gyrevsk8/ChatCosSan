import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;


public class IPHandler {

    public String ipset() throws InterruptedException {//метод подключения к ip, заданного с клавиатуры
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP:");


        Client.sleepe();
        String ip = Client.currentCommand;
        System.out.println(ip);
        return ip;
    }



    public String getLocalIpAddress() {//метод возвращающий адрес локального ip, к которому подключен клиент
        InetAddress ip = null;//ip
        String hostname;//имя хоста
        String ipFinal = null;//финальный адресс
        String ipString;

        try {
            ip = InetAddress.getLocalHost();//получаем "грязный" ip с ненужной информацией
            hostname = ip.getHostName();//получаем имя хоста
            int lengthOfHostname = hostname.length();
            ipString = String.valueOf(ip);// преобразуем в String
            StringBuffer stringBuffer = new StringBuffer(ipString);//Вызываем StringBuffer для работы со строкой
            ipFinal=ipString.substring(lengthOfHostname+1);//вырезаем из адреса ненужную информцию
            System.out.println("Your current IP address :" + ipFinal);
            System.out.println("Your current Hostname : " + hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipFinal;//возвращаем чистый ip
    }
}