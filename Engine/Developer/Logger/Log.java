package Engine.Developer.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class Log {
    private Logger mLogger;

    public Log(String name) {
        /*mLogger = Logger.getLogger(name);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String path = timeStamp + "/[" + timeStamp + "]_" + name + ".log";
        try {
            Path p = Paths.get("Logs", path);

            if(!Files.exists(Paths.get("Logs"))) {
                Files.createDirectory(Paths.get("Logs"));
            }

            if (!Files.exists(p.getParent())) {
                Files.createDirectory(p.getParent());
            }

            FileHandler fHandler = new FileHandler(p.toString());
            mLogger.addHandler(fHandler);
            XMLFormatter formatter = new XMLFormatter();
            fHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    public Logger GetInnerLogger() {
        return mLogger;
    }
}