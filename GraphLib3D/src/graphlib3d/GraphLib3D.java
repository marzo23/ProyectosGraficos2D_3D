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
import java.util.Iterator;
import java.util.Map;
import java.util.function.UnaryOperator;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class GraphLib3D extends Canvas{

    public GraphLib3D(JFrame frame, int width, int height) {
        super(frame, width, height);
    }
    
    
    public ArrayList<Point3D> getPointsExplicitCurve(UnaryOperator<Point3D> fn, int length, int space){
        ArrayList<Point3D> list = new ArrayList<Point3D>();
        for (int i = 0; i < length; i+=space) {
            list.add(fn.apply(new Point3D(i,0,0)));
        }
        return list;
    }
    
    public ArrayList<Point3D> getPoints3DCurve(UnaryOperator<Point3D> fn, int length, int space, int length2, int space2){
        ArrayList<Point3D> list = new ArrayList<Point3D>();
        for (int j = -length2; j < length2; j+=space2) {
            for (int i = 0; i < 360; i++) {
                list.add(fn.apply(new Point3D(i,0,j)));
            }
        }
        return list;
    }
    
    public ArrayList<Point3D> getPoints3DHiperboloid(UnaryOperator<Point3D> fn, int init, int end, int space){
        ArrayList<Point3D> list = new ArrayList<Point3D>();
        for (int j = init; j < end; j+=space) {
            for (int i = 0; i < 360; i++) {
                list.add(fn.apply(new Point3D(i,0,j)));
            }
        }
        return list;
    }
    
    public Map<Point3D, Point3D[]> getTrianglesSurface(UnaryOperator<Point3D> fn, int length, int space){
        return getTrianglesSurface(fn, 0, length, space, 0, length, space);
    }
    
    public Map<Point3D, Point3D[]> getTrianglesSurface(UnaryOperator<Point3D> fn, int init, int end, int space, int init2, int end2, int space2){
        Map<Point3D, Point3D[]> map = new HashMap<Point3D, Point3D[]>();
        ArrayList<ArrayList<Point3D>> grid = new ArrayList<ArrayList<Point3D>>();
        
        for (int i = init; i < end; i+=space) {
            ArrayList<Point3D> list = new ArrayList<Point3D>();
            for (int j = init2; j < end2; j+=space2) {
                list.add(fn.apply(new Point3D(i,0,j)));
            }
            grid.add(list);
        }
        
        for (int i = 1; i < grid.size(); i++) {
            for (int j = 1; j < grid.get(i).size(); j++) {
                Point3D p1 = grid.get(i-1).get(j-1);
                Point3D p2 = grid.get(i).get(j-1);
                Point3D p3 = grid.get(i).get(j);
                Point3D p4 = grid.get(i-1).get(j);
                map.put(p1, new Point3D[]{ p4,p3});
                map.put(p3, new Point3D[]{ p1,p2});
            }
        }
        
        return map;
    }
    
    private void printTriagnle(Map.Entry<Point3D, Point3D[]> entry){
        System.out.println("pivote: "+entry.getKey().x+","+entry.getKey().y+","+entry.getKey().z);
        System.out.println(entry.getValue()[0].x+","+entry.getValue()[0].y+","+entry.getValue()[0].z);
        System.out.println(entry.getValue()[1].x+","+entry.getValue()[1].y+","+entry.getValue()[1].z);
    }
    
    public void drawExplicitCurve(Point3D pDisplay, ArrayList<Point3D> points){
        for (int i = 1; i < points.size(); i++) {
            drawLine3DParalel(pDisplay, points.get(i-1), points.get(i), Color.green);
        }
    }
    
    
    public void drawRelatedPoints(Point3D pDisplay, Map<Point3D, Point3D[]> map){
        for (Map.Entry<Point3D, Point3D[]> entry : map.entrySet())
        {
            //printTriagnle(entry);
            //printTriagnle(entry);
            for (int i = 0; i < entry.getValue().length; i++) {
                drawLine3DParalel(pDisplay, entry.getKey(), entry.getValue()[i], Color.green);
            }
        }
    }
    
    public void fillTriangle(Point3D pDisplay, Map<Point3D, Point3D[]> map){
        for (Map.Entry<Point3D, Point3D[]> entry : map.entrySet())
        {
            int tmp = 1;
            Point3D min = entry.getValue()[0].x>entry.getValue()[1].x? entry.getValue()[1]: entry.getValue()[0];
            Point3D max = entry.getValue()[0].x<entry.getValue()[1].x? entry.getValue()[1]: entry.getValue()[0];
            int diff = Math.abs(max.x-min.x);
            if(min==max){
                tmp = 2;
                min = entry.getValue()[0].y>entry.getValue()[1].y? entry.getValue()[1]: entry.getValue()[0];
                max = entry.getValue()[0].y<entry.getValue()[1].y? entry.getValue()[1]: entry.getValue()[0];
                diff = max.y-min.y;
            }
            if(min==max){
                tmp = 3;
                min = entry.getValue()[0].z>entry.getValue()[1].z? entry.getValue()[1]: entry.getValue()[0];
                max = entry.getValue()[0].z<entry.getValue()[1].z? entry.getValue()[1]: entry.getValue()[0];
                diff = max.z-min.z;
            }
            if(min==max)
                continue;
            for (int i = 0; i < diff; i++) {
                Color color = new Color(Math.abs(entry.getKey().x%254), Math.abs(entry.getKey().y%254), Math.abs(entry.getKey().z%254));
                switch(tmp){
                    case 1:
                        drawLine3DParalel(pDisplay, entry.getKey(), new Point3D(min.x+i, min.y, min.z), color);
                        break;
                    case 2:
                        drawLine3DParalel(pDisplay, entry.getKey(), new Point3D(min.x, min.y+i, min.z), color);
                        break;
                    case 3:
                        drawLine3DParalel(pDisplay, entry.getKey(), new Point3D(min.x, min.y, min.z+i), color);
                        break;
                }
            }
        }
    }
    
    public Point3D rotatePoint (Point3D p, int dir, int degrees){
        int x, y, z;
        switch(dir){
            case 1:
                x = p.x;
                y = (int) Math.floor(((double) p.y * Math.cos(Math.toRadians(degrees))) + ((double) p.z * Math.sin(Math.toRadians(degrees))));
                z = (int) Math.floor(((double) p.y * -Math.sin(Math.toRadians(degrees))) + ((double) p.z * Math.cos(Math.toRadians(degrees))));
                return new Point3D(x, y, z);
            case 2:
                x = (int) Math.floor(((double) p.x * Math.cos(Math.toRadians(degrees))) + ((double) p.z * Math.sin(Math.toRadians(degrees))));
                y = p.y;
                z = (int) Math.floor(((double) p.x * -Math.sin(Math.toRadians(degrees))) + ((double) p.z * Math.cos(Math.toRadians(degrees))));
                return new Point3D(x, y, z);
            case 3:
                x = (int) Math.floor(((double) p.x * -Math.sin(Math.toRadians(degrees))) + ((double) p.y * Math.cos(Math.toRadians(degrees))));
                y = (int) Math.floor(((double) p.x * Math.cos(Math.toRadians(degrees))) + ((double) p.y * Math.sin(Math.toRadians(degrees))));
                z = p.z;
                return new Point3D(x, y, z);
        }
        return p;
    }
    
    public Point3D rotatePoint (Point3D p, Point3D degrees){
        int x, y, z;
        p=rotatePoint(p, 1, degrees.x);
        p=rotatePoint(p, 2, degrees.y);
        p=rotatePoint(p, 3, degrees.z);
        return p;
    }
    
    public void drawRotatedTriangles(Point3D pInit, Point3D pDisplay, int length, Point3D degrees, Map<Point3D, Point3D[]> tmp){
        degrees.x = degrees.x%360;
        degrees.y = degrees.y%360;
        degrees.z = degrees.z%360;
        
        Map<Point3D, Point3D[]> t = new HashMap<Point3D, Point3D[]>();
        for (Map.Entry<Point3D, Point3D[]> entry : tmp.entrySet())
        {
            Point3D pivot = rotatePoint(entry.getKey(), degrees);
            t.put(pivot, new Point3D[entry.getValue().length]);
            for (int i = 0; i < entry.getValue().length; i++) {
                t.get(pivot)[i] = rotatePoint(entry.getValue()[i], degrees);
            }
        }
        fillTriangle(pDisplay, t);
        //drawRelatedPoints(pDisplay, t);
    }
    
    public void drawRotatedPoints(Point3D pInit, Point3D pDisplay, int length, Point3D degrees, ArrayList<Point3D> tmp){
        degrees.x = degrees.x%360;
        degrees.y = degrees.y%360;
        degrees.z = degrees.z%360;
        
        ArrayList<Point3D> t = new ArrayList<Point3D>();
        int x=-1, y=-1, z=-1;
        for (int i = 0; i < tmp.size(); i++) {
            Point3D te = rotatePoint(tmp.get(i), degrees);
            if(i==0){
                x = te.x - pInit.x;
                y = te.y - pInit.y;
                z = te.z - pInit.z;
            }
            /*te.x -= x;
            te.y -= y;
            te.z -= z;*/
            t.add(te);
        }
        
        drawExplicitCurve(pDisplay, t);
    }
    
    private static Map<Integer, Integer[]> cubeRelations = null;
    private ArrayList<Point3D> getCubePoints(int length){
        int err = length/10;
        ArrayList<Point3D> tmp = new ArrayList<Point3D>();
        tmp.add(new Point3D(0, 0+length, 0+length));
        tmp.add(new Point3D(0+length, 0, 0+length));
        tmp.add(new Point3D(0+length, 0+length, 0));
        tmp.add(new Point3D(0, 0, 0));
        tmp.add(new Point3D(0+length, 0+length, 0+length));
        tmp.add(new Point3D(0+length, 0, 0));
        tmp.add(new Point3D(0, 0+length, 0));
        tmp.add(new Point3D(0, 0, 0+length));
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
        //*
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
        ArrayList<Point3D> tmp = getCubePoints(length);
        draw3DParalelCube(pInit, pDisplay, length, tmp);
    } 
    
    
    public void draw3DParalelCube(Point3D pInit, Point3D pDisplay, int length, ArrayList<Point3D> tmp){
        int err = length/10;
        Point3D p = pDisplay; //new Point3D(pInit.x+pDisplay.x, pInit.y+ pDisplay.y, pInit.z+pDisplay.z);
        System.out.println("______________________________");
        //*
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
        Point3D p = pDisplay;//new Point3D(pInit.x+pDisplay.x, pInit.y+ pDisplay.y, pInit.z+pDisplay.z);
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
        ArrayList<Point3D> tmp = getCubePoints(length);
        draw3DPerspectiveCube(pInit, pDisplay, length, tmp);
    }
    
    public void drawLine3DParalel(Point3D pD, Point3D p1, Point3D p2, Color color){
        int x1 = (int) (p1.x - (pD.z==0?0:(pD.x*(float)p1.z/(float)pD.z)));
        int y1 = (int) (p1.y - (pD.z==0?0:(pD.y*(float)p1.z/(float)pD.z)));
        int x2 = (int) (p2.x - (pD.z==0?0:(pD.x*(float)p2.z/(float)pD.z)));
        int y2 = (int) (p2.y - (pD.z==0?0:(pD.y*(float)p2.z/(float)pD.z)));
        
        //drawLineBresenham(x1, y1, x2, y2, color);
        drawLineBresenham(200+x1, 200+y1, 200+x2, 200+y2, color);
    }
    
    public void drawLine3DProjection(Point3D pD, Point3D p1, Point3D p2, Color color){
        int x1 = pD.x - ((p1.z-pD.z)==0?0:(pD.z*(p1.x-pD.x)/(p1.z-pD.z)));
        int y1 = pD.y - ((p1.z-pD.z)==0?0:(pD.z*(p1.y-pD.y)/(p1.z-pD.z)));
        int x2 = pD.x - ((p2.z-pD.z)==0?0:(pD.z*(p2.x-pD.x)/(p2.z-pD.z)));
        int y2 = pD.y - ((p2.z-pD.z)==0?0:(pD.z*(p2.y-pD.y)/(p2.z-pD.z)));
        drawLineBresenham(200+x1, 200+y1, 200+x2, 200+y2, color);
    }
    
    public void drawRotatedCube(Point3D pInit, Point3D pDisplay, int length, Point3D degrees){
        degrees.x = degrees.x%360;
        degrees.y = degrees.y%360;
        degrees.z = degrees.z%360;
        
        ArrayList<Point3D> tmp = getCubePoints(length);
        ArrayList<Point3D> t = new ArrayList<Point3D>();
        
        for (int i = 0; i < tmp.size(); i++) {
            t.add(rotatePoint(tmp.get(i), degrees));
        }
        
        if(true)
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