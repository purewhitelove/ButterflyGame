// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LightningOrbPassiveAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class LightningOrbPassiveAction extends AbstractGameAction
{

    public LightningOrbPassiveAction(DamageInfo info, AbstractOrb orb, boolean hitAll)
    {
        this.info = info;
        this.orb = orb;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE;
        this.hitAll = hitAll;
    }

    public void update()
    {
        if(!hitAll)
        {
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if(m != null)
            {
                float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
                if(Settings.FAST_MODE)
                    speedTime = 0.0F;
                info.output = AbstractOrb.applyLockOn(m, info.base);
                addToTop(new DamageAction(m, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE, true));
                addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
                addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
                if(orb != null)
                    addToTop(new VFXAction(new OrbFlareEffect(orb, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
            }
        } else
        {
            float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
            if(Settings.FAST_MODE)
                speedTime = 0.0F;
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(info.base, true, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m3 = (AbstractMonster)iterator.next();
                if(!m3.isDeadOrEscaped() && !m3.halfDead)
                    addToTop(new VFXAction(new LightningEffect(m3.drawX, m3.drawY), speedTime));
            } while(true);
            addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            if(orb != null)
                addToTop(new VFXAction(new OrbFlareEffect(orb, com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
        }
        isDone = true;
    }

    private DamageInfo info;
    private AbstractOrb orb;
    private boolean hitAll;
}
