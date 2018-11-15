/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traslacion3d;

import graphlib3d.GraphLib3D_2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class Traslacion3D extends JFrame implements KeyListener {
    int width = 1000;
    int height = 1000;
    
    public static void main(String[] args) {
        new Traslacion3D();
    }
    
    public Traslacion3D() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.addKeyListener(this);
        pInit = new GraphLib3D_2.Point3D(100,0,0);
    }
    GraphLib3D_2.Point3D pInit = new GraphLib3D_2.Point3D(100,0,0);
    
    public void paint(Graphics g){
        GraphLib3D_2 g3D = new GraphLib3D_2(this, width, height);
        g3D.drawRec(0, 0, width, height, Color.white, Color.white);
        GraphLib3D_2.Point3D pDisplay = //new GraphLib3D.Point3D(0,60,-200);
        new GraphLib3D_2.Point3D(200,50,150);
        if(true)
            g3D.draw3DParalelCube(pInit, pDisplay, 50);
        else
            g3D.draw3DPerspectiveCube(pInit, pDisplay, 50);
        g.drawImage(g3D.canvas, 0,0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()){
            case 'w': //y++
                pInit.y-=5;
                this.repaint();
                break;
            case 's': //y--
                pInit.y+=5;
                this.repaint();
                break;
            case 'a': //x--
                pInit.x-=5;
                this.repaint();
                break;
            case 'd': //x++
                pInit.x+=5;
                this.repaint();
                break;
            case 'z': //z--
                pInit.z-=5;
                this.repaint();
                break;
            case 'x': //z++
                pInit.z+=5;
                this.repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
