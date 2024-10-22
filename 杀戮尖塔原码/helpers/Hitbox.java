// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Hitbox.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            HitboxListener, ImageMaster

public class Hitbox
{

    public Hitbox(float width, float height)
    {
        this(-10000F, -10000F, width, height);
    }

    public Hitbox(float x, float y, float width, float height)
    {
        hovered = false;
        justHovered = false;
        clickStarted = false;
        clicked = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        cX = x + width / 2.0F;
        cY = y + height / 2.0F;
    }

    public void update()
    {
        update(x, y);
        if(clickStarted && InputHelper.justReleasedClickLeft)
        {
            if(hovered)
                clicked = true;
            clickStarted = false;
        }
    }

    public void update(float x, float y)
    {
        if(AbstractDungeon.isFadingOut)
            return;
        this.x = x;
        this.y = y;
        if(justHovered)
            justHovered = false;
        if(!hovered)
        {
            hovered = (float)InputHelper.mX > x && (float)InputHelper.mX < x + width && (float)InputHelper.mY > y && (float)InputHelper.mY < y + height;
            if(hovered)
                justHovered = true;
        } else
        {
            hovered = (float)InputHelper.mX > x && (float)InputHelper.mX < x + width && (float)InputHelper.mY > y && (float)InputHelper.mY < y + height;
        }
    }

    public void encapsulatedUpdate(HitboxListener listener)
    {
        update();
        if(justHovered)
            listener.hoverStarted(this);
        if(hovered && InputHelper.justClickedLeft)
        {
            clickStarted = true;
            listener.startClicking(this);
        } else
        if(clicked || hovered && CInputActionSet.select.isJustPressed())
        {
            CInputActionSet.select.unpress();
            clicked = false;
            listener.clicked(this);
        }
    }

    public void unhover()
    {
        hovered = false;
        justHovered = false;
    }

    public void move(float cX, float cY)
    {
        this.cX = cX;
        this.cY = cY;
        x = cX - width / 2.0F;
        y = cY - height / 2.0F;
    }

    public void moveY(float cY)
    {
        this.cY = cY;
        y = cY - height / 2.0F;
    }

    public void moveX(float cX)
    {
        this.cX = cX;
        x = cX - width / 2.0F;
    }

    public void translate(float x, float y)
    {
        this.x = x;
        this.y = y;
        cX = x + width / 2.0F;
        cY = y + height / 2.0F;
    }

    public void resize(float w, float h)
    {
        width = w;
        height = h;
    }

    public boolean intersects(Hitbox other)
    {
        return x < other.x + other.width && x + width > other.x && y < other.y + other.height && y + height > other.y;
    }

    public void render(SpriteBatch sb)
    {
        if(Settings.isDebug || Settings.isInfo)
        {
            if(clickStarted)
                sb.setColor(Color.CHARTREUSE);
            else
                sb.setColor(Color.RED);
            sb.draw(ImageMaster.DEBUG_HITBOX_IMG, x, y, width, height);
        }
    }

    public float x;
    public float y;
    public float cX;
    public float cY;
    public float width;
    public float height;
    public boolean hovered;
    public boolean justHovered;
    public boolean clickStarted;
    public boolean clicked;
}
