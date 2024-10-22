// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmotionChip.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.defect.ImpulseAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class EmotionChip extends AbstractRelic
{

    public EmotionChip()
    {
        super("Emotion Chip", "emotionChip.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
        pulse = false;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atTurnStart()
    {
        if(pulse)
        {
            pulse = false;
            flash();
            addToBot(new ImpulseAction());
        }
    }

    public void wasHPLost(int damageAmount)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && damageAmount > 0)
        {
            flash();
            if(!pulse)
            {
                beginPulse();
                pulse = true;
            }
        }
    }

    public void onVictory()
    {
        pulse = false;
    }

    public AbstractRelic makeCopy()
    {
        return new EmotionChip();
    }

    public static final String ID = "Emotion Chip";
}
