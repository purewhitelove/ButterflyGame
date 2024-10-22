// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GamblersBrew.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class GamblersBrew extends AbstractPotion
{

    public GamblersBrew()
    {
        super(potionStrings.NAME, "GamblersBrew", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.S, AbstractPotion.PotionColor.SMOKE);
        isThrown = false;
    }

    public void initializeData()
    {
        description = potionStrings.DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        if(!AbstractDungeon.player.hand.isEmpty())
            addToBot(new GamblingChipAction(AbstractDungeon.player, true));
    }

    public int getPotency(int ascensionLevel)
    {
        return 0;
    }

    public AbstractPotion makeCopy()
    {
        return new GamblersBrew();
    }

    public static final String POTION_ID = "GamblersBrew";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("GamblersBrew");
    }
}
