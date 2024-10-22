// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Ambrosia.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.DivinityStance;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class Ambrosia extends AbstractPotion
{

    public Ambrosia()
    {
        super(potionStrings.NAME, "Ambrosia", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.EYE, AbstractPotion.PotionColor.WEAK);
        labOutlineColor = Settings.PURPLE_RELIC_COLOR;
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STANCE.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.STANCE.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ChangeStanceAction("Divinity"));
    }

    public int getPotency(int ascensionLevel)
    {
        return 2;
    }

    public AbstractPotion makeCopy()
    {
        return new Ambrosia();
    }

    public static final String POTION_ID = "Ambrosia";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Ambrosia");
    }
}
