
package graficos2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class Graficos2D extends JFrame{

    BufferedImage buffer;
    Graphics graphics;
    int screenSize = 500;

    public Graficos2D(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setSize(screenSize, screenSize);
        this.setLocationByPlatform(true);
        this.setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) buffer.createGraphics();
    }

    public void drawPixel(int x, int y, Color color){
        buffer.setRGB(0, 0, color.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }
    
    public void drawLine(int x1, int y1, int x2, int y2, Color color){
        if((x2-x1)==0 && (y2-y1)==0){
            drawPixel(x1, y1, color);
        }else if((x2-x1)==0){
            for (int i = y2<y1?y2:y1; i < (y2>y1?y2:y1); i++) {
                drawPixel(x1, i, color);
            }
        }else if((y2-y1)==0){
            for (int i = x2<x1?x2:x1; i < (x2>x1?x2:x1); i++) {
                drawPixel(i, y1, color);
            }
        }else{
            double m = (x2>x1?y2-y1:y1-y2)/(x2>x1?x2-x1:x1-x2);
            if(Math.abs(m)>1){
                for (int i = y2<y1?y2:y1; i < (y2>y1?y2:y1); i++) {
                    drawPixel((int)((i-(y2<y1?x2:x1))/m), i, color);
                }
            }else{
                for (int i = x2<x1?x2:x1; i < (x2>x1?x2:x1); i++) {
                    drawPixel(i, (int)((x2<x1?y2:y1)+i*m), color);
                }
            }
        }
    }
    
    public void drawLineMiddlePoint(int x1, int y1, int x2, int y2, Color color){
        double x = x1<x2?x1:x2;
        double y = x1<x2?y1:y2;
        
        y2 = x1>x2?y1:y2;
        x2 = x1>x2?x1:x2;        
        x1 = (int)x;
        y1 = (int)y;
        
        int dx = x2 - x1; 
        int dy = y2 - y1;
        int d = dy - (dx/2); 
        //int x = x1, y = y1;  
        drawPixel((int)x, (int)y, color);
        while (x < x2){ 
            x++;  
            if (d < 0) 
                d = d + dy; 
            else{ 
                d += (dy - dx); 
                y++; 
            } 
            
            drawPixel((int)x, (int)y, color);
        } 
    }
    
    public void drawLineBresenham(int x1, int y1, int x2, int y2, Color color){
        int d = 0;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dx2 = 2 * dx;
        int dy2 = 2 * dy;
        int ix = x1 < x2 ? 1 : -1;
        int iy = y1 < y2 ? 1 : -1;
        int x = x1;
        int y = y1;
 
        if (dx >= dy) {
            while (true) {
                drawPixel((int)x, (int)y, color);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        }else{
            while (true) {
                drawPixel((int)x, (int)y, color);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }
    
    public void drawLineDAA(int x1, int y1, int x2, int y2, Color color){
        int deltaX = x2-x1;
        int deltaY = y2-y1;
        int steps = 0;
        if(Math.abs(deltaX)>Math.abs(deltaY))
            steps = Math.abs(deltaX);
        else
            steps = Math.abs(deltaY);
        double xInc = deltaX/steps;
        double yInc = deltaY/steps;
        double x = x1;
        double y = y1;
        drawPixel((int)x, (int)y, color);
        for (int i = 0; i < steps; i++) {
            x += xInc;
            y += yInc;
            drawPixel((int)x, (int)y, color);
        }
    }

    public static void main(String[] args) {
        Graficos2D p = new Graficos2D();
    }
    
    

    @Override
    public void paint(Graphics g){
        
    }

}
