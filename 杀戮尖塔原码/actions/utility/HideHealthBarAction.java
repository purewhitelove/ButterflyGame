// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HideHealthBarAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class HideHealthBarAction extends AbstractGameAction
{

    public HideHealthBarAction(AbstractCreature owner)
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        source = owner;
    }

    public void update()
    {
        source.hideHealthBar();
        isDone = true;
    }
}
