// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ToyOrnithopter.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class ToyOrnithopter extends AbstractRelic
{

    public ToyOrnithopter()
    {
        super("Toy Ornithopter", "ornithopter.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(5).append(DESCRIPTIONS[1]).toString();
    }

    public void onUsePotion()
    {
        flash();
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 5));
        } else
        {
            AbstractDungeon.player.heal(5);
        }
    }

    public AbstractRelic makeCopy()
    {
        return new ToyOrnithopter();
    }

    public static final String ID = "Toy Ornithopter";
    public static final int HEAL_AMT = 5;
}
