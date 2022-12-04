import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Mss implements LoggerF {
    public void setNewLogMessage(String name, String text){
    try (FileWriter writer = new FileWriter("Log.txt", true))
    {
        Date date = new Date();
        writer.append('\n');
        // запись всей строки
        writer.write(date.toString());
        writer.append('\n');
        writer.write(text);
        writer.append('\n');

        writer.flush();
    }
        catch(
    IOException ex){

        System.out.println(ex.getMessage());
    }
}}
