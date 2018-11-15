/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superficie;

import graphlib3d.GraphLib3D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.UnaryOperator;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class Superficie extends JFrame implements KeyListener, Runnable{
    int width = 1000;
    int height = 1000;
    static boolean automatic = true;
    boolean animate = true;
    public static void main(String[] args) {
        Superficie r = new Superficie();
        
        if(automatic)
            r.run();
    }
    
    public Superficie() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.addKeyListener(this);
        pInit = new GraphLib3D.Point3D(100,100,100);
        point = null;
        this.show();
    }
    GraphLib3D.Point3D pInit = new GraphLib3D.Point3D(0,0,0);
    GraphLib3D.Point3D degrees = new GraphLib3D.Point3D(-270,0,-270);
    ArrayList<GraphLib3D.Point3D> point = null;
    int init = 0;
    int end = 00;
    int factor = 1;
    public void paint(Graphics g){
        GraphLib3D g3D = new GraphLib3D(this, width, height);
        g3D.drawRec(0, 0, width, height, Color.white, Color.white);
        GraphLib3D.Point3D pDisplay = //new GraphLib3D.Point3D(0,60,-200);
        new GraphLib3D.Point3D(20,-50,150);
        UnaryOperator<GraphLib3D.Point3D> fn = (p)->{
            
            /*
            int a = 1;
            int b = 1;
            int c = 1;
            int y = (int)(Math.sqrt(400+p.z*p.z)*Math.sin(Math.toRadians(p.x)))*b;
            int x = (int)(Math.sqrt(400+p.z*p.z)*Math.cos(Math.toRadians(p.x)))*a;
            int z = c*p.z;
            //*/
            //*
            int y = (int)((2+Math.cos(Math.toRadians(3*p.z+factor)))*Math.sin(Math.toRadians(p.x))*20);
            int x = (int)((2+Math.cos(Math.toRadians(3*p.z+factor)))*Math.cos(Math.toRadians(p.x))*20);
            int z = p.z;
            //*/
            //System.out.println(x+","+y+","+z);
            return new GraphLib3D.Point3D(x,y,z);
            
        };
        g3D.drawRotatedTriangles(pInit, pDisplay, 30, degrees, g3D.getTrianglesSurface(fn, 0, 360, 1, init, end, 4));
        //g3D.drawRotatedPoints(pInit, pDisplay, 30, degrees, g3D.getPoints3DHiperboloid(fn, init, end, 4));
        g.drawImage(g3D.canvas, 0,40, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!automatic)
        switch(e.getKeyChar()){
            case 'w': //y++
                degrees.y-=5;
                this.repaint();
                break;
            case 's': //y--
                degrees.y+=5;
                this.repaint();
                break;
            case 'a': //x--
                degrees.x-=5;
                this.repaint();
                break;
            case 'd': //x++
                degrees.x+=5;
                this.repaint();
                break;
            case 'z': //z--
                degrees.z-=5;
                this.repaint();
                break;
            case 'x': //z++
                degrees.z+=5;
                this.repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        int tot = 70;
        init = 0;
        end = tot*2;
        if(!animate){
            int cont = 0;
            int tmp = new Random().nextInt(6);
            while(true){
                if(cont++>10){
                    tmp = new Random().nextInt(6);
                    cont = 0;
                }
                switch(tmp){
                    case 0: //y++
                        degrees.y-=5;
                        this.repaint();
                        break;
                    case 1: //y--
                        degrees.y+=5;
                        this.repaint();
                        break;
                    case 2: //x--
                        degrees.x-=5;
                        this.repaint();
                        break;
                    case 3: //x++
                        degrees.x+=5;
                        this.repaint();
                        break;
                    case 4: //z--
                        degrees.z-=5;
                        this.repaint();
                        break;
                    case 5: //z++
                        degrees.z+=5;
                        this.repaint();
                        break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
        }else{
            while(true){
                /*if(end>tot*2){
                    init = tot*-1;
                    end = tot;
                    continue;
                }
                init+=10;
                end+=10;*/
                if(factor>350)
                    factor = 1;
                factor+=20;
                this.repaint();
                try {
                    Thread.sleep(100);
                }catch (InterruptedException ex) {
                }
            }
        }
        
    }
    
}