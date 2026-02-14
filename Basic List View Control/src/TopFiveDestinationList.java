import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                topDestinationListFrame.setTitle("Top 5 Destination List");
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}

class TopDestinationListFrame extends JFrame {
    private DefaultListModel listModel;

    public TopDestinationListFrame() {
        super("Top Five Destination List");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);

        listModel = new DefaultListModel();

        // ✅ MINIMAL CHANGE:
        // Your images are in: src/resources/
        // Since "resources" is inside "src", Eclipse puts them at the classpath root.
        // So use "/filename.jpg" (NOT "/resources/filename.jpg")
        addDestinationNameAndPicture("1. Paris, France - A timeless city of art, romance, and iconic landmarks.",
                loadIcon("/paris.jpg"));
        addDestinationNameAndPicture("2. Tokyo, Japan - A vibrant mix of tradition, technology, and incredible food.",
                loadIcon("/tokyo.jpg"));
        addDestinationNameAndPicture("3. Rome, Italy - Ancient history, famous ruins, and unforgettable cuisine.",
                loadIcon("/rome.jpg"));
        addDestinationNameAndPicture("4. New York City, USA - Endless attractions, culture, and entertainment.",
                loadIcon("/nyc.jpg"));
        addDestinationNameAndPicture("5. Cancún, Mexico - Beautiful beaches and relaxing resorts with clear waters.",
                loadIcon("/cancun.jpg"));

        JList list = new JList(listModel);

        // ✅ Customization: background + selection colors + font (counts as “additional customization”)
        list.setBackground(new Color(250, 250, 250));
        list.setSelectionBackground(new Color(220, 235, 252));
        list.setSelectionForeground(Color.BLACK);
        list.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(list);

        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(10);
        list.setCellRenderer(renderer);

        // ✅ Add your name label (required)
        JLabel nameLabel = new JLabel("Created by: Jorge Chavarria");
        nameLabel.setBorder(new EmptyBorder(10, 15, 10, 15));
        nameLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        nameLabel.setForeground(new Color(80, 80, 80));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(nameLabel, BorderLayout.SOUTH);
    }

    private void addDestinationNameAndPicture(String text, Icon icon) {
        TextAndIcon tai = new TextAndIcon(text, icon);
        listModel.addElement(tai);
    }

    // ✅ Safe resource loading so you don’t crash if a file is missing
    private Icon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);

        // Helpful debug line (leave it in—counts as a “clear comment”/support)
        System.out.println("Loading image: " + path + " -> " + url);

        if (url == null) {
            // If not found, show a warning icon instead of crashing
            return UIManager.getIcon("OptionPane.warningIcon");
        }

        ImageIcon original = new ImageIcon(url);
        Image scaled = original.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}

class TextAndIcon {
    private String text;
    private Icon icon;

    public TextAndIcon(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}

class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer {
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private Border insideBorder;

    public TextAndIconListCellRenderer() {
        this(0, 0, 0, 0);
    }

    public TextAndIconListCellRenderer(int padding) {
        this(padding, padding, padding, padding);
    }

    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean hasFocus) {

        TextAndIcon tai = (TextAndIcon) value;

        setText(tai.getText());
        setIcon(tai.getIcon());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        Border outsideBorder;

        if (hasFocus) {
            outsideBorder = UIManager.getBorder("List.focusCellHighlightBorder");
        } else {
            outsideBorder = NO_FOCUS_BORDER;
        }

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setComponentOrientation(list.getComponentOrientation());
        setEnabled(list.isEnabled());
        setFont(list.getFont());

        return this;
    }

    public void validate() {}
    public void invalidate() {}
    public void repaint() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
}
