package Engine.Developer.Logger;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.TreeMap;
import java.util.logging.Level;

public class Logger {
    private static Logger sIntance = new Logger();
    private TreeMap<String, Log> mLogMap;

    private Logger() {
        mLogMap = new TreeMap<>();
    }

    public static Logger Instance() {
        return sIntance;
    }

    public Log GetLog(String name) {
        if(mLogMap.containsKey(name)) {
            return mLogMap.get(name);
        } else {
            mLogMap.put(name, new Log(name));
            return mLogMap.get(name);
        }
    }

    public void Log(Log log, String message, Level messagelevel) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        
        if(messagelevel == Level.CONFIG) {
            log.GetInnerLogger().config(message);
        } else if(messagelevel == Level.FINER) {
            log.GetInnerLogger().finer(message);
        } else if(messagelevel == Level.FINEST) {
            log.GetInnerLogger().finest(message);
        } else if(messagelevel == Level.INFO) {
            log.GetInnerLogger().info("[" + timeStamp + "]: " + message);
        } else if(messagelevel == Level.OFF) {
            log.GetInnerLogger().info("[" + timeStamp + ", OFF]: " + message);
        } else if(messagelevel == Level.SEVERE) {
            log.GetInnerLogger().severe("[" + timeStamp + "]: " + message);
        } else if(messagelevel == Level.WARNING) {
            log.GetInnerLogger().warning("[" + timeStamp + "]: " + message);
        }
    }

    public void Log(Log log, String message, Level messagelevel, float screentime, Color screencolor) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        
        if(messagelevel == Level.CONFIG) {
            log.GetInnerLogger().config(message);
        } else if(messagelevel == Level.FINER) {
            log.GetInnerLogger().finer(message);
        } else if(messagelevel == Level.FINEST) {
            log.GetInnerLogger().finest(message);
        } else if(messagelevel == Level.INFO) {
            log.GetInnerLogger().info("[" + timeStamp + "]: " + message);
        } else if(messagelevel == Level.OFF) {
            log.GetInnerLogger().info("[" + timeStamp + ", OFF]: " + message);
        } else if(messagelevel == Level.SEVERE) {
            log.GetInnerLogger().severe("[" + timeStamp + "]: " + message);
        } else if(messagelevel == Level.WARNING) {
            log.GetInnerLogger().warning("[" + timeStamp + "]: " + message);
        }
    }
}