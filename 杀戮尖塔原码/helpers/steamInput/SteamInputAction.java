// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SteamInputAction.java

package com.megacrit.cardcrawl.helpers.steamInput;

import com.codedisaster.steamworks.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;

// Referenced classes of package com.megacrit.cardcrawl.helpers.steamInput:
//            SteamInputHelper

public class SteamInputAction
{

    public SteamInputAction(SteamControllerDigitalActionHandle handle, CInputAction actionRef)
    {
        actionData = new SteamControllerDigitalActionData();
        actionHandle = handle;
        ref = actionRef;
    }

    public void init(SteamController controller, SteamControllerHandle controllerHandle)
    {
        this.controller = controller;
        this.controllerHandle = controllerHandle;
    }

    public void update()
    {
        if(controller == null || actionHandle == null)
            return;
        controller.getDigitalActionData(SteamInputHelper.handle, actionHandle, actionData);
        if(actionData.getActive() && actionData.getState())
        {
            if(!ref.pressed)
            {
                ref.pressed = true;
                ref.justPressed = true;
            } else
            {
                if(Settings.CONTROLLER_ENABLED && !Settings.isControllerMode)
                    Settings.isControllerMode = true;
                ref.justPressed = false;
            }
        } else
        if(ref.pressed)
        {
            ref.pressed = false;
            ref.justPressed = false;
        }
    }

    public SteamController controller;
    public SteamControllerHandle controllerHandle;
    public SteamControllerDigitalActionHandle actionHandle;
    public SteamControllerDigitalActionData actionData;
    private CInputAction ref;
}
