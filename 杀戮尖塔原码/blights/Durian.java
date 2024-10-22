// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Durian.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class Durian extends AbstractBlight
{

    public Durian()
    {
        super("BlightedDurian", NAME, (new StringBuilder()).append(DESC[0]).append(50).append(DESC[1]).toString(), "durian.png", false);
        counter = 1;
    }

    public void stack()
    {
        counter++;
        AbstractDungeon.player.decreaseMaxHealth(AbstractDungeon.player.maxHealth / 2);
    }

    public void onEquip()
    {
        AbstractDungeon.player.decreaseMaxHealth(AbstractDungeon.player.maxHealth / 2);
    }

    public static final String ID = "BlightedDurian";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("BlightedDurian");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
