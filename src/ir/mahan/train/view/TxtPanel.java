package ir.mahan.train.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class TxtPanel extends JPanel {
	
	JTextArea txtArea;

	public TxtPanel() {

		txtArea = new JTextArea();
		setLayout(new BorderLayout());
		JScrollPane scrol = new JScrollPane(txtArea);
		scrol.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		add(scrol, BorderLayout.CENTER);

		Dimension dim = getPreferredSize();
		dim.width = 400;
		setPreferredSize(dim);

		txtArea.setBorder(BorderFactory.createEmptyBorder());
		txtArea.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
	}
}
