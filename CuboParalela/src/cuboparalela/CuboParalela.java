/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuboparalela;

import graphlib3d.GraphLib3D;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class CuboParalela extends JFrame {
    int width = 500;
    int height = 500;
    public CuboParalela() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }
    
    public void paint(Graphics g){
        GraphLib3D g3D = new GraphLib3D(this, width, height);
        GraphLib3D.Point3D pInit = new GraphLib3D.Point3D(100,0,0);
        GraphLib3D.Point3D pDisplay = new GraphLib3D.Point3D(-60,-70,200);
        g3D.draw3DParalelCube(pInit, pDisplay, 50);
        //g3D.draw3DPerspectiveCube(pInit, pDisplay, 50);
        g.drawImage(g3D.canvas, 100,100, this);
    }
    
    public static void main(String[] args) {
        new CuboParalela();
    }
    
}
