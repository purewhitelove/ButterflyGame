// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PotionSlot.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class PotionSlot extends AbstractPotion
{

    public PotionSlot(int slot)
    {
        super(potionStrings.NAME, "Potion Slot", AbstractPotion.PotionRarity.PLACEHOLDER, AbstractPotion.PotionSize.T, AbstractPotion.PotionColor.NONE);
        isObtained = true;
        description = potionStrings.DESCRIPTIONS[0];
        name = potionStrings.DESCRIPTIONS[1];
        tips.add(new PowerTip(name, description));
        adjustPosition(slot);
    }

    public void use(AbstractCreature abstractcreature)
    {
    }

    public int getPotency(int ascensionLevel)
    {
        return 0;
    }

    public AbstractPotion makeCopy()
    {
        return null;
    }

    public static final String POTION_ID = "Potion Slot";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Potion Slot");
    }
}
