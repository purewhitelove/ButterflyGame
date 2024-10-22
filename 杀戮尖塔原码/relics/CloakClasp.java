// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CloakClasp.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class CloakClasp extends AbstractRelic
{

    public CloakClasp()
    {
        super("CloakClasp", "clasp.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void onPlayerEndTurn()
    {
        if(!AbstractDungeon.player.hand.group.isEmpty())
        {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, null, AbstractDungeon.player.hand.group.size() * 1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new CloakClasp();
    }

    public static final String ID = "CloakClasp";
}
