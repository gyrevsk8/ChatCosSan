import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger implements LoggerF {

    private static final LoggerCrypt crypter = new LoggerCrypt();
    public void setNewLogMessage(String name,String phonenumber ,String text){
    try (FileWriter writer = new FileWriter("Log.txt", true))
    {
        Date date = new Date();
        writer.append('\n');
        // запись всей строки

        writer.write(date.toString());
        writer.append('\n');
        writer.write("Surname:"+name+"("+phonenumber+"):"+crypter.encode(text));
        writer.append('\n');

        writer.flush();
    }
        catch(
    IOException ex){

        System.out.println(ex.getMessage());
    }
}}
