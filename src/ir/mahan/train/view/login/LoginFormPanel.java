package ir.mahan.train.view.login;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFormPanel extends JPanel {

	private JTextField usernameTxtField;
	private JPasswordField passwordTxtField;

	private JLabel usernameLbl;
	private JLabel passwordLbl;
	public JLabel connectToDBLbl;
	private JLabel usernameMandatoryLbl;
	private JLabel passwordMandatoryLbl;
	private JButton submitButton;

	private ILoginFormListener iLoginFormListener;

	public String getUsernameTxtField() {
		return usernameTxtField.getText();
	}

	public String getPasswordTxtField() {
		return passwordTxtField.getText();
	}

	public void setConnectToDBLbal(String str) {
		this.connectToDBLbl.setText(str);
	}

	public LoginFormPanel() {

		this.setLayout(new GridBagLayout());

		createComponent();
		addActionListenerToComponents();
		layoutComponent();

	}

	public void setLoginInterface(ILoginFormListener iLoginForm) {

		this.iLoginFormListener = iLoginForm;
	}

	private void createComponent() {

		usernameTxtField = new JTextField();
		usernameLbl = new JLabel("Username: ");
		usernameMandatoryLbl = new JLabel("*");

		passwordTxtField = new JPasswordField();
		passwordLbl = new JLabel("Password: ");
		passwordMandatoryLbl = new JLabel("*");

		submitButton = new JButton("Login");

		connectToDBLbl = new JLabel("Disconnect");
	}

	private void addActionListenerToComponents() {

		usernameTxtField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					KeyboardFocusManager.getCurrentKeyboardFocusManager()
							.focusNextComponent();
				}
			}
		});

		passwordTxtField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					iLoginFormListener.sendUserInfoFromPanelToFrame();
				}
			}
		});

		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				iLoginFormListener.sendUserInfoFromPanelToFrame();
			}
		});
	}

	private void layoutComponent() {

		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 0.1;

		// usernameLbl
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.CENTER;
		add(usernameLbl, gc);

		// usernameTxtField
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(usernameTxtField, gc);

		// usernameMandatoryLbl
		gc.gridx = 2;
		gc.gridy = 0;
		add(usernameMandatoryLbl, gc);
		usernameMandatoryLbl.setForeground(Color.RED);
		usernameMandatoryLbl.setVisible(false);

		// passwordLbl
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.NONE;
		add(passwordLbl, gc);

		// passwordTxtField
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(passwordTxtField, gc);

		// passwordMandatoryLbl
		gc.gridx++;
		add(passwordMandatoryLbl, gc);
		passwordMandatoryLbl.setForeground(Color.RED);
		passwordMandatoryLbl.setVisible(false);

		// submitButton
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(connectToDBLbl, gc);

		// submitButton
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(submitButton, gc);

	}

	public void setVisibleMandatortFiledsStar(String usr, String psw) {

		if (usr.isEmpty()) {
			this.usernameMandatoryLbl.setVisible(true);
		} else {
			this.usernameMandatoryLbl.setVisible(false);
		}

		if (psw.isEmpty()) {
			this.passwordMandatoryLbl.setVisible(true);
		} else {
			this.passwordMandatoryLbl.setVisible(false);
		}

	}

}
