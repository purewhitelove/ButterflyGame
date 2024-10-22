// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractBlight.java

package com.megacrit.cardcrawl.blights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.*;
import java.util.*;

public class AbstractBlight
{

    public AbstractBlight(String setId, String name, String description, String imgName, boolean unique)
    {
        isDone = false;
        isAnimating = false;
        isObtained = false;
        counter = -1;
        tips = new ArrayList();
        imgUrl = "";
        flashColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        goldOutlineColor = new Color(1.0F, 0.9F, 0.4F, 0.0F);
        isSeen = false;
        scale = Settings.scale;
        hb = new Hitbox(AbstractRelic.PAD_X, AbstractRelic.PAD_X);
        rotation = 0.0F;
        discarded = false;
        pulse = false;
        animationTimer = 0.0F;
        flashTimer = 0.0F;
        glowTimer = 0.0F;
        f_effect = new FloatyEffect(10F, 0.2F);
        blightID = setId;
        this.name = name;
        this.description = description;
        this.unique = unique;
        img = ImageMaster.loadImage((new StringBuilder()).append("images/blights/").append(imgName).toString());
        outlineImg = ImageMaster.loadImage((new StringBuilder()).append("images/blights/outline/").append(imgName).toString());
        increment = 0;
        tips.add(new PowerTip(name, description));
    }

    public void spawn(float x, float y)
    {
        AbstractDungeon.effectsQueue.add(new SmokePuffEffect(x, y));
        currentX = x;
        currentY = y;
        isAnimating = true;
        isObtained = false;
        f_effect.x = 0.0F;
        f_effect.y = 0.0F;
        hb = new Hitbox(AbstractRelic.PAD_X, AbstractRelic.PAD_X);
    }

    public void renderInTopPanel(SpriteBatch sb)
    {
        if(Settings.hideRelics)
        {
            return;
        } else
        {
            renderOutline(sb, true);
            sb.setColor(Color.WHITE);
            sb.draw(img, (currentX - 64F) + offsetX, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            renderCounter(sb, true);
            renderFlash(sb, true);
            hb.render(sb);
            return;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(Settings.hideRelics)
            return;
        if(isDone)
            renderOutline(sb, false);
        if(!isObtained && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP))
        {
            if(hb.hovered)
                renderTip(sb);
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
                if(hb.hovered)
                {
                    sb.setColor(PASSIVE_OUTLINE_COLOR);
                    sb.draw(outlineImg, (currentX - 64F) + f_effect.x, (currentY - 64F) + f_effect.y, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
                } else
                {
                    sb.setColor(PASSIVE_OUTLINE_COLOR);
                    sb.draw(outlineImg, (currentX - 64F) + f_effect.x, (currentY - 64F) + f_effect.y, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
                }
        }
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
        {
            if(!isObtained)
            {
                sb.setColor(Color.WHITE);
                sb.draw(img, (currentX - 64F) + f_effect.x, (currentY - 64F) + f_effect.y, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            } else
            {
                sb.setColor(Color.WHITE);
                sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
                renderCounter(sb, false);
            }
        } else
        {
            sb.setColor(Color.WHITE);
            sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            renderCounter(sb, false);
        }
        if(isDone)
            renderFlash(sb, false);
        hb.render(sb);
    }

    protected void updateAnimation()
    {
        if(animationTimer != 0.0F)
        {
            animationTimer -= Gdx.graphics.getDeltaTime();
            if(animationTimer < 0.0F)
                animationTimer = 0.0F;
        }
    }

    public void render(SpriteBatch sb, boolean renderAmount, Color outlineColor)
    {
        if(isSeen)
            renderOutline(outlineColor, sb, false);
        else
            renderOutline(Color.LIGHT_GRAY, sb, false);
        if(isSeen)
            sb.setColor(Color.WHITE);
        else
        if(hb.hovered)
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
        else
            sb.setColor(Color.BLACK);
        sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        if(hb.hovered)
            renderTip(sb);
        hb.render(sb);
    }

    public void renderCounter(SpriteBatch sb, boolean inTopPanel)
    {
        if(counter > -1)
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(counter), currentX + 30F * Settings.scale, currentY - 7F * Settings.scale, Color.WHITE);
    }

    public void renderOutline(Color c, SpriteBatch sb, boolean inTopPanel)
    {
        sb.setColor(c);
        if(AbstractDungeon.screen != null && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK)
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, Settings.scale * 2.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 15F, Settings.scale * 2.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 15F, rotation, 0, 0, 128, 128, false, false);
        else
        if(hb.hovered && Settings.isControllerMode)
        {
            sb.setBlendFunction(770, 1);
            goldOutlineColor.a = 0.6F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) / 5F;
            sb.setColor(goldOutlineColor);
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
        } else
        {
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        }
    }

    public void renderOutline(SpriteBatch sb, boolean inTopPanel)
    {
        if(hb.hovered && Settings.isControllerMode)
        {
            sb.setBlendFunction(770, 1);
            goldOutlineColor.a = 0.6F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) / 5F;
            sb.setColor(goldOutlineColor);
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
        } else
        {
            sb.setColor(PASSIVE_OUTLINE_COLOR);
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        }
    }

    public void renderFlash(SpriteBatch sb, boolean inTopPanel)
    {
        float tmp = Interpolation.exp10In.apply(0.0F, 4F, flashTimer / 2.0F);
        sb.setBlendFunction(770, 1);
        flashColor.a = flashTimer * 0.2F;
        sb.setColor(flashColor);
        sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale + tmp, scale + tmp, rotation, 0, 0, 128, 128, false, false);
        sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale + tmp * 0.66F, scale + tmp * 0.66F, rotation, 0, 0, 128, 128, false, false);
        sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale + tmp / 3F, scale + tmp / 3F, rotation, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void flash()
    {
        flashTimer = 2.0F;
    }

    public void renderTip(SpriteBatch sb)
    {
        if((float)InputHelper.mX < 1400F * Settings.scale)
        {
            if(CardCrawlGame.mainMenuScreen.screen == com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.RELIC_VIEW)
                TipHelper.queuePowerTips(180F * Settings.scale, (float)Settings.HEIGHT * 0.7F, tips);
            else
                TipHelper.queuePowerTips((float)InputHelper.mX + 50F * Settings.scale, (float)InputHelper.mY + 50F * Settings.scale, tips);
        } else
        {
            TipHelper.queuePowerTips((float)InputHelper.mX - 350F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, tips);
        }
    }

    protected void initializeTips()
    {
        Scanner desc = new Scanner(description);
        do
        {
            if(!desc.hasNext())
                break;
            String s = desc.next();
            if(s.charAt(0) == '#')
                s = s.substring(2);
            s = s.replace(',', ' ');
            s = s.replace('.', ' ');
            s = s.trim();
            s = s.toLowerCase();
            boolean alreadyExists = false;
            if(!GameDictionary.keywords.containsKey(s))
                continue;
            s = (String)GameDictionary.parentWord.get(s);
            Iterator iterator = tips.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                PowerTip t = (PowerTip)iterator.next();
                if(!t.header.toLowerCase().equals(s))
                    continue;
                alreadyExists = true;
                break;
            } while(true);
            if(!alreadyExists)
                tips.add(new PowerTip(TipHelper.capitalize(s), (String)GameDictionary.keywords.get(s)));
        } while(true);
        desc.close();
    }

    public void obtain()
    {
        hb.hovered = false;
        int slot = AbstractDungeon.player.blights.size();
        targetX = START_X + (float)slot * AbstractRelic.PAD_X;
        targetY = START_Y;
        AbstractDungeon.player.blights.add(this);
    }

    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip)
    {
        isDone = true;
        isObtained = true;
        if(slot >= p.blights.size())
            p.blights.add(this);
        else
            p.blights.set(slot, this);
        currentX = START_X + (float)slot * AbstractRelic.PAD_X;
        currentY = START_Y;
        targetX = currentX;
        targetY = currentY;
        hb.move(currentX, currentY);
        if(callOnEquip)
            onEquip();
    }

    private void updateFlash()
    {
        if(flashTimer != 0.0F)
        {
            flashTimer -= Gdx.graphics.getDeltaTime();
            if(flashTimer < 0.0F)
                if(pulse)
                    flashTimer = 1.0F;
                else
                    flashTimer = 0.0F;
        }
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }

    public void update()
    {
        updateFlash();
        if(!isDone)
        {
            if(isAnimating)
            {
                glowTimer -= Gdx.graphics.getDeltaTime();
                if(glowTimer < 0.0F)
                {
                    glowTimer = 0.5F;
                    AbstractDungeon.effectList.add(new GlowRelicParticle(img, currentX + f_effect.x, currentY + f_effect.y, rotation));
                }
                f_effect.update();
                if(hb.hovered)
                    scale = Settings.scale * 1.5F;
                else
                    scale = MathHelper.scaleLerpSnap(scale, Settings.scale * 1.1F);
            } else
            if(hb.hovered)
                scale = Settings.scale * 1.25F;
            else
                scale = MathHelper.scaleLerpSnap(scale, Settings.scale);
            if(isObtained)
            {
                if(rotation != 0.0F)
                    rotation = MathUtils.lerp(rotation, 0.0F, Gdx.graphics.getDeltaTime() * 6F * 2.0F);
                if(currentX != targetX)
                {
                    currentX = MathUtils.lerp(currentX, targetX, Gdx.graphics.getDeltaTime() * 6F);
                    if(Math.abs(currentX - targetX) < 0.5F)
                        currentX = targetX;
                }
                if(currentY != targetY)
                {
                    currentY = MathUtils.lerp(currentY, targetY, Gdx.graphics.getDeltaTime() * 6F);
                    if(Math.abs(currentY - targetY) < 0.5F)
                        currentY = targetY;
                }
                if(currentY == targetY && currentX == targetX)
                {
                    isDone = true;
                    onEquip();
                    hb.move(currentX, currentY);
                }
                scale = Settings.scale;
            }
            if(hb != null)
            {
                hb.update();
                if(hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD) && AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK)
                {
                    if(InputHelper.justClickedLeft && !isObtained)
                    {
                        InputHelper.justClickedLeft = false;
                        hb.clickStarted = true;
                    }
                    if((hb.clicked || CInputActionSet.select.isJustPressed()) && !isObtained)
                    {
                        CInputActionSet.select.unpress();
                        hb.clicked = false;
                        if(!Settings.isTouchScreen)
                        {
                            bossObtainLogic();
                        } else
                        {
                            AbstractDungeon.bossRelicScreen.confirmButton.show();
                            AbstractDungeon.bossRelicScreen.confirmButton.isDisabled = false;
                            AbstractDungeon.bossRelicScreen.touchBlight = this;
                        }
                    }
                }
            }
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
                updateAnimation();
        } else
        {
            hb.update();
            if(hb.hovered && AbstractDungeon.topPanel.potionUi.isHidden)
                scale = Settings.scale * 1.25F;
            else
                scale = MathHelper.scaleLerpSnap(scale, Settings.scale);
        }
    }

    public void bossObtainLogic()
    {
        if(AbstractDungeon.player.hasBlight(blightID))
        {
            AbstractDungeon.player.getBlight(blightID).stack();
            isObtained = true;
        } else
        {
            obtain();
            InputHelper.justClickedLeft = false;
            isObtained = true;
            f_effect.x = 0.0F;
            f_effect.y = 0.0F;
        }
    }

    public void onPlayCard(AbstractCard abstractcard, AbstractMonster abstractmonster)
    {
    }

    public boolean canPlay(AbstractCard card)
    {
        return true;
    }

    public void onVictory()
    {
    }

    public void atBattleStart()
    {
    }

    public void atTurnStart()
    {
    }

    public void onPlayerEndTurn()
    {
    }

    public void onBossDefeat()
    {
    }

    public void onCreateEnemy(AbstractMonster abstractmonster)
    {
    }

    public void effect()
    {
    }

    public void onEquip()
    {
    }

    public float effectFloat()
    {
        return floatModAmount;
    }

    public void incrementUp()
    {
    }

    public void setIncrement(int newInc)
    {
        increment = newInc;
    }

    public static String gameDataUploadHeader()
    {
        GameDataStringBuilder sb = new GameDataStringBuilder();
        sb.addFieldData("name");
        sb.addFieldData("text");
        return sb.toString();
    }

    public String gameDataUploadData()
    {
        GameDataStringBuilder sb = new GameDataStringBuilder();
        sb.addFieldData(name);
        sb.addFieldData(description);
        return sb.toString();
    }

    public void stack()
    {
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass playerclass)
    {
    }

    public void updateDescription()
    {
    }

    public String name;
    public String description;
    public String blightID;
    public Texture img;
    public Texture outlineImg;
    public boolean unique;
    public int increment;
    private static final String IMG_DIR = "images/blights/";
    private static final String OUTLINE_DIR = "images/blights/outline/";
    public float floatModAmount;
    public boolean isDone;
    public boolean isAnimating;
    public boolean isObtained;
    public int cost;
    public int counter;
    public ArrayList tips;
    public String imgUrl;
    public static final int RAW_W = 128;
    public float currentX;
    public float currentY;
    public float targetX;
    public float targetY;
    private static final float START_X;
    private static final float START_Y;
    private static final Color PASSIVE_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.33F);
    private Color flashColor;
    private Color goldOutlineColor;
    public boolean isSeen;
    public float scale;
    public static final int MAX_BLIGHTS_PER_PAGE = 25;
    public Hitbox hb;
    private static final float OBTAIN_SPEED = 6F;
    private static final float OBTAIN_THRESHOLD = 0.5F;
    private float rotation;
    public boolean discarded;
    protected boolean pulse;
    private float animationTimer;
    public float flashTimer;
    private static final float FLASH_ANIM_TIME = 2F;
    private static final float DEFAULT_ANIM_SCALE = 4F;
    private float glowTimer;
    private FloatyEffect f_effect;
    private static float offsetX = 0.0F;

    static 
    {
        START_X = 64F * Settings.xScale;
        START_Y = Settings.isMobile ? (float)Settings.HEIGHT - 206F * Settings.scale : (float)Settings.HEIGHT - 176F * Settings.scale;
    }
}
