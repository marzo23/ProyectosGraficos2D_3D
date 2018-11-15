/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotacion3d;

import graphlib3d.GraphLib3D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class Rotacion3D extends JFrame implements KeyListener, Runnable{
    int width = 1000;
    int height = 1000;
    static boolean automatic = false;
    public static void main(String[] args) {
        Rotacion3D r = new Rotacion3D();
        if(automatic)
            r.run();
    }
    
    public Rotacion3D() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.addKeyListener(this);
        pInit = new GraphLib3D.Point3D(100,100,100);
        point = null;
    }
    GraphLib3D.Point3D pInit = new GraphLib3D.Point3D(0,0,0);
    GraphLib3D.Point3D degrees = new GraphLib3D.Point3D(0,0,0);
    ArrayList<GraphLib3D.Point3D> point = null;
    
    public void paint(Graphics g){
        GraphLib3D g3D = new GraphLib3D(this, width, height);
        g3D.drawRec(0, 0, width, height, Color.white, Color.white);
        GraphLib3D.Point3D pDisplay = //new GraphLib3D.Point3D(0,60,-200);
        new GraphLib3D.Point3D(20,-50,150);
        g3D.drawRotatedCube(pInit, pDisplay, 30, degrees);
        //point = g3D.drawRotatedCube(pInit, pDisplay, 50, degrees, dir, point);
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
                Logger.getLogger(Rotacion3D.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
