import java.util.Date;
import java.text.SimpleDateFormat;

public class Logger {

    public Logger(){
	
    }

    public static void info(String log) {
    	String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    	System.out.println("[INFO]" + "[" + timestamp + "] " + log);
    }

    public static void error(String log) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        System.out.println("[INFO]" + "[" + timestamp + "] " + log);
    }

}