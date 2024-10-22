// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CInputHelper.java

package com.megacrit.cardcrawl.helpers.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

// Referenced classes of package com.megacrit.cardcrawl.helpers.controller:
//            CInputListener, CInputAction, CInputActionSet

public class CInputHelper
{
    public static final class ControllerModel extends Enum
    {

        public static ControllerModel[] values()
        {
            return (ControllerModel[])$VALUES.clone();
        }

        public static ControllerModel valueOf(String name)
        {
            return (ControllerModel)Enum.valueOf(com/megacrit/cardcrawl/helpers/controller/CInputHelper$ControllerModel, name);
        }

        public static final ControllerModel XBOX_360;
        public static final ControllerModel XBOX_ONE;
        public static final ControllerModel PS3;
        public static final ControllerModel PS4;
        public static final ControllerModel STEAM;
        public static final ControllerModel OTHER;
        private static final ControllerModel $VALUES[];

        static 
        {
            XBOX_360 = new ControllerModel("XBOX_360", 0);
            XBOX_ONE = new ControllerModel("XBOX_ONE", 1);
            PS3 = new ControllerModel("PS3", 2);
            PS4 = new ControllerModel("PS4", 3);
            STEAM = new ControllerModel("STEAM", 4);
            OTHER = new ControllerModel("OTHER", 5);
            $VALUES = (new ControllerModel[] {
                XBOX_360, XBOX_ONE, PS3, PS4, STEAM, OTHER
            });
        }

        private ControllerModel(String s, int i)
        {
            super(s, i);
        }
    }


    public CInputHelper()
    {
    }

    public static void loadSettings()
    {
        CInputActionSet.load();
    }

    public static void initializeIfAble()
    {
        if(!initializedController && Display.isActive())
        {
            initializedController = true;
            logger.info("[CONTROLLER] Utilizing DirectInput");
            try
            {
                controllers = Controllers.getControllers();
            }
            catch(Exception e)
            {
                logger.info(e.getMessage());
            }
            if(controllers == null)
            {
                logger.info("[ERROR] getControllers() returned null");
                return;
            }
            for(int i = 0; i < controllers.size; i++)
                logger.info((new StringBuilder()).append("CONTROLLER[").append(i).append("] ").append(((Controller)controllers.get(i)).getName()).toString());

            if(controllers.size != 0)
            {
                Settings.isControllerMode = true;
                Settings.isTouchScreen = false;
                listener = new CInputListener();
                controller = (Controller)controllers.first();
                controller.addListener(listener);
                if(controller.getName().contains("360"))
                {
                    model = ControllerModel.XBOX_360;
                    ImageMaster.loadControllerImages(ControllerModel.XBOX_360);
                } else
                if(controller.getName().contains("Xbox One"))
                {
                    model = ControllerModel.XBOX_ONE;
                    ImageMaster.loadControllerImages(ControllerModel.XBOX_ONE);
                } else
                {
                    model = ControllerModel.XBOX_360;
                    ImageMaster.loadControllerImages(ControllerModel.XBOX_360);
                }
            } else
            {
                logger.info("[CONTROLLER] No controllers detected");
            }
        }
    }

    public static void setController(Controller controllerToUse)
    {
        Settings.isControllerMode = true;
        Settings.isTouchScreen = false;
        controller = controllerToUse;
        controller.addListener(listener);
        Controllers.removeListener(listener);
    }

    public static void setCursor(Hitbox hb)
    {
        if(hb != null)
        {
            int y = Settings.HEIGHT - (int)hb.cY;
            if(y < 0)
                y = 0;
            else
            if(y > Settings.HEIGHT)
                y = Settings.HEIGHT;
            Gdx.input.setCursorPosition((int)hb.cX, y);
        }
    }

    public static void updateLast()
    {
        for(Iterator iterator = actions.iterator(); iterator.hasNext();)
        {
            CInputAction a = (CInputAction)iterator.next();
            a.justPressed = false;
            a.justReleased = false;
        }

    }

    public static boolean listenerPress(int keycode)
    {
        Iterator iterator = actions.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CInputAction a = (CInputAction)iterator.next();
            if(a.keycode != keycode)
                continue;
            a.justPressed = true;
            a.pressed = true;
            break;
        } while(true);
        return false;
    }

    public static boolean listenerRelease(int keycode)
    {
        Iterator iterator = actions.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CInputAction a = (CInputAction)iterator.next();
            if(a.keycode != keycode)
                continue;
            a.justReleased = true;
            a.pressed = false;
            break;
        } while(true);
        return false;
    }

    public static boolean isJustPressed(int keycode)
    {
        for(Iterator iterator = actions.iterator(); iterator.hasNext();)
        {
            CInputAction a = (CInputAction)iterator.next();
            if(a.keycode == keycode)
                return a.justPressed;
        }

        return false;
    }

    public static boolean isJustReleased(int keycode)
    {
        for(Iterator iterator = actions.iterator(); iterator.hasNext();)
        {
            CInputAction a = (CInputAction)iterator.next();
            if(a.keycode == keycode)
                return a.justReleased;
        }

        return false;
    }

    public static void regainInputFocus()
    {
        CInputListener.remapping = false;
    }

    public static boolean isTopPanelActive()
    {
        return AbstractDungeon.topPanel.selectPotionMode || AbstractDungeon.player.viewingRelics || !AbstractDungeon.topPanel.potionUi.isHidden;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/controller/CInputHelper.getName());
    public static Array controllers = null;
    public static Controller controller = null;
    public static ArrayList actions = new ArrayList();
    public static CInputListener listener = null;
    private static boolean initializedController = false;
    public static ControllerModel model = null;

}
