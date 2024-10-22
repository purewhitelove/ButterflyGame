// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionHandler.java

package com.megacrit.cardcrawl.core;

import org.apache.logging.log4j.Logger;

public class ExceptionHandler
{

    public ExceptionHandler()
    {
    }

    public static void handleException(Exception e, Logger logger)
    {
        logger.error("Exception caught", e);
    }
}
