// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CunningPotion.java

package com.megacrit.cardcrawl.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class CunningPotion extends AbstractPotion
{

    public CunningPotion()
    {
        super(potionStrings.NAME, "CunningPotion", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.SPIKY, AbstractPotion.PotionEffect.NONE, Color.GRAY, Color.DARK_GRAY, null);
        labOutlineColor = Settings.GREEN_RELIC_COLOR;
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
        AbstractCard shiv = new Shiv();
        shiv.upgrade();
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new MakeTempCardInHandAction(shiv.makeStatEquivalentCopy(), potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 3;
    }

    public AbstractPotion makeCopy()
    {
        return new CunningPotion();
    }

    public static final String POTION_ID = "CunningPotion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("CunningPotion");
    }
}
