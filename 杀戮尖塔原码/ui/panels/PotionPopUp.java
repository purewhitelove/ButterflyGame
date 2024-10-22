// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PotionPopUp.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.SmokeBomb;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.ui.panels:
//            TopPanel

public class PotionPopUp
{

    public PotionPopUp()
    {
        isHidden = true;
        targetMode = false;
        topHoverColor = new Color(0.5F, 0.9F, 1.0F, 0.0F);
        botHoverColor = new Color(1.0F, 0.4F, 0.3F, 0.0F);
        points = new Vector2[20];
        arrowScaleTimer = 0.0F;
        hoveredMonster = null;
        autoTargetFirst = false;
        hbTop = new Hitbox(286F * Settings.scale, 120F * Settings.scale);
        hbBot = new Hitbox(286F * Settings.scale, 90F * Settings.scale);
        for(int i = 0; i < points.length; i++)
            points[i] = new Vector2();

    }

    public void open(int slot, AbstractPotion potion)
    {
        topHoverColor.a = 0.0F;
        botHoverColor.a = 0.0F;
        AbstractDungeon.topPanel.selectPotionMode = false;
        this.slot = slot;
        this.potion = potion;
        x = potion.posX;
        y = (float)Settings.HEIGHT - 230F * Settings.scale;
        isHidden = false;
        hbTop.move(x, y + 44F * Settings.scale);
        hbBot.move(x, y - 76F * Settings.scale);
        hbTop.clickStarted = false;
        hbBot.clickStarted = false;
        hbTop.clicked = false;
        hbBot.clicked = false;
    }

    public void close()
    {
        isHidden = true;
    }

    public void update()
    {
        if(!isHidden)
        {
            updateControllerInput();
            hbTop.update();
            hbBot.update();
            updateInput();
            if(potion != null)
                TipHelper.queuePowerTips(x + 180F * Settings.scale, y + 70F * Settings.scale, potion.tips);
        } else
        if(targetMode)
        {
            updateControllerTargetInput();
            updateTargetMode();
        }
    }

    private void updateControllerTargetInput()
    {
        if(!Settings.isControllerMode)
            return;
        int offsetEnemyIndex = 0;
        if(autoTargetFirst)
        {
            autoTargetFirst = false;
            offsetEnemyIndex++;
        }
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            offsetEnemyIndex--;
        if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            offsetEnemyIndex++;
        if(offsetEnemyIndex != 0)
        {
            ArrayList prefiltered = AbstractDungeon.getCurrRoom().monsters.monsters;
            ArrayList sortedMonsters = new ArrayList(AbstractDungeon.getCurrRoom().monsters.monsters);
            AbstractMonster newTarget = prefiltered.iterator();
            do
            {
                if(!newTarget.hasNext())
                    break;
                AbstractMonster mons = (AbstractMonster)newTarget.next();
                if(mons.isDying)
                    sortedMonsters.remove(mons);
            } while(true);
            sortedMonsters.sort(AbstractMonster.sortByHitbox);
            if(sortedMonsters.isEmpty())
                return;
            newTarget = sortedMonsters.iterator();
            do
            {
                if(!newTarget.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)newTarget.next();
                if(!m.hb.hovered)
                    continue;
                hoveredMonster = m;
                break;
            } while(true);
            if(hoveredMonster == null)
            {
                if(offsetEnemyIndex == 1)
                    newTarget = (AbstractMonster)sortedMonsters.get(0);
                else
                    newTarget = (AbstractMonster)sortedMonsters.get(sortedMonsters.size() - 1);
            } else
            {
                int currentTargetIndex = sortedMonsters.indexOf(hoveredMonster);
                int newTargetIndex = currentTargetIndex + offsetEnemyIndex;
                newTargetIndex = (newTargetIndex + sortedMonsters.size()) % sortedMonsters.size();
                newTarget = (AbstractMonster)sortedMonsters.get(newTargetIndex);
            }
            if(newTarget != null)
            {
                Hitbox target = newTarget.hb;
                Gdx.input.setCursorPosition((int)target.cX, Settings.HEIGHT - (int)target.cY);
                hoveredMonster = newTarget;
            }
            if(hoveredMonster.halfDead)
                hoveredMonster = null;
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        if(CInputActionSet.cancel.isJustPressed())
        {
            CInputActionSet.cancel.unpress();
            close();
            return;
        }
        if(!hbTop.hovered && !hbBot.hovered)
        {
            if(potion.canUse())
                Gdx.input.setCursorPosition((int)hbTop.cX, Settings.HEIGHT - (int)hbTop.cY);
            else
                Gdx.input.setCursorPosition((int)hbBot.cX, Settings.HEIGHT - (int)hbBot.cY);
        } else
        if(hbTop.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.down.isJustPressed() || CInputActionSet.altUp.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                Gdx.input.setCursorPosition((int)hbBot.cX, Settings.HEIGHT - (int)hbBot.cY);
        } else
        if(hbBot.hovered && potion.canUse() && (CInputActionSet.up.isJustPressed() || CInputActionSet.down.isJustPressed() || CInputActionSet.altUp.isJustPressed() || CInputActionSet.altDown.isJustPressed()))
            Gdx.input.setCursorPosition((int)hbTop.cX, Settings.HEIGHT - (int)hbTop.cY);
    }

    private void updateTargetMode()
    {
        if(InputHelper.justClickedRight || AbstractDungeon.isScreenUp || (float)InputHelper.mY > (float)Settings.HEIGHT - 80F * Settings.scale || AbstractDungeon.player.hoveredCard != null || (float)InputHelper.mY < 140F * Settings.scale || CInputActionSet.cancel.isJustPressed())
        {
            CInputActionSet.cancel.unpress();
            targetMode = false;
            GameCursor.hidden = false;
        }
        hoveredMonster = null;
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.hb.hovered || m.isDying)
                continue;
            hoveredMonster = m;
            break;
        } while(true);
        if(InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())
        {
            InputHelper.justClickedLeft = false;
            CInputActionSet.select.unpress();
            if(hoveredMonster != null)
            {
                if(AbstractDungeon.player.hasPower("Surrounded"))
                    AbstractDungeon.player.flipHorizontal = hoveredMonster.drawX < AbstractDungeon.player.drawX;
                CardCrawlGame.metricData.potions_floor_usage.add(Integer.valueOf(AbstractDungeon.floorNum));
                potion.use(hoveredMonster);
                if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                    AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                AbstractRelic r;
                for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onUsePotion())
                    r = (AbstractRelic)iterator1.next();

                AbstractDungeon.topPanel.destroyPotion(slot);
                targetMode = false;
                GameCursor.hidden = false;
            }
        }
    }

    private void updateInput()
    {
        if(InputHelper.justClickedLeft)
        {
            InputHelper.justClickedLeft = false;
            if(hbTop.hovered)
                hbTop.clickStarted = true;
            else
            if(hbBot.hovered)
                hbBot.clickStarted = true;
            else
                close();
        }
        if((hbTop.clicked || hbTop.hovered && CInputActionSet.select.isJustPressed()) && (!AbstractDungeon.isScreenUp || potion.canUse()))
        {
            CInputActionSet.select.unpress();
            hbTop.clicked = false;
            if(potion.canUse())
            {
                if(!potion.targetRequired)
                {
                    CardCrawlGame.metricData.potions_floor_usage.add(Integer.valueOf(AbstractDungeon.floorNum));
                    potion.use(null);
                    AbstractRelic r;
                    for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onUsePotion())
                        r = (AbstractRelic)iterator.next();

                    CardCrawlGame.sound.play("POTION_1");
                    AbstractDungeon.topPanel.destroyPotion(slot);
                } else
                {
                    targetMode = true;
                    GameCursor.hidden = true;
                    autoTargetFirst = true;
                }
                close();
            } else
            if(potion.ID == "SmokeBomb" && AbstractDungeon.getCurrRoom().monsters != null)
            {
                Iterator iterator1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    AbstractMonster m = (AbstractMonster)iterator1.next();
                    if(m.hasPower("BackAttack"))
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3F, SmokeBomb.potionStrings.DESCRIPTIONS[1], true));
                } while(true);
            }
        } else
        if((hbBot.clicked || hbBot.hovered && CInputActionSet.select.isJustPressed()) && potion.canDiscard())
        {
            CInputActionSet.select.unpress();
            hbBot.clicked = false;
            CardCrawlGame.sound.play("POTION_DROP_2");
            AbstractDungeon.topPanel.destroyPotion(slot);
            slot = -1;
            potion = null;
            close();
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!isHidden)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.POTION_UI_BG, x - 200F, y - 169F, 200F, 169F, 400F, 338F, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 338, false, false);
            if(hbTop.hovered)
                topHoverColor.a = 0.5F;
            else
                topHoverColor.a = MathHelper.fadeLerpSnap(topHoverColor.a, 0.0F);
            if(hbBot.hovered)
                botHoverColor.a = 0.5F;
            else
                botHoverColor.a = MathHelper.fadeLerpSnap(botHoverColor.a, 0.0F);
            sb.setBlendFunction(770, 1);
            sb.setColor(topHoverColor);
            sb.draw(ImageMaster.POTION_UI_TOP, x - 200F, y - 169F, 200F, 169F, 400F, 338F, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 338, false, false);
            sb.setColor(botHoverColor);
            sb.draw(ImageMaster.POTION_UI_BOT, x - 200F, y - 169F, 200F, 169F, 400F, 338F, Settings.scale, Settings.scale, 0.0F, 0, 0, 400, 338, false, false);
            sb.setBlendFunction(770, 771);
            Color c = Settings.CREAM_COLOR;
            if(!potion.canUse() || AbstractDungeon.isScreenUp)
                c = Color.GRAY;
            if(potion.canUse())
                if(AbstractDungeon.getCurrRoom().event != null)
                {
                    if(!(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain))
                        c = Settings.CREAM_COLOR;
                } else
                {
                    c = Settings.CREAM_COLOR;
                }
            String label = TEXT[1];
            if(potion.isThrown)
                label = TEXT[0];
            FontHelper.renderFontCenteredWidth(sb, FontHelper.buttonLabelFont, label, x, hbTop.cY + 4F * Settings.scale, c);
            FontHelper.renderFontCenteredWidth(sb, FontHelper.buttonLabelFont, TEXT[2], x, hbBot.cY + 12F * Settings.scale, Settings.RED_TEXT_COLOR);
            hbTop.render(sb);
            hbBot.render(sb);
            if(hbTop.hovered)
            {
                if(potion.isThrown)
                    TipHelper.renderGenericTip(x + 174F * Settings.scale, y + 20F * Settings.scale, LABEL[0], MSG[0]);
                else
                    TipHelper.renderGenericTip(x + 174F * Settings.scale, y + 20F * Settings.scale, LABEL[1], MSG[1]);
            } else
            if(hbBot.hovered)
                TipHelper.renderGenericTip(x + 174F * Settings.scale, y + 20F * Settings.scale, LABEL[2], MSG[2]);
        }
        if(targetMode)
        {
            if(hoveredMonster != null)
                hoveredMonster.renderReticle(sb);
            renderTargetingUi(sb);
        }
    }

    private void renderTargetingUi(SpriteBatch sb)
    {
        float x = InputHelper.mX;
        float y = InputHelper.mY;
        controlPoint = new Vector2(potion.posX - (x - potion.posX) / 4F, y + (y - potion.posY - 40F * Settings.scale) / 2.0F);
        if(hoveredMonster == null)
        {
            arrowScale = Settings.scale;
            arrowScaleTimer = 0.0F;
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
        } else
        {
            arrowScaleTimer += Gdx.graphics.getDeltaTime();
            if(arrowScaleTimer > 1.0F)
                arrowScaleTimer = 1.0F;
            arrowScale = Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, arrowScaleTimer);
            sb.setColor(new Color(1.0F, 0.2F, 0.3F, 1.0F));
        }
        Vector2 tmp = new Vector2(controlPoint.x - x, controlPoint.y - y);
        tmp.nor();
        drawCurvedLine(sb, new Vector2(potion.posX, potion.posY - 40F * Settings.scale), new Vector2(x, y), controlPoint);
        sb.draw(ImageMaster.TARGET_UI_ARROW, x - 128F, y - 128F, 128F, 128F, 256F, 256F, arrowScale, arrowScale, tmp.angle() + 90F, 0, 0, 256, 256, false, false);
    }

    private void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control)
    {
        float radius = 7F * Settings.scale;
        for(int i = 0; i < points.length - 1; i++)
        {
            points[i] = (Vector2)Bezier.quadratic(points[i], (float)i / 20F, start, control, end, new Vector2());
            radius += 0.4F * Settings.scale;
            float angle;
            if(i != 0)
            {
                Vector2 tmp = new Vector2(points[i - 1].x - points[i].x, points[i - 1].y - points[i].y);
                angle = tmp.nor().angle() + 90F;
            } else
            {
                Vector2 tmp = new Vector2(controlPoint.x - points[i].x, controlPoint.y - points[i].y);
                angle = tmp.nor().angle() + 270F;
            }
            sb.draw(ImageMaster.TARGET_UI_CIRCLE, points[i].x - 64F, points[i].y - 64F, 64F, 64F, 128F, 128F, radius / 18F, radius / 18F, angle, 0, 0, 128, 128, false, false);
        }

    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private int slot;
    private AbstractPotion potion;
    public boolean isHidden;
    public boolean targetMode;
    private Hitbox hbTop;
    private Hitbox hbBot;
    private Color topHoverColor;
    private Color botHoverColor;
    private float x;
    private float y;
    private static final int SEGMENTS = 20;
    private Vector2 points[];
    private Vector2 controlPoint;
    private float arrowScale;
    private float arrowScaleTimer;
    private static final float ARROW_TARGET_SCALE = 1.2F;
    private static final int TARGET_ARROW_W = 256;
    private AbstractMonster hoveredMonster;
    private boolean autoTargetFirst;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Potion Panel Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        uiStrings = CardCrawlGame.languagePack.getUIString("PotionPopUp");
        TEXT = uiStrings.TEXT;
    }
}
