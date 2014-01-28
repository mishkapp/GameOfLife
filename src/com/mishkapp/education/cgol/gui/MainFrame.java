package com.mishkapp.education.cgol.gui;

import com.mishkapp.education.cgol.logic.Logic;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Created by Михаил on 10.01.14.
 */
public class MainFrame implements Runnable {
    static int RESOLUTION_X = 1024;
    static int RESOLUTION_Y = 1024;
    static int PIXEL_SIZE = 64;
    static int ICON_SIZE = 15;
    private Grid grid;
    private volatile Pixel[][] pixels;
    private volatile Pixel[][] newPixels;
    private boolean paused = true;
    private boolean escaped = false;
    private boolean benchmarking = false;
    private boolean gridEnabled = true;
    private long fps;
    private long gps;
    private long lastFPS;
    private String infoString;

    public MainFrame(){
        init();

    }

    private void init(){
        this.grid = new Grid();
        pixels = new Pixel[grid.getDimension().getWidth()][grid.getDimension().getHeight()];
        for(int i = 0; i < grid.getDimension().getWidth(); i++){
            for(int j = 0; j < grid.getDimension().getHeight(); j++){
                pixels[i][j] = new Pixel(i, j);
            }
        }
        newPixels = pixels;
        lastFPS = getTime();
    }

    private void initGL(){
        //Display init
        try{
            Display.setDisplayMode(new DisplayMode(RESOLUTION_X, RESOLUTION_Y));
            Display.create();
            Keyboard.create();
        }catch(LWJGLException e){
            e.printStackTrace();
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, RESOLUTION_X, 0, RESOLUTION_Y, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
        //Grid init

    }

    @Override
    public void run() {
        initGL();

        while(!Display.isCloseRequested()){

            if(Keyboard.next()){
                if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
                    paused = !paused;

                }
                if(Keyboard.isKeyDown(Keyboard.KEY_B)){
                    benchmarking = !benchmarking;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_R)){
                    init();
                    paused = true;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_G)){
                    gridEnabled = !gridEnabled;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_L)){
                    Logic.loop = !Logic.loop;
                }
            }

            if(Mouse.isButtonDown(0)){
                int i = Mouse.getX()/ PIXEL_SIZE;
                int j = Mouse.getY()/ PIXEL_SIZE;
                pixels[i][j].setAlive();
            }
            if(Mouse.isButtonDown(1)){
                int i = Mouse.getX()/ PIXEL_SIZE;
                int j = Mouse.getY()/ PIXEL_SIZE;
                pixels[i][j].kill();
            }

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            renderGrid();
            renderPixels();
            renderInfo();
            generateInfoString();
            updateFPS();
            Display.update();

            swapPixels();

            if(!benchmarking){
                Display.sync(60);
            }

        }
        escaped = true;
        try{
            Thread.sleep(50);
        }catch (Exception e){
            e.printStackTrace();
        }

        Display.destroy();

    }

    private void generateInfoString(){
        infoString = " ";
        if(paused){
            infoString += "[PAUSE]";
        }
        if(benchmarking){
            infoString += "[BENCHMARK]";
        }
    }

    private void renderInfo() {
        if(paused){
            renderPauseIcon();
        }
        if(benchmarking){
            renderBenchmarkIcon();
        }
    }

    private void renderGrid() {
        if(gridEnabled){
            grid.render();
        }
    }

    private void renderPixels() {
        for(int i = 0; i < grid.getDimension().getWidth(); i++){
            for(int j = 0; j < grid.getDimension().getHeight(); j++){
                pixels[i][j].render();
            }
        }
    }

    private long getTime() {
        return System.nanoTime() / 1000000;
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps + " GPS: " + gps + infoString);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void setGPS(long gps){
        this.gps = gps;
    }

    public boolean isBenchmarking(){
        return benchmarking;
    }
    public boolean isEscaped(){
        return escaped;
    }

    public void resume(){
        paused = true;
    }

    public void pause(){
        paused = false;
    }
    private void renderPauseIcon(){
        GL11.glColor4d(1, 0, 0, 0.75);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(RESOLUTION_X-ICON_SIZE,RESOLUTION_Y-ICON_SIZE);
        GL11.glVertex2d(RESOLUTION_X,RESOLUTION_Y-ICON_SIZE);
        GL11.glVertex2d(RESOLUTION_X,RESOLUTION_Y);
        GL11.glVertex2d(RESOLUTION_X-ICON_SIZE,RESOLUTION_Y);
        GL11.glEnd();
    }

    private void renderBenchmarkIcon(){
        GL11.glColor4d(0, 0, 1, 0.75);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(RESOLUTION_X-ICON_SIZE-ICON_SIZE,RESOLUTION_Y-ICON_SIZE);
        GL11.glVertex2d(RESOLUTION_X-ICON_SIZE,RESOLUTION_Y-ICON_SIZE);
        GL11.glVertex2d(RESOLUTION_X-ICON_SIZE,RESOLUTION_Y);
        GL11.glVertex2d(RESOLUTION_X-ICON_SIZE-ICON_SIZE,RESOLUTION_Y);
        GL11.glEnd();
    }

    public boolean isPaused(){
        return paused;
    }

    synchronized private void swapPixels(){
        this.pixels = this.newPixels.clone();
    }

    public Pixel[][] getPixels(){
        return pixels;
    }

    synchronized public void setPixels(Pixel[][] pixels){
        this.newPixels = pixels;
    }

    public Pixel[][] getPixelsStub(){
        Pixel[][] pixelsStub = new Pixel[grid.getDimension().getWidth()][grid.getDimension().getHeight()];
        for(int i = 0; i < grid.getDimension().getWidth(); i++){
            for(int j = 0; j < grid.getDimension().getHeight(); j++){
                pixelsStub[i][j] = new Pixel(i, j);
            }
        }
        return pixelsStub;

    }
}
