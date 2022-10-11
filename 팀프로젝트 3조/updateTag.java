import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class updateTag extends JDialog {
	Tag_ThemeDao dao = new Tag_ThemeDao();
	private JCheckBox[] check;

	updateTag(JFrame owner, String user) {
		super(owner, true);
		setTitle("태그 구독");

		JPanel panel = new JPanel();
		JPanel checkPnl = new JPanel();
		checkPnl.setBounds(22, 51, 439, 367);
		panel.setLayout(null);
		JLabel lblName = new JLabel(user + " 님의 태그 구독 정보");
		lblName.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblName.setBounds(12, 10, 219, 31);
		panel.add(lblName);

		// 데이터를 불러와서 체크박스를 만들어요~~~
		List<String> tag = new ArrayList<>();
		List<String> selecting = new ArrayList<>();
		List<String> selected = new ArrayList<>();
		ItemListener boxCheck = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				selecting.clear();
				for (int i = 0; i < tag.size(); i++) {
					if (check[i].isSelected()) {
						selecting.add(tag.get(i).toString());
					} else if (!check[i].isSelected()) {
						selecting.remove(tag.get(i).toString());
					}
				}
			}

		};
		try {
			tag.addAll(dao.readTag());
			selected.addAll(dao.readSubTag(user));
			check = new JCheckBox[tag.size()];
			for (int i = 0; i < tag.size(); i++) {
				check[i] = new JCheckBox(tag.get(i).toString());
				check[i].setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				checkPnl.add(check[i]);
				check[i].addItemListener(boxCheck);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < tag.size(); i++) {
			for (int j = 0; j < selected.size(); j++) {
				if (selected.get(j).equals(tag.get(i).toString())) {
					check[i].setSelected(true);
				}
			}
		}
		panel.add(checkPnl);
		checkPnl.setLayout(new GridLayout(0, 4, 10, 10));
		TitledBorder tbMyInfo = new TitledBorder(new LineBorder(Color.black), "나의 정보");
		tbMyInfo.setTitleColor(Color.BLACK);

		getContentPane().add(panel);

		getContentPane().add(panel);
		// 수정 버튼 체크한 부분을 반영해요~~(아이디가 같은 태그를 다 지우고 다시 설정해요)
		JButton instbtn = new JButton("수정");
		instbtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					dao.delete(user);
					dao.create(user, selecting);
					
					owner.dispose();
					try {
						new myProfile(user).setVisible(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});
		instbtn.setBounds(283, 428, 83, 23);
		panel.add(instbtn);
		// 취소 버튼 아무것도 없이 그냥 창을 꺼요~~
		JButton cancelbtn = new JButton("취소");
		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelbtn.setBounds(378, 428, 83, 23);
		panel.add(cancelbtn);
		setSize(500, 500);
//		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

	}

}
