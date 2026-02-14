import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class SlideShow extends JFrame {

	// Declare Variables
	private JPanel slidePane;
	private JPanel textPane;
	private JPanel buttonPane;
	private CardLayout card;
	private CardLayout cardText;
	private JButton btnPrev;
	private JButton btnNext;
	private JLabel lblSlide;
	private JLabel lblTextArea;

	// --------------------------------------------------------------------
	// UPDATED: Image filenames now match actual files in src/resources
	// Includes .jpg and .jfif extensions as used in the project
	// --------------------------------------------------------------------
	private static final String[] SLIDE_IMAGES = {
		"GrandCanyon.jpg",
		"NewYork.jfif",
		"Paris.jfif",
		"Tokyo.jfif",
		"Cancun.jpg"
	};

	/**
	 * Create the application.
	 */
	public SlideShow() throws HeadlessException {
		initComponent();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		// Initialize variables
		card = new CardLayout();
		cardText = new CardLayout();
		slidePane = new JPanel();
		textPane = new JPanel();
		textPane.setBackground(Color.BLUE);
		textPane.setVisible(true);

		buttonPane = new JPanel();
		btnPrev = new JButton();
		btnNext = new JButton();
		lblSlide = new JLabel();
		lblTextArea = new JLabel();

		// Frame settings
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Top 5 Destinations SlideShow");
		getContentPane().setLayout(new BorderLayout(10, 50));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel layouts
		slidePane.setLayout(card);
		textPane.setLayout(cardText);

		// Add slides and text
		for (int i = 1; i <= 5; i++) {
			lblSlide = new JLabel();
			lblTextArea = new JLabel();

			lblSlide.setText(getResizeIcon(i));
			lblTextArea.setText(getTextDescription(i));

			slidePane.add(lblSlide, "card" + i);
			textPane.add(lblTextArea, "cardText" + i);
		}

		getContentPane().add(slidePane, BorderLayout.CENTER);

		// --------------------------------------------------------------------
		// FIX: Combine text + buttons into one SOUTH panel
		// --------------------------------------------------------------------
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(textPane, BorderLayout.CENTER);

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		btnPrev.setText("Previous");
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goPrevious();
			}
		});
		buttonPane.add(btnPrev);

		btnNext.setText("Next");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goNext();
			}
		});
		buttonPane.add(btnNext);

		southPanel.add(buttonPane, BorderLayout.SOUTH);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Previous Button Functionality
	 */
	private void goPrevious() {
		card.previous(slidePane);
		cardText.previous(textPane);
	}

	/**
	 * Next Button Functionality
	 */
	private void goNext() {
		card.next(slidePane);
		cardText.next(textPane);
	}

	/**
	 * Method to get the images
	 */
	private String getResizeIcon(int i) {
		String filename = SLIDE_IMAGES[i - 1];
		URL url = getClass().getResource("/resources/" + filename);

		// If image not found, show clear message instead of blank slide
		if (url == null) {
			return "<html><body><font size='6'>Missing: " + filename + "</font></body></html>";
		}

		return "<html><body><img width='800' height='500' src='" + url + "'></body></html>";
	}

	/**
	 * Method to get the text values
	 */
	private String getTextDescription(int i) {
		String text = "";
		if (i == 1) {
			text = "<html><body><font size='5'>#1 The Grand Canyon</font><br>Spectacular canyon views and hiking.</body></html>";
		} else if (i == 2) {
			text = "<html><body><font size='5'>#2 New York City</font><br>Iconic landmarks, food, and culture.</body></html>";
		} else if (i == 3) {
			text = "<html><body><font size='5'>#3 Paris</font><br>World-famous art, history, and cuisine.</body></html>";
		} else if (i == 4) {
			text = "<html><body><font size='5'>#4 Tokyo</font><br>Modern city life blended with tradition.</body></html>";
		} else if (i == 5) {
			text = "<html><body><font size='5'>#5 Cancún</font><br>Beautiful beaches and tropical resorts.</body></html>";
		}
		return text;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				SlideShow ss = new SlideShow();
				ss.setVisible(true);
			}
		});
	}
}
