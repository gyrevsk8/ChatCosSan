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
        System.out.println(Arrays.toString(ipArray));

        String subnet = ipArray[0]+'.'+ipArray[1]+'.'+ipArray[2];

        String ip[] = new String[256];
        int iter = 0;
        ClientGUI gui = new ClientGUI();

        gui.setBounds(400, 150, 250, 100);
        gui.show(true);     //переопределение gui для хэндлера
        for (int i = 0; i < 254; i++) {


            gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//переопределение gui для хэндлера
            gui.setTitle("IP Handler");
            gui.container.remove(gui.auto);
            gui.container.remove(gui.manual);
            gui.container.remove(gui.outfit);
            gui.container.remove(gui.userlist);
            gui.button.show(false);                         //переопределение gui для хэндлера
            gui.input.show(false);                          //переопределение gui для хэндлера
            gui.textArea.show(false);                                 //переопределение gui для хэндлера
            gui.scrollPane.show(false);
            gui.setLayout(new GridLayout(0, 1, 0, 1));//переопределение gui для хэндлера
            //gui.setBounds();                                 //переопределение gui для хэндлера


            int timeout = 10;
            String host = subnet + "." + i;
            //gui.serverMessage.setText("IP in proggress:"+host);

            if (InetAddress.getByName(host).isReachable(timeout)) {
                System.out.println(host + " is reachable");
                gui.serverMessage.setForeground(Color.GREEN);
                gui.serverMessage.setText("IP in access:" + host);
                //gui.serverMessage.setForeground(Color.black);
                ip[iter] = host;
                iter++;
                try {
                    Socket socket = new Socket(ip[i], 1234,false);
                    socket.close();
                    return host;
                } catch (UnknownHostException e) {
                    continue;
                }


            } else {
                gui.serverMessage.setForeground(Color.RED);
                gui.serverMessage.setText("IP is closed:" + host);
                //gui.serverMessage.setForeground(Color.black);

            }
        }

        for (int i = 0; i < iter; i++) {
            try {
                Socket socket = new Socket(ip[i], 1234);
                return ip[i];
            } catch (UnknownHostException e) {
                continue;
            } finally {
                continue;
            }
        }

        return getLocalIpAddress();
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