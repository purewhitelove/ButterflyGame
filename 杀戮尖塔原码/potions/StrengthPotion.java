// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrengthPotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class StrengthPotion extends AbstractPotion
{

    public StrengthPotion()
    {
        super(potionStrings.NAME, "Strength Potion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.S, AbstractPotion.PotionColor.STRENGTH);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        target = AbstractDungeon.player;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, potency), potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 2;
    }

    public AbstractPotion makeCopy()
    {
        return new StrengthPotion();
    }

    public static final String POTION_ID = "Strength Potion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Strength Potion");
    }
}
