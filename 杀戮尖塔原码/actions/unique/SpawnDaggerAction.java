// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpawnDaggerAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Lethality;
import com.megacrit.cardcrawl.daily.mods.TimeDilation;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.PhilosopherStone;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @deprecated Class SpawnDaggerAction is deprecated
 */

public class SpawnDaggerAction extends AbstractGameAction
{

    public SpawnDaggerAction(AbstractMonster monster)
    {
        source = monster;
        duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_XFAST)
        {
            int count = 0;
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(m != source)
                {
                    if(m.isDying)
                    {
                        addToTop(new ApplyPowerAction(m, m, new MinionPower(source)));
                        addToTop(new ReviveMonsterAction(m, source, false));
                        if(AbstractDungeon.player.hasRelic("Philosopher's Stone"))
                        {
                            m.addPower(new StrengthPower(m, 1));
                            AbstractDungeon.onModifyPower();
                        }
                        if(ModHelper.isModEnabled("Lethality"))
                            addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 3), 3));
                        if(ModHelper.isModEnabled("Time Dilation"))
                            addToBot(new ApplyPowerAction(m, m, new SlowPower(m, 0)));
                        tickDuration();
                        return;
                    }
                    count++;
                }
            } while(true);
            if(count == 1)
                addToTop(new SpawnMonsterAction(new SnakeDagger(-220F, 90F), true));
            else
            if(count == 2)
                addToTop(new SpawnMonsterAction(new SnakeDagger(180F, 320F), true));
            else
            if(count == 3)
                addToTop(new SpawnMonsterAction(new SnakeDagger(-250F, 310F), true));
        }
        tickDuration();
    }

    public static final float pos0X = 210F;
    public static final float pos0Y = 50F;
    public static final float pos1X = -220F;
    public static final float pos1Y = 90F;
    private static final float pos2X = 180F;
    private static final float pos2Y = 320F;
    private static final float pos3X = -250F;
    private static final float pos3Y = 310F;
}
