// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkstonePeriapt.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Hoarder;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class DarkstonePeriapt extends AbstractRelic
{

    public DarkstonePeriapt()
    {
        super("Darkstone Periapt", "darkstone.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    public void onObtainCard(AbstractCard card)
    {
        if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE)
        {
            if(ModHelper.isModEnabled("Hoarder"))
            {
                AbstractDungeon.player.increaseMaxHp(6, true);
                AbstractDungeon.player.increaseMaxHp(6, true);
            }
            AbstractDungeon.player.increaseMaxHp(6, true);
        }
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(6).append(LocalizedStrings.PERIOD).toString();
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public AbstractRelic makeCopy()
    {
        return new DarkstonePeriapt();
    }

    public static final String ID = "Darkstone Periapt";
    private static final int HP_AMT = 6;
}
