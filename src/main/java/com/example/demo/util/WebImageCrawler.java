package com.example.demo.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component
public class WebImageCrawler {
	String savePath;
	String imgUrl;
	String imageName;
	String canonicalSavePath;

    public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getCanonicalSavePath() {
		return canonicalSavePath;
	}

	public void setCanonicalSavePath(String canonicalSavePath) {
		this.canonicalSavePath = canonicalSavePath;
	}

	public String downloadImage() throws IOException{
        imageName = imgUrl.substring( imgUrl.lastIndexOf("/") + 1 );
        
        OutputStream os = null;
        InputStream is = null;
        String saveFileName="";
        try {
        	File path = new File(savePath);
        	if(!path.isDirectory()) {
        		path.mkdir();
        	}
        	canonicalSavePath=path.getCanonicalPath();
        	path = new File(canonicalSavePath);
        	if(!path.isDirectory()) {
        		path.mkdir();
        	}
        	saveFileName=canonicalSavePath + "/" + imageName;
            URL urlImage = new URL(imgUrl);
            is = urlImage.openStream();
            
            byte[] buffer = new byte[4096];
            int n = -1;
            
            os =  new FileOutputStream(saveFileName);
            
            while ( (n = is.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }
            
            os.close();
            is.close();
            
        } catch (IOException e) {
            throw e;
        } finally {
        	if(os!=null) {try{os.close();}catch(Exception e) {}}
        	if(is!=null) {try{is.close();}catch(Exception e) {}}
        }
        return saveFileName;
    }


    public String resize(BufferedImage inputImage, int scaledWidth, int scaledHeight) throws IOException {
    	if(inputImage==null) {
            inputImage = getBufferedImage(imageName);
    	}
 
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
 
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        String formatName = getFormat(imageName);//imageName.lastIndexOf(".")>-1?imageName.substring(imageName.lastIndexOf(".") + 1):"jpg";
        String newFileName = (imageName.lastIndexOf(".")>-1?imageName.substring(0, imageName.lastIndexOf(".")):imageName)+"_"+scaledWidth+"_"+scaledHeight+"."+formatName;
        String saveFileName = canonicalSavePath + "/" +newFileName;
        ImageIO.write(outputImage, formatName, new File(saveFileName));
        
        return newFileName;
    }
 
    public String resize(double percent) throws IOException {
        BufferedImage inputImage = getBufferedImage(imageName);
        int scaledWidth = (int)(inputImage.getWidth() * percent);
        int scaledHeight = (int)(inputImage.getHeight() * percent);
        return resize(inputImage, scaledWidth, scaledHeight);
    }
    
    public String resize(int scaledWidth) throws IOException {
        BufferedImage inputImage = getBufferedImage(imageName);
        int gab = inputImage.getWidth()-scaledWidth;
        int percent = 100-(int)((gab/(inputImage.getWidth() * 1.0)) * 100);
        int scaledHeight = (int)(inputImage.getHeight() * (percent * 1.0)/(100 * 1.0));
        return resize(inputImage, scaledWidth, scaledHeight);
    }
    
    public BufferedImage getBufferedImage(String imageName) throws IOException{
        File inputFile = new File(canonicalSavePath + "/" + imageName);
        return ImageIO.read(inputFile);
    }
    
    public String getFormat(String imageName) throws IOException{
        File inputFile = new File(canonicalSavePath + "/" + imageName);
        String mimeType = new Tika().detect(inputFile);
        return mimeType==null?"":(mimeType.indexOf("/")>0?mimeType.substring(mimeType.indexOf("/")+1):mimeType);
    }
}
