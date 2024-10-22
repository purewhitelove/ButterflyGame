// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RegenPotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class RegenPotion extends AbstractPotion
{

    public RegenPotion()
    {
        super(potionStrings.NAME, "Regen Potion", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.HEART, AbstractPotion.PotionColor.WHITE);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.REGEN.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.REGEN.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, potency), potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 5;
    }

    public AbstractPotion makeCopy()
    {
        return new RegenPotion();
    }

    public static final String POTION_ID = "Regen Potion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Regen Potion");
    }
}
