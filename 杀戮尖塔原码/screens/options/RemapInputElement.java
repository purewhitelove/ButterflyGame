// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemapInputElement.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            RemapInputElementListener

public class RemapInputElement
    implements HitboxListener, InputProcessor
{

    public RemapInputElement(RemapInputElementListener listener, String label, InputAction action)
    {
        this(listener, label, action, null);
    }

    public RemapInputElement(RemapInputElementListener listener, String label, InputAction action, CInputAction controllerAction)
    {
        hasInputFocus = false;
        isHidden = false;
        isHeader = false;
        hb = new Hitbox(ROW_WIDTH, ROW_HEIGHT);
        labelText = label;
        this.action = action;
        cAction = controllerAction;
        this.listener = listener;
    }

    public void move(float x, float y)
    {
        hb.move(x, y);
    }

    public void update()
    {
        if(isHidden)
            return;
        hb.encapsulatedUpdate(this);
        if(hasInputFocus && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()))
        {
            CInputActionSet.select.unpress();
            logger.info("Lose focus");
            hasInputFocus = false;
            InputHelper.regainInputFocus();
            CInputHelper.regainInputFocus();
        }
    }

    public void render(SpriteBatch sb)
    {
        if(isHidden)
            return;
        sb.setBlendFunction(770, 1);
        sb.setColor(getRowBgColor());
        sb.draw(ImageMaster.INPUT_SETTINGS_ROW, hb.x, hb.y, ROW_WIDTH, ROW_RENDER_HEIGHT);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE);
        Color textColor = getTextColor();
        float textY = hb.cY + ROW_TEXT_Y_OFFSET;
        float textX = hb.x + ROW_TEXT_LEADING_OFFSET;
        FontHelper.renderFont(sb, FontHelper.topPanelInfoFont, labelText, textX, textY, textColor);
        textX += KEYBOARD_COLUMN_X_OFFSET;
        FontHelper.renderFont(sb, FontHelper.topPanelInfoFont, getKeyColumnText(), textX, textY, textColor);
        textX += CONTROLLER_COLUMN_X_OFFSET;
        if(!isHeader)
        {
            Texture img = getControllerColumnImg();
            if(img != null)
                sb.draw(img, textX - 32F, textY - 32F - 10F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 64, 64, false, false);
        } else
        {
            FontHelper.renderFont(sb, FontHelper.topPanelInfoFont, getControllerColumnText(), textX, textY, textColor);
        }
        hb.render(sb);
    }

    protected Color getRowBgColor()
    {
        Color bgColor = ROW_BG_COLOR;
        if(hasInputFocus)
            bgColor = ROW_SELECT_COLOR;
        else
        if(hb.hovered)
            bgColor = ROW_HOVER_COLOR;
        return bgColor;
    }

    protected Color getTextColor()
    {
        Color color = TEXT_DEFAULT_COLOR;
        if(hasInputFocus)
            color = TEXT_FOCUSED_COLOR;
        else
        if(hb.hovered)
            color = TEXT_HOVERED_COLOR;
        return color;
    }

    protected String getKeyColumnText()
    {
        return action == null ? "" : action.getKeyString();
    }

    protected String getControllerColumnText()
    {
        return cAction == null ? "" : cAction.getKeyString();
    }

    protected Texture getControllerColumnImg()
    {
        Texture retVal = null;
        if(cAction != null)
            retVal = cAction.getKeyImg();
        return retVal;
    }

    public void hoverStarted(Hitbox hitbox)
    {
        CardCrawlGame.sound.play("UI_HOVER");
    }

    public void startClicking(Hitbox hitbox)
    {
        CardCrawlGame.sound.play("UI_CLICK_1");
    }

    public void clicked(Hitbox hitbox)
    {
        logger.info("BEGIN REMAPPING...");
        listener.didStartRemapping(this);
        Gdx.input.setInputProcessor(this);
        CInputListener.listenForRemap(this);
        hasInputFocus = true;
    }

    public boolean keyDown(int keycode)
    {
        if(action != null && listener.willRemap(this, action.getKey(), keycode))
            action.remap(keycode);
        hasInputFocus = false;
        InputHelper.regainInputFocus();
        CInputHelper.regainInputFocus();
        return true;
    }

    public boolean keyUp(int keycode)
    {
        return false;
    }

    public boolean keyTyped(char character)
    {
        return false;
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
        if(amount == -1)
            InputHelper.scrolledUp = true;
        else
        if(amount == 1)
            InputHelper.scrolledDown = true;
        return false;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/options/RemapInputElement.getName());
    public Hitbox hb;
    public InputAction action;
    public CInputAction cAction;
    public boolean hasInputFocus;
    public boolean isHidden;
    public boolean isHeader;
    private String labelText;
    public RemapInputElementListener listener;
    public static final float ROW_HEIGHT;
    public static final float ROW_WIDTH;
    public static final float ROW_RENDER_HEIGHT;
    private static final float ROW_TEXT_Y_OFFSET;
    private static final float ROW_TEXT_LEADING_OFFSET;
    public static final float KEYBOARD_COLUMN_X_OFFSET;
    public static final float CONTROLLER_COLUMN_X_OFFSET;
    private static final Color ROW_BG_COLOR = new Color(0x230e0fff);
    private static final Color ROW_HOVER_COLOR = new Color(-193);
    private static final Color ROW_SELECT_COLOR = new Color(0x8d4432ff);
    private static final Color TEXT_DEFAULT_COLOR;
    private static final Color TEXT_FOCUSED_COLOR;
    private static final Color TEXT_HOVERED_COLOR;

    static 
    {
        ROW_HEIGHT = 58F * Settings.scale;
        ROW_WIDTH = 1048F * Settings.scale;
        ROW_RENDER_HEIGHT = 64F * Settings.scale;
        ROW_TEXT_Y_OFFSET = 12F * Settings.scale;
        ROW_TEXT_LEADING_OFFSET = 40F * Settings.scale;
        KEYBOARD_COLUMN_X_OFFSET = Settings.scale * 400F;
        CONTROLLER_COLUMN_X_OFFSET = Settings.scale * 250F;
        TEXT_DEFAULT_COLOR = Settings.CREAM_COLOR;
        TEXT_FOCUSED_COLOR = Settings.GREEN_TEXT_COLOR;
        TEXT_HOVERED_COLOR = Settings.GOLD_COLOR;
    }
}
