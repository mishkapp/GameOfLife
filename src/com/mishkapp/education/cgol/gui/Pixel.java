package com.mishkapp.education.cgol.gui;

import org.lwjgl.opengl.GL11;

/**
 * Created by Михаил on 10.01.14.
 */
public class Pixel {
    private boolean alive = false;
    private int x;
    private int y;

    Pixel(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(){
        alive = true;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }


    public void kill(){
        alive = false;
    }

    void render(){
        if(alive){
            GL11.glColor3d(0,1,0);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2i(x*MainFrame.PIXEL_SIZE, y*MainFrame.PIXEL_SIZE);
            GL11.glVertex2i(x*MainFrame.PIXEL_SIZE +MainFrame.PIXEL_SIZE, y*MainFrame.PIXEL_SIZE);
            GL11.glVertex2i(x*MainFrame.PIXEL_SIZE +MainFrame.PIXEL_SIZE, y*MainFrame.PIXEL_SIZE +MainFrame.PIXEL_SIZE);
            GL11.glVertex2i(x*MainFrame.PIXEL_SIZE, y*MainFrame.PIXEL_SIZE +MainFrame.PIXEL_SIZE);
            GL11.glEnd();
        }
    }
}
