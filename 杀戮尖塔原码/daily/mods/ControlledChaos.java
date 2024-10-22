// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ControlledChaos.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardAtBottomOfDeckAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class ControlledChaos extends AbstractDailyMod
{

    public ControlledChaos()
    {
        super("ControlledChaos", NAME, DESC, "controlled_chaos.png", true);
    }

    public static void modAction()
    {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardAtBottomOfDeckAction(10));
    }

    public static final String ID = "ControlledChaos";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("ControlledChaos");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
