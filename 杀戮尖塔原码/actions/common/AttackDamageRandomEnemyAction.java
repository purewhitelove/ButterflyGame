// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttackDamageRandomEnemyAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            DamageAction

public class AttackDamageRandomEnemyAction extends AbstractGameAction
{

    public AttackDamageRandomEnemyAction(AbstractCard card, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this.card = card;
        this.effect = effect;
    }

    public AttackDamageRandomEnemyAction(AbstractCard card)
    {
        this(card, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
    }

    public void update()
    {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(target != null)
        {
            card.calculateCardDamage((AbstractMonster)target);
            if(com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.LIGHTNING == effect)
            {
                addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
                addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
                addToTop(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
            } else
            {
                addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), effect));
            }
        }
        isDone = true;
    }

    private AbstractCard card;
    private com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect;
}
