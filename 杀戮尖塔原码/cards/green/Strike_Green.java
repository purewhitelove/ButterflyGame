// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Strike_Green.java

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

public class Strike_Green extends AbstractCard
{

    public Strike_Green()
    {
        super("Strike_G", cardStrings.NAME, "green/attack/strike", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 6;
        tags.add(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE);
        tags.add(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STARTER_STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(Settings.isDebug)
        {
            if(Settings.isInfo)
            {
                multiDamage = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
                for(int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++)
                    multiDamage[i] = 150;

                addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            } else
            {
                addToBot(new DamageAction(m, new DamageInfo(p, 150, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        } else
        {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Strike_Green();
    }

    public static final String ID = "Strike_G";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_G");
    }
}
