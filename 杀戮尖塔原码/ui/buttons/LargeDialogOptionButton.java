// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LargeDialogOptionButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;

public class LargeDialogOptionButton
{

    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled, AbstractCard previewCard, AbstractRelic previewRelic)
    {
        textColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        boxColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        y = -9999F * Settings.scale;
        animTimer = 0.5F;
        alpha = 0.0F;
        boxInactiveColor = new Color(0.2F, 0.25F, 0.25F, 0.0F);
        pressed = false;
        this.slot = 0;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$AbstractEvent$EventType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$AbstractEvent$EventType = new int[com.megacrit.cardcrawl.events.AbstractEvent.EventType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$AbstractEvent$EventType[com.megacrit.cardcrawl.events.AbstractEvent.EventType.TEXT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$AbstractEvent$EventType[com.megacrit.cardcrawl.events.AbstractEvent.EventType.IMAGE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$AbstractEvent$EventType[com.megacrit.cardcrawl.events.AbstractEvent.EventType.ROOM.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.AbstractEvent.EventType[AbstractEvent.type.ordinal()])
        {
        case 1: // '\001'
            x = 895F * Settings.xScale;
            break;

        case 2: // '\002'
            x = 1260F * Settings.xScale;
            break;

        case 3: // '\003'
            x = 620F * Settings.xScale;
            break;
        }
        this.slot = slot;
        this.isDisabled = isDisabled;
        cardToPreview = previewCard;
        relicToPreview = previewRelic;
        if(isDisabled)
            this.msg = stripColor(msg);
        else
            this.msg = msg;
        hb = new Hitbox(892F * Settings.xScale, 80F * Settings.yScale);
    }

    public LargeDialogOptionButton(int slot, String msg, AbstractCard previewCard, AbstractRelic previewRelic)
    {
        this(slot, msg, false, previewCard, previewRelic);
    }

    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled, AbstractCard previewCard)
    {
        this(slot, msg, isDisabled, previewCard, null);
    }

    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled, AbstractRelic previewRelic)
    {
        this(slot, msg, isDisabled, null, previewRelic);
    }

    public LargeDialogOptionButton(int slot, String msg)
    {
        this(slot, msg, false, null, null);
    }

    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled)
    {
        this(slot, msg, isDisabled, null, null);
    }

    public LargeDialogOptionButton(int slot, String msg, AbstractCard previewCard)
    {
        this(slot, msg, false, previewCard);
    }

    public LargeDialogOptionButton(int slot, String msg, AbstractRelic previewRelic)
    {
        this(slot, msg, false, previewRelic);
    }

    private String stripColor(String input)
    {
        input = input.replace("#r", "");
        input = input.replace("#g", "");
        input = input.replace("#b", "");
        input = input.replace("#y", "");
        return input;
    }

    public void calculateY(int size)
    {
        if(AbstractEvent.type != com.megacrit.cardcrawl.events.AbstractEvent.EventType.ROOM)
        {
            y = Settings.OPTION_Y - 424F * Settings.scale;
            y += (float)slot * OPTION_SPACING_Y;
            y -= (float)size * OPTION_SPACING_Y;
        } else
        {
            y = Settings.OPTION_Y - 500F * Settings.scale;
            y += (float)slot * OPTION_SPACING_Y;
            y -= (float)RoomEventDialog.optionList.size() * OPTION_SPACING_Y;
        }
        hb.move(x, y);
    }

    public void update(int size)
    {
        calculateY(size);
        hoverAndClickLogic();
        updateAnimation();
    }

    private void updateAnimation()
    {
        if(animTimer != 0.0F)
        {
            animTimer -= Gdx.graphics.getDeltaTime();
            if(animTimer < 0.0F)
                animTimer = 0.0F;
            alpha = Interpolation.exp5In.apply(0.0F, 1.0F, 1.0F - animTimer / 0.5F);
        }
        textColor.a = alpha;
        boxColor.a = alpha;
    }

    private void hoverAndClickLogic()
    {
        hb.update();
        if(hb.hovered && InputHelper.justClickedLeft && !isDisabled && animTimer < 0.1F)
        {
            InputHelper.justClickedLeft = false;
            hb.clickStarted = true;
        }
        if(hb.hovered && CInputActionSet.select.isJustPressed() && !isDisabled)
            hb.clicked = true;
        if(hb.clicked)
        {
            hb.clicked = false;
            pressed = true;
        }
        if(!isDisabled)
        {
            if(hb.hovered)
            {
                textColor = TEXT_ACTIVE_COLOR;
                boxColor = Color.WHITE.cpy();
            } else
            {
                textColor = TEXT_INACTIVE_COLOR;
                boxColor = new Color(0.4F, 0.4F, 0.4F, 1.0F);
            }
        } else
        {
            textColor = TEXT_DISABLED_COLOR;
            boxColor = boxInactiveColor;
        }
        if(hb.hovered)
        {
            if(!isDisabled)
                textColor = TEXT_ACTIVE_COLOR;
            else
                textColor = Color.GRAY.cpy();
        } else
        if(!isDisabled)
            textColor = TEXT_ACTIVE_COLOR;
        else
            textColor = Color.GRAY.cpy();
    }

    public void render(SpriteBatch sb)
    {
        float scale = Settings.scale;
        float xScale = Settings.xScale;
        if(hb.clickStarted)
        {
            scale *= 0.99F;
            xScale *= 0.99F;
        }
        if(isDisabled)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.EVENT_BUTTON_DISABLED, x - 445F, y - 38.5F, 445F, 38.5F, 890F, 77F, xScale, scale, 0.0F, 0, 0, 890, 77, false, false);
        } else
        {
            sb.setColor(boxColor);
            sb.draw(ImageMaster.EVENT_BUTTON_ENABLED, x - 445F, y - 38.5F, 445F, 38.5F, 890F, 77F, xScale, scale, 0.0F, 0, 0, 890, 77, false, false);
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
            sb.draw(ImageMaster.EVENT_BUTTON_ENABLED, x - 445F, y - 38.5F, 445F, 38.5F, 890F, 77F, xScale, scale, 0.0F, 0, 0, 890, 77, false, false);
            sb.setBlendFunction(770, 771);
        }
        if(FontHelper.getSmartWidth(FontHelper.largeDialogOptionFont, msg, Settings.WIDTH, 0.0F) > 800F * Settings.xScale)
            FontHelper.renderSmartText(sb, FontHelper.smallDialogOptionFont, msg, x + TEXT_ADJUST_X, y + TEXT_ADJUST_Y, Settings.WIDTH, 0.0F, textColor);
        else
            FontHelper.renderSmartText(sb, FontHelper.largeDialogOptionFont, msg, x + TEXT_ADJUST_X, y + TEXT_ADJUST_Y, Settings.WIDTH, 0.0F, textColor);
        hb.render(sb);
    }

    public void renderCardPreview(SpriteBatch sb)
    {
        if(cardToPreview != null && hb.hovered)
        {
            cardToPreview.current_x = x + hb.width / 1.75F;
            if(y < cardToPreview.hb.height / 2.0F + 5F)
                y = cardToPreview.hb.height / 2.0F + 5F;
            cardToPreview.current_y = y;
            cardToPreview.render(sb);
        }
    }

    public void renderRelicPreview(SpriteBatch sb)
    {
        if(!Settings.isControllerMode && relicToPreview != null && hb.hovered)
            TipHelper.queuePowerTips(470F * Settings.scale, (float)InputHelper.mY + TipHelper.calculateToAvoidOffscreen(relicToPreview.tips, InputHelper.mY), relicToPreview.tips);
    }

    private static final float OPTION_SPACING_Y;
    private static final float TEXT_ADJUST_X;
    private static final float TEXT_ADJUST_Y;
    public String msg;
    private Color textColor;
    private Color boxColor;
    private float x;
    private float y;
    public Hitbox hb;
    private static final float ANIM_TIME = 0.5F;
    private float animTimer;
    private float alpha;
    private static final Color TEXT_ACTIVE_COLOR;
    private static final Color TEXT_INACTIVE_COLOR = new Color(0.8F, 0.8F, 0.8F, 1.0F);
    private static final Color TEXT_DISABLED_COLOR;
    private Color boxInactiveColor;
    public boolean pressed;
    public boolean isDisabled;
    public int slot;
    private AbstractCard cardToPreview;
    private AbstractRelic relicToPreview;
    private static final int W = 890;
    private static final int H = 77;

    static 
    {
        OPTION_SPACING_Y = -82F * Settings.scale;
        TEXT_ADJUST_X = -400F * Settings.xScale;
        TEXT_ADJUST_Y = 10F * Settings.scale;
        TEXT_ACTIVE_COLOR = Color.WHITE.cpy();
        TEXT_DISABLED_COLOR = Color.FIREBRICK.cpy();
    }
}
