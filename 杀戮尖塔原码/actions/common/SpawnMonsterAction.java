// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpawnMonsterAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.daily.mods.Lethality;
import com.megacrit.cardcrawl.daily.mods.TimeDilation;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            ApplyPowerAction

public class SpawnMonsterAction extends AbstractGameAction
{

    public SpawnMonsterAction(AbstractMonster m, boolean isMinion)
    {
        this(m, isMinion, -99);
        useSmartPositioning = true;
    }

    public SpawnMonsterAction(AbstractMonster m, boolean isMinion, int slot)
    {
        used = false;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        duration = 0.1F;
        this.m = m;
        minion = isMinion;
        targetSlot = slot;
        useSmartPositioning = false;
    }

    public void update()
    {
        if(!used)
        {
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onSpawnMonster(m))
                r = (AbstractRelic)iterator.next();

            m.init();
            m.applyPowers();
            if(useSmartPositioning)
            {
                int position = 0;
                Iterator iterator1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    AbstractMonster mo = (AbstractMonster)iterator1.next();
                    if(m.drawX > mo.drawX)
                        position++;
                } while(true);
                AbstractDungeon.getCurrRoom().monsters.addMonster(position, m);
            } else
            {
                AbstractDungeon.getCurrRoom().monsters.addMonster(targetSlot, m);
            }
            m.showHealthBar();
            if(ModHelper.isModEnabled("Lethality"))
                addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 3), 3));
            if(ModHelper.isModEnabled("Time Dilation"))
                addToBot(new ApplyPowerAction(m, m, new SlowPower(m, 0)));
            if(minion)
                addToTop(new ApplyPowerAction(m, m, new MinionPower(m)));
            used = true;
        }
        tickDuration();
    }

    private boolean used;
    private static final float DURATION = 0.1F;
    private AbstractMonster m;
    private boolean minion;
    private int targetSlot;
    private boolean useSmartPositioning;
}
