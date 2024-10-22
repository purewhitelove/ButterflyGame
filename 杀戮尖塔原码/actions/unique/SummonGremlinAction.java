// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SummonGremlinAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Lethality;
import com.megacrit.cardcrawl.daily.mods.TimeDilation;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SummonGremlinAction extends AbstractGameAction
{

    public SummonGremlinAction(AbstractMonster gremlins[])
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        if(Settings.FAST_MODE)
            startDuration = Settings.ACTION_DUR_FAST;
        else
            startDuration = Settings.ACTION_DUR_LONG;
        duration = startDuration;
        int slot = identifySlot(gremlins);
        if(slot == -1)
        {
            logger.info("INCORRECTLY ATTEMPTED TO CHANNEL GREMLIN.");
            return;
        }
        m = getRandomGremlin(slot);
        gremlins[slot] = m;
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onSpawnMonster(m))
            r = (AbstractRelic)iterator.next();

    }

    private int identifySlot(AbstractMonster gremlins[])
    {
        for(int i = 0; i < gremlins.length; i++)
            if(gremlins[i] == null || gremlins[i].isDying)
                return i;

        return -1;
    }

    private AbstractMonster getRandomGremlin(int slot)
    {
        ArrayList pool = new ArrayList();
        pool.add("GremlinWarrior");
        pool.add("GremlinWarrior");
        pool.add("GremlinThief");
        pool.add("GremlinThief");
        pool.add("GremlinFat");
        pool.add("GremlinFat");
        pool.add("GremlinTsundere");
        pool.add("GremlinWizard");
        float x;
        float y;
        switch(slot)
        {
        case 0: // '\0'
            x = GremlinLeader.POSX[0];
            y = GremlinLeader.POSY[0];
            break;

        case 1: // '\001'
            x = GremlinLeader.POSX[1];
            y = GremlinLeader.POSY[1];
            break;

        case 2: // '\002'
            x = GremlinLeader.POSX[2];
            y = GremlinLeader.POSY[2];
            break;

        default:
            x = GremlinLeader.POSX[0];
            y = GremlinLeader.POSY[0];
            break;
        }
        return MonsterHelper.getGremlin((String)pool.get(AbstractDungeon.aiRng.random(0, pool.size() - 1)), x, y);
    }

    private int getSmartPosition()
    {
        int position = 0;
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster mo = (AbstractMonster)iterator.next();
            if(m.drawX <= mo.drawX)
                break;
            position++;
        } while(true);
        return position;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            m.animX = 1200F * Settings.xScale;
            m.init();
            m.applyPowers();
            AbstractDungeon.getCurrRoom().monsters.addMonster(getSmartPosition(), m);
            if(ModHelper.isModEnabled("Lethality"))
                addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 3), 3));
            if(ModHelper.isModEnabled("Time Dilation"))
                addToBot(new ApplyPowerAction(m, m, new SlowPower(m, 0)));
            addToBot(new ApplyPowerAction(m, m, new MinionPower(m)));
        }
        tickDuration();
        if(isDone)
        {
            m.animX = 0.0F;
            m.showHealthBar();
            m.usePreBattleAction();
        } else
        {
            m.animX = Interpolation.fade.apply(0.0F, 1200F * Settings.xScale, duration);
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/actions/unique/SummonGremlinAction.getName());
    private AbstractMonster m;

}
