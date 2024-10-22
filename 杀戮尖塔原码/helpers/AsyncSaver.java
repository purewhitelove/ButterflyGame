// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AsyncSaver.java

package com.megacrit.cardcrawl.helpers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            File, FileSaver

public class AsyncSaver
{

    public AsyncSaver()
    {
    }

    public static void save(String filepath, String data)
    {
        boolean enableAsyncSave = true;
        if(enableAsyncSave)
        {
            logger.debug((new StringBuilder()).append("Enqueue: qsize=").append(saveQueue.size()).append(" file=").append(filepath).toString());
            saveQueue.add(new File(filepath, data));
            ensureSaveThread();
        } else
        {
            logger.info("Saving synchronously");
            File saveFile = new File(filepath, data);
            saveFile.save();
        }
    }

    private static void ensureSaveThread()
    {
        if(saveThread == null)
            startSaveThread();
        else
        if(!saveThread.isAlive())
        {
            logger.info("Save thread is dead. Starting save thread!");
            startSaveThread();
        }
    }

    private static void startSaveThread()
    {
        saveThread = new Thread(new FileSaver(saveQueue));
        saveThread.setName("FileSaver");
        saveThread.start();
    }

    public static void shutdownSaveThread()
    {
        if(saveThread != null)
            saveThread.interrupt();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/AsyncSaver.getName());
    private static Thread saveThread;
    private static final BlockingQueue saveQueue = new LinkedBlockingQueue();

}
