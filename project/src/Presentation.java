import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.io.IOException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
public class Presentation implements ActionListener {

	private JFrame Window;
	private SensorAnimation sa;
	
	private int[] scores;
	private JTextField TurnsField;
	private JTextField SpeedField;
	private JTextField NumOfSensorsField;
	private JTextField RadiusField;
	public static boolean goodToGo = false;
	private JTextField lastScore;
	
	private int sensors = 10;
	private int radius = 25;
	private int method = 0;
	private int speed = 10;
	private int turns = 10;
	private JTextField textMethod;
	private JLabel lblMethods;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Presentation window = new Presentation();
					window.Window.setVisible(true);
					Timer timer = new Timer(window.speed, window);
					timer.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	 
	public Presentation() throws IOException {
		initialize();
		scores = new int[turns];
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		Window = new JFrame();
		Window.setTitle("COMP 3203 Project - Harth, Ryan - Fall 2016 - Evangelos Kranakis");
		Window.getContentPane().setBackground(new Color(250,240,230));
		Window.setResizable(false);
		Window.setBounds(100, 100, 600, 264);
		Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Window.getContentPane().setLayout(null);
		
		JPanel StatusBar = new JPanel();
		StatusBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		StatusBar.setBackground(SystemColor.controlHighlight);
		StatusBar.setBounds(10, 181, 574, 46);
		Window.getContentPane().add(StatusBar);
		StatusBar.setLayout(null);
		
		TurnsField = new JTextField();
		TurnsField.setEditable(false);
		TurnsField.setBounds(67, 20, 86, 20);
		StatusBar.add(TurnsField);
		TurnsField.setColumns(10);
		
		JLabel TurnsLabel = new JLabel("Turns");
		TurnsLabel.setBounds(84, 5, 57, 14);
		StatusBar.add(TurnsLabel);
		
		JLabel SpeedLabel = new JLabel("Speed");
		SpeedLabel.setBounds(180, 5, 57, 14);
		StatusBar.add(SpeedLabel);
		
		SpeedField = new JTextField();
		SpeedField.setEditable(false);
		SpeedField.setColumns(10);
		SpeedField.setBounds(163, 20, 86, 20);
		StatusBar.add(SpeedField);
		
		JLabel NumOfSensorsLabel = new JLabel("# of Sensors");
		NumOfSensorsLabel.setBounds(269, 5, 76, 14);
		StatusBar.add(NumOfSensorsLabel);
		
		NumOfSensorsField = new JTextField();
		NumOfSensorsField.setEditable(false);
		NumOfSensorsField.setColumns(10);
		NumOfSensorsField.setBounds(259, 20, 86, 20);
		StatusBar.add(NumOfSensorsField);
		
		JLabel RadiusLabel = new JLabel("Radius");
		RadiusLabel.setBounds(372, 5, 57, 14);
		StatusBar.add(RadiusLabel);
		
		RadiusField = new JTextField();
		RadiusField.setEditable(false);
		RadiusField.setColumns(10);
		RadiusField.setBounds(355, 20, 86, 20);
		StatusBar.add(RadiusField);
		
		lastScore = new JTextField();
		lastScore.setBackground(Color.GREEN);
		lastScore.setEditable(false);
		lastScore.setText("123");
		lastScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lastScore.setBounds(10, 5, 50, 35);
		StatusBar.add(lastScore);
		lastScore.setColumns(10);
		
		textMethod = new JTextField();
		textMethod.setEditable(false);
		textMethod.setColumns(10);
		textMethod.setBounds(451, 20, 86, 20);
		StatusBar.add(textMethod);
		
		lblMethods = new JLabel("Methods");
		lblMethods.setBounds(468, 5, 57, 14);
		StatusBar.add(lblMethods);
		
		JPanel Animation = new JPanel();
		Animation.setBorder(new LineBorder(new Color(0, 0, 0)));
		Animation.setBounds(10, 11, 574, 159);
		Window.getContentPane().add(Animation); 
		Animation.setLayout(null);
		
		
		sensors = Integer.parseInt(JOptionPane.showInputDialog(null, null, 
	               "Please enter number of Sensors", JOptionPane.OK_CANCEL_OPTION));
		NumOfSensorsField.setText(String.valueOf(sensors));
		
		radius = Integer.parseInt(JOptionPane.showInputDialog(null, null, 
	               "Please enter Radius", JOptionPane.OK_CANCEL_OPTION));
		RadiusField.setText(String.valueOf(radius));
		
		method = Integer.parseInt(JOptionPane.showInputDialog(null, null, 
	               "0 = Rigid, 1 = Simple", JOptionPane.OK_CANCEL_OPTION));
		if(method == 0)
			textMethod.setText("Rigid");
		else if(method == 1)
			textMethod.setText("Simple");
		
		speed = Integer.parseInt(JOptionPane.showInputDialog(null, null, 
	               "Please enter a Speed, recommend 1-10", JOptionPane.OK_CANCEL_OPTION));
		SpeedField.setText(String.valueOf(speed));
		
		turns = Integer.parseInt(JOptionPane.showInputDialog(null, null, 
	               "Please enter a number of turns recommend 1-10", JOptionPane.OK_CANCEL_OPTION));
		TurnsField.setText(String.valueOf(turns));
		
		sa = new SensorAnimation(sensors, radius, method); // num, range
		sa.setBorder(new LineBorder(new Color(0,0,0)));
		sa.setBounds(0, 0, 573, 158);
		Animation.add(sa);
		sa.setLayout(null);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			//System.out.println(turns);
		if(sa.getMethod() == 0)
		{
			if(sa.isRigidDone() == true)
			{
				if(turns == 0)
					return;
				
				else if(turns !=0)
				{
					scores[turns-1] = sa.getTotalSteps();
					lastScore.setText(String.valueOf(sa.getTotalSteps()));
					sa.resetRigid();
					turns--;
				}
			}
			else if(sa.isRigidDone() == false && turns > 0)
				sa.simulateRigid();
		}
			
		if(sa.getMethod() == 1)
		{
			if(sa.isSimpleDone() == true)
			{
				if(turns == 0)
					return;
			else if(turns !=0)
				{
					scores[turns-1] = sa.getTotalSteps();
					lastScore.setText(String.valueOf(sa.getTotalSteps()));
					sa.resetSimple();
					turns--;
				}
			}
		else if(sa.isSimpleDone() == false && turns > 0)
			sa.simulateSimple();
		}
	
		sa.repaint();
	}
}
	
//how to add a jfree chart to jpanel

	/*
	 * if(sa.isRigidDone() == true)
			{
				if(turns == 0)
					return;
				else if(turns !=0)
				{
					scores[turns-1] = sa.getTotalSteps();
					sa.resetRigid();
					turns--;
				}
			}
			else if(sa.isRigidDone() == false && turns > 0)
				sa.simulateRigid();
				
				
				//DOESNT SHOW ANY ANIMATION
				if(turns == 0)
				return;
			else if(turns > 0)
			{
				if(sa.isRigidDone() == false)
				{
					sa.simulateRigid();
					return;
				}
				else if(sa.isRigidDone() == true)
				{
					scores[turns-1] = sa.getTotalSteps();
					sa.resetRigid();
					turns -= 1;
					return;
				}
			}
			
			try {
			//if(sa.action(e, sa.isRigidDone()==true)==true)
				//System.out.print("Hello");
			/*
			//System.out.println(turns);
			if(sa.isRigidDone() == true)
			{
				if(turns == 0)
					return;
				else if(turns !=0)
				{
					scores[turns-1] = sa.getTotalSteps();
					sa.resetRigid();
					System.out.print(turns+" --> ");
					turns--;
					System.out.println(turns);
				}
			}
			else if(sa.isRigidDone() == false && turns > 0)
				sa.simulateRigid();
				
			sa.simulateRigid();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sa.repaint();
	*/
	
