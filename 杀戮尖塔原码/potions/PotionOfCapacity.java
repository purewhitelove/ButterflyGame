// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PotionOfCapacity.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class PotionOfCapacity extends AbstractPotion
{

    public PotionOfCapacity()
    {
        super(potionStrings.NAME, "PotionOfCapacity", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.BLUE);
        labOutlineColor = Settings.BLUE_RELIC_COLOR;
        isThrown = false;
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
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new IncreaseMaxOrbAction(potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 2;
    }

    public AbstractPotion makeCopy()
    {
        return new PotionOfCapacity();
    }

    public static final String POTION_ID = "PotionOfCapacity";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("PotionOfCapacity");
    }
}
