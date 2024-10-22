// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HexaghostBody.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.TintEffect;

public class HexaghostBody
    implements Disposable
{

    public HexaghostBody(AbstractMonster m)
    {
        rotationSpeed = 1.0F;
        targetRotationSpeed = 30F;
        effect = new BobEffect(0.75F);
        plasma1Angle = 0.0F;
        plasma2Angle = 0.0F;
        plasma3Angle = 0.0F;
        this.m = m;
        plasma1 = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/plasma1.png");
        plasma2 = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/plasma2.png");
        plasma3 = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/plasma3.png");
        shadow = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/shadow.png");
    }

    public void update()
    {
        effect.update();
        plasma1Angle += rotationSpeed * Gdx.graphics.getDeltaTime();
        plasma2Angle += (rotationSpeed / 2.0F) * Gdx.graphics.getDeltaTime();
        plasma3Angle += (rotationSpeed / 3F) * Gdx.graphics.getDeltaTime();
        rotationSpeed = MathHelper.fadeLerpSnap(rotationSpeed, targetRotationSpeed);
        effect.speed = rotationSpeed * Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(m.tint.color);
        sb.draw(plasma3, (m.drawX - 256F) + m.animX + 12F * Settings.scale, ((m.drawY + m.animY + effect.y * 2.0F) - 256F) + BODY_OFFSET_Y, 256F, 256F, 512F, 512F, Settings.scale * 0.95F, Settings.scale * 0.95F, plasma3Angle, 0, 0, 512, 512, false, false);
        sb.draw(plasma2, (m.drawX - 256F) + m.animX + 6F * Settings.scale, ((m.drawY + m.animY + effect.y) - 256F) + BODY_OFFSET_Y, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, plasma2Angle, 0, 0, 512, 512, false, false);
        sb.draw(plasma1, (m.drawX - 256F) + m.animX, ((m.drawY + m.animY + effect.y * 0.5F) - 256F) + BODY_OFFSET_Y, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, plasma1Angle, 0, 0, 512, 512, false, false);
        sb.draw(shadow, (m.drawX - 256F) + m.animX + 12F * Settings.scale, ((m.drawY + m.animY + effect.y / 4F) - 15F * Settings.scale - 256F) + BODY_OFFSET_Y, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
    }

    public void dispose()
    {
        plasma1.dispose();
        plasma2.dispose();
        plasma3.dispose();
        shadow.dispose();
    }

    public static final String ID = "HexaghostBody";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private float rotationSpeed;
    public float targetRotationSpeed;
    private BobEffect effect;
    private AbstractMonster m;
    private static final String IMG_DIR = "images/monsters/theBottom/boss/ghost/";
    private static final int W = 512;
    private Texture plasma1;
    private Texture plasma2;
    private Texture plasma3;
    private Texture shadow;
    private float plasma1Angle;
    private float plasma2Angle;
    private float plasma3Angle;
    private static final float BODY_OFFSET_Y;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("HexaghostBody");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        BODY_OFFSET_Y = 256F * Settings.scale;
    }
}
