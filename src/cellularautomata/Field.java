package cellularautomata;

public class Field
{

private int width;
private int height;
private int neighbor;
private int[][] map;
private int value;

public Field(int[][] map)
{
	this.map = map;
	width = map.length;
	height = map[0].length;
	neighbor = 0;
	value = 0;
}


public int getWidth() {
	return width;
}

public int getHeight() {
	return height;
}

public int getNeighbor() {
	return neighbor;
}

public void setNeighbor(int neighbor) {
	this.neighbor = neighbor;
}

public int[][] getMap() {
	return map;
}

public void setMap(int[][] map) {
	this.map = map;
}

public int getValue() {
	return value;
}

public void setValue(int value) {
	this.value = value;
}
}