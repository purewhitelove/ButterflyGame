// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapRoomNode.java

package com.megacrit.cardcrawl.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Flight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WingBoots;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.map:
//            MapEdge, DungeonMap, Legend

public class MapRoomNode
{

    public MapRoomNode(int x, int y)
    {
        offsetX = (int)MathUtils.random(-JITTER_X, JITTER_X);
        offsetY = (int)MathUtils.random(-JITTER_Y, JITTER_Y);
        color = NOT_TAKEN_COLOR.cpy();
        oscillateTimer = MathUtils.random(0.0F, 6.28F);
        hb = null;
        scale = 0.5F;
        angle = MathUtils.random(360F);
        parents = new ArrayList();
        fEffects = new ArrayList();
        flameVfxTimer = 0.0F;
        room = null;
        edges = new ArrayList();
        taken = false;
        highlighted = false;
        animWaitTimer = 0.0F;
        hasEmeraldKey = false;
        this.x = x;
        this.y = y;
        float hitbox_w = Settings.isMobile ? 114F * Settings.scale : 64F * Settings.scale;
        hb = new Hitbox(hitbox_w, hitbox_w);
    }

    public boolean hasEdges()
    {
        return !edges.isEmpty();
    }

    public void addEdge(MapEdge e)
    {
        Boolean unique = Boolean.valueOf(true);
        Iterator iterator = edges.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MapEdge otherEdge = (MapEdge)iterator.next();
            if(e.compareTo(otherEdge) == 0)
                unique = Boolean.valueOf(false);
        } while(true);
        if(unique.booleanValue())
            edges.add(e);
    }

    public void delEdge(MapEdge e)
    {
        edges.remove(e);
    }

    public ArrayList getEdges()
    {
        return edges;
    }

    public boolean isConnectedTo(MapRoomNode node)
    {
        for(Iterator iterator = edges.iterator(); iterator.hasNext();)
        {
            MapEdge edge = (MapEdge)iterator.next();
            if(node.x == edge.dstX && node.y == edge.dstY)
                return true;
        }

        return false;
    }

    public boolean wingedIsConnectedTo(MapRoomNode node)
    {
        for(Iterator iterator = edges.iterator(); iterator.hasNext();)
        {
            MapEdge edge = (MapEdge)iterator.next();
            if(ModHelper.isModEnabled("Flight") && node.y == edge.dstY)
                return true;
            if(node.y == edge.dstY && AbstractDungeon.player.hasRelic("WingedGreaves") && AbstractDungeon.player.getRelic("WingedGreaves").counter > 0)
                return true;
        }

        return false;
    }

    public MapEdge getEdgeConnectedTo(MapRoomNode node)
    {
        for(Iterator iterator = edges.iterator(); iterator.hasNext();)
        {
            MapEdge edge = (MapEdge)iterator.next();
            if(node.x == edge.dstX && node.y == edge.dstY)
                return edge;
        }

        return null;
    }

    public void setRoom(AbstractRoom room)
    {
        this.room = room;
    }

    public boolean leftNodeAvailable()
    {
        for(Iterator iterator = edges.iterator(); iterator.hasNext();)
        {
            MapEdge edge = (MapEdge)iterator.next();
            if(edge.dstX < x)
                return true;
        }

        return false;
    }

    public boolean centerNodeAvailable()
    {
        for(Iterator iterator = edges.iterator(); iterator.hasNext();)
        {
            MapEdge edge = (MapEdge)iterator.next();
            if(edge.dstX == x)
                return true;
        }

        return false;
    }

    public boolean rightNodeAvailable()
    {
        for(Iterator iterator = edges.iterator(); iterator.hasNext();)
        {
            MapEdge edge = (MapEdge)iterator.next();
            if(edge.dstX > x)
                return true;
        }

        return false;
    }

    public void addParent(MapRoomNode parent)
    {
        parents.add(parent);
    }

    public ArrayList getParents()
    {
        return parents;
    }

    public String getRoomSymbol(Boolean showSpecificRoomSymbol)
    {
        if(room == null || !showSpecificRoomSymbol.booleanValue())
            return "*";
        else
            return room.getMapSymbol();
    }

    public String toString()
    {
        return (new StringBuilder()).append("(").append(x).append(",").append(y).append("):").append(edges.toString()).toString();
    }

    public AbstractRoom getRoom()
    {
        return room;
    }

    public void update()
    {
        if(animWaitTimer != 0.0F)
        {
            animWaitTimer -= Gdx.graphics.getDeltaTime();
            if(animWaitTimer < 0.0F)
            {
                if(!AbstractDungeon.firstRoomChosen)
                    AbstractDungeon.setCurrMapNode(this);
                else
                    AbstractDungeon.getCurrMapNode().taken = true;
                MapEdge connectedEdge = AbstractDungeon.getCurrMapNode().getEdgeConnectedTo(this);
                if(connectedEdge != null)
                    connectedEdge.markAsTaken();
                animWaitTimer = 0.0F;
                AbstractDungeon.nextRoom = this;
                AbstractDungeon.pathX.add(Integer.valueOf(x));
                AbstractDungeon.pathY.add(Integer.valueOf(y));
                CardCrawlGame.metricData.path_taken.add(AbstractDungeon.nextRoom.getRoom().getMapSymbol());
                if(!AbstractDungeon.isDungeonBeaten)
                {
                    AbstractDungeon.nextRoomTransitionStart();
                    CardCrawlGame.music.fadeOutTempBGM();
                }
            }
        }
        updateEmerald();
        highlighted = false;
        scale = MathHelper.scaleLerpSnap(scale, 0.5F);
        hb.move((float)x * SPACING_X + OFFSET_X + offsetX, (float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY + offsetY);
        hb.update();
        Iterator iterator = edges.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MapEdge edge = (MapEdge)iterator.next();
            if(!edge.taken)
                edge.color = NOT_TAKEN_COLOR;
        } while(true);
        if(AbstractDungeon.getCurrRoom().phase.equals(com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE))
        {
            if(equals(AbstractDungeon.getCurrMapNode()))
            {
                for(Iterator iterator1 = edges.iterator(); iterator1.hasNext();)
                {
                    MapEdge edge = (MapEdge)iterator1.next();
                    edge.color = AVAILABLE_COLOR;
                }

            }
            boolean normalConnection = AbstractDungeon.getCurrMapNode().isConnectedTo(this);
            boolean wingedConnection = AbstractDungeon.getCurrMapNode().wingedIsConnectedTo(this);
            if(normalConnection || Settings.isDebug || wingedConnection)
            {
                if(hb.hovered)
                {
                    if(hb.justHovered)
                        playNodeHoveredSound();
                    if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP && AbstractDungeon.dungeonMapScreen.clicked && animWaitTimer <= 0.0F)
                    {
                        playNodeSelectedSound();
                        AbstractDungeon.dungeonMapScreen.clicked = false;
                        AbstractDungeon.dungeonMapScreen.clickTimer = 0.0F;
                        if(!normalConnection && wingedConnection && AbstractDungeon.player.hasRelic("WingedGreaves"))
                        {
                            AbstractDungeon.player.getRelic("WingedGreaves").counter--;
                            if(AbstractDungeon.player.getRelic("WingedGreaves").counter <= 0)
                                AbstractDungeon.player.getRelic("WingedGreaves").setCounter(-2);
                        }
                        AbstractDungeon.topLevelEffects.add(new MapCircleEffect((float)x * SPACING_X + OFFSET_X + offsetX, (float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY + offsetY, angle));
                        if(!Settings.FAST_MODE)
                            AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
                        animWaitTimer = 0.25F;
                        if(room instanceof EventRoom)
                            CardCrawlGame.mysteryMachine++;
                    }
                    highlighted = true;
                } else
                {
                    color = AVAILABLE_COLOR.cpy();
                }
                oscillateColor();
            } else
            if(hb.hovered && !taken)
            {
                scale = 1.0F;
                color = AVAILABLE_COLOR.cpy();
            } else
            {
                color = NOT_TAKEN_COLOR.cpy();
            }
        } else
        if(hb.hovered)
        {
            scale = 1.0F;
            color = AVAILABLE_COLOR.cpy();
        } else
        {
            color = NOT_TAKEN_COLOR.cpy();
        }
        if(!AbstractDungeon.firstRoomChosen && y == 0 && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE)
        {
            if(hb.hovered)
            {
                if(hb.justHovered)
                    playNodeHoveredSound();
                if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MAP && (CInputActionSet.select.isJustPressed() || AbstractDungeon.dungeonMapScreen.clicked))
                {
                    playNodeSelectedSound();
                    AbstractDungeon.dungeonMapScreen.clicked = false;
                    AbstractDungeon.dungeonMapScreen.clickTimer = 0.0F;
                    AbstractDungeon.dungeonMapScreen.dismissable = true;
                    if(!AbstractDungeon.firstRoomChosen)
                        AbstractDungeon.firstRoomChosen = true;
                    AbstractDungeon.topLevelEffects.add(new MapCircleEffect((float)x * SPACING_X + OFFSET_X + offsetX, (float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY + offsetY, angle));
                    AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
                    animWaitTimer = 0.25F;
                }
                highlighted = true;
            } else
            if(y != 0)
            {
                highlighted = true;
                scale = 1.0F;
            } else
            {
                color = AVAILABLE_COLOR.cpy();
            }
            oscillateColor();
        }
        if(equals(AbstractDungeon.getCurrMapNode()))
        {
            color = AVAILABLE_COLOR.cpy();
            scale = MathHelper.scaleLerpSnap(scale, 0.5F);
        }
    }

    private void updateEmerald()
    {
        if(Settings.isFinalActAvailable && hasEmeraldKey)
        {
            flameVfxTimer -= Gdx.graphics.getDeltaTime();
            if(flameVfxTimer < 0.0F)
            {
                flameVfxTimer = MathUtils.random(0.2F, 0.4F);
                fEffects.add(new FlameAnimationEffect(hb));
            }
            Iterator i = fEffects.iterator();
            do
            {
                if(!i.hasNext())
                    break;
                FlameAnimationEffect e = (FlameAnimationEffect)i.next();
                if(e.isDone)
                {
                    e.dispose();
                    i.remove();
                }
            } while(true);
            FlameAnimationEffect e;
            for(Iterator iterator = fEffects.iterator(); iterator.hasNext(); e.update())
                e = (FlameAnimationEffect)iterator.next();

        }
    }

    private void playNodeHoveredSound()
    {
        int roll = MathUtils.random(3);
        switch(roll)
        {
        case 0: // '\0'
            CardCrawlGame.sound.play("MAP_HOVER_1");
            break;

        case 1: // '\001'
            CardCrawlGame.sound.play("MAP_HOVER_2");
            break;

        case 2: // '\002'
            CardCrawlGame.sound.play("MAP_HOVER_3");
            break;

        default:
            CardCrawlGame.sound.play("MAP_HOVER_4");
            break;
        }
    }

    private void playNodeSelectedSound()
    {
        int roll = MathUtils.random(3);
        switch(roll)
        {
        case 0: // '\0'
            CardCrawlGame.sound.play("MAP_SELECT_1");
            break;

        case 1: // '\001'
            CardCrawlGame.sound.play("MAP_SELECT_2");
            break;

        case 2: // '\002'
            CardCrawlGame.sound.play("MAP_SELECT_3");
            break;

        default:
            CardCrawlGame.sound.play("MAP_SELECT_4");
            break;
        }
    }

    private void oscillateColor()
    {
        if(!taken)
        {
            oscillateTimer += Gdx.graphics.getDeltaTime() * 5F;
            color.a = 0.66F + (MathUtils.cos(oscillateTimer) + 1.0F) / 6F;
            scale = 0.25F + color.a;
        } else
        {
            scale = MathHelper.scaleLerpSnap(scale, Settings.scale);
        }
    }

    public void render(SpriteBatch sb)
    {
        MapEdge edge;
        for(Iterator iterator = edges.iterator(); iterator.hasNext(); edge.render(sb))
            edge = (MapEdge)iterator.next();

        renderEmeraldVfx(sb);
        if(highlighted)
            sb.setColor(new Color(0.9F, 0.9F, 0.9F, 1.0F));
        else
            sb.setColor(OUTLINE_COLOR);
        boolean legendHovered = AbstractDungeon.dungeonMapScreen.map.legend.isIconHovered(getRoomSymbol(Boolean.valueOf(true)));
        if(legendHovered)
        {
            scale = 0.68F;
            sb.setColor(Color.LIGHT_GRAY);
        }
        if(!Settings.isMobile)
            sb.draw(room.getMapImgOutline(), (((float)x * SPACING_X + OFFSET_X) - 64F) + offsetX, (((float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY) - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        else
            sb.draw(room.getMapImgOutline(), (((float)x * SPACING_X + OFFSET_X) - 64F) + offsetX, (((float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY) - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * Settings.scale * 2.0F, scale * Settings.scale * 2.0F, 0.0F, 0, 0, 128, 128, false, false);
        if(taken)
            sb.setColor(AVAILABLE_COLOR);
        else
            sb.setColor(color);
        if(legendHovered)
            sb.setColor(AVAILABLE_COLOR);
        if(!Settings.isMobile)
            sb.draw(room.getMapImg(), (((float)x * SPACING_X + OFFSET_X) - 64F) + offsetX, (((float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY) - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        else
            sb.draw(room.getMapImg(), (((float)x * SPACING_X + OFFSET_X) - 64F) + offsetX, (((float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY) - 64F) + offsetY, 64F, 64F, 128F, 128F, scale * Settings.scale * 2.0F, scale * Settings.scale * 2.0F, 0.0F, 0, 0, 128, 128, false, false);
        if(taken || AbstractDungeon.firstRoomChosen && equals(AbstractDungeon.getCurrMapNode()))
        {
            sb.setColor(AVAILABLE_COLOR);
            if(!Settings.isMobile)
                sb.draw(ImageMaster.MAP_CIRCLE_5, (((float)x * SPACING_X + OFFSET_X) - 96F) + offsetX, (((float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY) - 96F) + offsetY, 96F, 96F, 192F, 192F, (scale * 0.95F + 0.2F) * Settings.scale, (scale * 0.95F + 0.2F) * Settings.scale, angle, 0, 0, 192, 192, false, false);
            else
                sb.draw(ImageMaster.MAP_CIRCLE_5, (((float)x * SPACING_X + OFFSET_X) - 96F) + offsetX, (((float)y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY) - 96F) + offsetY, 96F, 96F, 192F, 192F, (scale * 0.95F + 0.2F) * Settings.scale * 2.0F, (scale * 0.95F + 0.2F) * Settings.scale * 2.0F, angle, 0, 0, 192, 192, false, false);
        }
        if(hb != null)
            hb.render(sb);
    }

    private void renderEmeraldVfx(SpriteBatch sb)
    {
        if(Settings.isFinalActAvailable && hasEmeraldKey)
        {
            FlameAnimationEffect e;
            for(Iterator iterator = fEffects.iterator(); iterator.hasNext(); e.render(sb, scale))
                e = (FlameAnimationEffect)iterator.next();

        }
    }

    private static final int IMG_WIDTH;
    public static final float OFFSET_X;
    private static final float OFFSET_Y;
    private static final float SPACING_X;
    private static final float JITTER_X;
    private static final float JITTER_Y;
    public float offsetX;
    public float offsetY;
    public static final Color AVAILABLE_COLOR = new Color(0.09F, 0.13F, 0.17F, 1.0F);
    private static final Color NOT_TAKEN_COLOR = new Color(0.34F, 0.34F, 0.34F, 1.0F);
    private static final Color OUTLINE_COLOR = Color.valueOf("8c8c80ff");
    public Color color;
    private float oscillateTimer;
    public Hitbox hb;
    private static final int W = 128;
    private static final int O_W = 192;
    private float scale;
    private float angle;
    private ArrayList parents;
    private ArrayList fEffects;
    private float flameVfxTimer;
    public int x;
    public int y;
    public AbstractRoom room;
    private ArrayList edges;
    public boolean taken;
    public boolean highlighted;
    private float animWaitTimer;
    public boolean hasEmeraldKey;
    private static final float ANIM_WAIT_TIME = 0.25F;

    static 
    {
        IMG_WIDTH = (int)(Settings.xScale * 64F);
        OFFSET_X = Settings.isMobile ? 496F * Settings.xScale : 560F * Settings.xScale;
        OFFSET_Y = 180F * Settings.scale;
        SPACING_X = Settings.isMobile ? (float)IMG_WIDTH * 2.2F : (float)IMG_WIDTH * 2.0F;
        JITTER_X = Settings.isMobile ? 13F * Settings.xScale : 27F * Settings.xScale;
        JITTER_Y = Settings.isMobile ? 18F * Settings.xScale : 37F * Settings.xScale;
    }
}
