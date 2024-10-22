// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemapInputElementHeader.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            RemapInputElement

public class RemapInputElementHeader extends RemapInputElement
{

    public RemapInputElementHeader(String commandText, String keyboardText, String controllerText)
    {
        super(null, commandText, null);
        this.keyboardText = keyboardText;
        this.controllerText = controllerText;
        isHeader = true;
    }

    public void update()
    {
    }

    protected String getKeyColumnText()
    {
        return keyboardText;
    }

    protected String getControllerColumnText()
    {
        return controllerText;
    }

    protected Color getTextColor()
    {
        return Settings.GOLD_COLOR;
    }

    public void hoverStarted(Hitbox hitbox1)
    {
    }

    public void startClicking(Hitbox hitbox1)
    {
    }

    public boolean keyDown(int keycode)
    {
        return false;
    }

    private String keyboardText;
    private String controllerText;
}
