// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyRoom.java

package com.megacrit.cardcrawl.rooms;


// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class EmptyRoom extends AbstractRoom
{

    public EmptyRoom()
    {
        phase = AbstractRoom.RoomPhase.COMPLETE;
    }

    public void onPlayerEntry()
    {
    }
}
