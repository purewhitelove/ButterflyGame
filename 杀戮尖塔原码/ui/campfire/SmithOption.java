// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmithOption.java

package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.campfire:
//            AbstractCampfireOption

public class SmithOption extends AbstractCampfireOption
{

    public SmithOption(boolean active)
    {
        label = TEXT[0];
        usable = active;
        updateUsability(active);
    }

    public void updateUsability(boolean canUse)
    {
        description = canUse ? TEXT[1] : TEXT[2];
        img = ImageMaster.CAMPFIRE_SMITH_BUTTON;
    }

    public void useOption()
    {
        if(usable)
            AbstractDungeon.effectList.add(new CampfireSmithEffect());
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Smith Option");
        TEXT = uiStrings.TEXT;
    }
}
