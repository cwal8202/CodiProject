import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.server.UID;
import java.sql.SQLException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Color;

class LoginCheck extends JDialog {
	JLabel loginResult = new JLabel("아이디와 비밀번호 확인 결과 나오는곳");
	JButton checkButton = new JButton("확인");
	public LoginCheck() {
		setLayout(new FlowLayout());
		add(loginResult);
		add(checkButton);
		setSize(250, 100);
		
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		setLocationRelativeTo(null);
	}
}

public class login extends JFrame {
	UserDaoImpl uDI = new UserDaoImpl();
	boolean loginStatement;
	String loginUser_id;
	
	public login() {
		getContentPane().setBackground(Color.WHITE);
		
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel id_lbl = new JLabel("아이디");
		id_lbl.setBounds(57, 76, 57, 34);
		getContentPane().add(id_lbl);
		
		JLabel pw_lbl = new JLabel("비밀번호");
		pw_lbl.setBounds(57, 120, 57, 34);
		getContentPane().add(pw_lbl);
		
		
////////////////////////////////////텍스트필드/////////////////////////////////////////////////		
		
		JTextField id_tf = new JTextField("아이디");
		id_tf.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (id_tf.getText().equals("")) {
					id_tf.setText("아이디");
				}	
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (id_tf.getText().equals("아이디")) {
					id_tf.setText("");
				}
			}
		});
		
		id_tf.setBounds(142, 83, 116, 21);
		getContentPane().add(id_tf);
		id_tf.setColumns(10);
		
		JPasswordField pw_tf = new JPasswordField("비밀번호");
		pw_tf.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (pw_tf.getText().equals("")) {
					pw_tf.setText("비밀번호");
				}	
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (pw_tf.getText().equals("비밀번호")) {
					pw_tf.setText("");
				}
			}
		});
		
		pw_tf.setColumns(10);
		pw_tf.setBounds(142, 127, 116, 21);
		getContentPane().add(pw_tf);
		
		JButton login_button = new JButton("로그인");
		login_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					System.out.println("아이디와 비밀번호 확인 들어갑니다~");
					
						try {
							if (id_tf.getText().equals("*MIN*") && pw_tf.getText().equals("java")) {
								System.out.println("관리자 창으로 갑니다 + 여기에 관리자창 여는 코드 쓰세요!");
								dispose();
								Management managePage = new Management();
								managePage.setVisible(true);
							} else {
								if (uDI.login_read(id_tf.getText(), pw_tf.getText()) != null && (id_tf.getText().matches("^[a-z0-9]*$"))) {
									System.out.println("로그인 되었습니다");
									loginStatement = true;
									loginUser_id = id_tf.getText();
									System.out.println("메인창으로~! + 여기에 메인창 여는 코드 쓰세요!");
									MainFrame3 mainPage = new MainFrame3(loginUser_id, loginStatement);	//메인페이지 열린다.
									mainPage.setVisible(true);
									dispose();
								} else {
									System.out.println("아이디 또는 비밀번호가 틀렸습니다");
									LoginCheck loginCheck = new LoginCheck();
									loginCheck.setVisible(true);
									loginCheck.loginResult.setText("아이디 또는 비밀번호가 틀렸습니다");
								}
							} 
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				}
		});
		login_button.setBounds(60, 212, 97, 23);
		getContentPane().add(login_button);
		
		JButton signUp_button = new JButton("회원가입");
		signUp_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new signUp().setVisible(true);
			}
		});
		
		signUp_button.setBounds(203, 212, 97, 23);
		getContentPane().add(signUp_button);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new login().setVisible(true);
	}
}
