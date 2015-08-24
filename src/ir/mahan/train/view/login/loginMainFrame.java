package ir.mahan.train.view.login;

import ir.mahan.train.Model.User;
import ir.mahan.train.controller.Controller;
import ir.mahan.train.view.MainFrame;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class loginMainFrame extends JDialog {

	private LoginFormPanel loginFormPannel;
	private Controller controller;

	public loginMainFrame(String title) {

		setView();

		loginFormPannel = new LoginFormPanel();
		controller = new Controller();

		this.add(loginFormPannel);

		connectToDB();

		clickOnLoginButton();

	}

	private void connectToDB() {

		try {

			controller.connectToDatabase();
			loginFormPannel.setConnectToDBLbal("*Database is Connected*");

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(loginFormPannel, e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(loginFormPannel, e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void clickOnLoginButton() {

		loginFormPannel.setLoginInterface(new ILoginFormListener() {

			@Override
			public void sendUserInfoFromPanelToFrame() {

				try {

					String username = loginFormPannel.getUsernameTxtField();
					String password = loginFormPannel.getPasswordTxtField();

					if (!username.isEmpty() && !password.isEmpty()) {

						User user = new User(username, password);

						boolean userExist = controller.enableUserToLogin(user);

						if (userExist) {

							SwingUtilities.getWindowAncestor(loginFormPannel)
									.dispose();
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									new MainFrame(controller, "Hello "
											+ loginFormPannel
													.getUsernameTxtField());

								}
							});

						} else {

							throw new Exception(
									"Username or Password is not correct!");
						}
					} else {

						loginFormPannel.setVisibleMandatortFiledsStar(username,
								password);

						throw new Exception("Username or Password is Empty!");

					}

				} catch (SQLException e) {

					JOptionPane.showMessageDialog(loginFormPannel,
							e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {

					JOptionPane.showMessageDialog(loginFormPannel,
							e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	private void setView() {

		this.setSize(300, 150);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}

}
