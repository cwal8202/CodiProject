import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
public class allUser extends JFrame{
	int page = 0;
	private DAO_codiset dao_codiset = new DAO_codiset();
	
	public ImageIcon convertedImg(int page, int i) throws IOException, SQLException {
		Blob imageBlob = dao_codiset.AlluserCodisetImg(page).get(i).getCodiset_image();
        InputStream in = imageBlob.getBinaryStream();
        BufferedImage bimg = ImageIO.read(in);
        Image blobImage = bimg;
        ImageIcon convertedImage = new ImageIcon(blobImage.getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        return convertedImage;
	}
	
	public allUser(String login_userid, boolean yesno) throws SQLException, IOException {
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		JLabel pagelbl = new JLabel("");
		
		
		URL url1 = MainFrame3.class.getClassLoader().getResource("hand/mypage.png");
		ImageIcon mypageImg = new ImageIcon(kit.getImage(url1));
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.WHITE);
		getContentPane().add(pnl);
		pnl.setLayout(null);
		
		JPanel top_panel = new JPanel();
		top_panel.setBackground(Color.WHITE);
		top_panel.setBounds(0, 0, 834, 32);
		pnl.add(top_panel);
		top_panel.setLayout(null);
		
		JLabel loginLbl = new JLabel("유저 아이디");
		loginLbl.setText("ID : " + login_userid);
		loginLbl = new JLabel(login_userid);
		loginLbl.setBounds(369, 5, 97, 23);
		top_panel.add(loginLbl);
		
		
		URL refresh_url = MainFrame3.class.getClassLoader().getResource("white/refresh.png");
		ImageIcon refresh = new ImageIcon(kit.getImage(refresh_url).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		JButton f5 = new JButton(refresh);
		f5.addActionListener(new ActionListener() {
			@Override	
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					new allUser(login_userid,yesno).setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		f5.setPreferredSize(new Dimension(32, 32));
		f5.setContentAreaFilled(false);// 버튼 안 색 채우기 안함
		f5.setBorderPainted(false);// 버튼 외곽선 안 보이게
		f5.setFocusPainted(false);// 버튼 눌렀을 때 외곽선 안 보이게
		f5.setBounds(673, 5, 81, 23);
		top_panel.add(f5);
		
		JButton myPageBtn = new JButton(mypageImg);
		myPageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					myProfile myPage = new myProfile(login_userid);
					myPage.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		myPageBtn.setBounds(766, 0, 32, 32);
		myPageBtn.setBackground(Color.WHITE);
		top_panel.add(myPageBtn);
		
		JPanel middle_panel = new JPanel();
		middle_panel.setBackground(Color.WHITE);
		middle_panel.setBounds(0, 31, 834, 749);
		pnl.add(middle_panel);
		middle_panel.setLayout(null);
		
		JLabel[] codisetlblAll = new JLabel[20];
		JLabel[] codisetnamelbl = new JLabel[20];
		for (int i = 0; i < codisetlblAll.length; i++) {
			codisetlblAll[i] = new JLabel("모든유저의 코디셋아이디가 담깁니다.");
			codisetnamelbl[i] = new JLabel("유저 아이디가 담깁니다");
		}
		for (int i = 0; i < dao_codiset.AlluserCodisetImg(page).size(); i++) {
            codisetlblAll[i].setText(String.valueOf(dao_codiset.AlluserCodisetImg(page).get(i).getId()));	// 코디셋 아이디;
            codisetlblAll[i].setIcon(convertedImg(page, i));
            codisetnamelbl[i].setText(String.valueOf(dao_codiset.AlluserCodisetImg(page).get(i).getUser_id()));
		}
		
		for (int i = 0; i < codisetlblAll.length; i++) {
			codisetlblAll[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("글 눌렀음! -> 글 상세보기 페이지로 이동합니다.");
					JLabel clicklbl = (JLabel)e.getSource();
					System.out.println("클릭한 라벨 코디 아이디" + clicklbl.getText());
					LookCodiset lookCodiset = null;
					lookCodiset = new LookCodiset(allUser.this, login_userid, Integer.valueOf(clicklbl.getText()), yesno);
					lookCodiset.setVisible(true);
				}
			});
		}
		
//		JLabel codisetlbl1 = new JLabel("New label");
//		JLabel codisetlbl2 = new JLabel("New label");
//		JLabel codisetlbl3 = new JLabel("New label");
//		JLabel codisetlbl4 = new JLabel("New label");
//		JLabel codisetlbl5 = new JLabel("New label");
//		JLabel codisetlbl6 = new JLabel("New label");
//		JLabel codisetlbl7 = new JLabel("New label");
//		JLabel codisetlbl8 = new JLabel("New label");
//		JLabel codisetlbl9 = new JLabel("New label");
//		JLabel codisetlbl10 = new JLabel("New label");
//		JLabel codisetlbl11 = new JLabel("New label");
//		JLabel codisetlbl12 = new JLabel("New label");
//		JLabel codisetlbl13 = new JLabel("New label");
//		JLabel codisetlbl14 = new JLabel("New label");
//		JLabel codisetlbl15 = new JLabel("New label");
//		JLabel codisetlbl16 = new JLabel("New label");
//		JLabel codisetlbl17 = new JLabel("New label");
//		JLabel codisetlbl18 = new JLabel("New label");
//		JLabel codisetlbl19 = new JLabel("New label");
//		JLabel codisetlbl20 = new JLabel("New label");
		
	// --------------------------------------코디셋lbl 레이아웃 시작---------------------------------------------------//	
		int a = 17;
		for (int i = 0; i < 5; i++) {
			codisetlblAll[i].setBounds(a, 18, 130, 130);
			  codisetnamelbl[i].setBounds(a, 89, 130, 130);
			  codisetnamelbl[i].setHorizontalAlignment(JLabel.CENTER);
			middle_panel.add(codisetnamelbl[i]);
			middle_panel.add(codisetlblAll[i]);
			a += 168;
		}

		int b = 17;
		for (int i = 5; i < 10; i++) {
			codisetlblAll[i].setBounds(b, 204, 130, 130);
			 codisetnamelbl[i].setBounds(b, 276, 130, 130);
			  codisetnamelbl[i].setHorizontalAlignment(JLabel.CENTER);
			middle_panel.add(codisetnamelbl[i]);
			middle_panel.add(codisetlblAll[i]);
			b += 168;
		}
		
		int c = 17;
		for (int i = 10; i < 15 ; i++) {
			codisetlblAll[i].setBounds(c, 390, 130, 130);
			 codisetnamelbl[i].setBounds(c, 461, 130, 130);
			  codisetnamelbl[i].setHorizontalAlignment(JLabel.CENTER);
			middle_panel.add(codisetnamelbl[i]);
			middle_panel.add(codisetlblAll[i]);
			c += 168;
		}

		int d = 17;
		for (int i = 15; i < 20; i++) {
			codisetlblAll[i].setBounds(d, 577, 130, 130);
			 codisetnamelbl[i].setBounds(d, 637, 130, 130);
			  codisetnamelbl[i].setHorizontalAlignment(JLabel.CENTER);
			middle_panel.add(codisetnamelbl[i]);
			middle_panel.add(codisetlblAll[i]);
			d += 168;
		}
		
		// --------------------------------------코디셋lbl 레이아웃 끝---------------------------------------------------//
		
		JButton prevbtn = new JButton("prev");
		prevbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (page >= 0) {
					page--;
					pagelbl.setText(String.valueOf(page));
					for (int i = 0; i <  codisetlblAll.length; i++) {
					 codisetlblAll[i].setText(null);	// 코디셋 아이디;
		        	 codisetlblAll[i].setIcon(null);
		        	 codisetnamelbl[i].setText(null);
					}
					try {
							for (int i = 0; i < dao_codiset.AlluserCodisetImg(page).size(); i++) {
							     try {
							    	 if(convertedImg(page, i) != null) {
							        	 codisetlblAll[i].setText(String.valueOf(dao_codiset.AlluserCodisetImg(page).get(i).getId()));	// 코디셋 아이디;
							        	 codisetlblAll[i].setIcon(convertedImg(page, i));
							        	 codisetnamelbl[i].setText(String.valueOf(dao_codiset.AlluserCodisetImg(page).get(i).getUser_id()));
							    	 } 
								} catch (IOException e1) {
									e1.printStackTrace();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}		
							}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		prevbtn.setBounds(318, 716, 74, 23);
		middle_panel.add(prevbtn);
		
		JButton nextbtn = new JButton("next");	//next 다음 
		nextbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (dao_codiset.AlluserCodisetImg(page).size() > 0) {
						page++;
						pagelbl.setText(String.valueOf(page));
						for (int i = 0; i <  codisetlblAll.length; i++) {
							 codisetlblAll[i].setText(null);	// 코디셋 아이디;
				        	 codisetlblAll[i].setIcon(null);
				        	 codisetnamelbl[i].setText(null);
							}
						try {
							for (int i = 0; i < dao_codiset.AlluserCodisetImg(page).size(); i++) {
							     try {
							    	 if(convertedImg(page, i) != null) {
							        	 codisetlblAll[i].setText(String.valueOf(dao_codiset.AlluserCodisetImg(page).get(i).getId()));	// 코디셋 아이디;
							        	 codisetlblAll[i].setIcon(convertedImg(page, i));
							        	 codisetnamelbl[i].setText(String.valueOf(dao_codiset.AlluserCodisetImg(page).get(i).getUser_id()));
							    	 } else {
							    		 codisetlblAll[i].setText(null);	// 코디셋 아이디;
							        	 codisetlblAll[i].setIcon(null);
							        	 codisetnamelbl[i].setText(null);
							    	 }
								} catch (IOException e1) {
									e1.printStackTrace();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}		
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		nextbtn.setBounds(442, 716, 74, 23);
		middle_panel.add(nextbtn);
		
		
		pagelbl.setHorizontalAlignment(SwingConstants.CENTER);
		pagelbl.setText(String.valueOf(page));
		pagelbl.setBounds(404, 716, 26, 23);
		middle_panel.add(pagelbl);
		
		JPanel bottom_panel = new JPanel();
		bottom_panel.setBackground(Color.WHITE);
		bottom_panel.setBounds(0, 781, 834, 30);
		pnl.add(bottom_panel);
		
		JButton writeBtn = new JButton("글쓰기");
		writeBtn.setBounds(382, 1, 69, 23);
		writeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Clothes writePage = new Clothes(login_userid);
					writePage.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		bottom_panel.setLayout(null);
		bottom_panel.add(writeBtn);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(850, 850);
	}
	public static void main(String[] args) throws SQLException, IOException {
		new allUser("test1", true).setVisible(true);
	}
}
