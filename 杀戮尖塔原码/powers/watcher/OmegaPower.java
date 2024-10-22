// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OmegaPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.OmegaFlashEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class OmegaPower extends AbstractPower
{

    public OmegaPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "OmegaPower";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("omega");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer)
        {
            flash();
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(m != null && !m.isDeadOrEscaped())
                    if(Settings.FAST_MODE)
                        addToBot(new VFXAction(new OmegaFlashEffect(m.hb.cX, m.hb.cY)));
                    else
                        addToBot(new VFXAction(new OmegaFlashEffect(m.hb.cX, m.hb.cY), 0.2F));
            } while(true);
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(amount, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, true));
        }
    }

    public static final String POWER_ID = "OmegaPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("OmegaPower");
    }
}
