// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EternalFeather.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class EternalFeather extends AbstractRelic
{

    public EternalFeather()
    {
        super("Eternal Feather", "eternal_feather.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(5).append(DESCRIPTIONS[1]).append(3).append(DESCRIPTIONS[2]).toString();
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if(room instanceof RestRoom)
        {
            flash();
            int amountToGain = (AbstractDungeon.player.masterDeck.size() / 5) * 3;
            AbstractDungeon.player.heal(amountToGain);
        }
    }

    public AbstractRelic makeCopy()
    {
        return new EternalFeather();
    }

    public static final String ID = "Eternal Feather";
}
