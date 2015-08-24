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

		setTextArea();

		setLayout(new BorderLayout());

		createScrol();

		setDemension();

	}

	private void setDemension() {
		Dimension dim = getPreferredSize();
		dim.width = 400;
		setPreferredSize(dim);
	}

	private void setTextArea() {

		txtArea = new JTextArea();
		txtArea.setBorder(BorderFactory.createEmptyBorder());
		txtArea.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

	}

	private void createScrol() {

		JScrollPane scrol = new JScrollPane(txtArea);

		scrol.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrol, BorderLayout.CENTER);

	}
}
