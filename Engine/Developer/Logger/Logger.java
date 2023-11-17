package Engine.Developer.Logger;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;

public class Logger {
    private static Logger sIntance = new Logger();
    private TreeMap<String, Log> mLogMap;

    private class ScreenLog {

        ScreenLog(String message, float screentime, Color screencolor) {
            mMessage = message;
            mInitialTime = System.currentTimeMillis();
            mScreenTime = screentime;
            mScreenColor = screencolor;
        }

        public String mMessage;
        public long mInitialTime;
        public float mScreenTime;
        public Color mScreenColor;
    }

    private ArrayList<ScreenLog> mLogQueue;

    private Logger() {
        mLogMap = new TreeMap<>();
        mLogQueue = new ArrayList<>();
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

    public static void Render(Graphics2D gfx) {
        int y = 20;
        ArrayList<ScreenLog> remodables = new ArrayList<>();

        for(ScreenLog log : Instance().mLogQueue) {
            if(System.currentTimeMillis() - log.mInitialTime < log.mScreenTime * 1000) {
                gfx.setColor(log.mScreenColor);
                gfx.drawString(log.mMessage, 20, y);
                y += 10;
            } else {
                remodables.add(log);
            }
        }

        for(ScreenLog log : remodables) {
            Instance().mLogQueue.remove(log);
        }
    }

    public void Log(Log log, String message, Level messagelevel) {
        /*String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        
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
        } else {
            log.GetInnerLogger().fine(message);
        }*/
    }

    public void Log(Log log, String message, Level messagelevel, float screentime, Color screencolor) {
        Log(log, message, messagelevel);

        if(java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("-agentlib:jdwp")) {
            if(messagelevel == Level.CONFIG) {
                message = "[CONFIG]: " + message;
            } else if(messagelevel == Level.SEVERE) {
                message = "[SEVERE]: " + message;
            } else if(messagelevel == Level.WARNING) {
                message = "[WARNING]: " + message;
            }

            mLogQueue.add(new ScreenLog(message, screentime, screencolor));
        }
    }
}