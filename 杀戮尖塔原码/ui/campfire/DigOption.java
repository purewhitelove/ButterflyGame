// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DigOption.java

package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.campfire.CampfireDigEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.campfire:
//            AbstractCampfireOption

public class DigOption extends AbstractCampfireOption
{

    public DigOption()
    {
        label = TEXT[0];
        description = TEXT[1];
        img = ImageMaster.CAMPFIRE_DIG_BUTTON;
    }

    public void useOption()
    {
        AbstractDungeon.effectList.add(new CampfireDigEffect());
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Dig Option");
        TEXT = uiStrings.TEXT;
    }
}
