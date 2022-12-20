import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;


public class IPHandler {

    public String ipset() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP:");

        ///String ip = scanner.nextLine();
        Client.sleepe();
        String ip = Client.currentCommand;
        System.out.println(ip);
        return ip;
    }

    public String ipautoset() throws Exception {
        String ipFull=getLocalIpAddress();
        String [] ipArray = ipFull.split("\\.");
        return ipFull;

    }

    public static String getLocalIpAddress() {
        InetAddress ip = null;
        String hostname;
        String ipFinal = null;
        String ipString;

        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            int lengthOfHostname = hostname.length();
            ipString = String.valueOf(ip);
            StringBuffer stringBuffer = new StringBuffer(ipString);
            ipFinal=ipString.substring(lengthOfHostname+1);
            System.out.println("Your current IP address :" + ipFinal);
            System.out.println("Your current Hostname : " + hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipFinal;
    }
}