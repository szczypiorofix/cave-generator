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
private JMenuItem menuGenCzysc = new JMenuItem("Czyść");
private JMenuItem menuGerLosowo = new JMenuItem("Losowy teren");
private JMenuItem menuGenWygladzanie = new JMenuItem("Wygładzanie");
private final KeyStroke ctrl_W = KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK);
private final KeyStroke ctrl_T = KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK);
private final KeyStroke ctrl_C = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
private final int ROWS = 40, COLS = 40;
private Random random;
private int[][] cellMap = new int[ROWS][COLS];
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

public int[][] wygladzanie(int [][] inputMap, int fullBlocks, int emptyBlocks, int defaultBlock, int defaultEmpty)
{
	int MX = inputMap.length;
	int MY = inputMap[0].length;
	
	int temp[][] = new int[MX][MY];
	
	// PRZEPISANIE OBECNEJ MAPY NA TEMPMAPE
	for (int i = 0; i < MX; i++)
	{
		for (int j = 0; j < MY; j++)
		{
			temp[i][j] = inputMap[i][j];
		}
	}
	
	// TUTAJ JEST WYGŁADZANIE TERENU
	int[][] maxT = new int[MX][MY]; // TABLICA BLOKÓW
	for (int i = 0; i < MX; i++)
		for (int j = 0; j < MY; j++)
			maxT[i][j] = defaultEmpty;
			
	int[][] maxE = new int[MX][MY]; // TABLICA BLOKÓW
	for (int i = 0; i < MX; i++)
		for (int j = 0; j < MY; j++)
			maxE[i][j] = defaultEmpty;

	for (int i = 0; i < MX; i++)
	{
		for (int j = 0; j < MY; j++)
		{
			if ((i > 0) && i < (MX-1) && (j > 0) && (j < MY-1))
			{			
				/// SPRAWDZANIE KONKRETNEGO BLOKU PEŁNEGO
				maxT[i][j] = 0;
				int[][] tempNeighbor = new int[3][3];
							
				// ZBIERANIE SASIADÓW
				for (int a = -1; a < 2; a++)
					for (int b = -1; b < 2; b++)
					{
						tempNeighbor[a+1][b+1] = temp[i+a][j+b];						
					}
							
				for (int a = 0; a < 3; a++)
					for (int b = 0; b < 3; b++)
					{
						if (tempNeighbor[a][b] == 1) maxT[i][j] += 1; // NABIJANIE ILOŚCI SASIADÓW
					}	
			}

			if (maxT[i][j] > fullBlocks)
			{
				temp[i][j] = defaultBlock;
			}
		}
	}
	
	for (int i = 0; i < MX; i++)
	{
		for (int j = 0; j < MY; j++)
		{
			if ((i > 0) && i < (MX-1) && (j > 0) && (j < MY-1))
			{			
				/// SPRAWDZANIE KONKRETNEGO BLOKU PE�NEGO
				maxE[i][j] = 0;
				int[][] tempNeighbor = new int[3][3];			
				// ZBIERANIE SASIAD�W
				for (int a = -1; a < 2; a++)
					for (int b = -1; b < 2; b++)						
						tempNeighbor[a+1][b+1] = temp[i+a][j+b];
							
				for (int a = 0; a < 3; a++)
					for (int b = 0; b < 3; b++)
					{
						if (tempNeighbor[a][b] == 0) maxE[i][j] += 1; // NABIJANIE ILO�CI SASIAD�W
					}	
			}
			if (maxE[i][j] > emptyBlocks)
			{
				temp[i][j] = defaultEmpty;
			}
		}
	}
	return temp;
}


@Override
public void actionPerformed(ActionEvent e) {
	
	if (e.getActionCommand().equalsIgnoreCase("LOSOWO"))
	{
		//clearTempMap(); // NAKŁADANIE LOSOWYCH POZIOMÓW ?
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