import javax.swing.*;
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
        gui.setBounds(400,150,250,100);
        gui.show(true);     //переопределение gui для хэндлера
        for(int i=0;i<255;i++){

            gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//переопределение gui для хэндлера
            gui.button.show(false);                         //переопределение gui для хэндлера
            gui.input.show(false);                          //переопределение gui для хэндлера
                                            //переопределение gui для хэндлера
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

}
