// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SystemStats.java

package com.megacrit.cardcrawl.core;

import java.io.File;
import java.text.NumberFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemStats
{

    public SystemStats()
    {
    }

    public static long getMaxMemory()
    {
        return Runtime.getRuntime().maxMemory();
    }

    public static long getAllocatedMemory()
    {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getUnallocatedMemory()
    {
        return getMaxMemory() - getAllocatedMemory();
    }

    public static long getFreeMemory()
    {
        return Runtime.getRuntime().freeMemory();
    }

    public static long getUsedMemory()
    {
        return getAllocatedMemory() - getFreeMemory();
    }

    public static long getTotalFreeMemory()
    {
        return getFreeMemory() + (getMaxMemory() - getAllocatedMemory());
    }

    private static long toMb(long sizeInBytes)
    {
        return sizeInBytes / 1024L / 1024L;
    }

    private static String prettyBytes(long sizeInBytes, NumberFormat format)
    {
        return format.format(toMb(sizeInBytes));
    }

    public static void logMemoryStats()
    {
        StringBuilder sb = new StringBuilder();
        NumberFormat format = NumberFormat.getInstance();
        sb.append("Free Memory: ").append(prettyBytes(getFreeMemory(), format)).append("Mb\n");
        sb.append("Max Memory: ").append(prettyBytes(getMaxMemory(), format)).append("Mb\n");
        sb.append("Allocated Memory: ").append(prettyBytes(getAllocatedMemory(), format)).append("Mb\n");
        sb.append("Unallocated Memory: ").append(prettyBytes(getUnallocatedMemory(), format)).append("Mb\n");
        sb.append("Total Free Memory: ").append(prettyBytes(getTotalFreeMemory(), format)).append("Mb\n");
        sb.append("Used Memory: ").append(prettyBytes(getUsedMemory(), format)).append("Mb\n");
        logger.info((new StringBuilder()).append("MEMORY STATS:\n").append(sb.toString()).toString());
    }

    public static long getDiskSpaceTotal()
    {
        return (new File("/")).getTotalSpace();
    }

    public static long getDiskSpaceFree()
    {
        return (new File("/")).getFreeSpace();
    }

    public static long getDiskSpaceUsable()
    {
        return (new File("/")).getUsableSpace();
    }

    public static void logDiskStats()
    {
        StringBuilder sb = new StringBuilder();
        NumberFormat format = NumberFormat.getInstance();
        sb.append("Total Space: ").append(prettyBytes(getDiskSpaceTotal(), format)).append("Mb\n");
        sb.append("Usable Space: ").append(prettyBytes(getDiskSpaceUsable(), format)).append("Mb\n");
        sb.append("Free Space: ").append(prettyBytes(getDiskSpaceFree(), format)).append("Mb\n");
        logger.info((new StringBuilder()).append("DISK STATS:\n").append(sb.toString()).toString());
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/core/SystemStats.getName());
    private static final String diskRoot = "/";

}
