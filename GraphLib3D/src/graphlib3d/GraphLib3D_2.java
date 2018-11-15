/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphlib3d;

import graphlib2d.Canvas;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class GraphLib3D_2 extends Canvas{

    public GraphLib3D_2(JFrame frame, int width, int height) {
        super(frame, width, height);
    }
    
    private static Map<Integer, Integer[]> cubeRelations = null;
    private ArrayList<Point3D> getCubePoints(Point3D pInit, int length){
        int err = length/10;
        ArrayList<Point3D> tmp = new ArrayList<Point3D>();
        tmp.add(new Point3D(pInit.x, pInit.y+length, pInit.z+length));
        tmp.add(new Point3D(pInit.x+length, pInit.y, pInit.z+length));
        tmp.add(new Point3D(pInit.x+length, pInit.y+length, pInit.z));
        tmp.add(new Point3D(pInit.x, pInit.y, pInit.z));
        tmp.add(new Point3D(pInit.x+length, pInit.y+length, pInit.z+length));
        tmp.add(new Point3D(pInit.x+length, pInit.y, pInit.z));
        tmp.add(new Point3D(pInit.x, pInit.y+length, pInit.z));
        tmp.add(new Point3D(pInit.x, pInit.y, pInit.z+length));
        if(cubeRelations==null){
            cubeRelations = new HashMap<Integer, Integer[]>();
            int[] count = new int[tmp.size()];
            for (int i = 0; i < tmp.size(); i++) {
                cubeRelations.put(i, new Integer[3]);
                if(count[i]==3)
                    continue;
                int c = 0;
                for (int j = 0; j < tmp.size(); j++) {
                    if(( aprox(tmp.get(i).x, tmp.get(j).x, err) && aprox(tmp.get(i).y, tmp.get(j).y, err))|| 
                            (aprox(tmp.get(i).x, tmp.get(j).x, err) && aprox(tmp.get(i).z, tmp.get(j).z, err)) || 
                            ( aprox(tmp.get(i).z, tmp.get(j).z, err) && aprox(tmp.get(i).y, tmp.get(j).y, err)))
                    {
                        cubeRelations.get(i)[c] = j;
                        count[j]++;
                        c++;
                    }
                    if(c==3)
                        break;
                }
                count[i]++;
            }

        }
        return tmp;
    }
    
    private boolean aprox (int i, int j, int err){
        return Math.abs(i-j)<=err;
    }
    
    /*
    public void draw3DParalelCube(Point3D pInit, Point3D pDisplay, int length, ArrayList<Point3D> tmp){
        int err = length/10;
        Point3D p = new Point3D(pInit.x+pDisplay.x, pInit.y+ pDisplay.y, pInit.z+pDisplay.z);
        System.out.println("______________________________");
    
        int[] count = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            System.out.println(tmp.get(i).x+" ,"+tmp.get(i).y+" ,"+tmp.get(i).z);
            if(count[i]==3)
                continue;
            for (int j = 0; j < tmp.size(); j++) {
                int c = 0;
                if(( aprox(tmp.get(i).x, tmp.get(j).x, err) && aprox(tmp.get(i).y, tmp.get(j).y, err))|| 
                        (aprox(tmp.get(i).x, tmp.get(j).x, err) && aprox(tmp.get(i).z, tmp.get(j).z, err)) || 
                        ( aprox(tmp.get(i).z, tmp.get(j).z, err) && aprox(tmp.get(i).y, tmp.get(j).y, err)))
                {
                    drawLine3DParalel(p, tmp.get(i), tmp.get(j), Color.GREEN);
                    count[j]++;
                    c++;
                }
                if(c==3)
                    break;
            }
            count[i]++;
        }
    }
    */
    public void draw3DParalelCube(Point3D pInit, Point3D pDisplay, int length){
        ArrayList<Point3D> tmp = getCubePoints(pInit, length);
        draw3DParalelCube(pInit, pDisplay, length, tmp);
    } 
    
    
    public void draw3DParalelCube(Point3D pInit, Point3D pDisplay, int length, ArrayList<Point3D> tmp){
        int err = length/10;
        Point3D p = pDisplay; new Point3D(pInit.x+pDisplay.x, pInit.y+ pDisplay.y, pInit.z+pDisplay.z);
        System.out.println("______________________________");
    
        int[] count = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            System.out.println(tmp.get(i).x+" ,"+tmp.get(i).y+" ,"+tmp.get(i).z);
            if(count[i]==3)
                continue;
            for (int j = 0; j < cubeRelations.get(i).length; j++) {
                if(cubeRelations.get(i)[j]==null)
                    break;
                drawLine3DParalel(p, tmp.get(i), tmp.get(cubeRelations.get(i)[j]), Color.GREEN);
            }
            count[i]++;
        }
    }
    
    public void draw3DPerspectiveCube(Point3D pInit, Point3D pDisplay, int length, ArrayList<Point3D> tmp){
        Point3D p = new Point3D(pInit.x+pDisplay.x, pInit.y+ pDisplay.y, pInit.z+pDisplay.z);
        System.out.println("______________________________");
        int[] count = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            System.out.println(tmp.get(i).x+" ,"+tmp.get(i).y+" ,"+tmp.get(i).z);
            if(count[i]==3)
                continue;
            for (int j = 0; j < cubeRelations.get(i).length; j++) {
                if(cubeRelations.get(i)[j]==null)
                    break;
                drawLine3DProjection(p, tmp.get(i), tmp.get(cubeRelations.get(i)[j]), Color.GREEN);
            }
            count[i]++;
        }
    }
    
    public void draw3DPerspectiveCube(Point3D pInit, Point3D pDisplay, int length){
        ArrayList<Point3D> tmp = getCubePoints(pInit, length);
        draw3DPerspectiveCube(pInit, pDisplay, length, tmp);
    }
    
    public void drawLine3DParalel(Point3D pD, Point3D p1, Point3D p2, Color color){
        int x1 = (int) (p1.x - (pD.z==0?0:(pD.x*(float)p1.z/(float)pD.z)));
        int y1 = (int) (p1.y - (pD.z==0?0:(pD.y*(float)p1.z/(float)pD.z)));
        int x2 = (int) (p2.x - (pD.z==0?0:(pD.x*(float)p2.z/(float)pD.z)));
        int y2 = (int) (p2.y - (pD.z==0?0:(pD.y*(float)p2.z/(float)pD.z)));
        
        drawLineBresenham(x1, y1, x2, y2, color);
    }
    
    public void drawLine3DProjection(Point3D pD, Point3D p1, Point3D p2, Color color){
    
        int x1 = pD.x - ((p1.z-pD.z)==0?0:(pD.z*(p1.x-pD.x)/(p1.z-pD.z)));
        int y1 = pD.y - ((p1.z-pD.z)==0?0:(pD.z*(p1.y-pD.y)/(p1.z-pD.z)));
        int x2 = pD.x - ((p2.z-pD.z)==0?0:(pD.z*(p2.x-pD.x)/(p2.z-pD.z)));
        int y2 = pD.y - ((p2.z-pD.z)==0?0:(pD.z*(p2.y-pD.y)/(p2.z-pD.z)));
        drawLineBresenham(x1, y1, x2, y2, color);
    }
    
    public void drawLine3DProjectionRotated(Point3D pD, Point3D p1, Point3D p2, Color color, int degrees){
    
        int x1 = pD.x - ((p1.z-pD.z)==0?0:(pD.z*(p1.x-pD.x)/(p1.z-pD.z)));
        int y1 = pD.y - ((p1.z-pD.z)==0?0:(pD.z*(p1.y-pD.y)/(p1.z-pD.z)));
        int x2 = pD.x - ((p2.z-pD.z)==0?0:(pD.z*(p2.x-pD.x)/(p2.z-pD.z)));
        int y2 = pD.y - ((p2.z-pD.z)==0?0:(pD.z*(p2.y-pD.y)/(p2.z-pD.z)));
        drawLineBresenham(x1, y1, x2, y2, color);
    }
    
    public void drawRotatedCube(Point3D pInit, Point3D pDisplay, int length, Point3D degrees){
        boolean isParalel = true;
        degrees.x = degrees.x%360;
        degrees.y = degrees.y%360;
        degrees.z = degrees.z%360;
        
        ArrayList<Point3D> tmp = getCubePoints(pInit, length);
        ArrayList<Point3D> t = new ArrayList<Point3D>();
        
        for (int i = 0; i < tmp.size(); i++) {
            int x = tmp.get(i).x;
            int y = (int) Math.floor(((double) tmp.get(i).y * Math.cos(Math.toRadians(degrees.x))) + ((double) tmp.get(i).z * Math.sin(Math.toRadians(degrees.x))));
            int z = (int) Math.floor(((double) tmp.get(i).y * -Math.sin(Math.toRadians(degrees.x))) + ((double) tmp.get(i).z * Math.cos(Math.toRadians(degrees.x))));
            t.add(new Point3D(x, y, z));
        }
        tmp = t;
        t = new ArrayList<Point3D>();
        for (int i = 0; i < tmp.size(); i++) {
            int y = tmp.get(i).y;
            int x = (int) Math.floor(((double) tmp.get(i).x * Math.cos(Math.toRadians(degrees.y))) + ((double) tmp.get(i).z * Math.sin(Math.toRadians(degrees.y))));
            int z = (int) Math.floor(((double) tmp.get(i).x * -Math.sin(Math.toRadians(degrees.y))) + ((double) tmp.get(i).z * Math.cos(Math.toRadians(degrees.y))));
            t.add(new Point3D(x, y, z));
        }
        tmp = t;
        t = new ArrayList<Point3D>();
        for (int i = 0; i < tmp.size(); i++) {
            int z = tmp.get(i).z;
            int y = (int) Math.floor(((double) tmp.get(i).x * Math.cos(Math.toRadians(degrees.z))) + ((double) tmp.get(i).y * Math.sin(Math.toRadians(degrees.z))));
            int x = (int) Math.floor(((double) tmp.get(i).x * -Math.sin(Math.toRadians(degrees.z))) + ((double) tmp.get(i).y * Math.cos(Math.toRadians(degrees.z))));
            t.add(new Point3D(x, y, z));
        }
        if(isParalel)
            draw3DParalelCube(pInit, pDisplay, length, t);
        else
            draw3DPerspectiveCube(pInit, pDisplay, length, t);
    }
    
    public static class Point3D{
        public int x;
        public int y;
        public int z;
        public Point3D(){

        }
        public Point3D(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

    }
}