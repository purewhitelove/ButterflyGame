// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DuplicationPotion.java

package com.megacrit.cardcrawl.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class DuplicationPotion extends AbstractPotion
{

    public DuplicationPotion()
    {
        super(potionStrings.NAME, "DuplicationPotion", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.CARD, AbstractPotion.PotionEffect.RAINBOW, Color.WHITE, null, null);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        if(AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic("SacredBark"))
            description = potionStrings.DESCRIPTIONS[0];
        else
            description = potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DuplicationPower(AbstractDungeon.player, potency), potency));
    }

    public int getPotency(int ascensionLevel)
    {
        return 1;
    }

    public AbstractPotion makeCopy()
    {
        return new DuplicationPotion();
    }

    public static final String POTION_ID = "DuplicationPotion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("DuplicationPotion");
    }
}
