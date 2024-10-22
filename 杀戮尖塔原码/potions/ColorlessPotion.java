// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColorlessPotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.relics.SacredBark;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class ColorlessPotion extends AbstractPotion
{

    public ColorlessPotion()
    {
        super(potionStrings.NAME, "ColorlessPotion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.CARD, AbstractPotion.PotionColor.WHITE);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        if(AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic("SacredBark"))
            description = potionStrings.DESCRIPTIONS[0];
        else
            description = potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        addToBot(new DiscoveryAction(true, potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 1;
    }

    public AbstractPotion makeCopy()
    {
        return new ColorlessPotion();
    }

    public static final String POTION_ID = "ColorlessPotion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("ColorlessPotion");
    }
}
