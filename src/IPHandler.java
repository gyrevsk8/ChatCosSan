import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.Enumeration;
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
    public String ipautoset()throws Exception{
        String subnet = "192.168.0";

        String ip[]=new String[256];
        int iter = 0;
        ClientGUI gui = new ClientGUI();
        gui.setBounds(400,150,250,100);
        gui.show(true);     //переопределение gui для хэндлера
        for(int i=0;i<255;i++){

            gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//переопределение gui для хэндлера
            gui.button.show(false);                         //переопределение gui для хэндлера
            gui.input.show(false);                          //переопределение gui для хэндлера
            gui.textArea.show(false);                                 //переопределение gui для хэндлера
            gui.setLayout(new GridLayout(0,1,0,1));//переопределение gui для хэндлера
            //gui.setBounds();                                 //переопределение gui для хэндлера


            int timeout = 10;
            String host = subnet+"."+i;
            //gui.johnsnow.setText("IP in proggress:"+host);

            if(InetAddress.getByName(host).isReachable(timeout)){
                System.out.println(host+" is reachable");
                gui.johnsnow.setForeground(Color.GREEN);
                gui.johnsnow.setForeground(Color.GREEN);
                gui.johnsnow.setText("IP in access:"+host);
                gui.johnsnow.setForeground(Color.black);
                ip[iter]=host;
                iter++;
            }
            else{
                gui.johnsnow.setForeground(Color.RED);
                gui.johnsnow.setText("IP is closed:"+host);
               //gui.johnsnow.setForeground(Color.black);

            }
        }

        for(int i=0;i<iter;i++)
        {
            try {
                Socket socket = new Socket(ip[i],1234);
                return ip[i];
            }
            catch (UnknownHostException e){
                continue;
            }
            finally {
                continue;
            }
        }

    return "192.168.0.0";
    }
    public static String getLocalIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual())
                    continue;


                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address) {
                        ip = addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

}
