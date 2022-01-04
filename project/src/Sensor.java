import java.awt.*;

public class Sensor implements Comparable<Sensor> {
	
	private int x;
	private int y;
	private int radius;
	private Color status;
	private int steps = 0;
	private int targetX;
	private boolean onTarget = false;
	
	public Sensor(int xCoor, int yCoor, int r) {
		x = xCoor;
		y = yCoor;
		radius = r;
		status = Color.RED;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(status);
		g.fillOval((x-radius), (y-radius), radius, radius);
		g.setColor(Color.black);
		g.drawLine(x-(radius/2), 73-(radius/2), targetX, 90);
		g.setColor(Color.MAGENTA);
		g.fillOval(targetX, 90, radius/4, radius/4);
	}
	
	public int getX()
	{	return x;	}
	
	public int getTarget()
	{	return targetX;		}
	
	public int getSteps()
	{	return steps;	}
	
	public void reset()
	{
		steps = 0;
		x = 0;
		setRed();
	}
	
	public boolean isOnTarget()
	{
		if(onTarget == true)
			return true;
		if(x == targetX)
		{
			onTarget = true;
			setGreen();
			return true;
		}
		if(x != targetX)
			return false;
		return false;
	}
	
	private void setRed()
	{	status = Color.ORANGE;	}
	
	private void setYellow()
	{	status = Color.ORANGE;	}
	
	private void setGreen()	{
		if(status == Color.magenta)
			return;
		status = Color.GREEN;	}
	
	public void setPurple()	//may want to delete
	{	status = Color.MAGENTA;	}
	
	public void setPosition(int x)
	{
		if(steps == 0 && (status != Color.MAGENTA))
			setYellow();
		this.x += x;
		steps++;
	}
	
	public void setTarget(int x)
	{	targetX = x;	}

	@Override
	public int compareTo(Sensor arg0) {
		// TODO Auto-generated method stub
		return this.getX() - arg0.getX();
	}
	
}
