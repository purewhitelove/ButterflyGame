// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IndignationAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.actions.watcher:
//            ChangeStanceAction

public class IndignationAction extends AbstractGameAction
{

    public IndignationAction(int amount)
    {
        this.amount = amount;
    }

    public void update()
    {
        if(AbstractDungeon.player.stance.ID.equals("Wrath"))
        {
            AbstractMonster mo;
            for(Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator.hasNext(); addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, amount, false), amount, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE)))
                mo = (AbstractMonster)iterator.next();

        } else
        {
            addToBot(new ChangeStanceAction("Wrath"));
        }
        isDone = true;
    }
}
