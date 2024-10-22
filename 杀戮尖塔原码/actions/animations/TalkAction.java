// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TalkAction.java

package com.megacrit.cardcrawl.actions.animations;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.ArrayList;

public class TalkAction extends AbstractGameAction
{

    public TalkAction(AbstractCreature source, String text, float duration, float bubbleDuration)
    {
        used = false;
        player = false;
        setValues(source, source);
        if(Settings.FAST_MODE)
            this.duration = Settings.ACTION_DUR_MED;
        else
            this.duration = duration;
        msg = text;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
        this.bubbleDuration = bubbleDuration;
        player = false;
    }

    public TalkAction(AbstractCreature source, String text)
    {
        this(source, text, 2.0F, 2.0F);
    }

    public TalkAction(boolean isPlayer, String text, float duration, float bubbleDuration)
    {
        this(((AbstractCreature) (AbstractDungeon.player)), text, duration, bubbleDuration);
        player = isPlayer;
    }

    public void update()
    {
        if(!used)
        {
            if(player)
                AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, bubbleDuration, msg, source.isPlayer));
            else
                AbstractDungeon.effectList.add(new SpeechBubble(source.hb.cX + source.dialogX, source.hb.cY + source.dialogY, bubbleDuration, msg, source.isPlayer));
            used = true;
        }
        tickDuration();
    }

    private String msg;
    private boolean used;
    private float bubbleDuration;
    private boolean player;
}
