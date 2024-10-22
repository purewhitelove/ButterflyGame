// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RecallOption.java

package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.campfire.CampfireRecallEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.campfire:
//            AbstractCampfireOption

public class RecallOption extends AbstractCampfireOption
{

    public RecallOption()
    {
        label = TEXT[0];
        description = TEXT[1];
        img = ImageMaster.CAMPFIRE_RECALL_BUTTON;
    }

    public void useOption()
    {
        AbstractDungeon.effectList.add(new CampfireRecallEffect());
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Recall Option");
        TEXT = uiStrings.TEXT;
    }
}
