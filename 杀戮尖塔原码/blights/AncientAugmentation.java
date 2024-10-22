// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AncientAugmentation.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class AncientAugmentation extends AbstractBlight
{

    public AncientAugmentation()
    {
        super("MetallicRebirth", NAME, (new StringBuilder()).append(DESC[0]).append(1).append(DESC[1]).append(10).append(DESC[2]).append(10).append(DESC[3]).toString(), "ancient.png", false);
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
        description = (new StringBuilder()).append(DESC[0]).append(counter).append(DESC[1]).append(counter * 10).append(DESC[2]).append(counter * 10).append(DESC[3]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onCreateEnemy(AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new ArtifactPower(m, counter), counter));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new PlatedArmorPower(m, counter * 10), counter * 10));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, counter * 10), counter * 10));
    }

    public static final String ID = "MetallicRebirth";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("MetallicRebirth");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
