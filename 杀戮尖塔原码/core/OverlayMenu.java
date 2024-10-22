// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OverlayMenu.java

package com.megacrit.cardcrawl.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.core:
//            Settings

public class OverlayMenu
{

    public OverlayMenu(AbstractPlayer player)
    {
        combatPanelsShown = true;
        tipHoverDuration = 0.0F;
        hoveredTip = false;
        relicQueue = new ArrayList();
        bottomBgPanel = new BottomBgPanel();
        energyPanel = new EnergyPanel();
        blackScreenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        blackScreenTarget = 0.0F;
        combatDeckPanel = new DrawPilePanel();
        discardPilePanel = new DiscardPilePanel();
        exhaustPanel = new ExhaustPanel();
        endTurnButton = new EndTurnButton();
        proceedButton = new ProceedButton();
        cancelButton = new CancelButton();
        this.player = player;
    }

    public void update()
    {
        hoveredTip = false;
        bottomBgPanel.updatePositions();
        energyPanel.updatePositions();
        energyPanel.update();
        player.hand.update();
        combatDeckPanel.updatePositions();
        discardPilePanel.updatePositions();
        exhaustPanel.updatePositions();
        endTurnButton.update();
        proceedButton.update();
        cancelButton.update();
        updateBlackScreen();
        Iterator iterator = AbstractDungeon.player.relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r != null)
                r.update();
        } while(true);
        iterator = AbstractDungeon.player.blights.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractBlight b = (AbstractBlight)iterator.next();
            if(b != null)
                b.update();
        } while(true);
        if(!relicQueue.isEmpty())
        {
            AbstractRelic r;
            for(Iterator iterator1 = relicQueue.iterator(); iterator1.hasNext(); AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r))
                r = (AbstractRelic)iterator1.next();

            relicQueue.clear();
        }
        if(hoveredTip)
        {
            tipHoverDuration += Gdx.graphics.getDeltaTime();
            if(tipHoverDuration > 0.01F)
                tipHoverDuration = 0.02F;
        } else
        {
            tipHoverDuration -= Gdx.graphics.getDeltaTime();
            if(tipHoverDuration < 0.0F)
                tipHoverDuration = 0.0F;
        }
    }

    public void showCombatPanels()
    {
        combatPanelsShown = true;
        bottomBgPanel.changeMode(com.megacrit.cardcrawl.ui.panels.BottomBgPanel.Mode.NORMAL);
        combatDeckPanel.show();
        discardPilePanel.show();
        exhaustPanel.show();
        energyPanel.show();
        endTurnButton.show();
        if(AbstractDungeon.ftue == null)
            proceedButton.hide();
        player.hand.refreshHandLayout();
    }

    public void hideCombatPanels()
    {
        combatPanelsShown = false;
        bottomBgPanel.changeMode(com.megacrit.cardcrawl.ui.panels.BottomBgPanel.Mode.HIDDEN);
        combatDeckPanel.hide();
        discardPilePanel.hide();
        exhaustPanel.hide();
        endTurnButton.hide();
        energyPanel.hide();
        for(Iterator iterator = player.hand.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            c.target_y = -AbstractCard.IMG_HEIGHT;
        }

    }

    public void showBlackScreen(float target)
    {
        blackScreenTarget = target;
    }

    public void showBlackScreen()
    {
        if(blackScreenTarget < 0.85F)
            blackScreenTarget = 0.85F;
    }

    public void hideBlackScreen()
    {
        blackScreenTarget = 0.0F;
    }

    private void updateBlackScreen()
    {
        if(blackScreenColor.a != blackScreenTarget)
            if(blackScreenTarget > blackScreenColor.a)
            {
                blackScreenColor.a += Gdx.graphics.getDeltaTime() * 2.0F;
                if(blackScreenColor.a > blackScreenTarget)
                    blackScreenColor.a = blackScreenTarget;
            } else
            {
                blackScreenColor.a -= Gdx.graphics.getDeltaTime() * 2.0F;
                if(blackScreenColor.a < blackScreenTarget)
                    blackScreenColor.a = blackScreenTarget;
            }
    }

    public void render(SpriteBatch sb)
    {
        endTurnButton.render(sb);
        proceedButton.render(sb);
        cancelButton.render(sb);
        if(!Settings.hideLowerElements)
        {
            energyPanel.render(sb);
            combatDeckPanel.render(sb);
            discardPilePanel.render(sb);
            exhaustPanel.render(sb);
        }
        player.renderHand(sb);
        player.hand.renderTip(sb);
    }

    public void renderBlackScreen(SpriteBatch sb)
    {
        if(blackScreenColor.a != 0.0F)
        {
            sb.setColor(blackScreenColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    private AbstractPlayer player;
    public static final float HAND_HIDE_Y;
    public boolean combatPanelsShown;
    public float tipHoverDuration;
    private static final float HOVER_TIP_TIME = 0.01F;
    public boolean hoveredTip;
    public ArrayList relicQueue;
    public BottomBgPanel bottomBgPanel;
    public EnergyPanel energyPanel;
    private Color blackScreenColor;
    private float blackScreenTarget;
    public DrawPilePanel combatDeckPanel;
    public DiscardPilePanel discardPilePanel;
    public ExhaustPanel exhaustPanel;
    public EndTurnButton endTurnButton;
    public ProceedButton proceedButton;
    public CancelButton cancelButton;

    static 
    {
        HAND_HIDE_Y = 300F * Settings.scale;
    }
}
