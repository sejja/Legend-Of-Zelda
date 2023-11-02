package Engine.Developer.Logger;

import java.util.TreeMap;
import java.util.logging.Level;

public class Logger {
    private static Logger sIntance = new Logger();
    private TreeMap<String, Log> mLogMap;

    private Logger() {
    }

    public Logger Instance() {
        return sIntance;
    }

    public Log GetLog(String name) {
        return new Log();
    }

    public void Log(Log log, String message, Level messagelevel, boolean toscreen) {

    }
}