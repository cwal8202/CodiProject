import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import java.awt.Color;

class check_dialog extends JDialog {
	JLabel signUpEnd = new JLabel("회원가입이 되었습니다");
	JButton checkButton = new JButton("확인");
	public check_dialog() {
		setLayout(new FlowLayout());
		add(signUpEnd);
		add(checkButton);
		setSize(200, 100);
		
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		setLocationRelativeTo(null);
	}
	
}

public class signUp extends JFrame {
	UserDaoImpl uDI = new UserDaoImpl();
	
	public boolean signUp_id_condition(String text) {
		return text.matches("^[0-9a-z]*$") && text.length() <= 20 && text.length() >= 5 && !text.matches("^[0-9]*$") && !text.matches("^[a-z]*$");
	}
	
	public boolean signUp_password_condition(String text) {
		return text.matches("^[0-9a-z]*$") && text.length() <= 20 && text.length() >= 5 && !text.matches("^[0-9]*$");
	}
	
	public boolean signUp_Name_Condition(String text) {
		return text.matches("^[가-힣]*$") && text.length() > 1 && text.length() <= 17 && !text.equals("이름을 입력하세요");
	}
	
	boolean id_check;
	boolean pw_check;
	boolean name_check;
	
	public signUp() {
		getContentPane().setBackground(Color.WHITE);
		////////dialog생성함///////////
		check_dialog check_dialog = new check_dialog();
		/////////////////
		
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel id_lbl = new JLabel("아이디");
		id_lbl.setBounds(66, 44, 76, 31);
		getContentPane().add(id_lbl);
		
		JLabel pw_lbl = new JLabel("비밀번호");
		pw_lbl.setBounds(66, 113, 76, 31);
		getContentPane().add(pw_lbl);
		
		JLabel re_pw_lbl = new JLabel("비밀번호 재확인");
		re_pw_lbl.setBounds(66, 185, 97, 31);
		getContentPane().add(re_pw_lbl);
		
		JLabel name_lbl = new JLabel("이름");
		name_lbl.setBounds(66, 250, 76, 31);
		getContentPane().add(name_lbl);
		
		JLabel id_check_lbl = new JLabel();
		id_check_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		id_check_lbl.setBounds(66, 85, 326, 18);
		getContentPane().add(id_check_lbl);
		
		JLabel pw_check_lbl = new JLabel();
		pw_check_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		pw_check_lbl.setBounds(66, 154, 326, 18);
		getContentPane().add(pw_check_lbl);
		
		JLabel re_pw_check_lbl = new JLabel();
		re_pw_check_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		re_pw_check_lbl.setBounds(66, 221, 326, 18);
		getContentPane().add(re_pw_check_lbl);
		
		JLabel name_check_lbl = new JLabel();
		name_check_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		name_check_lbl.setBounds(66, 283, 326, 18);
		getContentPane().add(name_check_lbl);

		
///////////////////////////////////////////////////////////텍스트필드///////////////////////////////////////////////////////		
		
		
		JTextField id_tf = new JTextField("아이디");
		id_tf.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// 아이디에 아무값도 입력하지 않았을때
				if (id_tf.getText().equals("")) {
					id_tf.setText("아이디");
					id_check_lbl.setText("필수정보 입니다");
					id_check = false;
				}
				// 아이디 조건에 맞을때
				if (signUp_id_condition(id_tf.getText())) {
					try {
						if (uDI.signUp_id_read(id_tf.getText()) == null) {
							id_check_lbl.setText("사용 가능한 아이디입니다.");
							id_check = true;
						} else {// 중복
							id_check_lbl.setText("중복된 아이디 입니다");
							id_check = false;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} 
				// 아이디 조건에 맞지 않을때
				else {
					id_check_lbl.setText("5~20자의 영문 소문자 또는 영문 소문자 와 숫자의 조합만 사용가능 합니다.");
					id_check = false;
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (id_tf.getText().equals("아이디")) {
					id_tf.setText("");
				}
			}
		});
		id_tf.setBounds(187, 49, 205, 21);
		getContentPane().add(id_tf);
		id_tf.setColumns(10);
		
		JTextField pw_tf = new JTextField("비밀번호");
		pw_tf.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// 비밀번호에 아무값도 입력하지 않았을때
				if (pw_tf.getText().equals("")) {
					pw_tf.setText("비밀번호");
					pw_check_lbl.setText("필수정보 입니다");
				}
				// 비밀번호 조건에 맞을때
				if (signUp_password_condition(pw_tf.getText())) {
					pw_check_lbl.setText("사용 가능한 비밀번호");
				} 
				// 비밀번호 조건에 맞지 않을때
				else {
					pw_check_lbl.setText("5~10자의 영문 소문자와 숫자의 조합만 사용가능 합니다.");
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
		pw_tf.setBounds(187, 118, 205, 21);
		getContentPane().add(pw_tf);
		
		JTextField re_pw_tf = new JTextField("비밀번호를 한번 더 입력하세요");
		re_pw_tf.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// 비밀번호확인란 에 아무값도 입력하지 않았을때
				if (re_pw_tf.getText().equals("")) {
					re_pw_tf.setText("비밀번호를 한번 더 입력하세요");
					re_pw_check_lbl.setText("비밀번호가 일치하지 않습니다");
					pw_check = false;
				}
				// 비밀번호확인란과 비밀번호가 같을때
				if (re_pw_tf.getText().equals(pw_tf.getText())) {
					re_pw_check_lbl.setText("일치");
					pw_check = true;
				} 
				// 비밀번호확인란과 비밀번호가 같지 않을때
				else {
					re_pw_check_lbl.setText("비밀번호가 일치하지 않습니다");
					pw_check = false;
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (re_pw_tf.getText().equals("비밀번호를 한번 더 입력하세요")) {
					re_pw_tf.setText("");
				}
			}
		});
		re_pw_tf.setColumns(10);
		re_pw_tf.setBounds(187, 190, 205, 21);
		getContentPane().add(re_pw_tf);
		
		JTextField name_tf = new JTextField("이름을 입력하세요");
		name_tf.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// 이름에 아무값도 입력하지 않았을때
				if (name_tf.getText().equals("")) {
					name_tf.setText("이름을 입력하세요");
					name_check_lbl.setText("필수 정보입니다(정확한 이름을 입력하세요)");
					name_check = false;
				}
				// 이름이 정확하면
				if (signUp_Name_Condition(name_tf.getText())) {
					name_check_lbl.setText("확인되었습니다");
					name_check = true;
				} 
				// 이름이 이상하면
				else {
					name_check_lbl.setText("정확한 이름을 입력하세요");
					name_check = false;
				}

			}
			@Override
			public void focusGained(FocusEvent e) {
				if (name_tf.getText().equals("이름을 입력하세요")) {
					name_tf.setText("");
				}
			}
		});
		name_tf.setColumns(10);
		name_tf.setBounds(187, 255, 205, 21);
		getContentPane().add(name_tf);
		
		JButton signUp = new JButton("가입하기");
		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (id_check && pw_check && name_check && pw_tf.getText().equals(re_pw_tf.getText())) {
					System.out.println("합격~");
					try {
						uDI.create(id_tf.getText(), name_tf.getText(), pw_tf.getText());
						System.out.println(uDI.login_read(id_tf.getText(), pw_tf.getText()));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					check_dialog.setVisible(true);
				}
				else {
					System.out.println("말들어");
				}
			}
		});
		
		////////////////////dialong 관리/////////////////
		check_dialog.checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		/////////////////////////////////////////////////////
		
		signUp.setBounds(173, 385, 97, 23);
		getContentPane().add(signUp);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new signUp().setVisible(true);
	}
}
