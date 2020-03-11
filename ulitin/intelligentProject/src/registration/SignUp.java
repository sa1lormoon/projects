package registration;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//fix identycal users
public class SignUp extends JFrame {
	private JButton login = new JButton("Sign Up");
	private JLabel appLabel = new JLabel("SYSTEM");
	private JLabel passwrdDiscription = new JLabel("Enter your password");
	private JLabel userLabel = new JLabel("Your username");
	private JLabel nameLabel = new JLabel("Enter your name");
	private JLabel surnameLabel = new JLabel("Enter your surname");
	private JLabel otherNameLabel = new JLabel("Enter your patronymic (optional)");
	private JLabel numberLabel = new JLabel("Enter your phone number");
	private JLabel birthLabel = new JLabel("Enter your birth date");
	private JLabel groupLabel = new JLabel("Enter your group number");
	private JTextField number = new JTextField();
	private JTextField birth = new JTextField();
	private JTextField group = new JTextField();
	private JTextField name = new JTextField();
	private JTextField surname = new JTextField();
	private JTextField otherName = new JTextField();
	private JTextField user = new JTextField();
	private JPasswordField passwrd = new JPasswordField();
	private JButton back = new JButton("Back");

	public SignUp(String username) {
		super("SignUp");
		try {
			this.setBounds(350, 200, 600, 320);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
			user.setText(username);
			user.setEnabled(false);
			//appLabel.setHorizontalAlignment(appLabel.CENTER);
			//passwrdDiscription.setHorizontalAlignment(passwrdDiscription.CENTER);
			Container container = new Container();
			Container container1 = new Container();
			
			container.setLayout(new GridLayout(9, 2, 2, 2));
			
			container1.setLayout(new GridLayout(1, 1, 2, 2));
			appLabel.setForeground(Color.RED);
			passwrd.setEchoChar('*');
			appLabel.setHorizontalAlignment(appLabel.CENTER);
			container1.add(appLabel);
			this.add(container1);
			this.add(container);
			container.add(userLabel);
			container.add(user);
			container.add(passwrdDiscription);
			container.add(passwrd);
			container.add(nameLabel);
			container.add(name);
			container.add(surnameLabel);
			container.add(surname);
			container.add(otherNameLabel);
			container.add(otherName);
			container.add(numberLabel);
			container.add(number);
			container.add(birthLabel);
			container.add(birth);
			container.add(groupLabel);
			container.add(group);
			container.add(login);
			container.add(back);
			login.addActionListener(new LoginEventListener());
			back.addActionListener(new BackEventListener());
		} catch (Exception e) {
			String message = "Please close other SignUp window";
			JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
			throw new RuntimeException();
		}
	}

	class LoginEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String finalUser = user.getText();
				char[] arrayPassword = passwrd.getPassword();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < arrayPassword.length; i++) {
					sb.append(arrayPassword[i]);
				}
				String finalPassword = sb.toString();
				if(name.getText().length() == 0 || surname.getText().length() == 0 || number.getText().length() == 0
						|| birth.getText().length() == 0 || group.getText().length() == 0) {
					throw new RuntimeException();
				}
				if(finalPassword.length()<6) {
					String message = "Password length should be 6 symbols or more";
					JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
					passwrd.setText("");
				}else {
				if (utilities.MysqlConnect.checkUser(finalUser) == 0) {
					if (finalUser.equals("") != true && finalPassword.equals("") != true) {
						utilities.MysqlConnect.signUp(finalUser, finalPassword, name.getText(), surname.getText(), 
								otherName.getText(), number.getText(), birth.getText(), group.getText());
						dispose();
						String message = "Thanks for the registration";
						JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
						app.MainGUI app = new app.MainGUI();
						app.setVisible(true);
					} else
						throw new RuntimeException();
				} 
				}
			} catch (Exception error) {
				System.out.println("Please, enter valid sentences: " + error);
				String message = "Please, enter valid sentences";
				JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	class BackEventListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			app.MainGUI app = new app.MainGUI();
			app.setVisible(true);
		}
	}

	public static void main(String[] args) {
		SignUp app = new SignUp("p");
		app.setVisible(true);
	}
}
