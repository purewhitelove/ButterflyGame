// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaveFileLoadError.java

package com.megacrit.cardcrawl.exceptions;


public class SaveFileLoadError extends Exception
{

    public SaveFileLoadError()
    {
    }

    public SaveFileLoadError(String message)
    {
        super(message);
    }

    public SaveFileLoadError(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SaveFileLoadError(Throwable cause)
    {
        super(cause);
    }

    private static final long serialVersionUID = 1L;
}
