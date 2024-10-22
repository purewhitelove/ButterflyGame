// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SRCallback.java

package com.megacrit.cardcrawl.integrations.steam;

import com.codedisaster.steamworks.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SRCallback
    implements SteamRemoteStorageCallback
{

    public SRCallback()
    {
    }

    public void onFileShareResult(SteamUGCHandle fileHandle, String fileName, SteamResult result)
    {
        logger.info((new StringBuilder()).append("The 'onFileShareResult' callback was called and returns: fileHandle=").append(fileHandle.toString()).append(", fileName=").append(fileName).append(", result=").append(result.toString()).toString());
    }

    public void onDownloadUGCResult(SteamUGCHandle fileHandle, SteamResult result)
    {
        logger.info((new StringBuilder()).append("The 'onDownloadUGCResult' callback was called and returns: fileHandle=").append(fileHandle.toString()).append(", result=").append(result.toString()).toString());
    }

    public void onPublishFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result)
    {
        logger.info((new StringBuilder()).append("The 'onPublishFileResult' callback was called and returns: publishedFileID=").append(publishedFileID.toString()).append(", needsToAcceptWLA=").append(needsToAcceptWLA).append(", result=").append(result.toString()).toString());
    }

    public void onUpdatePublishedFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result)
    {
        logger.info((new StringBuilder()).append("The 'onUpdatePublishedFileResult' callback was called and returns: publishedFileID=").append(publishedFileID.toString()).append(", needsToAcceptWLA=").append(needsToAcceptWLA).append(", result=").append(result.toString()).toString());
    }

    public void onPublishedFileSubscribed(SteamPublishedFileID steampublishedfileid, int i)
    {
    }

    public void onPublishedFileUnsubscribed(SteamPublishedFileID steampublishedfileid, int i)
    {
    }

    public void onPublishedFileDeleted(SteamPublishedFileID steampublishedfileid, int i)
    {
    }

    public void onFileWriteAsyncComplete(SteamResult steamresult)
    {
    }

    public void onFileReadAsyncComplete(SteamAPICall steamapicall, SteamResult steamresult, int i, int j)
    {
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/integrations/steam/SRCallback.getName());

}
