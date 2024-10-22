// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeleteSaveConfirmPopup.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import com.megacrit.cardcrawl.vfx.WarningSignEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class DeleteSaveConfirmPopup extends ConfirmPopup
{

    public DeleteSaveConfirmPopup()
    {
        super(D_TEXT[0], D_TEXT[3], com.megacrit.cardcrawl.screens.options.ConfirmPopup.ConfirmType.DELETE_SAVE);
        effects = new ArrayList();
    }

    public void update()
    {
        super.update();
        if(shown && effects.isEmpty())
            effects.add(new WarningSignEffect((float)Settings.WIDTH / 2.0F, Settings.OPTION_Y + 275F * Settings.scale));
        Iterator i = effects.iterator();
        do
        {
            if(!i.hasNext())
                break;
            WarningSignEffect e = (WarningSignEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    public void open(int slot)
    {
        this.slot = slot;
        shown = true;
    }

    public void render(SpriteBatch sb)
    {
        super.render(sb);
        renderWarning(sb);
    }

    private void renderWarning(SpriteBatch sb)
    {
        WarningSignEffect e;
        for(Iterator iterator = effects.iterator(); iterator.hasNext(); e.render(sb))
            e = (WarningSignEffect)iterator.next();

    }

    protected static final String D_TEXT[];
    private ArrayList effects;

    static 
    {
        D_TEXT = CardCrawlGame.languagePack.getUIString("DeletePopup").TEXT;
    }
}
