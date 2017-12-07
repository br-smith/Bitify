import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Bitify {

    private Graphics2D graphics;
    private File path;
    private BufferedImage image;
    private int imageHeight;
    private int imageWidth;
    private BufferedImage pixel;
    //private int pixelHeight;
    //private int pixelWidth;
    private static final int stepSize = 15;

    public void getImage(String pathname){
        path = new File(pathname);
        try{
            image = ImageIO.read(path);

            imageHeight = image.getHeight();
            imageWidth = image.getWidth();

            //pixelHeight = imageHeight/stepSize;
            //pixelWidth = imageWidth/stepSize;
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public BufferedImage drawPixel(){
        pixel = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_INT_ARGB);
        //pixel = new BufferedImage(pixelHeight, pixelWidth, BufferedImage.TYPE_INT_ARGB);
        //Color[][] colors = new Color [imageHeight/stepSize][imageWidth/stepSize];

        int redTotal;
        int greenTotal;
        int blueTotal;

        int redAver;
        int greenAver;
        int blueAver;
        int pixelCount;

        for (int i = 0; i < imageHeight; i+=stepSize){
            for (int j = 0; j < imageWidth; j+=stepSize){

                redTotal = 0;
                greenTotal = 0;
                blueTotal = 0;
                redAver = 0;
                greenAver = 0;
                blueAver = 0;
                pixelCount = 0;

                for(int x = i; x < i + stepSize; x++){
                    for(int y = j; y < j + stepSize; y++){
                        if (x < imageHeight && y < imageWidth){
                            int colorValue = image.getRGB(x,y);
                            Color color1 = new Color(colorValue);
                            redTotal += color1.getRed();
                            greenTotal += color1.getGreen();
                            blueTotal += color1.getBlue();
                            pixelCount++;
                        }
                    }
                }
                System.out.println("Red Total: " + redTotal + " Green Total: " + greenTotal + " Blue Total: " + blueTotal + " Pixel Count: " + pixelCount);
                redAver = Math.min(255, (redTotal/pixelCount));
                greenAver = Math.min(255, (greenTotal/pixelCount));
                blueAver = Math.min(255, (blueTotal/pixelCount));
                Color averageColor = new Color(redAver, greenAver, blueAver);

                for(int x = i; x < i + stepSize; x++){
                    for(int y = j; y < j + stepSize; y++){
                        if (x < imageHeight && y < imageWidth){
                            pixel.setRGB(x,y, averageColor.getRGB());
                        }
                    }
                }

            }
        }


        return pixel;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public static void main(String[] args){
        Bitify bits = new Bitify();
        DrawingKit canvas = new DrawingKit(400,400);
        //canvas.drawPicture("src/fire.png");
        //Scanner in = new Scanner(System.in);
        bits.getImage("src/fire.png");

        Graphics2D myGraphics = canvas.getGraphics();
        BufferedImage image = bits.drawPixel();
        canvas.drawPicture(image, 0, 0);

    }
}
