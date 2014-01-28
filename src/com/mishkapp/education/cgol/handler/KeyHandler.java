package com.mishkapp.education.cgol.handler;

import com.mishkapp.education.cgol.gui.MainFrame;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

/**
 * Created by Михаил on 14.01.14.
 */
public class KeyHandler implements Runnable{
    private MainFrame mainFrame;

    public KeyHandler(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        init();
    }

    private void init(){
        try{
            Keyboard.create();

        }catch(LWJGLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(Keyboard.next()){
            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
                if(mainFrame.isPaused()){
                    mainFrame.resume();
                } else{
                    mainFrame.pause();
                }
            }
        }
    }
}
