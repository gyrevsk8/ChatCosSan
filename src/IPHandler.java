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
        for(int i=0;i<255;i++){


            int timeout = 10;
            String host = subnet+"."+i;


            if(InetAddress.getByName(host).isReachable(timeout)){
                System.out.println(host+" is reachable");
                ip[iter]=host;
                iter++;
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
