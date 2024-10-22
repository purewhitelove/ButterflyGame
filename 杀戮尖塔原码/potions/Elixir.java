// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Elixir.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class Elixir extends AbstractPotion
{

    public Elixir()
    {
        super(potionStrings.NAME, "ElixirPotion", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.T, AbstractPotion.PotionColor.WHITE);
        labOutlineColor = Settings.RED_RELIC_COLOR;
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.EXHAUST.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ExhaustAction(false, true, true));
    }

    public int getPotency(int ascensionLevel)
    {
        return 0;
    }

    public AbstractPotion makeCopy()
    {
        return new Elixir();
    }

    public static final String POTION_ID = "ElixirPotion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("ElixirPotion");
    }
}
