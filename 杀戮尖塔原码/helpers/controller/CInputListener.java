// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CInputListener.java

package com.megacrit.cardcrawl.helpers.controller;

import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.math.Vector3;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.options.RemapInputElement;
import com.megacrit.cardcrawl.screens.options.RemapInputElementListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers.controller:
//            CInputAction, CInputHelper

public class CInputListener
    implements ControllerListener
{

    public CInputListener()
    {
    }

    public static void listenForRemap(RemapInputElement e)
    {
        element = e;
        remapping = true;
    }

    public void connected(Controller controller)
    {
        logger.info((new StringBuilder()).append("CONNECTED: ").append(controller.getName()).toString());
    }

    public void disconnected(Controller controller)
    {
        logger.info((new StringBuilder()).append("DISCONNECTED: ").append(controller.getName()).toString());
    }

    public boolean buttonDown(Controller controller, int buttonCode)
    {
        if(!Settings.CONTROLLER_ENABLED)
            return false;
        if(remapping && element.cAction != null)
        {
            if(element.listener.willRemapController(element, element.cAction.keycode, buttonCode))
                element.cAction.remap(buttonCode);
            remapping = false;
            element.hasInputFocus = false;
            InputHelper.regainInputFocus();
            CInputHelper.regainInputFocus();
            element = null;
            return false;
        }
        if(!Settings.isControllerMode)
        {
            Settings.isControllerMode = true;
            Settings.isTouchScreen = false;
        }
        CInputHelper.listenerPress(buttonCode);
        return false;
    }

    public boolean buttonUp(Controller controller, int buttonCode)
    {
        CInputHelper.listenerRelease(buttonCode);
        return false;
    }

    public boolean axisMoved(Controller controller, int axisCode, float value)
    {
        if(!Settings.CONTROLLER_ENABLED)
            return false;
        if(remapping && element.cAction != null)
        {
            if(value > 0.5F && axisValues[axisCode] < 0.5F)
            {
                if(element.listener.willRemapController(element, element.cAction.keycode, 1000 + axisCode))
                    element.cAction.remap(1000 + axisCode);
                remapping = false;
                element.hasInputFocus = false;
                InputHelper.regainInputFocus();
                CInputHelper.regainInputFocus();
                element = null;
                return false;
            }
            if(value < -0.5F && axisValues[axisCode] > -0.5F)
            {
                if(element.listener.willRemapController(element, element.cAction.keycode, -1000 - axisCode))
                    element.cAction.remap(-1000 - axisCode);
                remapping = false;
                element.hasInputFocus = false;
                InputHelper.regainInputFocus();
                CInputHelper.regainInputFocus();
                element = null;
                return false;
            }
        }
        if(value > 0.5F && axisValues[axisCode] < 0.5F)
        {
            CInputHelper.listenerPress(1000 + axisCode);
            if(!Settings.isControllerMode)
            {
                Settings.isControllerMode = true;
                Settings.isTouchScreen = false;
                logger.info("Entering controller mode: AXIS moved");
            }
        } else
        if(value < -0.5F && axisValues[axisCode] > -0.5F)
        {
            CInputHelper.listenerPress(-1000 - axisCode);
            if(!Settings.isControllerMode)
            {
                Settings.isControllerMode = true;
                Settings.isTouchScreen = false;
                logger.info("Entering controller mode: AXIS moved");
            }
        }
        axisValues[axisCode] = value;
        return false;
    }

    public boolean povMoved(Controller controller, int povCode, PovDirection value)
    {
        if(!Settings.CONTROLLER_ENABLED)
            return false;
        static class _cls1
        {

            static final int $SwitchMap$com$badlogic$gdx$controllers$PovDirection[];

            static 
            {
                $SwitchMap$com$badlogic$gdx$controllers$PovDirection = new int[PovDirection.values().length];
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.north.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.south.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.northWest.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.southWest.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.west.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.northEast.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.southEast.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$badlogic$gdx$controllers$PovDirection[PovDirection.east.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
            }
        }

        if(remapping && element.cAction != null)
        {
            int code = -2000;
            switch(_cls1..SwitchMap.com.badlogic.gdx.controllers.PovDirection[value.ordinal()])
            {
            case 1: // '\001'
                code = -2000;
                break;

            case 2: // '\002'
                code = 2000;
                break;

            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
                code = -2001;
                break;

            case 6: // '\006'
            case 7: // '\007'
            case 8: // '\b'
                code = 2001;
                break;

            default:
                code = -2000;
                break;
            }
            if(element.listener.willRemapController(element, element.cAction.keycode, code))
                element.cAction.remap(code);
            remapping = false;
            element.hasInputFocus = false;
            InputHelper.regainInputFocus();
            CInputHelper.regainInputFocus();
            element = null;
            return false;
        }
        if(!Settings.isControllerMode)
        {
            Settings.isControllerMode = true;
            Settings.isTouchScreen = false;
            logger.info("Entering controller mode: D-Pad pressed");
        }
        switch(_cls1..SwitchMap.com.badlogic.gdx.controllers.PovDirection[value.ordinal()])
        {
        case 1: // '\001'
            CInputHelper.listenerPress(-2000);
            break;

        case 2: // '\002'
            CInputHelper.listenerPress(2000);
            break;

        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
            CInputHelper.listenerPress(-2001);
            break;

        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
            CInputHelper.listenerPress(2001);
            break;
        }
        return false;
    }

    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value)
    {
        return false;
    }

    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value)
    {
        return false;
    }

    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value)
    {
        return false;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/controller/CInputListener.getName());
    private static final float DEADZONE = 0.5F;
    private float axisValues[] = {
        0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F
    };
    public static boolean remapping = false;
    public static RemapInputElement element = null;

}
