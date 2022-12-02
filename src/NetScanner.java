import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class NetScanner {
    public static void main(String[] args)throws Exception{
        String subnet = "192.168.0";

        //loop
        for(int i=0;i<255;i++){
            int timeout = 10;
            String host = subnet+"."+i;
            if(InetAddress.getByName(host).isReachable(timeout)){
                System.out.println(host+" is reachable");
                try {
                    Socket socket = new Socket(host, 22356);
                } catch (IOException e){
                    e.printStackTrace();

                }
            }
        }
    }
}