// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HorizontalScrollBar.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            ScrollBarListener

public class HorizontalScrollBar
{

    public HorizontalScrollBar(ScrollBarListener listener)
    {
        this(listener, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT - 150F * Settings.scale - TRACK_H / 2.0F, (float)Settings.WIDTH - 256F * Settings.scale);
        cursorDrawPosition = getXPositionForPercent(0.0F);
    }

    public HorizontalScrollBar(ScrollBarListener listener, float x, float y, float width)
    {
        isBackgroundVisible = true;
        TRACK_CAP_WIDTH = TRACK_H;
        CURSOR_W = 60F * Settings.scale;
        CURSOR_H = 38F * Settings.scale;
        DRAW_BORDER = CURSOR_W / 4F;
        cursorDrawPosition = 0.0F;
        sliderListener = listener;
        currentScrollPercent = 0.0F;
        isDragging = false;
        sliderHb = new Hitbox(width, TRACK_H);
        sliderHb.move(x, y);
        reset();
    }

    public void setCenter(float x, float y)
    {
        sliderHb.move(x, y);
        reset();
    }

    public void move(float xOffset, float yOffset)
    {
        sliderHb.move(sliderHb.cX + xOffset, sliderHb.cY + yOffset);
        reset();
    }

    public void changeWidth(float newWidth)
    {
        sliderHb.width = newWidth;
        sliderHb.move(sliderHb.cX, sliderHb.cY);
        reset();
    }

    public float width()
    {
        return sliderHb.width;
    }

    public void reset()
    {
        cursorDrawPosition = getXPositionForPercent(0.0F);
    }

    public boolean update()
    {
        sliderHb.update();
        if(sliderHb.hovered && InputHelper.isMouseDown)
            isDragging = true;
        if(isDragging && InputHelper.justReleasedClickLeft)
        {
            isDragging = false;
            return true;
        }
        if(isDragging)
        {
            float newPercent = getPercentFromX(InputHelper.mX);
            sliderListener.scrolledUsingBar(newPercent);
            return true;
        } else
        {
            return false;
        }
    }

    public void parentScrolledToPercent(float percent)
    {
        currentScrollPercent = boundedPercent(percent);
    }

    private float getPercentFromX(float x)
    {
        float minX = sliderHb.x + DRAW_BORDER;
        float maxX = (sliderHb.x + sliderHb.width) - DRAW_BORDER;
        return boundedPercent(MathHelper.percentFromValueBetween(minX, maxX, x));
    }

    public void render(SpriteBatch sb)
    {
        Color previousColor = sb.getColor();
        sb.setColor(Color.WHITE);
        if(isBackgroundVisible)
            renderTrack(sb);
        renderCursor(sb);
        sliderHb.render(sb);
        sb.setColor(previousColor);
    }

    private float getXPositionForPercent(float percent)
    {
        float topX = sliderHb.x - DRAW_BORDER;
        float bottomX = ((sliderHb.x + sliderHb.width) - CURSOR_W) + DRAW_BORDER;
        return MathHelper.valueFromPercentBetween(topX, bottomX, boundedPercent(percent));
    }

    private void renderCursor(SpriteBatch sb)
    {
        float y = sliderHb.cY - CURSOR_H / 2.0F;
        float xForPercent = getXPositionForPercent(currentScrollPercent);
        cursorDrawPosition = MathHelper.scrollSnapLerpSpeed(cursorDrawPosition, xForPercent);
        sb.draw(ImageMaster.SCROLL_BAR_HORIZONTAL_TRAIN, cursorDrawPosition, y, CURSOR_W, CURSOR_H);
    }

    private void renderTrack(SpriteBatch sb)
    {
        sb.draw(ImageMaster.SCROLL_BAR_HORIZONTAL_MIDDLE, sliderHb.x, sliderHb.y, sliderHb.width, sliderHb.height);
        sb.draw(ImageMaster.SCROLL_BAR_RIGHT, sliderHb.x + sliderHb.width, sliderHb.y, TRACK_CAP_WIDTH, sliderHb.height);
        sb.draw(ImageMaster.SCROLL_BAR_LEFT, sliderHb.x - TRACK_CAP_WIDTH, sliderHb.y, TRACK_CAP_WIDTH, sliderHb.height);
    }

    private float boundedPercent(float percent)
    {
        return Math.max(0.0F, Math.min(percent, 1.0F));
    }

    public ScrollBarListener sliderListener;
    public boolean isBackgroundVisible;
    private Hitbox sliderHb;
    private float currentScrollPercent;
    private boolean isDragging;
    public static final float TRACK_H;
    private final float TRACK_CAP_WIDTH;
    private final float CURSOR_W;
    private final float CURSOR_H;
    private final float DRAW_BORDER;
    private float cursorDrawPosition;

    static 
    {
        TRACK_H = 54F * Settings.scale;
    }
}
