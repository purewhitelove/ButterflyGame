// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScrollBar.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            ScrollBarListener

public class ScrollBar
{

    public ScrollBar(ScrollBarListener listener)
    {
        this(listener, (float)Settings.WIDTH - 150F * Settings.scale - TRACK_W / 2.0F, (float)Settings.HEIGHT / 2.0F, (float)Settings.HEIGHT - 256F * Settings.scale);
        cursorDrawPosition = getYPositionForPercent(0.0F);
    }

    public ScrollBar(ScrollBarListener listener, float x, float y, float height)
    {
        isBackgroundVisible = true;
        isInteractable = true;
        TRACK_CAP_HEIGHT = TRACK_W;
        TRACK_CAP_VISIBLE_HEIGHT = 22F * Settings.scale;
        CURSOR_W = 38F * Settings.scale;
        CURSOR_H = 60F * Settings.scale;
        DRAW_BORDER = CURSOR_H / 4F;
        cursorDrawPosition = 0.0F;
        sliderListener = listener;
        currentScrollPercent = 0.0F;
        isDragging = false;
        sliderHb = new Hitbox(TRACK_W, height);
        sliderHb.move(x, y);
        reset();
    }

    public ScrollBar(ScrollBarListener listener, float x, float y, float height, boolean isProgressBar)
    {
        isBackgroundVisible = true;
        isInteractable = true;
        TRACK_CAP_HEIGHT = TRACK_W;
        TRACK_CAP_VISIBLE_HEIGHT = 22F * Settings.scale;
        CURSOR_W = 38F * Settings.scale;
        CURSOR_H = 60F * Settings.scale;
        DRAW_BORDER = CURSOR_H / 4F;
        cursorDrawPosition = 0.0F;
        isInteractable = !isProgressBar;
        sliderListener = listener;
        currentScrollPercent = 0.0F;
        isDragging = false;
        sliderHb = new Hitbox(TRACK_W / 2.0F, height);
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

    public void changeHeight(float newHeight)
    {
        sliderHb.height = newHeight;
        sliderHb.move(sliderHb.cX, sliderHb.cY);
        reset();
    }

    public void positionWithinOnRight(float right, float top, float bottom)
    {
        sliderHb.x = right - TRACK_W;
        setCenter(right - TRACK_W / 2.0F, (bottom + top) / 2.0F);
        changeHeight(top - bottom - 2.0F * TRACK_CAP_VISIBLE_HEIGHT);
        reset();
    }

    public float width()
    {
        return sliderHb.width;
    }

    public void reset()
    {
        cursorDrawPosition = getYPositionForPercent(currentScrollPercent);
    }

    public boolean update()
    {
        if(!isInteractable)
            return false;
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
            float newPercent = getPercentFromY(InputHelper.mY);
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

    private float getPercentFromY(float y)
    {
        float minY = (sliderHb.y + sliderHb.height) - DRAW_BORDER;
        float maxY = sliderHb.y + DRAW_BORDER;
        return boundedPercent(MathHelper.percentFromValueBetween(minY, maxY, y));
    }

    public void render(SpriteBatch sb)
    {
        if(!isInteractable)
            return;
        sb.setColor(Color.WHITE);
        if(isBackgroundVisible)
            renderTrack(sb);
        renderCursor(sb);
        sliderHb.render(sb);
    }

    private float getYPositionForPercent(float percent)
    {
        float topY = ((sliderHb.y + sliderHb.height) - CURSOR_H) + DRAW_BORDER;
        float bottomY = sliderHb.y - DRAW_BORDER;
        return MathHelper.valueFromPercentBetween(topY, bottomY, boundedPercent(percent));
    }

    private void renderCursor(SpriteBatch sb)
    {
        float x = sliderHb.cX - CURSOR_W / 2.0F;
        float yForPercent = getYPositionForPercent(currentScrollPercent);
        cursorDrawPosition = MathHelper.scrollSnapLerpSpeed(cursorDrawPosition, yForPercent);
        if(isInteractable)
        {
            sb.draw(ImageMaster.SCROLL_BAR_TRAIN, x, cursorDrawPosition, CURSOR_W, CURSOR_H);
        } else
        {
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.GRAYSCALE);
            sb.draw(ImageMaster.SCROLL_BAR_TRAIN, x + CURSOR_W / 4F, cursorDrawPosition, CURSOR_W / 2.0F, CURSOR_H);
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.DEFAULT);
        }
    }

    private void renderTrack(SpriteBatch sb)
    {
        sb.draw(ImageMaster.SCROLL_BAR_MIDDLE, sliderHb.x, sliderHb.y, sliderHb.width, sliderHb.height);
        sb.draw(ImageMaster.SCROLL_BAR_TOP, sliderHb.x, sliderHb.y + sliderHb.height, sliderHb.width, TRACK_CAP_HEIGHT);
        sb.draw(ImageMaster.SCROLL_BAR_BOTTOM, sliderHb.x, sliderHb.y - TRACK_CAP_HEIGHT, sliderHb.width, TRACK_CAP_HEIGHT);
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
    private boolean isInteractable;
    public static final float TRACK_W;
    private final float TRACK_CAP_HEIGHT;
    private final float TRACK_CAP_VISIBLE_HEIGHT;
    private final float CURSOR_W;
    private final float CURSOR_H;
    private final float DRAW_BORDER;
    private float cursorDrawPosition;

    static 
    {
        TRACK_W = 54F * Settings.scale;
    }
}
