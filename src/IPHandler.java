import java.awt.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class IPHandler {

    public String ipset()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP:");
        String ip = scanner.nextLine();
        System.out.println(ip);
        return ip;
    }
    public String ipautoset()throws Exception{
        String subnet = "192.168.0";
        String ip[]=new String[256];
        int iter = 0;
        ClientGUI gui = new ClientGUI();
        for(int i=0;i<255;i++){

            gui.button.show(false);
            gui.input.show(false);
            gui.show(true);
            gui.setLayout(new GridLayout(0,1,0,1));
            //gui.setBounds();
            int timeout = 10;
            String host = subnet+"."+i;
            //gui.snow.setText("IP in proggress:"+host);

            if(InetAddress.getByName(host).isReachable(timeout)){
                System.out.println(host+" is reachable");
                gui.snow.setForeground(Color.GREEN);
                gui.snow.setText("IP in access:"+host);
                gui.snow.setForeground(Color.black);
                ip[iter]=host;
                iter++;
            }
            else{
                gui.snow.setForeground(Color.RED);
                gui.snow.setText("IP is closed:"+host);
               //gui.snow.setForeground(Color.black);

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
        }

    return "192.168.0.0";
    }

}
