// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FruitJuice.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class FruitJuice extends AbstractPotion
{

    public FruitJuice()
    {
        super(potionStrings.NAME, "Fruit Juice", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.HEART, AbstractPotion.PotionColor.FRUIT);
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
        AbstractDungeon.player.increaseMaxHp(potency, true);
    }

    public boolean canUse()
    {
        if(AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            return false;
        return AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);
    }

    public int getPotency(int ascensionLevel)
    {
        return 5;
    }

    public AbstractPotion makeCopy()
    {
        return new FruitJuice();
    }

    public static final String POTION_ID = "Fruit Juice";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Fruit Juice");
    }
}
