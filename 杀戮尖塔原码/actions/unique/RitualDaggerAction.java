// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RitualDaggerAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.*;

public class RitualDaggerAction extends AbstractGameAction
{

    public RitualDaggerAction(AbstractCreature target, DamageInfo info, int incAmount, UUID targetUUID)
    {
        this.info = info;
        setValues(target, info);
        increaseAmount = incAmount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        duration = 0.1F;
        uuid = targetUUID;
    }

    public void update()
    {
        if(duration == 0.1F && target != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            target.damage(info);
            if((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion"))
            {
                Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractCard c = (AbstractCard)iterator.next();
                    if(c.uuid.equals(uuid))
                    {
                        c.misc += increaseAmount;
                        c.applyPowers();
                        c.baseDamage = c.misc;
                        c.isDamageModified = false;
                    }
                } while(true);
                for(Iterator iterator1 = GetAllInBattleInstances.get(uuid).iterator(); iterator1.hasNext();)
                {
                    AbstractCard c = (AbstractCard)iterator1.next();
                    c.misc += increaseAmount;
                    c.applyPowers();
                    c.baseDamage = c.misc;
                }

            }
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }

    private int increaseAmount;
    private DamageInfo info;
    private UUID uuid;
}
