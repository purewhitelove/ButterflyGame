// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IceWallAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import java.util.ArrayList;

public class IceWallAction extends AbstractGameAction
{

    public IceWallAction(int blockAmt, int perOrbAmt)
    {
        duration = Settings.ACTION_DUR_FAST;
        amount = blockAmt;
        this.perOrbAmt = perOrbAmt;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            int count = 0;
            for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
                if(AbstractDungeon.player.orbs.get(i) instanceof Frost)
                    count++;

            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount + count * perOrbAmt));
        }
        tickDuration();
    }

    private int perOrbAmt;
}
