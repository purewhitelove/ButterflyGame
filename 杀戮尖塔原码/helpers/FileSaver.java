// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileSaver.java

package com.megacrit.cardcrawl.helpers;

import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            File

public class FileSaver
    implements Runnable
{

    public FileSaver(BlockingQueue q)
    {
        queue = q;
    }

    public void run()
    {
        while(!Thread.currentThread().isInterrupted()) 
            try
            {
                consume((File)queue.take());
            }
            catch(InterruptedException e)
            {
                logger.info("Save thread interrupted!");
                Thread.currentThread().interrupt();
            }
        logger.info("Save thread will die now.");
    }

    private void consume(File file)
    {
        logger.debug((new StringBuilder()).append("Dequeue: qsize=").append(queue.size()).append(" file=").append(file.getFilepath()).toString());
        file.save();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/FileSaver.getName());
    private final BlockingQueue queue;

}
