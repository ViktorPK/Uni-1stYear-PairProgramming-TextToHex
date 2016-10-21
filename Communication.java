package kristiyanviktor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This Class is a custom JFrame that represents a communication panel featuring
 * a Camera, a TextArea and a Button.
 * 
 * @author Viktor
 * @author Kristiyan
 */
public class Communication extends JFrame {
	private static String defaultTitle = "Matt Damon on Mars";
	private ArrayList<String> charValues;
	private ArrayList<String> hexValues;
	private ArrayList<Integer> intValues;
	private JTextArea textArea;
	private HashMap<String, String> tableHex;
	private HashMap<String, String> tableDec;
	private Camera cam;
	private JButton sendButton;

	/**
	 * Class constructor. Creates a Communication frame with name
	 * "Matt Damon on Mars" and a 300x300 size. Also imports an ascii table used
	 * for conversions and an ActionListener for the sendButton.
	 */
	public Communication() { // Class constructor.
		super(defaultTitle); // sets the title of the frame to the defaultTitle
		charValues = new ArrayList<String>();
		hexValues = new ArrayList<String>();
		intValues = new ArrayList<Integer>();
		tableHex = new HashMap<String, String>();
		tableDec = new HashMap<String, String>();
		cam = new Camera();
		sendButton = new JButton("Send");
		textArea = new JTextArea();
		setLayout(new BorderLayout());
		add(cam, BorderLayout.NORTH);
		JPanel mid=new JPanel();
		add(textArea, BorderLayout.CENTER);
		add(sendButton, BorderLayout.SOUTH);
		setSize(300, 300); // sets the size to be 300x300
		setVisible(true);
		importTable(); // imports the ascii table
		addButtonListener(); // adds an ActionListener to the sendButton
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Adds an ActionListener to the sendButton which starts a new Thread and
	 * invokes the communication method on click.
	 */
	public void addButtonListener() { // adds an ActionListener to the
										// sendButton
		sendButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Thread t1 = new Thread(new Runnable() { // creates a new Thread
					public void run() {
						communicate(); // starts the communication method when
										// the thread runs
					}
				});
				t1.start(); // starts the thread

			}
		});
	}

	/**
	 * Starts moving the slider to represent the text written in the textArea.
	 * Changes the title of the frame to "Sending..." while the slider is moving
	 * and then returns it to the default title afterwards.
	 */
	public void communicate() { // Starts moving the slider to represent the
								// text written in the textArea.
		setTitle("Sending..."); // Changes the title of the frame to
								// "Sending..." while the slider is moving
		getArea();
		textArea.setText(""); // deletes the text in the textArea (5. Testing
								// your program)
		toHex();
		toInt();
		moveSlider();
		// System.out.println(charValues); I've left them here if I want to
		// check what are the stored values
		// System.out.println(hexValues);
		// System.out.println(intValues);
		setTitle(defaultTitle); // sets the title of the frame to the default
								// one after the slider stops moving
	}

	/**
	 * Gets the text in the textArea field in an ArrayList.
	 * 
	 * @return an ArrayList of all the characters typed
	 */
	public ArrayList<String> getArea() { // Gets the text in the textArea field
											// in an ArrayList.
		charValues.clear(); // clears the charValues arrayList on invocation to
							// prevent accumulation of used data
		String typed = textArea.getText();
		for (String s : typed.split("")) { // splits the string typed in the
											// textArea to separate characters.
			charValues.add(s); // adds those characters to the charValues
								// arrayList
		}
		return charValues;
	}

	/**
	 * Converts all characters in the charValues ArrayList to their hexadecimal
	 * values according to the ascii table provided.
	 */
	public void toHex() { // Converts all characters in the charValues ArrayList
							// to their hexadecimal values according to the
							// ascii table provided.
		hexValues.clear(); // clears the hexValues arrayList on invocation to
							// prevent accumulation of used data
		for (String i : charValues) {
			for (Map.Entry<String, String> entry : tableHex.entrySet()) {
				if (i.equals(entry.getKey())) {

					i = entry.getValue();
					hexValues.add(i);

				}
			}

		}
	}

	/**
	 * Converts every hexadecimal value in the hexValues ArrayList to their
	 * integer representations according to the ascii table provided.
	 */
	public void toInt() { // Converts every hexadecimal value in the hexValues
							// ArrayList to their integer representations
							// according to the ascii table provided.
		intValues.clear(); // clears the hexValues arrayList on invocation to
							// prevent accumulation of used data
		for (String s : hexValues) {
			for (String x : s.split("")) {
				for (Map.Entry<String, String> entry : tableDec.entrySet()) {
					if (x.equals(entry.getKey())) {
						x = entry.getValue();
						intValues.add(Integer.parseInt(x));
					}
				}
			}
		}
	}

	/**
	 * Imports the ascii table provided as two HashMaps tableHex used for the
	 * conversion from characters to their hexadecimal values tableInt used for
	 * conversion form hexadecimal to decimal
	 */
	public void importTable() { // Imports the ascii table provided as two
								// HashMaps
		int i = 0;
		BufferedReader br = null;
		String line = "";
		String ascii = "ascii_table.csv";

		try {
			br = new BufferedReader(new FileReader(ascii));
			while ((line = br.readLine()) != null) { // reads the table line by
														// line
				String[] stuff = line.split(","); // puts the columns in an
													// array of strings
				tableHex.put(stuff[4], stuff[2]); // gets the Symbol and the Hex
													// columns of this line and
													// puts them in a HashMap
				if (i <= 10 && i != 0)
					tableDec.put(stuff[2], stuff[0]); // gets the Hex and Dec
														// columns of this line
														// and puts them in a
														// HashMap
				if (i > 10 && i <= 16)
					tableDec.put(stuff[2].substring(1), stuff[0]);
				i++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("No ascii table found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Moves the slider to the according positions and pauses the thread after
	 * every move.
	 */
	public void moveSlider() { // Moves the slider to the according positions
								// and pauses the thread after every move.
		for (Integer i : intValues) {
			if (i <= 15)
				cam.setValue(i);
			try {
				Thread.sleep(1000); // pauses the thread for a second
			} catch (InterruptedException e1) {
				System.out.println("Something happened");
			}
		}
	}
}
