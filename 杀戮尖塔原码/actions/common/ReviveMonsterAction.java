// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReviveMonsterAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PhilosopherStone;
import com.megacrit.cardcrawl.vfx.TintEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ReviveMonsterAction extends AbstractGameAction
{

    public ReviveMonsterAction(AbstractMonster target, AbstractCreature source, boolean healEffect)
    {
        setValues(target, source, 0);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        if(AbstractDungeon.player.hasRelic("Philosopher's Stone"))
            target.addPower(new StrengthPower(target, 1));
        healingEffect = healEffect;
    }

    public void update()
    {
        if(duration == 0.5F && (target instanceof AbstractMonster))
        {
            target.isDying = false;
            target.heal(target.maxHealth, healingEffect);
            target.healthBarRevivedEvent();
            ((AbstractMonster)target).deathTimer = 0.0F;
            ((AbstractMonster)target).tint = new TintEffect();
            ((AbstractMonster)target).tintFadeOutCalled = false;
            ((AbstractMonster)target).isDead = false;
            target.powers.clear();
            if(target instanceof SnakeDagger)
            {
                ((SnakeDagger)target).firstMove = true;
                ((SnakeDagger)target).initializeAnimation();
            }
            if(target instanceof AbstractMonster)
            {
                AbstractRelic r;
                for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onSpawnMonster((AbstractMonster)target))
                    r = (AbstractRelic)iterator.next();

            }
            ((AbstractMonster)target).intent = com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.NONE;
            ((AbstractMonster)target).rollMove();
        }
        tickDuration();
    }

    private boolean healingEffect;
}
