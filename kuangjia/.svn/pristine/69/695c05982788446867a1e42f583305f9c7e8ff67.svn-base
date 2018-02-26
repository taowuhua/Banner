package com.bank.quickpay.utils;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;

import java.util.List;

public class CameraUtils {

	public static final int CAMERA_FACING_BACK = 0;  
    public static final int CAMERA_FACING_FRONT = 1;  
    public static final int CAMERA_NONE = 2;  
      /**
       * 后置摄像头
       * @return
       */
     public static int HasBackCamera()  
      {  
          int numberOfCameras = Camera.getNumberOfCameras();    
            CameraInfo cameraInfo = new CameraInfo();    
            for (int i = 0; i < numberOfCameras; i++) {    
                Camera.getCameraInfo(i, cameraInfo);    
                if (cameraInfo.facing == CAMERA_FACING_BACK) {    
                    return i;    
                }    
            }    
            return 2;  
      }  
     	/**
     	 * 前置摄像头
     	 * @return
     	 */
      public static int HasFrontCamera()  
      {  
          int numberOfCameras = Camera.getNumberOfCameras();    
            CameraInfo cameraInfo = new CameraInfo();    
            for (int i = 0; i < numberOfCameras; i++) {    
                Camera.getCameraInfo(i, cameraInfo);    
                if (cameraInfo.facing == CAMERA_FACING_FRONT) {    
                    return i;    
                }    
            }    
            return 2;  
      }  
      /**
       * 获取像素值单位为W
       * @param paramInt
       * @return
       */
      public static int getCameraPixels(int paramInt)  
      {  
        if (paramInt == 2) {
            return 0;
        }
        Camera localCamera = Camera.open(paramInt);  
        Camera.Parameters localParameters = localCamera.getParameters();  
        localParameters.set("camera-id", 1);  
        List<Size> localList = localParameters.getSupportedPictureSizes();  
        if (localList != null)  
        {  
            int heights[] = new int[localList.size()];   
            int widths[] = new int[localList.size()];   
            for (int i = 0; i < localList.size(); i++)   
            {   
            Size size = (Size) localList.get(i);   
            int sizehieght = size.height;   
            int sizewidth = size.width;   
            heights[i] = sizehieght;   
            widths[i] =sizewidth;      
            }  
            int pixels = getMaxNumber(heights) * getMaxNumber(widths);  
            localCamera.release();  
            return pixels / 10000;  
         
        }  
        else {
            return 0;
        }
         
      }  
  
      public static int getMaxNumber(int[] paramArray)  
      {  
          int temp = paramArray[0];  
          for(int i = 0;i<paramArray.length;i++)  
          {  
              if(temp < paramArray[i])  
              {  
                  temp = paramArray[i];  
              }  
          }  
          return temp;  
      }  
}
