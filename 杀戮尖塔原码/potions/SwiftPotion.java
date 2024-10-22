// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SwiftPotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class SwiftPotion extends AbstractPotion
{

    public SwiftPotion()
    {
        super(potionStrings.NAME, "Swift Potion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.H, AbstractPotion.PotionColor.SWIFT);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[1]).append(potency).append(potionStrings.DESCRIPTIONS[2]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        addToBot(new DrawCardAction(AbstractDungeon.player, potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 3;
    }

    public AbstractPotion makeCopy()
    {
        return new SwiftPotion();
    }

    public static final String POTION_ID = "Swift Potion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Swift Potion");
    }
}
