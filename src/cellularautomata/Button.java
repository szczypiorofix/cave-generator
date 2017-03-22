package cellularautomata;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Button extends JLabel {

private static final long serialVersionUID = -5319332076895098047L;


private String title;
private Color color;

public Button(String title, Color color)
{
	this.title = title;
	this.color = color;
	this.setOpaque(true);
	setFont(new Font("Verdana", Font.PLAIN, 8));
	//setText(this.title);
	setBackground(this.color);
}
}
