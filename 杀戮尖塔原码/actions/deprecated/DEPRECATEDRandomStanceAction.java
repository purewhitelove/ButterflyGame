// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDRandomStanceAction.java

package com.megacrit.cardcrawl.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DEPRECATEDRandomStanceAction extends AbstractGameAction
{

    public DEPRECATEDRandomStanceAction()
    {
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            ArrayList stances = new ArrayList();
            AbstractStance oldStance = AbstractDungeon.player.stance;
            if(!oldStance.ID.equals("Wrath"))
                stances.add(new WrathStance());
            if(!oldStance.ID.equals("Calm"))
                stances.add(new CalmStance());
            Collections.shuffle(stances, new Random(AbstractDungeon.cardRandomRng.randomLong()));
            addToBot(new ChangeStanceAction(((AbstractStance)stances.get(0)).ID));
            if(Settings.FAST_MODE)
            {
                isDone = true;
                return;
            }
        }
        tickDuration();
    }
}
