// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CInputAction.java

package com.megacrit.cardcrawl.helpers.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Map;

// Referenced classes of package com.megacrit.cardcrawl.helpers.controller:
//            CInputHelper

public class CInputAction
{

    public CInputAction(int keycode)
    {
        justPressed = false;
        pressed = false;
        justReleased = false;
        this.keycode = keycode;
    }

    public int getKey()
    {
        return keycode;
    }

    public String getKeyString()
    {
        String keycodeStr = com.badlogic.gdx.Input.Keys.toString(keycode);
        return (String)TEXT_CONVERSIONS.getOrDefault(keycodeStr, keycodeStr);
    }

    public Texture getKeyImg()
    {
        switch(keycode)
        {
        case 0: // '\0'
            return ImageMaster.CONTROLLER_A;

        case 1: // '\001'
            return ImageMaster.CONTROLLER_B;

        case 2: // '\002'
            return ImageMaster.CONTROLLER_X;

        case 3: // '\003'
            return ImageMaster.CONTROLLER_Y;

        case 4: // '\004'
            return ImageMaster.CONTROLLER_LB;

        case 5: // '\005'
            return ImageMaster.CONTROLLER_RB;

        case 6: // '\006'
            return ImageMaster.CONTROLLER_BACK;

        case 7: // '\007'
            return ImageMaster.CONTROLLER_START;

        case 8: // '\b'
            return ImageMaster.CONTROLLER_LS;

        case 9: // '\t'
            return ImageMaster.CONTROLLER_RS;

        case 1000: 
            return ImageMaster.CONTROLLER_LS_DOWN;

        case -1000: 
            return ImageMaster.CONTROLLER_LS_UP;

        case 1001: 
            return ImageMaster.CONTROLLER_LS_RIGHT;

        case -1001: 
            return ImageMaster.CONTROLLER_LS_LEFT;

        case 1003: 
            return ImageMaster.CONTROLLER_RS_RIGHT;

        case -1002: 
            return ImageMaster.CONTROLLER_RS_UP;

        case 1002: 
            return ImageMaster.CONTROLLER_RS_DOWN;

        case -1003: 
            return ImageMaster.CONTROLLER_RS_LEFT;

        case 1004: 
            return ImageMaster.CONTROLLER_LT;

        case -1004: 
            return ImageMaster.CONTROLLER_RT;

        case 2000: 
            return ImageMaster.CONTROLLER_D_DOWN;

        case -2000: 
            return ImageMaster.CONTROLLER_D_UP;

        case 2001: 
            return ImageMaster.CONTROLLER_D_RIGHT;

        case -2001: 
            return ImageMaster.CONTROLLER_D_LEFT;
        }
        return ImageMaster.CONTROLLER_A;
    }

    public boolean isJustPressed()
    {
        return justPressed;
    }

    public void unpress()
    {
        justPressed = false;
    }

    public boolean isJustReleased()
    {
        return CInputHelper.isJustReleased(keycode);
    }

    public void remap(int newKeycode)
    {
        keycode = newKeycode;
    }

    public boolean isPressed()
    {
        return pressed;
    }

    private static final UIStrings uiStrings;
    public static final Map TEXT_CONVERSIONS;
    public int keycode;
    public boolean justPressed;
    public boolean pressed;
    public boolean justReleased;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("InputKeyNames");
        TEXT_CONVERSIONS = uiStrings.TEXT_DICT;
    }
}
