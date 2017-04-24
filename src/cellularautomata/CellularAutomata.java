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
private JMenu menuGen = new JMenu("Generate");
private JMenuItem menuGenCzysc = new JMenuItem("Clear");
private JMenuItem menuGerLosowo = new JMenuItem("Random");
private JMenuItem menuGenWygladzanie = new JMenuItem("Generate");
private final KeyStroke ctrl_W = KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK);
private final KeyStroke ctrl_T = KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK);
private final KeyStroke ctrl_C = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
private final int ROWS = 50, COLS = 80;
private Random random;
private int[][] cellMap = new int[ROWS][COLS];
private Button[][] buttons;
private int[][] fields;


public CellularAutomata()
{
	super("Cave Generator");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(1000, 700);
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
			buttons[i][j] = new Button(fields[i][j]+"", Color.GRAY);
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
			buttons[i][j].setBackground(Color.GRAY);
			//buttons[i][j].setText(fields[i][j]+"");
		}
	}
}

public int countNeighbours(int[][] map, int x, int y){
  int count = 0;
  for (int i=-1; i<2; i++) {
      for (int j=-1; j<2; j++) {
    	  int neighbour_x = x+i;
          int neighbour_y = y+j;
          if (i != 0 || j != 0) {
	      	  if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length) count = count + 1;
	      	  else if (map[neighbour_x][neighbour_y] == 1) count = count + 1;        	  
          }
      }
  }
  return count;
}

public int[][] wygladzanie(int [][] inputMap, int fullBlocks, int emptyBlocks, int defaultBlock, int defaultEmpty)
{
	int MX = inputMap.length;
	int MY = inputMap[0].length;
	int newMap[][] = new int[MX][MY];
	
	
	for (int x = 1; x < MX-1; x++) {
        for (int y = 1; y < MY-1; y++) {
        	newMap[x][y] = defaultEmpty;
            int nbs = countNeighbours(inputMap, x, y);
            if (inputMap[x][y] == 1) {
                if (nbs >= fullBlocks) {
                    newMap[x][y] = defaultBlock;
                }
            }
            else {
            	if (nbs >= emptyBlocks) {
                    newMap[x][y] = defaultBlock;
                }
            }
        }
    }
	return newMap;
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
				buttons[i][j].setBackground(Color.GRAY);
			}
		}
		
		cellMap = Arrays.copyOf(fields, fields.length);
		// LOSOWY TEREN
		for (int i = 1; i < ROWS-1; i++)
		{
			for (int j = 1; j < COLS-1; j++)
			{
				if (random(100) < 45) cellMap[i][j] = 1; // 45% bloków ścian
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
					buttons[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}
	
	if (e.getActionCommand().equalsIgnoreCase("WYGLADZANIE"))
	{
		fields = wygladzanie(fields, 3, 5, 1, 0);
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLS; j++)
			{
				if (fields[i][j] > 0)
				{
					buttons[i][j].setBackground(Color.WHITE);
				} else buttons[i][j].setBackground(Color.GRAY);
			}
		}
	}
	
	if (e.getActionCommand().equalsIgnoreCase("CZYSC"))
	{
		clearMap(true);
	}
}
}