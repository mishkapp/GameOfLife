package com.mishkapp.education.cgol;

import com.mishkapp.education.cgol.gui.MainFrame;
import com.mishkapp.education.cgol.logic.Logic;
import jcuda.Pointer;
import jcuda.runtime.JCuda;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by Михаил on 10.01.14.
 */
public class Main {
    private Main(){
        init();
    }

    public static void main(String[] args){
        new Main();

    }

    private void init(){
        MainFrame mainFrame = new MainFrame();
        Logic logic = new Logic(mainFrame);

        new Thread(mainFrame).start();
        new Thread(logic).start();
    }
}
