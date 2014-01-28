package com.mishkapp.education.cgol.logic;

import com.mishkapp.education.cgol.gui.Pixel;

/**
 * Created by Михаил on 15.01.14.
 */
public class MatrixHandler implements Runnable {
    private Pixel[][] sourcePixels;
    private Pixel[][] newPixels;
    private int threadNumber;
    private int echelon;
    private int width;
    private int height;
    private int echelonSize;

    MatrixHandler(Pixel[][] sourcePixels, Pixel[][] newPixels, int threadNumber, int width, int height, int echelon){
        this.sourcePixels = sourcePixels;
        this.newPixels = newPixels;
        this.threadNumber = threadNumber;
        this.width = width;
        this.height = height;
        this.echelon = echelon;
        this.echelonSize = width / echelon;

    }
    @Override
    public void run() {
        if(echelon == 1){
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    int neighborhoods = getNeighborhoods(i,j,width-1,height-1, sourcePixels);
                    if(sourcePixels[i][j].isAlive()){
                        if(neighborhoods >= 2 && neighborhoods <= 3){
                            newPixels[i][j].setAlive();
                        }else{
                            newPixels[i][j].kill();
                        }
                    }else{
                        if(neighborhoods == 3){
                            newPixels[i][j].setAlive();
                        }else{
                            newPixels[i][j].kill();
                        }
                    }
                }
            }
        }else{
            int startI = (threadNumber  % echelon) * echelonSize;
            int startJ = (threadNumber  / echelon) * echelonSize;
            for(int i = startI; i < startI + echelonSize; i++ ){
                for(int j = startJ; j < startJ + echelonSize; j++){
                    int neighborhoods = getNeighborhoods(i,j,width-1,height-1, sourcePixels);
                    if(sourcePixels[i][j].isAlive()){
                        if(neighborhoods >= 2 && neighborhoods <= 3){
                            newPixels[i][j].setAlive();
                        }else{
                            newPixels[i][j].kill();
                        }
                    }else{
                        if(neighborhoods == 3){
                            newPixels[i][j].setAlive();
                        }else{
                            newPixels[i][j].kill();
                        }
                    }
                }

            }
        }
    }

    //TODO: FIX:Govnokod
    private int getNeighborhoods(int i, int j, int maxI, int maxJ, Pixel[][] pixels){
        int neighborhoods = 0;

        if(i != 0 && i != maxI && j != 0 && j != maxJ){
            if(pixels[i][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j+1].isAlive()){
                neighborhoods +=1;
            }
        }


        if(j == 0 && i != 0 && i != maxI){
            if(pixels[i-1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(Logic.loop){
                if(pixels[i-1][maxJ].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[i][maxJ].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[i+1][maxJ].isAlive()){
                    neighborhoods +=1;
                }
            }
        }
        if(j == maxJ && i != 0 && i != maxI){
            if(pixels[i-1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(Logic.loop){
                if(pixels[i-1][0].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[i][0].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[i+1][0].isAlive()){
                    neighborhoods +=1;
                }
            }
        }
        if(i == 0 && j != 0 && j != maxJ){
            if(pixels[i][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i+1][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(Logic.loop){
                if(pixels[maxI][j-1].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[maxI][j].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[maxI][j+1].isAlive()){
                    neighborhoods +=1;
                }
            }
        }
        if(i == maxI && j != 0 && j != maxJ){
            if(pixels[i][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j-1].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j].isAlive()){
                neighborhoods +=1;
            }
            if(pixels[i-1][j+1].isAlive()){
                neighborhoods +=1;
            }
            if(Logic.loop){
                if(pixels[0][j-1].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[0][j].isAlive()){
                    neighborhoods +=1;
                }
                if(pixels[0][j+1].isAlive()){
                    neighborhoods +=1;
                }
            }
        }
        //CORNERS
        if(i == 0 && j == 0){
            if(pixels[i+1][j].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i+1][j+1].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i][j+1].isAlive()){
                neighborhoods += 1;
            }
            if(Logic.loop){
                if(pixels[i+1][maxJ].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[i][maxJ].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[maxI][j].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[maxI][j+1].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[maxI][maxJ].isAlive()){
                    neighborhoods += 1;
                }
            }
        }

        if(i == maxI && j == 0){
            if(pixels[i-1][j].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i-1][j+1].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i][j+1].isAlive()){
                neighborhoods += 1;
            }
            if(Logic.loop){
                if(pixels[i-1][maxJ].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[i][maxJ].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[0][j].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[0][j+1].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[0][maxJ].isAlive()){
                    neighborhoods += 1;
                }
            }
        }

        if(i == maxI && j == maxJ){
            if(pixels[i-1][j].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i-1][j-1].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i][j-1].isAlive()){
                neighborhoods += 1;
            }
            if(Logic.loop){
                if(pixels[i-1][0].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[i][0].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[0][j].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[0][j-1].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[0][0].isAlive()){
                    neighborhoods += 1;
                }
            }
        }
        if(i == 0 && j == maxJ){
            if(pixels[i+1][j].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i+1][j-1].isAlive()){
                neighborhoods += 1;
            }
            if(pixels[i][j-1].isAlive()){
                neighborhoods += 1;
            }
            if(Logic.loop){
                if(pixels[i+1][0].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[i][0].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[maxI][j].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[maxI][j-1].isAlive()){
                    neighborhoods += 1;
                }
                if(pixels[maxI][0].isAlive()){
                    neighborhoods += 1;
                }
            }
        }
        //END CORNERS
        return neighborhoods;
    }
}
