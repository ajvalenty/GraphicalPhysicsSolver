import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

/**
 * Created by markmcconachie on 1/21/17.
 */
public class PendulumPanel extends PhysicsPanel
{

    private JButton startButton;
    private JButton stopButton;
    private JButton setButton;
    private JFormattedTextField thetaField;
    private JFormattedTextField lengthField;
    private NumberFormat thetaFormat;
    private NumberFormat lengthFormat;
    private RunnablePanel pendulumPanel;
    private JPanel fieldPanel;
    private JPanel upperButtonPanel;
    private JPanel upperPanel;
    private ActionPanel lowerPanel;
    private SimplePendulum pendulum;
    private PhysicsEquations Eq;
    private double thetaLocal;
    private double lengthLocal;

    public PendulumPanel(double theta, double length)
    {
        super();
        Eq= new PhysicsEquations();
        thetaLocal=theta;
        lengthLocal=length;
        init(thetaLocal,lengthLocal);


    }

    public void init(double theta, double length)
    {


        //defines panels
        //mainPanel = new JPanel();
        //adds the pendulum to he array list
        pendulum = new SimplePendulum(length, Eq.degreesToRadian(theta));
        pendulumPanel = new RunnablePanel(pendulum);
        lowerPanel= new ActionPanel();
        //lowerPanel.setBackground(Color.GRAY);
        upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBackground(Color.WHITE);
        upperButtonPanel = new JPanel();
        upperButtonPanel.setBackground(Color.WHITE);
        fieldPanel = new JPanel();
        fieldPanel.setBackground(Color.WHITE);
        //fieldPanel.setLayout(new GridLayout(1,5));
        //simulationPanel= new RunablePanel();
        pendulumPanel.setLayout(new BorderLayout());
        pendulumPanel.add(lowerPanel, BorderLayout.SOUTH);
        pendulumPanel.add(upperPanel,BorderLayout.NORTH);
        //pendulumPanel.add(simulationPanel,BorderLayout.CENTER);


        //defines buttons
        stopButton = new JButton("Stop");
        startButton = new JButton("Start");
        setButton = new  JButton("Set");

        //defines text fields
        lengthField = new JFormattedTextField(thetaFormat);
        lengthField.setValue(lengthLocal);
        lengthField.setColumns(4);

        thetaField = new JFormattedTextField(thetaFormat);
        thetaField.setValue(thetaLocal);
        thetaField.setColumns(4);

        //adds buttons to the panel
        lowerPanel.add(startButton);
        lowerPanel.add(stopButton);

        fieldPanel.add(new JLabel("Angle (deg)"));
        fieldPanel.add(thetaField);
        fieldPanel.add(new JLabel("                  "));
        fieldPanel.add(new JLabel("Length (cm)"));
        fieldPanel.add(lengthField);



        //add the buttons to the upper button panel
        upperButtonPanel.add(setButton);

        //add the button and field panels to the upper panel
        upperPanel.add(fieldPanel,BorderLayout.NORTH);
        upperPanel.add(upperButtonPanel,BorderLayout.SOUTH);


        //add actionliseners to buttons on a clickable panel
        lengthField.addPropertyChangeListener("Length", lowerPanel);
        thetaField.addPropertyChangeListener("Theta", lowerPanel);
        stopButton.addActionListener(lowerPanel);
        startButton.addActionListener(lowerPanel);
        setButton.addActionListener(lowerPanel);


        //intitalizes the equation conversions/solver


        //simulationPanel.add(pendulum);
        pendulumPanel.add(pendulum);
        pendulum.repaint();
        //simulationPanel.repaint();
        pendulumPanel.repaint();
        this.setLayout(new BorderLayout());
        this.add(pendulumPanel,BorderLayout.CENTER);
        //starts the tread in the main panel
        Thread simulationThread = new Thread(pendulumPanel);
        simulationThread.start();

    }


    public class ActionPanel extends JPanel implements ActionListener, PropertyChangeListener
    {


        @Override
        public void actionPerformed(ActionEvent event)
        {

            Object source = event.getSource();

            if (source == stopButton)
            {
                //pendulum.setTheta(Eq.degreesToRadian((double)thetaField.getValue()));

                //System.out.println((double)thetaField.getValue());

                //pendulum.setLength((double)lengthField.getValue());
                //pendulum.reset();
                pendulumPanel.setRunning(false);
                System.out.println("Stop clicked");
                //mainPanel.run();
            } else if (source == startButton)
            {
                //pendulum.setTheta(Eq.degreesToRadian((double)thetaField.getValue()));
                //System.out.println((double)thetaField.getValue());
                //pendulum.setLength((double)lengthField.getValue());
                //pendulum.reset();
                System.out.println("Start clicked");
                pendulumPanel.setRunning(true);
                //mainPanel.run();
            }
            else if (source == setButton)
            {
                pendulum.setTheta(Eq.degreesToRadian((double)thetaField.getValue()));
                System.out.println((double)thetaField.getValue());
                pendulum.setLength((double)lengthField.getValue());
                pendulum.reset();
            }

        }

        @Override
        public void propertyChange(PropertyChangeEvent event)
        {
            Object source = event.getSource();
            if (source == thetaField)
            {
                thetaLocal = ((Number) thetaField.getValue()).doubleValue();
                System.out.println("theta Changed");
            }
            else if (source == lengthField)
            {
                lengthLocal = ((Number) lengthField.getValue()).doubleValue();
            }
        }
    }
}