// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlessingOfTheForge.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class BlessingOfTheForge extends AbstractPotion
{

    public BlessingOfTheForge()
    {
        super(potionStrings.NAME, "BlessingOfTheForge", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.ANVIL, AbstractPotion.PotionColor.FIRE);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.UPGRADE.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.UPGRADE.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ArmamentsAction(true));
    }

    public int getPotency(int ascensionLevel)
    {
        return 0;
    }

    public AbstractPotion makeCopy()
    {
        return new BlessingOfTheForge();
    }

    public static final String POTION_ID = "BlessingOfTheForge";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("BlessingOfTheForge");
    }
}
