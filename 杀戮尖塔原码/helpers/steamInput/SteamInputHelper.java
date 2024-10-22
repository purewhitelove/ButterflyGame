// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SteamInputHelper.java

package com.megacrit.cardcrawl.helpers.steamInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.codedisaster.steamworks.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers.steamInput:
//            SteamInputDetect, SteamInputAction, SteamInputActionSet

public class SteamInputHelper
{

    public SteamInputHelper()
    {
        if(!SteamAPI.isSteamRunning())
        {
            logger.info("Steam isn't running? SteamInput is disabled.");
            return;
        }
        controller = new SteamController();
        alive = controller.init();
        if(!alive)
        {
            logger.info("Steam controller failed to initialize.");
            return;
        } else
        {
            controllerHandles = new SteamControllerHandle[16];
            logger.info("Starting input detection...");
            Thread controllerDetectThread = new Thread(new SteamInputDetect());
            CardCrawlGame.sInputDetectThread = controllerDetectThread;
            controllerDetectThread.setName("InputDetect");
            controllerDetectThread.start();
            model = com.megacrit.cardcrawl.helpers.controller.CInputHelper.ControllerModel.XBOX_ONE;
            ImageMaster.loadControllerImages(com.megacrit.cardcrawl.helpers.controller.CInputHelper.ControllerModel.XBOX_ONE);
            return;
        }
    }

    public static void initActions(SteamControllerHandle controllerHandle)
    {
        handle = controllerHandle;
        SteamInputActionSet.load(controller);
        SteamInputAction a;
        for(Iterator iterator = actions.iterator(); iterator.hasNext(); a.init(controller, handle))
            a = (SteamInputAction)iterator.next();

    }

    public static void updateFirst()
    {
        controller.runFrame();
        SteamInputAction a;
        for(Iterator iterator = actions.iterator(); iterator.hasNext(); a.update())
            a = (SteamInputAction)iterator.next();

    }

    public static void setCursor(Hitbox hb)
    {
        if(hb != null)
            Gdx.input.setCursorPosition((int)hb.cX, Settings.HEIGHT - (int)hb.cY);
    }

    public static boolean isJustPressed(int keycode)
    {
        return false;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/steamInput/SteamInputHelper.getName());
    public static Array controllers = null;
    public static ArrayList actions = new ArrayList();
    public static com.megacrit.cardcrawl.helpers.controller.CInputHelper.ControllerModel model = null;
    public static boolean alive = false;
    public static SteamController controller;
    public static SteamControllerHandle controllerHandles[];
    public static SteamControllerHandle handle;
    public static int numControllers = 0;
    public static SteamControllerActionSetHandle setHandle;

}
