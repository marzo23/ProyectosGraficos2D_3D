/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock2;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 *
 * @author L440
 */
public class Clock2 extends JFrame implements Runnable{
    Clip sonido;
    
    enum Time{
        Hour, Minute, Second
    }

    long durationInMillis;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    int millis = 0;
    
    Ball b = new Ball();
    
    //final int delay = 1000;
    
    int diameter = 500;
    int windowSize = 540;
    int center = windowSize/2;
    
    
    int x =0;
    int y =0;
    boolean boolIncrement = true;
    int radio = 0;
    final int delay = 10;
    int constatn = 100;
    String soundFile = "C:\\Users\\L440\\Documents\\tick.wav";
    public Clock2() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
        super();
        this.sonido = AudioSystem.getClip();
        File s = new File(soundFile);
        sonido.open(AudioSystem.getAudioInputStream(s));
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setSize(windowSize, windowSize);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        show();
        this.run();
    }
    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        new Clock2();
    }
    List<int[]> figure = new ArrayList();
    boolean i = true;
    int prevSec = 0;
    public void paint(Graphics g){
        if(i){
            i= false;
            update(g);
            
        }
        g.setColor(Color.white);
        if(seconds == 0){
            radio= 0;
            figure = new ArrayList();
        }
        if(prevSec!=seconds)
            update(g);
        
        prevSec=seconds;
        radio +=1;
        
        System.out.println("radio: "+radio+" X: "+x+" Y: "+y);
        x= (int)(radio*Math.cos((constatn*Math.PI-minutes/6)*radio/constatn)/constatn*10);
        y= (int)(radio*Math.sin((constatn*Math.PI-1)*radio/constatn)/constatn*10);
        figure.add(new int[]{x,y});
        
        for (int j = 0; j < figure.size() && (x<diameter/2 || y<diameter/2); j++) {
            g.drawRect(figure.get(j)[0]+center,figure.get(j)[1]+center, 1,1);
        }
        
        
        int large = (diameter/2-20);
        int shortH = diameter/3;
        System.out.println(hours+":"+minutes+":"+seconds);
        g.setColor(Color.red);
        int secAngle = 360-(90-6*seconds);//>0?90-6*seconds:360-6*seconds;
        int xSec = (int)(Math.cos(Math.toRadians(secAngle))*large)+center;
        int ySec = (int)(Math.sin(Math.toRadians(secAngle))*large)+center;
        g.drawLine(center, center, xSec, ySec);
        
        g.setColor(Color.blue);
        int minAngle = 360-(90-6*minutes);//>0?90-6*minutes:360-6*minutes;
        int xMin = (int)(Math.cos(Math.toRadians(minAngle))*large)+center;
        int yMin = (int)(Math.sin(Math.toRadians(minAngle))*large)+center;
        g.drawLine(center, center, xMin, yMin);
        g.setColor(Color.green);
        int hoursAngle = 360-(90-30*hours);//>0?90-30*hours:360-30*hours;
        int xHour = (int)(Math.cos(Math.toRadians(hoursAngle))*shortH)+center;
        int yHour = (int)(Math.sin(Math.toRadians(hoursAngle))*shortH)+center;
        g.drawLine(center, center, xHour, yHour);
        
        double ballDiam = Math.sqrt(b.x*b.x+b.y*b.y);
        int ballAngle = (int)Math.acos(b.x/ballDiam);
        //ADD VALIDATION FOR OUT OF THE BOUNDS
        if((ballAngle<secAngle+3 || ballAngle>secAngle-3 && ballDiam<large) ||
           (ballAngle<minAngle+3 || ballAngle>minAngle-3 && ballDiam<large) ||
           (ballAngle<hoursAngle+3 || ballAngle>hoursAngle-3 && ballDiam<shortH) && ballDiam<diameter){
            
        }
    }
    
    public void update(Graphics g){
        
        g.setColor(Color.black);
        g.fillOval(20, 20, diameter, diameter);
        
        
    }
    
    @Override
    public void run() {
        while(true){
            
            durationInMillis = System.currentTimeMillis();
            millis = (int) durationInMillis % 1000;
            seconds = (int)(durationInMillis / 1000) % 60;
            minutes = (int) (durationInMillis / (1000 * 60)) % 60;
            hours = (int) (durationInMillis / (1000 * 60 * 60)+7) %12 ;
            try {
                if(prevSec!=seconds){
                    sonido.stop();
                    sonido.start();
                }
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
        }
    }
    
}

class Ball{
    public int x = -1;
    public int y = -1;
    public boolean xIncrement = false;
    public boolean yIncrement = false;
}
