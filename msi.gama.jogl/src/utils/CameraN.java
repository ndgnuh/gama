/*
 * This is first but not final version of this class v 25.07.2010
 * 
 */

package utils;


import com.sun.opengl.util.BufferUtil;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;

public class CameraN implements MouseListener, KeyListener,MouseMotionListener,MouseWheelListener {

protected Point mouseXY;
protected Wector pos_of_camera;
private float cameraRadius  = 5;
private float cameraCelPhi,cameraCelTheta;
protected float phi = 0,theta = 0;
protected float mouseSensitivity;
protected float keyboardSensitivity;
protected int button= -1;
public int  selectedAtom=-1;
public static boolean isPressed = false;


public CameraN(GLCanvas canvas){
       
        mouseXY = new Point();
        pos_of_camera = new Wector();
        cameraCelPhi = 0;
        cameraCelTheta = 0;
        mouseSensitivity = 4f;
        keyboardSensitivity = .5f;

        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }
public void setMouseSensitivity(float sens){
    mouseSensitivity = sens;
}
public void setKeyboardSensitivityy(float sens){
    keyboardSensitivity = sens;
}

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        
        
                button = e.getButton();
                mouseXY.x = e.getX();
                mouseXY.y = e.getY();

        if(e.getButton()==1) isPressed  = true;
        

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {


    }

    
    public void mouseDragged(MouseEvent e) {
         

                Point przesunieciePozKursora = new Point(e.getX()- mouseXY.x,e.getY()- mouseXY.y);
                if((przesunieciePozKursora.x==0 && przesunieciePozKursora.y==0 ))return;
            if(button == 3){
                
                 cameraCelPhi+=przesunieciePozKursora.x/mouseSensitivity;
                 float zmianaCelTheta = przesunieciePozKursora.y/mouseSensitivity;
                 if(Math.abs(cameraCelTheta+zmianaCelTheta)<90)cameraCelTheta+=zmianaCelTheta;

            }
            if(button == 1){
  
                 phi+=przesunieciePozKursora.x/mouseSensitivity;
                 theta+=przesunieciePozKursora.y/mouseSensitivity;

            }
            if(button == 2){
                 pos_of_camera.x+=przesunieciePozKursora.x/mouseSensitivity;
                 pos_of_camera.y-=przesunieciePozKursora.y/mouseSensitivity;
            }
            mouseXY.x = e.getX();
            mouseXY.y = e.getY();
        

    }

     public void mouseWheelMoved(MouseWheelEvent e) {
       // cameraRadius+=e.getWheelRotation();
        if(e.getWheelRotation()<0){
             pos_of_camera.z+=Math.cos(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
             pos_of_camera.x-=Math.sin(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
        }else {
             pos_of_camera.z-=Math.cos(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
             pos_of_camera.x+=Math.sin(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
        }

    }

    public void mouseMoved(MouseEvent e) {

    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
          switch(e.getKeyCode()){              
              case KeyEvent.VK_W:{
                pos_of_camera.z+=Math.cos(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
                pos_of_camera.x-=Math.sin(Math.toRadians(cameraCelPhi))*keyboardSensitivity;

              }break;
              case KeyEvent.VK_S:{
                pos_of_camera.z-=Math.cos(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
                pos_of_camera.x+=Math.sin(Math.toRadians(cameraCelPhi))*keyboardSensitivity;

              }break;
              case KeyEvent.VK_D:{
                pos_of_camera.z+=Math.sin(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
                pos_of_camera.x+=Math.cos(Math.toRadians(cameraCelPhi))*keyboardSensitivity;

              }break;
              case KeyEvent.VK_A:{
                pos_of_camera.z-=Math.sin(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
                pos_of_camera.x-=Math.cos(Math.toRadians(cameraCelPhi))*keyboardSensitivity;
              }break;
         }
    }


  

    public void keyReleased(KeyEvent e) {

    }

    public void cameraFPP(GL gl){
      
        gl.glLoadIdentity();     
        gl.glRotatef(cameraCelPhi,0,1,0);
        gl.glRotatef(cameraCelTheta, (float)Math.cos(Math.toRadians(cameraCelPhi)), 0, (float)Math.sin(Math.toRadians(cameraCelPhi)));
        gl.glTranslated(pos_of_camera.x,pos_of_camera.y,pos_of_camera.z-cameraRadius);
    }

    public void cameraTPP(GL gl){

        gl.glLoadIdentity();
        gl.glRotatef(cameraCelPhi,0,1,0);
        gl.glRotatef(cameraCelTheta, (float)Math.cos(Math.toRadians(cameraCelPhi)), 0, (float)Math.sin(Math.toRadians(cameraCelPhi)));
        gl.glTranslated(pos_of_camera.x,pos_of_camera.y,pos_of_camera.z-cameraRadius);
        gl.glRotatef(phi,0,1,0);
        gl.glRotatef(theta, (float)Math.cos(Math.toRadians(phi)), 0, (float)Math.sin(Math.toRadians(phi)));
    }


public void selectedAtom(GL gl){
    if(!isPressed) return;
    isPressed = false;
    GLU glu = new GLU();


    int capacity =4*100;//it can be changed
    //creating the selecBuffer
    IntBuffer selectBuffer = BufferUtil.newIntBuffer(capacity);
    gl.glSelectBuffer(selectBuffer.capacity(), selectBuffer);

    //set to projection matrix
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glPushMatrix();
    gl.glLoadIdentity();

    
    
    //take a height and width of GLcanvas from viewport
    int viewport[] = new int[4];
    gl.glGetIntegerv(GL.GL_VIEWPORT, viewport,0);
    int width = viewport[2];
    int height = viewport[3];



    glu.gluPickMatrix(mouseXY.x,height- mouseXY.y, 1, 1, viewport, 0);

    //it can be changed but dont all
    float h = width/(float)height;
    glu.gluPerspective(30.0f, h, 1.0, 500.0);
    //shitch to the modelview
    gl.glMatrixMode(GL.GL_MODELVIEW);

    //switch to the select Render mode
    gl.glRenderMode(GL.GL_SELECT);



    //here is a place  where must be use draw-somthing method
    //f.e  glu.gluSphere(quadric, .8, 15, 15);
    //display lists also work here
    

    int howManyObjects = gl.glRenderMode(GL.GL_RENDER);

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glPopMatrix();
    gl.glMatrixMode(GL.GL_MODELVIEW);
    System.out.println("Nuber of hits:"+howManyObjects);

    //code below derive which ocjects is nearest from monitor
    if(howManyObjects>0){
        selectedAtom = selectBuffer.get(3);
        int mindistance = Math.abs(selectBuffer.get(1));
        for(int i =0;i<howManyObjects;i++){

            if(mindistance<Math.abs(selectBuffer.get(1+i*4))){
                    mindistance = Math.abs(selectBuffer.get(1+i*4));
                    selectedAtom = selectBuffer.get(3+i*4);

            }

        }
     System.out.println("ID:"+selectedAtom);

    }else selectedAtom = -1;

}
}
