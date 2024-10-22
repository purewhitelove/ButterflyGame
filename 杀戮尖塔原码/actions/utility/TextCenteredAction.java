// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextCenteredAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextCenteredEffect;
import java.util.ArrayList;

public class TextCenteredAction extends AbstractGameAction
{

    public TextCenteredAction(AbstractCreature source, String text)
    {
        used = false;
        setValues(source, source);
        msg = text;
        duration = 2.0F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
    }

    public void update()
    {
        if(!used)
        {
            AbstractDungeon.effectList.add(new TextCenteredEffect(msg));
            used = true;
        }
        tickDuration();
    }

    private boolean used;
    private String msg;
    private static final float DURATION = 2F;
}
