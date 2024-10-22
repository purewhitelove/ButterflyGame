// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExplosivePotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class ExplosivePotion extends AbstractPotion
{

    public ExplosivePotion()
    {
        super(potionStrings.NAME, "Explosive Potion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.H, AbstractPotion.PotionColor.EXPLOSIVE);
        isThrown = true;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDeadOrEscaped())
                addToBot(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
        } while(true);
        addToBot(new WaitAction(0.5F));
        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(potency, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
    }

    public int getPotency(int ascensionLevel)
    {
        return 10;
    }

    public AbstractPotion makeCopy()
    {
        return new ExplosivePotion();
    }

    public static final String POTION_ID = "Explosive Potion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Explosive Potion");
    }
}
