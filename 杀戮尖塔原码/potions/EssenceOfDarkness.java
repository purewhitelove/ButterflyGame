// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EssenceOfDarkness.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.defect.EssenceOfDarknessAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class EssenceOfDarkness extends AbstractPotion
{

    public EssenceOfDarkness()
    {
        super(potionStrings.NAME, "EssenceOfDarkness", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.MOON, AbstractPotion.PotionColor.SMOKE);
        labOutlineColor = Settings.BLUE_RELIC_COLOR;
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.CHANNEL.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.CHANNEL.NAMES[0])));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.DARK.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.DARK.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        target = AbstractDungeon.player;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new EssenceOfDarknessAction(potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 1;
    }

    public AbstractPotion makeCopy()
    {
        return new EssenceOfDarkness();
    }

    public static final String POTION_ID = "EssenceOfDarkness";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("EssenceOfDarkness");
    }
}
