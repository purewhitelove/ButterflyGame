// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FearPotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class FearPotion extends AbstractPotion
{

    public FearPotion()
    {
        super(potionStrings.NAME, "FearPotion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.H, AbstractPotion.PotionColor.FEAR);
        isThrown = true;
        targetRequired = true;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.VULNERABLE.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.VULNERABLE.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, potency, false), potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 3;
    }

    public AbstractPotion makeCopy()
    {
        return new FearPotion();
    }

    public static final String POTION_ID = "FearPotion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("FearPotion");
    }
}
