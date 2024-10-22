// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SteamUGC.java

package com.megacrit.cardcrawl.integrations.steam;

import com.codedisaster.steamworks.*;

public class SteamUGC
    implements SteamUGCCallback
{

    public SteamUGC()
    {
    }

    public void onUGCQueryCompleted(SteamUGCQuery steamugcquery, int i, int j, boolean flag, SteamResult steamresult)
    {
    }

    public void onSubscribeItem(SteamPublishedFileID steampublishedfileid, SteamResult steamresult)
    {
    }

    public void onUnsubscribeItem(SteamPublishedFileID steampublishedfileid, SteamResult steamresult)
    {
    }

    public void onRequestUGCDetails(SteamUGCDetails steamugcdetails, SteamResult steamresult)
    {
    }

    public void onCreateItem(SteamPublishedFileID steampublishedfileid, boolean flag, SteamResult steamresult)
    {
    }

    public void onSubmitItemUpdate(SteamPublishedFileID steampublishedfileid, boolean flag, SteamResult steamresult)
    {
    }

    public void onDownloadItemResult(int i, SteamPublishedFileID steampublishedfileid, SteamResult steamresult)
    {
    }

    public void onUserFavoriteItemsListChanged(SteamPublishedFileID steampublishedfileid, boolean flag, SteamResult steamresult)
    {
    }

    public void onSetUserItemVote(SteamPublishedFileID steampublishedfileid, boolean flag, SteamResult steamresult)
    {
    }

    public void onGetUserItemVote(SteamPublishedFileID steampublishedfileid, boolean flag, boolean flag1, boolean flag2, SteamResult steamresult)
    {
    }

    public void onStartPlaytimeTracking(SteamResult steamresult)
    {
    }

    public void onStopPlaytimeTracking(SteamResult steamresult)
    {
    }

    public void onStopPlaytimeTrackingForAllItems(SteamResult steamresult)
    {
    }

    public void onDeleteItem(SteamPublishedFileID steampublishedfileid, SteamResult steamresult)
    {
    }
}
