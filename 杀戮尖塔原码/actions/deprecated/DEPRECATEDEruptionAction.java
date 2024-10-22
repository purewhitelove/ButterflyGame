// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDEruptionAction.java

package com.megacrit.cardcrawl.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DEPRECATEDEruptionAction extends AbstractGameAction
{

    public DEPRECATEDEruptionAction(int baseDamage)
    {
        this.baseDamage = baseDamage;
    }

    public void update()
    {
        isDone = true;
    }

    private int baseDamage;
}
