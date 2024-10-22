// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TypeHelper.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.InputProcessor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.panels.RenamePopup;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            SeedHelper, FontHelper

public class TypeHelper
    implements InputProcessor
{

    public TypeHelper()
    {
        seed = false;
    }

    public TypeHelper(boolean seed)
    {
        this.seed = seed;
    }

    public boolean keyDown(int keycode)
    {
        return false;
    }

    public boolean keyUp(int keycode)
    {
        return false;
    }

    public boolean keyTyped(char character)
    {
        String charStr = String.valueOf(character);
        logger.info(charStr);
        if(charStr.length() != 1)
            return false;
        if(seed)
        {
            if(SeedPanel.isFull())
                return false;
            if(InputHelper.isPasteJustPressed())
                return false;
            String converted = SeedHelper.getValidCharacter(charStr, SeedPanel.textField);
            if(converted != null)
                SeedPanel.textField = (new StringBuilder()).append(SeedPanel.textField).append(converted).toString();
        } else
        {
            if(FontHelper.getSmartWidth(FontHelper.cardTitleFont, RenamePopup.textField, 1E+007F, 0.0F, 0.82F) >= 240F * Settings.scale)
                return false;
            if(Character.isDigit(character) || Character.isLetter(character))
                RenamePopup.textField = (new StringBuilder()).append(RenamePopup.textField).append(charStr).toString();
        }
        return true;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int i)
    {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int i)
    {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    public boolean scrolled(int amount)
    {
        return false;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/TypeHelper.getName());
    private boolean seed;

}
