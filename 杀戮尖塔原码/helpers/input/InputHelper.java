// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InputHelper.java

package com.megacrit.cardcrawl.helpers.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers.input:
//            ScrollInputProcessor, InputActionSet, InputAction

public class InputHelper
{

    public InputHelper()
    {
    }

    public static void initialize()
    {
        processor = new ScrollInputProcessor();
        Gdx.input.setInputProcessor(processor);
        logger.info("Setting input processor to Scroller");
        InputActionSet.load();
    }

    public static void regainInputFocus()
    {
        Gdx.input.setInputProcessor(processor);
        ignoreOneCycle = true;
    }

    public static void updateFirst()
    {
        if(ignoreOneCycle)
        {
            ignoreOneCycle = false;
            return;
        }
        if(!Settings.isTouchScreen)
        {
            mX = Gdx.input.getX();
            if(mX > Settings.WIDTH)
                mX = Settings.WIDTH;
            else
            if(mX < 0)
                mX = 0;
            mY = Settings.HEIGHT - Gdx.input.getY();
            if(mY > Settings.HEIGHT)
                mY = Settings.HEIGHT;
            else
            if(mY < 1)
                mY = 1;
        } else
        {
            mX = Gdx.input.getX() + Settings.VERT_LETTERBOX_AMT;
            mY = (Settings.HEIGHT - Gdx.input.getY()) + Settings.HORIZ_LETTERBOX_AMT;
            if(mY < 1)
                mY = 1;
        }
        isMouseDown = Gdx.input.isButtonPressed(0);
        isMouseDown_R = Gdx.input.isButtonPressed(1);
        if(Gdx.input.getDeltaX() != 0 && AbstractDungeon.player != null && AbstractDungeon.player.isInKeyboardMode)
            GameCursor.hidden = false;
        if(!isPrevMouseDown && isMouseDown || touchDown)
        {
            CardCrawlGame.cursor.color.a = 0.7F;
            touchDown = false;
            justClickedLeft = true;
            if(Settings.isControllerMode)
                leaveControllerMode();
            if(Settings.isDebug)
                logger.info((new StringBuilder()).append("Clicked: (").append(mX).append(",").append(mY).append(")").toString());
        } else
        if(isPrevMouseDown && !isMouseDown || touchUp)
        {
            touchUp = false;
            justReleasedClickLeft = true;
        }
        if(!isPrevMouseDown_R && isMouseDown_R)
        {
            justClickedRight = true;
            if(Settings.isControllerMode)
                leaveControllerMode();
        } else
        if(isPrevMouseDown_R && !isMouseDown_R)
            justReleasedClickRight = true;
        pressedEscape = InputActionSet.cancel.isJustPressed();
        isPrevMouseDown_R = isMouseDown_R;
        isPrevMouseDown = isMouseDown;
    }

    private static void leaveControllerMode()
    {
        if(Settings.isConsoleBuild)
        {
            logger.info("ENTERING TOUCH SCREEN MODE");
            Settings.isTouchScreen = true;
        } else
        {
            logger.info("LEAVING CONTROLLER MODE");
            Settings.isTouchScreen = Settings.TOUCHSCREEN_ENABLED;
        }
        Settings.isControllerMode = false;
        GameCursor.hidden = false;
        if(AbstractDungeon.player != null && AbstractDungeon.isPlayerInDungeon())
        {
            AbstractDungeon.player.viewingRelics = false;
            AbstractDungeon.topPanel.selectPotionMode = false;
            AbstractDungeon.player.releaseCard();
        }
    }

    public static void updateLast()
    {
        justClickedLeft = false;
        justClickedRight = false;
        justReleasedClickLeft = false;
        justReleasedClickRight = false;
        scrolledUp = false;
        scrolledDown = false;
    }

    public static AbstractCard getCardSelectedByHotkey(CardGroup cards)
    {
        for(int i = 0; i < InputActionSet.selectCardActions.length && i < cards.size(); i++)
            if(InputActionSet.selectCardActions[i].isJustPressed())
                return (AbstractCard)cards.group.get(i);

        return null;
    }

    public static boolean isShortcutModifierKeyPressed()
    {
        int ai[] = SHORTCUT_MODIFIER_KEYS;
        int i = ai.length;
        for(int j = 0; j < i; j++)
        {
            int keycode = ai[j];
            if(Gdx.input.isKeyPressed(keycode))
                return true;
        }

        return false;
    }

    public static boolean isPasteJustPressed()
    {
        return isShortcutModifierKeyPressed() && Gdx.input.isKeyJustPressed(50);
    }

    public static boolean didMoveMouse()
    {
        return Gdx.input.getDeltaX() != 0 || Gdx.input.getDeltaY() != 0;
    }

    public static void moveCursorToNeutralPosition()
    {
        if(Settings.isTouchScreen && !Settings.isControllerMode)
        {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            CardCrawlGame.cursor.color.a = 0.0F;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/input/InputHelper.getName());
    public static int mX;
    public static int mY;
    public static boolean isMouseDown = false;
    public static boolean isMouseDown_R = false;
    private static boolean isPrevMouseDown = false;
    private static boolean isPrevMouseDown_R = false;
    public static boolean justClickedLeft = false;
    public static boolean justClickedRight = false;
    public static boolean touchDown = false;
    public static boolean touchUp = false;
    public static boolean justReleasedClickLeft = false;
    public static boolean justReleasedClickRight = false;
    public static boolean scrolledUp = false;
    public static boolean scrolledDown = false;
    public static boolean pressedEscape = false;
    private static ScrollInputProcessor processor;
    public static int scrollY = 0;
    private static boolean ignoreOneCycle = false;
    public static final int SHORTCUT_MODIFIER_KEYS[] = {
        129, 130, 63
    };

}
