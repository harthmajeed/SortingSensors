import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class SensorAnimation extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage sensorBar, done;
	
	private static final int PathYValue = 73;
	private int sensorsNum;
	private int radius;
	private int totalSteps = 0;
	private int method;
	
	private Random random = new Random();
	
	private int[] sensorsTarget;
	private Sensor[] sensors;
	private Sensor[] sensorsToBeMoved;
	
	private boolean isRigidDone = false;
	private boolean isSimpleDone = false;
	
	
	public SensorAnimation(int sensorNum, int radius, int method) throws IOException
	{
		sensorBar = ImageIO.read(getClass().getResource("sensorBar.png"));
		
		this.method = method;
		this.radius = radius;
		this.sensorsNum = sensorNum;
		
		sensors = new Sensor[sensorsNum];
		sensorsToBeMoved = new Sensor[sensorsNum];
		sensorsTarget = new int[sensorsNum];
		
		if(method == 0)
			initRigid();
		else if(method == 1)
			initSimple();
	}
	
	public int getMethod()
	{	return method;		}
	
	private void initRigid()
	{
		
		int rand;
		for(int i=0; i < sensorsNum; i++)
		{
			rand = random.nextInt(566);
			sensors[i] = new Sensor(rand, PathYValue, radius);
		}
		
		int j = radius;
		for(int i=0; i < sensorsNum; i++)
		{
			if(i > sensorsNum)
				break;
			else if(i < sensorsNum)
			{
				sensorsTarget[i] = j;
				j += radius + 1;
			}
		}
		
		Arrays.sort(sensors);	//amazing class
		
		for(int i=0; i < sensorsNum; i++)
			sensors[i].setTarget(sensorsTarget[i]);
	}
	
	private void initSimple()
	{
		int rand;
		for(int i=0; i < sensorsNum; i++)
		{
			rand = random.nextInt(566);
			sensors[i] = new Sensor(rand, PathYValue, radius);
		}
		int j = radius;
		for(int i=0; i < sensorsNum; i++)
		{
			if(i > sensorsNum)
				break;
			else if(i < sensorsNum)
			{
				sensorsTarget[i] = j;
				j += radius + 1;
			}
		}
		Arrays.sort(sensors);	//amazing class
		for(int i=0; i < sensorsNum; i++)
			sensors[i].setTarget(sensorsTarget[i]);
		findOverlaps();
		findAndFillGaps(); 
	}
	
	private void findOverlaps()
	{
		int found = 0;
		for(int i=1; i < sensorsNum-1; i++)
		{
			if((sensors[i+1].getX() - sensors[i-1].getX()) <= (radius*2))
			{
				sensorsToBeMoved[found] = sensors[i];
				found++;
			}
		}
	}
	
	private void findAndFillGaps()
	{
		int index = 0;
		for(int i=0; i < sensorsNum-1; i++)
		{
			if((sensors[i+1].getX() - sensors[i].getX()) > (radius*2))
			{
				if(sensorsToBeMoved[index] == null)
					continue;
				sensorsToBeMoved[index].setTarget(sensors[i].getX()+(radius*2));
				sensorsToBeMoved[index].setPurple();
				index++;	
				
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(sensorBar, 0, 0, null);
		for(int i=0; i < sensorsNum; i++)
			sensors[i].draw(g);
	}
	
	public int getRGB(int x, int y) {
		return sensorBar.getRGB(x, y);
	}
	
	public boolean isRigidDone()
	{	return isRigidDone;		}
	
	public boolean isSimpleDone()
	{	return isSimpleDone;		}
	
	private int isDone()
	{
		int done = 0;
		for(int i=0; i < sensorsNum; i++)
			if(sensors[i].isOnTarget() == true)
				done++;
		return done;
	}
	
	public int getTotalSteps()
	{	return totalSteps;		}
	
	public void resetRigid()
	{
		for(int i=0; i < sensorsNum; i++)
			sensors[i].reset();
		isRigidDone = false;
		totalSteps = 0;
		initRigid();
	}
	
	public void resetSimple()
	{
		for(int i=0; i < sensorsNum; i++)
			sensors[i].reset();
		isSimpleDone = false;
		totalSteps = 0;
		initSimple();
	}
	
	public void simulateRigid() 
	{
		if(isRigidDone == true)
		{
			resetRigid();
		}
		if(isDone() == sensorsNum)
		{
			isRigidDone = true;
			for(int i=0; i < sensorsNum; i++)
				totalSteps += sensors[i].getSteps();
			System.out.println(totalSteps);
			return;
		}
		for(int i=0; i < sensorsNum; i++)
			if(sensors[i].getX() != sensors[i].getTarget())
			{
				if(sensors[i].getX() > sensors[i].getTarget())
					sensors[i].setPosition(-1);
				else if(sensors[i].getX() < sensors[i].getTarget())
					sensors[i].setPosition(1);
			}
	}
	
	public void simulateSimple()
	{
		if(isSimpleDone == true)
			resetSimple();
		if(isDone() == sensorsNum)
		{
			isSimpleDone = true;
			for(int i=0; i < sensorsNum; i++)
				totalSteps += sensors[i].getSteps();
			System.out.println(totalSteps);
			return;
		}
		for(int i=0; i < sensorsNum; i++)
			if(sensors[i].getX() != sensors[i].getTarget())
			{
				if(sensors[i].getX() > sensors[i].getTarget())
					sensors[i].setPosition(-1);
				else if(sensors[i].getX() < sensors[i].getTarget())
					sensors[i].setPosition(1);
			}
	}

}

