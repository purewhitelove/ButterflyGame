// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Careless.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MillAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class Careless extends AbstractDailyMod
{

    public Careless()
    {
        super("Careless", NAME, DESC, "slow_start.png", false);
    }

    public static void modAction()
    {
        AbstractDungeon.actionManager.addToBottom(new MillAction(AbstractDungeon.player, AbstractDungeon.player, 1));
    }

    public static final String ID = "Careless";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Careless");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
