// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ActionLogEntry.java

package com.megacrit.cardcrawl.actions;


// Referenced classes of package com.megacrit.cardcrawl.actions:
//            AbstractGameAction

public class ActionLogEntry
{

    public ActionLogEntry(AbstractGameAction.ActionType type)
    {
        this.type = type;
    }

    public String toString()
    {
        return type.toString();
    }

    public AbstractGameAction.ActionType type;
}
