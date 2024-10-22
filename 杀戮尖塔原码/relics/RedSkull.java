// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RedSkull.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class RedSkull extends AbstractRelic
{

    public RedSkull()
    {
        super("Red Skull", "red_skull.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
        isActive = false;
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        isActive = false;
        addToBot(new AbstractGameAction() {

            public void update()
            {
                if(!isActive && AbstractDungeon.player.isBloodied)
                {
                    flash();
                    pulse = true;
                    AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, 3));
                    addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, RedSkull.this));
                    isActive = true;
                    AbstractDungeon.onModifyPower();
                }
                isDone = true;
            }

            final RedSkull this$0;

            
            {
                this.this$0 = RedSkull.this;
                super();
            }
        }
);
    }

    public void onBloodied()
    {
        flash();
        pulse = true;
        if(!isActive && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            isActive = true;
            AbstractDungeon.player.hand.applyPowers();
        }
    }

    public void onNotBloodied()
    {
        if(isActive && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -3), -3));
        }
        stopPulse();
        isActive = false;
        AbstractDungeon.player.hand.applyPowers();
    }

    public void onVictory()
    {
        pulse = false;
        isActive = false;
    }

    public AbstractRelic makeCopy()
    {
        return new RedSkull();
    }

    public static final String ID = "Red Skull";
    private static final int STR_AMT = 3;
    private boolean isActive;


}
