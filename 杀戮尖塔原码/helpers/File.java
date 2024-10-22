// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   File.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            FileWriteValidationError

public class File
{

    public File(String filepath, String data)
    {
        this.filepath = filepath;
        this.data = data.getBytes(StandardCharsets.UTF_8);
    }

    public String getFilepath()
    {
        return filepath;
    }

    public void save()
    {
        int MAX_RETRIES = 5;
        String localStoragePath = Gdx.files.getLocalStoragePath();
        Path destination = FileSystems.getDefault().getPath((new StringBuilder()).append(localStoragePath).append(filepath).toString(), new String[0]);
        Path backup = FileSystems.getDefault().getPath((new StringBuilder()).append(localStoragePath).append(filepath).append(".backUp").toString(), new String[0]);
        Path parent = destination.getParent();
        logger.debug((new StringBuilder()).append("Attempting to save file=").append(destination).toString());
        if(java.nio.file.Files.exists(parent, new LinkOption[0]))
        {
            if(java.nio.file.Files.exists(destination, new LinkOption[0]))
            {
                copyAndValidate(destination, backup, 5);
                deleteFile(destination);
            }
        } else
        {
            try
            {
                java.nio.file.Files.createDirectories(parent, new FileAttribute[0]);
            }
            catch(IOException e)
            {
                logger.info("Failed to create directory", e);
            }
        }
        boolean success = writeAndValidate(destination, data, 5);
        if(success)
            logger.debug((new StringBuilder()).append("Successfully saved file=").append(destination.toString()).toString());
    }

    private static void copyAndValidate(Path source, Path target, int retry)
    {
        byte sourceData[] = new byte[0];
        try
        {
            sourceData = java.nio.file.Files.readAllBytes(source);
            java.nio.file.Files.copy(source, target, new CopyOption[] {
                StandardCopyOption.REPLACE_EXISTING
            });
        }
        catch(IOException e)
        {
            if(retry <= 0)
            {
                logger.info((new StringBuilder()).append("Failed to copy ").append(source.toString()).append(" to ").append(target.toString()).append(", but the retry expired").toString(), e);
                return;
            }
            logger.info((new StringBuilder()).append("Failed to copy file=").append(source.toString()).toString(), e);
            sleep(300);
            copyAndValidate(source, target, retry - 1);
        }
        Exception err = validateWrite(target, sourceData);
        if(err != null)
        {
            if(retry <= 0)
            {
                logger.info((new StringBuilder()).append("Failed to copy ").append(source.toString()).append(" to ").append(target.toString()).append(", but the retry expired").toString(), err);
                return;
            }
            logger.info((new StringBuilder()).append("Failed to copy file=").append(source.toString()).toString(), err);
            sleep(300);
            copyAndValidate(source, target, retry - 1);
        }
    }

    private static void sleep(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e)
        {
            logger.info(e);
        }
    }

    private static void deleteFile(Path filepath)
    {
        try
        {
            java.nio.file.Files.delete(filepath);
        }
        catch(IOException e)
        {
            logger.info("Failed to delete", e);
        }
    }

    private static Exception validateWrite(Path filepath, byte inMemoryBytes[])
    {
        byte writtenBytes[];
        try
        {
            writtenBytes = java.nio.file.Files.readAllBytes(filepath);
        }
        catch(IOException e)
        {
            return e;
        }
        boolean valid = Arrays.equals(writtenBytes, inMemoryBytes);
        if(!valid)
            return new FileWriteValidationError((new StringBuilder()).append("Not valid: written=").append(Arrays.toString(writtenBytes)).append(" vs inMemory=").append(Arrays.toString(inMemoryBytes)).toString());
        else
            return null;
    }

    static boolean writeAndValidate(Path filepath, byte data[], int retry)
    {
        try
        {
            java.nio.file.Files.write(filepath, data, new OpenOption[] {
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.SYNC
            });
        }
        catch(Exception ex)
        {
            if(retry <= 0)
            {
                logger.info((new StringBuilder()).append("Failed to write file ").append(filepath.toString()).append(", but the retry expired.").toString(), ex);
                return false;
            } else
            {
                logger.info((new StringBuilder()).append("Failed to validate source=").append(filepath.toString()).append(", retrying...").toString(), ex);
                sleep(300);
                return writeAndValidate(filepath, data, retry - 1);
            }
        }
        Exception err = validateWrite(filepath, data);
        if(err != null)
        {
            if(retry <= 0)
            {
                logger.info((new StringBuilder()).append("Failed to write file ").append(filepath.toString()).append(", but the retry expired.").toString(), err);
                return false;
            } else
            {
                logger.info((new StringBuilder()).append("Failed to validate source=").append(filepath.toString()).append(", retrying...").toString(), err);
                sleep(300);
                return writeAndValidate(filepath, data, retry - 1);
            }
        } else
        {
            return true;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/File.getName());
    private String filepath;
    private byte data[];

}
