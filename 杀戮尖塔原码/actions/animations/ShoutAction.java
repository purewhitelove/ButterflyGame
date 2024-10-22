// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShoutAction.java

package com.megacrit.cardcrawl.actions.animations;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import java.util.ArrayList;

public class ShoutAction extends AbstractGameAction
{

    public ShoutAction(AbstractCreature source, String text, float duration, float bubbleDuration)
    {
        used = false;
        setValues(source, source);
        if(Settings.FAST_MODE)
            this.duration = Settings.ACTION_DUR_MED;
        else
            this.duration = duration;
        msg = text;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
        this.bubbleDuration = bubbleDuration;
    }

    public ShoutAction(AbstractCreature source, String text)
    {
        this(source, text, 0.5F, 3F);
    }

    public void update()
    {
        if(!used)
        {
            AbstractDungeon.effectList.add(new MegaSpeechBubble(source.hb.cX + source.dialogX, source.hb.cY + source.dialogY, bubbleDuration, msg, source.isPlayer));
            used = true;
        }
        tickDuration();
    }

    private String msg;
    private boolean used;
    private float bubbleDuration;
    private static final float DEFAULT_BUBBLE_DUR = 3F;
}
