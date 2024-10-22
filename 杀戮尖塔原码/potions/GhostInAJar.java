// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GhostInAJar.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class GhostInAJar extends AbstractPotion
{

    public GhostInAJar()
    {
        super(potionStrings.NAME, "GhostInAJar", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.GHOST, AbstractPotion.PotionColor.WHITE);
        labOutlineColor = Settings.GREEN_RELIC_COLOR;
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.INTANGIBLE.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.INTANGIBLE.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        target = AbstractDungeon.player;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new IntangiblePlayerPower(target, potency), potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 1;
    }

    public AbstractPotion makeCopy()
    {
        return new GhostInAJar();
    }

    public static final String POTION_ID = "GhostInAJar";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("GhostInAJar");
    }
}
