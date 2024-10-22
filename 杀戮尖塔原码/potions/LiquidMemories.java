// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LiquidMemories.java

package com.megacrit.cardcrawl.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class LiquidMemories extends AbstractPotion
{

    public LiquidMemories()
    {
        super(potionStrings.NAME, "LiquidMemories", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.EYE, AbstractPotion.PotionEffect.NONE, new Color(0xd74bbff), new Color(0x173095ff), null);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        if(potency == 1)
            description = potionStrings.DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[1]).append(potency).append(potionStrings.DESCRIPTIONS[2]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        addToBot(new BetterDiscardPileToHandAction(potency, 0));
    }

    public int getPotency(int ascensionLevel)
    {
        return 1;
    }

    public AbstractPotion makeCopy()
    {
        return new LiquidMemories();
    }

    public static final String POTION_ID = "LiquidMemories";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("LiquidMemories");
    }
}
