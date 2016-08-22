package cellularautomata;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class CellularAutomata extends JFrame implements ActionListener{

private static final long serialVersionUID = -6462465077833094465L;


private JPanel panel;
private JMenuBar bar = new JMenuBar();
private JMenu menuGen = new JMenu("Generuj");
private JMenuItem menuGenCzysc = new JMenuItem("Czyœæ");
private JMenuItem menuGerLosowo = new JMenuItem("Losowy teren");
private JMenuItem menuGenWygladzanie = new JMenuItem("Wyg³adzanie");
private final KeyStroke ctrl_W = KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK);
private final KeyStroke ctrl_T = KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK);
private final KeyStroke ctrl_C = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
private final int ROWS = 40, COLS = 40;
private Random random;
private boolean[][] cellMap = new boolean[ROWS][COLS];
private Button[][] buttons;
private int[][] fields;


public CellularAutomata()
{
	super("Automat Komórkowy");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(800, 700);
	setLocationRelativeTo(null);
	
	random = new Random();
	
	menuGen.add(menuGenCzysc);
	menuGen.add(menuGerLosowo);
	menuGen.add(menuGenWygladzanie);
	
	menuGenCzysc.setAccelerator(ctrl_C);
	menuGenCzysc.addActionListener(this);
	menuGenCzysc.setActionCommand("CZYSC");

	menuGenWygladzanie.setAccelerator(ctrl_W);
	menuGenWygladzanie.addActionListener(this);
	menuGenWygladzanie.setActionCommand("WYGLADZANIE");
	
	menuGerLosowo.setAccelerator(ctrl_T);
	menuGerLosowo.addActionListener(this);
	menuGerLosowo.setActionCommand("LOSOWO");
	
	bar.add(menuGen);
	setJMenuBar(bar);
	
	panel = new JPanel(new GridLayout(ROWS, COLS));
	
	buttons = new Button[ROWS][COLS];
	fields = new int[ROWS][COLS];
	
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			buttons[i][j] = new Button(fields[i][j]+"", Color.WHITE);
			panel.add(buttons[i][j]);
			fields[i][j] = 0;
		}
	}
	add(panel);
}

	
public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable()
	{
		@Override
		public void run()
		{
			new CellularAutomata().setVisible(true);	
		}
	});
}

public int random(int number)
{
	int i = random.nextInt(number);
	return i;
}
	
public int random(int start, int end)
{
	int i = random.nextInt(end-start+1)+start;
	return i;
}

public void clearMap(boolean b)
{
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			if (b) fields[i][j] = 0;
			buttons[i][j].setBackground(Color.WHITE);
			buttons[i][j].setText(fields[i][j]+"");
		}
	}
}

public boolean[][] wygladzanie(boolean[][] inputMap, int x, int y)
{
	int count = 0;
    for(int i=-1; i<2; i++){
        for(int j=-1; j<2; j++){
            int neighbour_x = x+i;
            int neighbour_y = y+j;
            //If we're looking at the middle point
            if(i == 0 && j == 0){
                //Do nothing, we don't want to add ourselves in!
            }
            //In case the index we're looking at it off the edge of the map
            else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= inputMap.length || neighbour_y >= inputMap[0].length){
                count = count + 1;
            }
            //Otherwise, a normal check of the neighbour
            else if(inputMap[neighbour_x][neighbour_y]){
                count = count + 1;
            }
        }
    }
	return inputMap;
}

public void clearTempMap()
{
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			cellMap[i][j] = 0;
		}
	}
}

@Override
public void actionPerformed(ActionEvent e) {
	
	if (e.getActionCommand().equalsIgnoreCase("LOSOWO"))
	{
		//clearTempMap();
		
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLS; j++)
			{
				buttons[i][j].setBackground(Color.WHITE);
			}
		}
		
		cellMap = Arrays.copyOf(fields, fields.length);
		
		// LOSOWY TEREN
		for (int i = 1; i < ROWS-1; i++)
		{
			for (int j = 1; j < COLS-1; j++)
			{
				if (random(4) == 0) cellMap[i][j] = 1;
				//else tempMap[i][j] = 0;
			}
		}
		
		fields = Arrays.copyOf(cellMap, cellMap.length);
		
		// DODANIE DO OBECNEGO TERENU - TERENU LOSOWEGO
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLS; j++)
			{
				if (fields[i][j] == 1)
				{
					buttons[i][j].setBackground(Color.GRAY);
					buttons[i][j].setText(fields[i][j]+"");
				}
			}
		}
	}
	
	
	if (e.getActionCommand().equalsIgnoreCase("WYGLADZANIE"))
	{
		
		fields = wygladzanie(fields, 5, 4, 1, 0);		
		
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLS; j++)
			{
				if (fields[i][j] > 0)
				{
					buttons[i][j].setBackground(Color.GRAY);
				} else buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].setText(fields[i][j]+"");
			}
		}
		
		
	}
	
	if (e.getActionCommand().equalsIgnoreCase("CZYSC"))
	{
		clearMap(true);
	}
}
}