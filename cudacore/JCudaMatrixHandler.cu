#include <stdio.h>

extern "C"
__device__ int getNeighborhoods(int i, int j, int maxI, int maxJ, short *pixels){

        int neighborhoods = 0;
        int width = maxI+1;

        if(i != 0 && i != maxI && j != 0 && j != maxJ){
            if(pixels[i + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j+1) * width] == 1){
                neighborhoods +=1;
            }
        }


        if(j == 0 && i != 0 && i != maxI){
            if(pixels[i-1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(true){
                if(pixels[i-1 + (maxJ) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[i + (maxJ) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[i+1 + (maxJ) * width] == 1){
                    neighborhoods +=1;
                }
            }
        }
        if(j == maxJ && i != 0 && i != maxI){
            if(pixels[i-1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(true){
                if(pixels[i-1 + (0) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[i + (0) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[i+1 + (0) * width] == 1){
                    neighborhoods +=1;
                }
            }
        }
        if(i == 0 && j != 0 && j != maxJ){
            if(pixels[i + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i+1 + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(true){
                if(pixels[maxI + (j-1) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[maxI + (j) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[maxI + (j+1) * width] == 1){
                    neighborhoods +=1;
                }
            }
        }
        if(i == maxI && j != 0 && j != maxJ){
            if(pixels[i + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j-1) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j) * width] == 1){
                neighborhoods +=1;
            }
            if(pixels[i-1 + (j+1) * width] == 1){
                neighborhoods +=1;
            }
            if(true){
                if(pixels[0 + (j-1) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[0 + (j) * width] == 1){
                    neighborhoods +=1;
                }
                if(pixels[0 + (j+1) * width] == 1){
                    neighborhoods +=1;
                }
            }
        }
        //CORNERS
        if(i == 0 && j == 0){
            if(pixels[i+1 + (j) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i+1 + (j+1) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i + (j+1) * width] == 1){
                neighborhoods += 1;
            }
            if(true){
                if(pixels[i+1 + (maxJ) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[i + (maxJ) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[maxI + (j) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[maxI + (j+1) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[maxI + (maxJ) * width] == 1){
                    neighborhoods += 1;
                }
            }
        }

        if(i == maxI && j == 0){
            if(pixels[i-1 + (j) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i-1 + (j+1) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i + (j+1) * width] == 1){
                neighborhoods += 1;
            }
            if(true){
                if(pixels[i-1 + (maxJ) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[i + (maxJ) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[0 + (j) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[0 + (j+1) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[0 + (maxJ) * width] == 1){
                    neighborhoods += 1;
                }
            }
        }

        if(i == maxI && j == maxJ){
            if(pixels[i-1 + (j) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i-1 + (j-1) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i + (j-1) * width] == 1){
                neighborhoods += 1;
            }
            if(true){
                if(pixels[i-1 + (0) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[i + (0) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[0 + (j) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[0 + (j-1) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[0 + (0) * width] == 1){
                    neighborhoods += 1;
                }
            }
        }
        if(i == 0 && j == maxJ){
            if(pixels[i+1 + (j) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i+1 + (j-1) * width] == 1){
                neighborhoods += 1;
            }
            if(pixels[i + (j-1) * width] == 1){
                neighborhoods += 1;
            }
            if(true){
                if(pixels[i+1 + (0) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[i + (0) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[maxI + (j) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[maxI + (j-1) * width] == 1){
                    neighborhoods += 1;
                }
                if(pixels[maxI] == 1){
                    neighborhoods += 1;
                }
            }
        }
        //END CORNERS
        return neighborhoods;
    }
extern "C"
__global__ void handle(short *sourcePixels, short *newPixels, int width, int height, int echelon){
    int i = threadIdx.x;
    int j = i / width;

    int neighborhoods = getNeighborhoods(i,j,width-1,height-1, sourcePixels);
    if(sourcePixels[i] == 1){
        if(neighborhoods >= 2 && neighborhoods <= 3){
            newPixels[i] = 1;
        }else{
            newPixels[i] = 0;
        }
    }else{
        if(neighborhoods == 3){
            newPixels[i] = 1;
        }else{
            newPixels[i] = 0;
        }
    }
}
// extern "C"
// __host__ void start(short *sourcePixels, short *newPixels, int *width, int *height, int *echelon){

// 	int N = *height * *width;

// 	int *d_width;
// 	int *d_height;
// 	int *d_echelon;

//     short *d_sourcePixels;
//     short *d_newPixels;

// 	cudaMalloc((void **)&d_width, sizeof(int));
// 	cudaMalloc((void **)&d_height, sizeof(int));
// 	cudaMalloc((void **)&d_echelon, sizeof(int)); //??
//     cudaMalloc((void **)&d_sourcePixels, sizeof(short) * N);
//     cudaMalloc((void **)&d_newPixels, sizeof(short) * N);
    

// 	cudaMemcpy(d_width, &width, sizeof(int), cudaMemcpyHostToDevice);
// 	cudaMemcpy(d_height, &height, sizeof(int), cudaMemcpyHostToDevice);
// 	cudaMemcpy(d_echelon, &echelon, sizeof(int), cudaMemcpyHostToDevice);
//     cudaMemcpy(d_sourcePixels, &sourcePixels, sizeof(short) * N, cudaMemcpyHostToDevice);
//     cudaMemcpy(d_newPixels, &sourcePixels, sizeof(short) * N, cudaMemcpyHostToDevice);

// 	handle<<<1,N>>>(d_sourcePixels, d_newPixels, *d_width, *d_height, *d_echelon);

//     cudaMemcpy(newPixels, &d_newPixels, sizeof(short) * N, cudaMemcpyDeviceToHost);
//     // cudaFree();
// }

