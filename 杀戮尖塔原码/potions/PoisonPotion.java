// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PoisonPotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.PoisonPower;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class PoisonPotion extends AbstractPotion
{

    public PoisonPotion()
    {
        super(potionStrings.NAME, "Poison Potion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.M, AbstractPotion.PotionColor.POISON);
        labOutlineColor = Settings.GREEN_RELIC_COLOR;
        isThrown = true;
        targetRequired = true;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.POISON.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.POISON.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new PoisonPower(target, AbstractDungeon.player, potency), potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 6;
    }

    public AbstractPotion makeCopy()
    {
        return new PoisonPotion();
    }

    public static final String POTION_ID = "Poison Potion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Poison Potion");
    }
}
