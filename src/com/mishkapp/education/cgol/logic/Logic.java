package com.mishkapp.education.cgol.logic;

import static jcuda.driver.JCudaDriver.*;
import com.mishkapp.education.cgol.gui.MainFrame;
import com.mishkapp.education.cgol.gui.Pixel;
import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.driver.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Михаил on 10.01.14.
 */
public class Logic implements Runnable {
    MainFrame mainFrame;
    public static boolean loop = true;
    private long lastGPS;
    private long gps;
    private int echelon = 2;
    private boolean cudaEnabled = true;
    private CUfunction cuFunction;

    public Logic(MainFrame mainFrame){
        this.mainFrame = mainFrame;

        lastGPS = getTime();

//        JCudaDriver.setExceptionsEnabled(true);
//
//        cuInit(0);
//        CUcontext pctx = new CUcontext();
//        CUdevice dev = new CUdevice();
//        cuDeviceGet(dev, 0);
//        cuCtxCreate(pctx, 0, dev);
//
//        module = new CUmodule();
//        cuModuleLoad(module, "JCudaMatrixHandler.ptx");
//
//        cuFunction = new CUfunction();
//        cuModuleGetFunction(cuFunction, module, "start");

        // Enable exceptions and omit all subsequent error checks
        try{

            JCudaDriver.setExceptionsEnabled(true);

            // Create the PTX file by calling the NVCC
            String ptxFileName = preparePtxFile("./cudacore/JCudaMatrixHandler.cu");

            // Initialize the driver and create a context for the first device.
            cuInit(0);
            CUdevice device = new CUdevice();

            cuDeviceGet(device, 0);
            CUcontext context = new CUcontext();
            cuCtxCreate(context, 0, device);

            // Load the ptx file.
            CUmodule module = new CUmodule();
            cuModuleLoad(module, ptxFileName);

            // Obtain a function pointer to the "add" function.
            cuFunction = new CUfunction();
            cuModuleGetFunction(cuFunction, module, "handle");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String preparePtxFile(String cuFileName) throws IOException
    {
        int endIndex = cuFileName.lastIndexOf('.');
        if (endIndex == -1)
        {
            endIndex = cuFileName.length()-1;
        }
        String ptxFileName = cuFileName.substring(0, endIndex+1)+"ptx";

        File cuFile = new File(cuFileName);
        if (!cuFile.exists())
        {
            throw new IOException("Input file not found: "+cuFileName);
        }
        String modelString = "-m"+System.getProperty("sun.arch.data.model");
        String command =
                "nvcc " + modelString + " -ptx "+
                        cuFile.getPath()+" -o "+ptxFileName;

        System.out.println("Executing\n"+command);
        Process process = Runtime.getRuntime().exec(command);

        String errorMessage =
                new String(toByteArray(process.getErrorStream()));
        String outputMessage =
                new String(toByteArray(process.getInputStream()));
        int exitValue;
        try
        {
            exitValue = process.waitFor();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new IOException(
                    "Interrupted while waiting for nvcc output", e);
        }

        if (exitValue != 0)
        {
            System.out.println("nvcc process exitValue "+exitValue);
            System.out.println("errorMessage:\n"+errorMessage);
            System.out.println("outputMessage:\n"+outputMessage);
            throw new IOException(
                    "Could not create .ptx file: "+errorMessage);
        }

        System.out.println("Finished creating PTX file");
        return ptxFileName;
    }

    private static byte[] toByteArray(InputStream inputStream)
            throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buffer[] = new byte[8192];
        while (true)
        {
            int read = inputStream.read(buffer);
            if (read == -1)
            {
                break;
            }
            baos.write(buffer, 0, read);
        }
        return baos.toByteArray();
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public void updateGPS() {
        if (getTime() - lastGPS > 1000) {
            mainFrame.setGPS(gps);
            gps = 0;
            lastGPS += 1000;
        }
        gps++;
    }

    @Override
    public void run() {
        while(true){

            if(!mainFrame.isPaused()){
                if(cudaEnabled){
                    handleCUDA();
                }else{
                    handle();
                }
                updateGPS();
            } else {
                mainFrame.setGPS(0);
            }
            if(mainFrame.isEscaped()){
                break;
            }
            if(!mainFrame.isBenchmarking()){
                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized void handleCUDA(){
        Pixel[][] currentPixels = mainFrame.getPixels();
        Pixel[][] newPixels = mainFrame.getPixelsStub();

        int width = newPixels.length;
        int height = newPixels[0].length;

        int[] pixels = new int[width * height];
        int[] pixelsStub = new int[width * height];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                pixels[i + j * width] = currentPixels[i][j].isAlive()?1:0;
            }
        }
        //CUDA STARTS HERE!

        cuInit(0);
        CUdevice device = new CUdevice();

        cuDeviceGet(device, 0);
        CUcontext context = new CUcontext();
        cuCtxCreate(context, 0, device);

        CUdeviceptr devicePixels = new CUdeviceptr();
        cuMemAlloc(devicePixels, width*height*Sizeof.INT);
        cuMemcpyHtoD(devicePixels, Pointer.to(pixels), width*height*Sizeof.INT);

        CUdeviceptr devicePixelStub = new CUdeviceptr();
        cuMemAlloc(devicePixelStub, width*height*Sizeof.INT);
        cuMemcpyHtoD(devicePixelStub,Pointer.to(pixelsStub), width*height*Sizeof.INT);

        Pointer kernelParameters = Pointer.to(
                Pointer.to(devicePixels),
                Pointer.to(devicePixelStub),
                Pointer.to(new int[]{width}),
                Pointer.to(new int[]{height}),
                Pointer.to(new int[]{echelon})
        );

//        int blockSizeX = 256;
//        int gridSizeX = (int)Math.ceil((double)(width*height)/blockSizeX);

        cuLaunchKernel(cuFunction,
                1, 1, 1,
                height*width, 1,1,
                0, null,
                kernelParameters, null);
        cuCtxSynchronize();


//
//        CUdeviceptr deviceInputPixels = new CUdeviceptr();
//        cuMemAlloc(deviceInputPixels,width*height*Sizeof.BYTE);
//        cuMemcpyHtoD(deviceInputPixels,Pointer.to(pixels),width*height*Sizeof.BYTE);
//
//        CUdeviceptr deviceInputPixelsStub = new CUdeviceptr();
//        cuMemAlloc(deviceInputPixelsStub,width*height*Sizeof.BYTE);
//        cuMemcpyHtoD(deviceInputPixelsStub,Pointer.to(pixelsStub),width*height*Sizeof.BYTE);
//
//        Pointer kernelParameters = Pointer.to(
//                Pointer.to(deviceInputPixels),
//                Pointer.to(deviceInputPixelsStub),
//                Pointer.to(new int[]{width}),
//                Pointer.to(new int[]{height}),
//                Pointer.to(new int[]{echelon})
//        );
//
//        JCudaDriver.cuLaunchKernel( cuFunction,
//                256, 1, 1,
//                width*height, 1, 1,
//                0, null,
//                kernelParameters ,null
//        );


//
//        JCuda.cudaFree(pixelsPointer);
//        JCuda.cudaFree(pixelStubPointer);

        //AND ENDS HERE!
        boolean tempPixel;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                tempPixel = pixelsStub[i + j * width] == 1;
                newPixels[i][j].setAlive(tempPixel);
            }
        }

        mainFrame.setPixels(newPixels);
    }

    private synchronized void handle(){
        Pixel[][] currentPixels = mainFrame.getPixels();
        Pixel[][] newPixels = mainFrame.getPixelsStub();
        int width = newPixels.length;
        int height = newPixels[0].length;

        Thread[] threads = new Thread[width];

        for(int i = 0; i < echelon * echelon; i++){
            threads[i] = new Thread(new MatrixHandler(currentPixels, newPixels, i, width, height, echelon));
            threads[i].start();
        }
        for(int i = 0; i < echelon * echelon; i++){
            try{
                threads[i].join();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        mainFrame.setPixels(newPixels);
    }
}