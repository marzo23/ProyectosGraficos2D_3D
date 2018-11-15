/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalamiento3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import graphlib3d.GraphLib3D;

/**
 *
 * @author L440
 */
public class Escalamiento3D extends JFrame implements KeyListener {
    int width = 1000;
    int height = 1000;
    
    public static void main(String[] args) {
        new Escalamiento3D();
    }
    
    public Escalamiento3D() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.addKeyListener(this);
        pInit = new GraphLib3D.Point3D(100,0,0);
    }
    GraphLib3D.Point3D pInit = new GraphLib3D.Point3D(100,0,0);
    
    int length = 50;
    
    public void paint(Graphics g){
        GraphLib3D g3D = new GraphLib3D(this, width, height);
        g3D.drawRec(0, 0, width, height, Color.white, Color.white);
        GraphLib3D.Point3D pDisplay = //new GraphLib3D.Point3D(0,60,-200);
        new GraphLib3D.Point3D(20,-50,150);
        if(true)
            g3D.draw3DParalelCube(pInit, pDisplay, length);
        else
            g3D.draw3DPerspectiveCube(pInit, pDisplay, length);
        g.drawImage(g3D.canvas, 0,0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()){
            case 'w': //y++
                if(length-5>0)
                    length-=5;
                this.repaint();
                break;
            case 's': //y--
                length+=5;
                this.repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}

