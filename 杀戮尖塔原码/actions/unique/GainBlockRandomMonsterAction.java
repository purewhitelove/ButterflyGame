// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainBlockRandomMonsterAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class GainBlockRandomMonsterAction extends AbstractGameAction
{

    public GainBlockRandomMonsterAction(AbstractCreature source, int amount)
    {
        duration = 0.5F;
        this.source = source;
        this.amount = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            ArrayList validMonsters = new ArrayList();
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(m != source && m.intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE && !m.isDying)
                    validMonsters.add(m);
            } while(true);
            if(!validMonsters.isEmpty())
                target = (AbstractCreature)validMonsters.get(AbstractDungeon.aiRng.random(validMonsters.size() - 1));
            else
                target = source;
            if(target != null)
            {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SHIELD));
                target.addBlock(amount);
            }
        }
        tickDuration();
    }
}
