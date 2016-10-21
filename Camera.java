package kristiyanviktor;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * This Class is a JSlider representation of a Camera.
 * 
 * @author Viktor
 * @author Kristiyan
 */
public class Camera extends JSlider {
	/**
	 * Class constructor. Creates a custom JSlider with 16 positions labeled
	 * from 0-9 and then from A-F.
	 */
	public Camera() { // Class constructor. Creates a custom JSlider with 16
						// positions labeled from 0-9 and then from A-F.
		super(0, 15, 0);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0"));
		labelTable.put(1, new JLabel("1"));
		labelTable.put(2, new JLabel("2"));
		labelTable.put(3, new JLabel("3"));
		labelTable.put(4, new JLabel("4"));
		labelTable.put(5, new JLabel("5"));
		labelTable.put(6, new JLabel("6"));
		labelTable.put(7, new JLabel("7"));
		labelTable.put(8, new JLabel("8"));
		labelTable.put(9, new JLabel("9"));
		labelTable.put(10, new JLabel("A"));
		labelTable.put(11, new JLabel("B"));
		labelTable.put(12, new JLabel("C"));
		labelTable.put(13, new JLabel("D"));
		labelTable.put(14, new JLabel("E"));
		labelTable.put(15, new JLabel("F"));
		setLabelTable(labelTable);
		setMajorTickSpacing(1);
		setPaintTicks(true);
		setPaintLabels(true);
	}
}
