import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class Logout extends JDialog {
	JButton yesButton;
	public Logout() {
		JLabel logout = new JLabel("로그아웃 하시겠습니까?");
		yesButton = new JButton("확인");
		JButton noButton = new JButton("아니요");
		setLayout(new FlowLayout());
		add(logout);
		add(yesButton);
		add(noButton);
		setSize(200, 100);
		
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		setLocationRelativeTo(null);
	}
}

public class MainFrame3 extends JFrame {

	JButton loginBtn;
	public String what;
	public JLabel lbl1;
	public JLabel lbl2;
	public JLabel lbl3;
	DAO_heart daoheart;
	DAO_cart daocart;
	public JButton likeBtn1;
	public JButton likeBtn2;
	public JButton likeBtn3;
	public JButton getBtn1;
	public JButton getBtn2;
	public JButton getBtn3;
	public JButton writeBtn;
	public JButton myBtn;
	public ImageIcon like;
	public ImageIcon like2;
	public ImageIcon addcart;
	public ImageIcon cancel;
	public ImageIcon mypageImg;
	public DAO_allproduct daoallpro;
	boolean isHeart1;
	boolean isHeart2;
	boolean isHeart3;
	boolean iscart1;
	boolean iscart2;
	boolean iscart3;
	public int liketop1;
	public int liketop2;
	public int liketop3;
	public String listnew1;
	public String listnew2;
	public String listnew3;
	public Color color;

///////////////getter setter///////////////////////

	public JLabel getLbl1() {
		return lbl1;
	}

	public void setLbl1(JLabel lbl1) {
		this.lbl1 = lbl1;
	}

///////////////getter setter///////////////////////

///////////// 생성자/////////////////////////////////
	public MainFrame3(String login_userid, boolean yesno) {
		//////////////// 페이지 컴포넌트 생성됨////////////////////
		super("메인창");
		color = new Color(200, 221, 242);
		// 버튼 아이콘들 이미지 경로지정
		Toolkit kit = Toolkit.getDefaultToolkit();

		URL url1 = MainFrame3.class.getClassLoader().getResource("hand/mypage.png");
		URL url2 = MainFrame3.class.getClassLoader().getResource("hand/likeImg.png");
		URL url3 = MainFrame3.class.getClassLoader().getResource("hand/like2.png");
		URL url4 = MainFrame3.class.getClassLoader().getResource("hand/add.png");
		URL url5 = MainFrame3.class.getClassLoader().getResource("hand/cancel.png");
		mypageImg = new ImageIcon(kit.getImage(url1));
		like = new ImageIcon(kit.getImage(url2));
		like2 = new ImageIcon(kit.getImage(url3));
		addcart = new ImageIcon(kit.getImage(url4));
		cancel = new ImageIcon(kit.getImage(url5));
		// 제일 큰 프레임
		JPanel pnl = new JPanel();

		// 윗부분
		JPanel topPnl = new JPanel();
		topPnl.setBounds(379, 0, 475, 32);
		topPnl.setBackground(Color.white);

		loginBtn = new JButton(login_userid);
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Logout logout = new Logout();	//로그아웃 객체
				if (yesno == true) {
					logout.setVisible(true);
					logout.yesButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
				}
			}
		});
		myBtn = new JButton(mypageImg);
		myBtn.addActionListener(new ActionListener() {
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
		settingBtn(myBtn);
		myBtn.setBounds(800, 0, 32, 32);
		SpringLayout sl_topPnl = new SpringLayout();
		sl_topPnl.putConstraint(SpringLayout.EAST, loginBtn, -6, SpringLayout.WEST, myBtn);
		sl_topPnl.putConstraint(SpringLayout.WEST, myBtn, 434, SpringLayout.WEST, topPnl);
		sl_topPnl.putConstraint(SpringLayout.EAST, myBtn, -9, SpringLayout.EAST, topPnl);
		sl_topPnl.putConstraint(SpringLayout.SOUTH, loginBtn, 0, SpringLayout.SOUTH, myBtn);
		sl_topPnl.putConstraint(SpringLayout.NORTH, myBtn, 4, SpringLayout.NORTH, topPnl);
		sl_topPnl.putConstraint(SpringLayout.SOUTH, myBtn, -1, SpringLayout.SOUTH, topPnl);
		topPnl.setLayout(sl_topPnl);
		topPnl.add(loginBtn);
		topPnl.add(myBtn);

		// 중간부분
		JPanel middlePnl = new JPanel();// 중간 전체 감싸는 패널
		middlePnl.setBounds(0, 32, 854, 775);
		middlePnl.setBackground(Color.white);
		// 위 - 좋아요 top3를 감싸는 제일 큰 패널
		JPanel middletop = new JPanel();
		middletop.setBackground(Color.white);
		middletop.setBorder(new TitledBorder(new LineBorder(Color.pink),"좋아요를 가장 많이 받은 top3"));
		// 하나씩 top3를 감싸는 패널(사진,버튼을 한 셋트로 묶을 패널)
		JPanel pnltoplbl1 = new JPanel();
		JPanel pnltoplbl2 = new JPanel();
		JPanel pnltoplbl3 = new JPanel();
		// 그림이 들어갈 라벨
		JLabel toplbl1 = new JLabel();
		JLabel toplbl2 = new JLabel();
		JLabel toplbl3 = new JLabel();
		toplbl1.setPreferredSize(new Dimension(276, 315));
		toplbl2.setPreferredSize(new Dimension(276, 315));
		toplbl3.setPreferredSize(new Dimension(276, 315));
		toplbl1.setBorder(new LineBorder(color, 2));
		toplbl2.setBorder(new LineBorder(color, 2));
		toplbl3.setBorder(new LineBorder(color, 2));
		pnltoplbl1.setBackground(Color.white);
		pnltoplbl2.setBackground(Color.white);
		pnltoplbl3.setBackground(Color.white);

		likeBtn1 = new JButton(like);
		likeBtn1.setPreferredSize(new Dimension(33, 12));
		likeBtn2 = new JButton(like);
		likeBtn2.setPreferredSize(new Dimension(33, 12));
		likeBtn3 = new JButton(like);
		likeBtn3.setPreferredSize(new Dimension(33, 12));
		settingBtn(likeBtn1);
		settingBtn(likeBtn2);
		settingBtn(likeBtn3);

		// 좋아요 패널에 구성요소 더하기
		pnltoplbl1.add(toplbl1);
		pnltoplbl2.add(toplbl2);
		pnltoplbl3.add(toplbl3);
		pnltoplbl1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnltoplbl2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnltoplbl3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnltoplbl1.add(likeBtn1);
		pnltoplbl2.add(likeBtn2);
		pnltoplbl3.add(likeBtn3);
		
		// 아래 - 신상품 top3
				// 신상 부분 제일 큰 패널
				JPanel pnlNew = new JPanel();
				pnlNew.setBackground(Color.white);
				pnlNew.setBorder(new TitledBorder(new LineBorder(Color.pink),"신상 top3"));
				// 3개 감쌀 패널(사진,버튼을 한 셋트로 묶을 패널)
				JPanel pnllblnew1 = new JPanel();
				JPanel pnllblnew2 = new JPanel();
				JPanel pnllblnew3 = new JPanel();
				pnllblnew1.setBackground(Color.white);
				pnllblnew2.setBackground(Color.white);
				pnllblnew3.setBackground(Color.white);

				// 담기 버튼
				getBtn1 = new JButton(addcart);
				getBtn2 = new JButton(addcart);
				getBtn3 = new JButton(addcart);
				getBtn1.setPreferredSize(new Dimension(33, 12));
				getBtn2.setPreferredSize(new Dimension(33, 12));
				getBtn3.setPreferredSize(new Dimension(33, 12));
				settingBtn(getBtn1);
				settingBtn(getBtn2);
				settingBtn(getBtn3);

				// 사진 들어갈 라벨
				JLabel lblnew1 = new JLabel();
				JLabel lblnew2 = new JLabel();
				JLabel lblnew3 = new JLabel();
//				lblnew2.setBounds(0, 0, 276, 315);
//				lblnew3.setBounds(0, 0, 276, 315);
				lblnew1.setBorder(new LineBorder(color, 2));
				lblnew2.setBorder(new LineBorder(color, 2));
				lblnew3.setBorder(new LineBorder(color, 2));
				lblnew1.setPreferredSize((new Dimension(276, 315)));
				lblnew2.setPreferredSize((new Dimension(276, 315)));
				lblnew3.setPreferredSize((new Dimension(276, 315)));

				// 사진,버튼 하나씩 더해준다.
				pnllblnew1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				pnllblnew2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				pnllblnew3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				pnllblnew1.add(lblnew1);
				pnllblnew2.add(lblnew2);
				pnllblnew3.add(lblnew3);
				pnllblnew1.add(getBtn1);
				pnllblnew2.add(getBtn2);
				pnllblnew3.add(getBtn3);

				// 3개의 구성요소를 비율에 맞춰 셋팅하기.
				pnlNew.add(pnllblnew1);
				pnlNew.add(pnllblnew2);
				pnlNew.add(pnllblnew3);
				pnlNew.setLayout(new GridLayout(0, 3));
				/////


				// 중간부분을 가로로 2개 나눈다.
				middlePnl.add(middletop);
				middlePnl.add(pnlNew);
				middlePnl.setLayout(new GridLayout(2, 0));
				/////////////////////// 중간 부분 끝

		////////////////////// bottom
				JPanel bottomPnl = new JPanel();// bottom전체 감싸는 큰 패널
				bottomPnl.setBounds(393, 807, 68, 23);
				bottomPnl.setBackground(Color.WHITE);
				
				bottomPnl.setLayout(new BoxLayout(bottomPnl, BoxLayout.LINE_AXIS));
				writeBtn = new JButton("글쓰기");
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
		writeBtn.setForeground(Color.WHITE);
		writeBtn.setBackground(Color.black);
		writeBtn.setBorderPainted(false);// 버튼 외곽선 안 보이게
		writeBtn.setFocusPainted(false);// 버튼 눌렀을 때 외곽선 안 보이게
		JButton allUserCodiset = new JButton("모든코디보기");
		allUserCodiset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					allUser allUser = new allUser(login_userid, yesno);
					allUser.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		allUserCodiset.setBounds(12, 0, 140, 32);
		pnl.add(allUserCodiset);
		bottomPnl.add(writeBtn);
		pnl.setLayout(null);
		////////
		/// 구성요소 모두 더하기
		pnl.add(topPnl);

		JLabel nowUser = new JLabel("ID :  " + login_userid);
		sl_topPnl.putConstraint(SpringLayout.SOUTH, nowUser, -4, SpringLayout.SOUTH, topPnl);
		sl_topPnl.putConstraint(SpringLayout.EAST, nowUser, -157, SpringLayout.EAST, topPnl);
		sl_topPnl.putConstraint(SpringLayout.WEST, loginBtn, 6, SpringLayout.EAST, nowUser);
		nowUser.setFont(new Font("굴림", Font.PLAIN, 15));
		topPnl.add(nowUser);
		
		URL refresh_url = MainFrame3.class.getClassLoader().getResource("white/refresh.png");
		ImageIcon refresh = new ImageIcon(refresh_url);
		JButton f5 = new JButton(refresh);
		sl_topPnl.putConstraint(SpringLayout.NORTH, f5, 0, SpringLayout.NORTH, myBtn);
		sl_topPnl.putConstraint(SpringLayout.WEST, f5, -54, SpringLayout.WEST, nowUser);
		sl_topPnl.putConstraint(SpringLayout.SOUTH, f5, 0, SpringLayout.SOUTH, loginBtn);
		sl_topPnl.putConstraint(SpringLayout.EAST, f5, -15, SpringLayout.WEST, nowUser);
		settingBtn(f5);
		topPnl.add(f5);
		f5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainFrame3(login_userid,yesno).setVisible(true);
			}
		});

		pnl.add(middlePnl);
		pnl.add(bottomPnl);

		btnSetLogin(yesno);
		getContentPane().add(pnl);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(870, 870);
		pnl.setBackground(Color.white);
		////// 컴포넌트 구성요소 전부 완료

////////////////////////////////////////이벤트 리스너, 다오

		daoallpro = new DAO_allproduct();
		daoheart = new DAO_heart();
		daocart = new DAO_cart();

		///////////// 좋아요 top3부분
		try {
			liketop1 = daoheart.intliketop().get(0);
			liketop2 = daoheart.intliketop().get(1);
			liketop3 = daoheart.intliketop().get(2);
		} catch (SQLException e3) {
			e3.printStackTrace();
		}

		//// 좋아요 top3 사진 출력 부분
//		 좋아요 top1
try {
	// 좋아요 top1의 사진
	daoheart.imgliketop3().get(0);
	InputStream in = daoheart.imgliketop3().get(0).getBinaryStream();
	BufferedImage blobImage = ImageIO.read(in);
	Image img = blobImage;
	ImageIcon convertedImage = new ImageIcon(img);
	toplbl1.setIcon(scaleImage(convertedImage, 276, 315));
	// 좋아요 top1의 codiset_id
	System.out.println("좋아요 탑1 의 코디셋아이디:" + liketop1);
} catch (MalformedURLException | SQLException e2) {
	e2.printStackTrace();
} catch (IOException e1) {
	e1.printStackTrace();
}
//좋아요 top2
try {
// 좋아요 top2의 사진
daoheart.imgliketop3().get(1);
InputStream in = daoheart.imgliketop3().get(1).getBinaryStream();
BufferedImage blobImage = ImageIO.read(in);
Image img = blobImage;
ImageIcon convertedImage = new ImageIcon(img);
toplbl2.setIcon(scaleImage(convertedImage, 276, 315));
// 좋아요 top2의 codiset_id
System.out.println("좋아요 탑2 의 코디셋아이디:" + liketop2);
} catch (MalformedURLException | SQLException e2) {
e2.printStackTrace();
} catch (IOException e1) {
e1.printStackTrace();
}
//좋아요 top3
try {
// 좋아요 top3의 사진
daoheart.imgliketop3().get(2);
InputStream in = daoheart.imgliketop3().get(2).getBinaryStream();
BufferedImage blobImage = ImageIO.read(in);
Image img = blobImage;
ImageIcon convertedImage = new ImageIcon(img);
toplbl3.setIcon(scaleImage(convertedImage, 276, 315));
middletop.setLayout(new GridLayout(0, 3, 0, 0));
middletop.add(pnltoplbl1);
middletop.add(pnltoplbl2);
middletop.add(pnltoplbl3);
// 좋아요 top3의 codiset_id
System.out.println("좋아요 탑3 의 코디셋아이디:" + liketop3);
} catch (MalformedURLException | SQLException e2) {
e2.printStackTrace();
} catch (IOException e1) {
e1.printStackTrace();
}

///////////////[좋아요]버튼 이벤트 부분 
// 좋아요 버튼 두 번 누루면 취소되게 끔

// 좋아요 버튼을 눌렀을 상태에 대해 생각 못했다!!!!
// 로그인 한 유저가 좋아요 한 게 있으면 좋아요 버튼을 눌러진 상태로 셋팅해놓기.
try {
	for (int i = 0; i < daoheart.likedCodisetId(login_userid).size(); i++) {
		if (daoheart.likedCodisetId(login_userid).get(i) == liketop1) {
			likeBtn1.setIcon(like2);
			isHeart1 = true;
		} else if (daoheart.likedCodisetId(login_userid).get(i) == liketop2) {
			likeBtn2.setIcon(like2);
			isHeart2 = true;
		} else if (daoheart.likedCodisetId(login_userid).get(i) == liketop3) {
			likeBtn3.setIcon(like2);
			isHeart3 = true;
		}
	}
} catch (SQLException e1) {
	e1.printStackTrace();
}

likeBtn1.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		// 취소부분
		if (isHeart1 == true) {
			System.out.println("좋아요 취소 -> 좋아요 db에 데이터 삭제");
			likeBtn1.setIcon(like);
			isHeart1 = false;
			try {
				System.out.println("유저아이디:" + login_userid);
				daoheart.intliketop().get(0);
				System.out.println("좋아요 top1 코디셋아이디:" + liketop1);
				daoheart.delete(login_userid, liketop1);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 좋아요 클릭부분
		} else {
			System.out.println("좋아요 버튼 클릭 -> 좋아요 db에 데이터 삽입");
			likeBtn1.setIcon(like2);
			isHeart1 = true;
			try {
				System.out.println("유저아이디:" + login_userid);
				daoheart.intliketop().get(0);
				System.out.println("좋아요 top1 코디셋아이디:" + liketop1);
				daoheart.create(login_userid, liketop1);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
});
likeBtn2.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		// 취소부분
		if (isHeart2 == true) {
			System.out.println("좋아요 취소 -> 좋아요 db에 데이터 삭제");
			likeBtn2.setIcon(like);
			isHeart2 = false;
			try {
				System.out.println("유저아이디:" + login_userid);
				daoheart.intliketop().get(1);
				System.out.println("좋아요 top2 코디셋아이디:" + liketop2);
				daoheart.delete(login_userid, liketop2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 좋아요 클릭부분
		} else {
			System.out.println("좋아요 버튼 클릭 -> 좋아요 db에 데이터 삽입");
			likeBtn2.setIcon(like2);
			isHeart2 = true;
			try {
				System.out.println("유저아이디:" + login_userid);
				daoheart.intliketop().get(1);
				System.out.println("좋아요 top2 코디셋아이디:" + liketop2);
				daoheart.create(login_userid, liketop2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
});
likeBtn3.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		// 취소부분
		if (isHeart3 == true) {
			System.out.println("좋아요 취소 -> 좋아요 db에 데이터 삭제");
			likeBtn3.setIcon(like);
			isHeart3 = false;
			try {
				System.out.println("유저아이디:" + login_userid);
				daoheart.intliketop().get(2);
				System.out.println("좋아요 top3 코디셋아이디:" + liketop3);
				daoheart.delete(login_userid, liketop3);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 좋아요 클릭부분
		} else {
			System.out.println("좋아요 버튼 클릭 -> 좋아요 db에 데이터 삽입");
			likeBtn3.setIcon(like2);
			isHeart3 = true;
			try {
				System.out.println("유저아이디:" + login_userid);
				daoheart.intliketop().get(2);
				System.out.println("좋아요 top3 코디셋아이디:" + liketop3);
				daoheart.create(login_userid, liketop3);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
});

//클릭한 코디셋의 상세보기 페이지 들어가는 부분.
		pnltoplbl1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("글 눌렀음! -> 글 상세보기 페이지로 이동합니다.");
				LookCodiset lookCodiset = null;
				lookCodiset = new LookCodiset(MainFrame3.this, login_userid, liketop1, yesno);
				lookCodiset.setVisible(true);
			}
		});
		pnltoplbl2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("글 눌렀음! -> 글 상세보기 페이지로 이동합니다.");
				LookCodiset lookCodiset = null;
				lookCodiset = new LookCodiset(MainFrame3.this, login_userid, liketop2, yesno);
				lookCodiset.setVisible(true);
			}
		});
		pnltoplbl3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("글 눌렀음! -> 글 상세보기 페이지로 이동합니다.");
				LookCodiset lookCodiset = null;
				lookCodiset = new LookCodiset(MainFrame3.this, login_userid, liketop3, yesno);
				lookCodiset.setVisible(true);
			}
		});

/////////////// 아래 - 신상품 부분
	try {
		listnew1 = daoallpro.listnew3().get(0);
		listnew2 = daoallpro.listnew3().get(1);
		listnew3 = daoallpro.listnew3().get(2);
	} catch (SQLException e2) {
		e2.printStackTrace();
	}


	// 로그인 한 유저가 담은게 있으면 카트 버튼을 눌러진 상태로 셋팅해놓기.
	try {
		for (int i = 0; i < daocart.readcart(login_userid).size(); i++) {
			if (listnew1.equals(daocart.readcart(login_userid).get(i))) {
				getBtn1.setIcon(cancel);
				iscart1 = true;
			}
			if (listnew2.equals(daocart.readcart(login_userid).get(i))) {
				getBtn2.setIcon(cancel);
				iscart2 = true;
			}
			if (listnew3.equals(daocart.readcart(login_userid).get(i))) {
				getBtn3.setIcon(cancel);
				iscart3 = true;
			}
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
	}

	// [담기] 버튼 누루면 cart테이블에 정보 삽입
	getBtn1.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (iscart1 == true) {
					System.out.println("담기 취소 -> cart db에 데이터 삭제");
					System.out.println("유저아이디:" + login_userid + "상품명:" + listnew1);
					daocart.delete(login_userid, listnew1);
					getBtn1.setIcon(addcart);
					iscart1 = false;
				} else {
					System.out.println("담기  -> cart테이블에 product이름을 넣습니다.");
					System.out.println("유저아이디:" + login_userid + "상품명:" + listnew1);
					daocart.create(login_userid, listnew1);
					getBtn1.setIcon(cancel);
					iscart1 = true;

				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	});
	getBtn2.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (iscart2 == true) {
					System.out.println("담기 취소 -> cart db에 데이터 삭제");
					System.out.println("유저아이디:" + login_userid + "상품명:" + listnew2);
					daocart.delete(login_userid, listnew2);
					getBtn2.setIcon(addcart);
					iscart2 = false;
				} else {
					System.out.println("담기  -> cart테이블에 product이름을 넣습니다.");
					System.out.println("유저아이디:" + login_userid + "상품명:" + listnew2);
					daocart.create(login_userid, listnew2);
					getBtn2.setIcon(cancel);
					iscart2 = true;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	});
	getBtn3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (iscart3 == true) {
					System.out.println("담기 취소 -> cart db에 데이터 삭제");
					System.out.println("유저아이디:" + login_userid + "상품명:" + listnew3);
					daocart.delete(login_userid, listnew3);
					getBtn3.setIcon(addcart);
					iscart3 = false;
				} else {
					System.out.println("담기  -> cart테이블에 product이름을 넣습니다.");
					System.out.println("유저아이디:" + login_userid + "상품명:" + listnew3);
					daocart.create(login_userid, listnew3);
					getBtn3.setIcon(cancel);
					iscart3 = true;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	});


	// 최근 신상품 3개 사진 출력
	// 신상품1
	try {

		InputStream in = daoallpro.imgnew3().get(0).getBinaryStream();
		BufferedImage blobImage = ImageIO.read(in);
		Image img = blobImage;
		ImageIcon convertedImage = new ImageIcon(img);
		lblnew1.setIcon(scaleImage(convertedImage, 276, 315));

	} catch (MalformedURLException | SQLException e1) {
		e1.printStackTrace();
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	// 신상품2
	try {
		InputStream in = daoallpro.imgnew3().get(1).getBinaryStream();
		BufferedImage blobImage = ImageIO.read(in);
		Image img = blobImage;
		ImageIcon convertedImage = new ImageIcon(img);
		lblnew2.setIcon(scaleImage(convertedImage, 276, 315));

	} catch (MalformedURLException | SQLException e1) {
		e1.printStackTrace();
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	// 신상품3
	try {
		InputStream in = daoallpro.imgnew3().get(2).getBinaryStream();
		BufferedImage blobImage = ImageIO.read(in);
		Image img = blobImage;
		ImageIcon convertedImage = new ImageIcon(img);
		lblnew3.setIcon(scaleImage(convertedImage, 276, 315));
	} catch (MalformedURLException | SQLException e1) {
		e1.printStackTrace();
	} catch (IOException e1) {
		e1.printStackTrace();
	}

////////////////////////////////////////////////////////////생성자 끝//////////////////////////////////////
}


	// 사진 사이즈 조절
	public ImageIcon scaleImage(ImageIcon icon, int w, int h) {
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if (icon.getIconWidth() > w) {
			nw = w;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}

		if (nh > h) {
			nh = h;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
	}

	// 로그아웃 된 상태에서는 쓸데 없는 버튼 들이 보이지 않게
	public void btnSetLogin(boolean yesno) {
		likeBtn1.setVisible(yesno);
		likeBtn2.setVisible(yesno);
		likeBtn3.setVisible(yesno);
		getBtn1.setVisible(yesno);
		getBtn2.setVisible(yesno);
		getBtn3.setVisible(yesno);
		writeBtn.setVisible(yesno);
		myBtn.setVisible(yesno);

	}

	// 버튼 예쁘게 셋팅하기~~~
	public void settingBtn(JButton btn) {
		btn.setPreferredSize(new Dimension(32, 32));
		btn.setContentAreaFilled(false);// 버튼 안 색 채우기 안함
		btn.setBorderPainted(false);// 버튼 외곽선 안 보이게
		btn.setFocusPainted(false);// 버튼 눌렀을 때 외곽선 안 보이게
	}

//	public static void main(String[] args) {
//		MainFrame3 mainframe = new MainFrame3("lossryeong", true);
//		mainframe.setVisible(true);
//
//	}

}