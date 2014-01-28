package com.mishkapp.education.cgol.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Dimension;

/**
 * Created by Михаил on 10.01.14.
 */
public class Grid {
    private Dimension dimension;

    Grid(){
        this.dimension = new Dimension(MainFrame.RESOLUTION_X/MainFrame.PIXEL_SIZE,
                MainFrame.RESOLUTION_Y/MainFrame.PIXEL_SIZE);
    }

    void render(){
        for(int i = 0; i < dimension.getWidth(); i++){
            GL11.glColor3f(0.5f,0.5f,1.0f);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2i(i*MainFrame.PIXEL_SIZE, 0);
            GL11.glVertex2i(i*MainFrame.PIXEL_SIZE, MainFrame.RESOLUTION_Y);
            GL11.glEnd();
        }
        for(int i = 0; i < dimension.getHeight(); i++){
            GL11.glColor3f(0.5f,0.5f,1.0f);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2i(0, i*MainFrame.PIXEL_SIZE);
            GL11.glVertex2i(MainFrame.RESOLUTION_X, i*MainFrame.PIXEL_SIZE);
            GL11.glEnd();
        }
    }

    Dimension getDimension(){
        return dimension;
    }
}
