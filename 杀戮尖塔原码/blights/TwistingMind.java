// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TwistingMind.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class TwistingMind extends AbstractBlight
{

    public TwistingMind()
    {
        super("TwistingMind", NAME, (new StringBuilder()).append(DESC[0]).append(1).append(DESC[1]).toString(), "twist.png", false);
        counter = 1;
    }

    public void stack()
    {
        counter++;
        updateDescription();
        flash();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESC[0]).append(counter).append(DESC[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onPlayerEndTurn()
    {
        flash();
        CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        group.addToBottom(new Slimed());
        group.addToBottom(new VoidCard());
        group.addToBottom(new Burn());
        group.addToBottom(new Dazed());
        group.addToBottom(new Wound());
        group.shuffle();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(group.getBottomCard(), counter, false, true));
        group.clear();
    }

    public static final String ID = "TwistingMind";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("TwistingMind");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
