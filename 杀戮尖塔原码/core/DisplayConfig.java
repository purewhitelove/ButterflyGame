// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DisplayConfig.java

package com.megacrit.cardcrawl.core;

import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.core:
//            ExceptionHandler

public class DisplayConfig
{

    private DisplayConfig(int width, int height, int fps_limit, boolean isFullscreen, boolean wfs, boolean vsync)
    {
        this.width = width;
        this.height = height;
        this.fps_limit = fps_limit;
        this.isFullscreen = isFullscreen;
        this.wfs = wfs;
        this.vsync = vsync;
    }

    public String toString()
    {
        HashMap hm = new HashMap();
        hm.put("width", Integer.valueOf(width));
        hm.put("height", Integer.valueOf(height));
        hm.put("fps_limit", Integer.valueOf(fps_limit));
        hm.put("isFullscreen", Boolean.valueOf(isFullscreen));
        hm.put("wfs", Boolean.valueOf(wfs));
        hm.put("vsync", Boolean.valueOf(vsync));
        return hm.toString();
    }

    public static DisplayConfig readConfig()
    {
        logger.info("Reading info.displayconfig");
        ArrayList configLines = readDisplayConfFile();
        if(configLines.size() < 4)
        {
            createNewConfig();
            return readConfig();
        }
        if(configLines.size() == 5)
        {
            appendFpsLimit(configLines);
            return readConfig();
        }
        DisplayConfig dc;
        try
        {
            dc = new DisplayConfig(Integer.parseInt(((String)configLines.get(0)).trim()), Integer.parseInt(((String)configLines.get(1)).trim()), Integer.parseInt(((String)configLines.get(2)).trim()), Boolean.parseBoolean(((String)configLines.get(3)).trim()), Boolean.parseBoolean(((String)configLines.get(4)).trim()), Boolean.parseBoolean(((String)configLines.get(5)).trim()));
        }
        catch(Exception e)
        {
            logger.info("Failed to parse the info.displayconfig going to recreate it with defaults.");
            createNewConfig();
            return readConfig();
        }
        logger.info("DisplayConfig successfully read.");
        return dc;
    }

    private static ArrayList readDisplayConfFile()
    {
        ArrayList configLines;
        Scanner s;
        configLines = new ArrayList();
        s = null;
        for(s = new Scanner(new File("info.displayconfig")); s.hasNextLine(); configLines.add(s.nextLine()));
        if(s != null)
            s.close();
        break MISSING_BLOCK_LABEL_98;
        FileNotFoundException e;
        e;
        ArrayList arraylist;
        logger.info("File info.displayconfig not found, creating with defaults.");
        createNewConfig();
        arraylist = readDisplayConfFile();
        if(s != null)
            s.close();
        return arraylist;
        Exception exception;
        exception;
        if(s != null)
            s.close();
        throw exception;
        return configLines;
    }

    public static void writeDisplayConfigFile(int w, int h, int fps, boolean fs, boolean wfs, boolean vs)
    {
        PrintWriter writer = null;
        writer = new PrintWriter("info.displayconfig", "UTF-8");
        writer.println(Integer.toString(w));
        writer.println(Integer.toString(h));
        writer.println(Integer.toString(fps));
        writer.println(Boolean.toString(fs));
        writer.println(Boolean.toString(wfs));
        writer.println(Boolean.toString(vs));
        if(writer != null)
            writer.close();
        break MISSING_BLOCK_LABEL_123;
        IOException e;
        e;
        ExceptionHandler.handleException(e, logger);
        if(writer != null)
            writer.close();
        break MISSING_BLOCK_LABEL_123;
        Exception exception;
        exception;
        if(writer != null)
            writer.close();
        throw exception;
    }

    private static void createNewConfig()
    {
        logger.info("Creating new config with default values...");
        writeDisplayConfigFile(1280, 720, 60, false, false, true);
    }

    private static void appendFpsLimit(ArrayList configLines)
    {
        logger.info("Updating config...");
        try
        {
            writeDisplayConfigFile(Integer.parseInt(((String)configLines.get(0)).trim()), Integer.parseInt(((String)configLines.get(1)).trim()), 60, Boolean.parseBoolean(((String)configLines.get(2)).trim()), Boolean.parseBoolean(((String)configLines.get(3)).trim()), true);
        }
        catch(Exception e)
        {
            logger.info("Failed to parse the info.displayconfig going to recreate it with defaults.");
            createNewConfig();
        }
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getMaxFPS()
    {
        return fps_limit;
    }

    public boolean getIsFullscreen()
    {
        return isFullscreen;
    }

    public boolean getWFS()
    {
        return wfs;
    }

    public boolean getIsVsync()
    {
        return vsync;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/core/DisplayConfig.getName());
    private static final String DISPLAY_CONFIG_LOC = "info.displayconfig";
    private static final int DEFAULT_W = 1280;
    private static final int DEFAULT_H = 720;
    private static final int DEFAULT_FPS_LIMIT = 60;
    private static final boolean DEFAULT_FS = false;
    private static final boolean DEFAULT_WFS = false;
    private static final boolean DEFAULT_VSYNC = true;
    private int width;
    private int height;
    private int fps_limit;
    private boolean isFullscreen;
    private boolean wfs;
    private boolean vsync;

}
