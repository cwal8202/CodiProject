import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import kr.co.greenart.dbutil.DBUtil;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

class UI extends BasicTabbedPaneUI {

	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
//보통 보이는 부분 여기서 그려주고

		g.setColor(Color.white);
		g.drawRect(x, y, w, h);
	}
}

class myProfile extends JFrame {
	public JButton mainbtn;
	jinhoDao dao = new jinhoDao();
	DAO_heart daoheart = new DAO_heart();
	DAO_cart daocart = new DAO_cart();
	List<Boolean> iscart = new ArrayList<Boolean>();
	List<Boolean> isheart =  new ArrayList<Boolean>();
	List<JButton> likeBtn = new ArrayList<JButton>();
	List<JButton> cartBtn = new ArrayList<JButton>();
	
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

	
	// 버튼 설정 해주는 메소드
	public void buttonSet(JButton btn) {
		btn.setBorderPainted(false);
		btn.setPreferredSize(new Dimension(32, 32));
		btn.setBackground(Color.white);
		btn.setFocusPainted(false);
	}

	public myProfile(String user) throws SQLException {
		super(user + "의 프로필");

		Toolkit kit = Toolkit.getDefaultToolkit();

		URL cart_sel_url = myProfile.class.getClassLoader().getResource("king/cart_sel.png");
		URL cart_url = myProfile.class.getClassLoader().getResource("king/cart.png");
		URL heart_w_url = myProfile.class.getClassLoader().getResource("king/heart_w.png");
		URL heart_url = myProfile.class.getClassLoader().getResource("king/heart.png");
		URL hearted2_url = myProfile.class.getClassLoader().getResource("king/hearted2.png");
		URL like_sel_url = myProfile.class.getClassLoader().getResource("king/like_sel.png");
		URL like_url = myProfile.class.getClassLoader().getResource("king/like.png");
		URL list_w_url = myProfile.class.getClassLoader().getResource("king/list_w.png");
		URL list_url = myProfile.class.getClassLoader().getResource("king/list.png");
		URL myFeed_sel_url = myProfile.class.getClassLoader().getResource("king/myFeed_sel.png");
		URL myFeed_url = myProfile.class.getClassLoader().getResource("king/myFeed.png");
		URL profile_w_url = myProfile.class.getClassLoader().getResource("king/profile_w.png");
		URL profile_url = myProfile.class.getClassLoader().getResource("king/profile.png");
		URL upload_w_url = myProfile.class.getClassLoader().getResource("king/upload_w.png");
		URL upload_url = myProfile.class.getClassLoader().getResource("king/upload.png");
		URL write_url = myProfile.class.getClassLoader().getResource("king/write.png");
		URL tagInstBtn_url = myProfile.class.getClassLoader().getResource("king/tagInstBtn.png");
		URL addcart_url = myProfile.class.getClassLoader().getResource("hand/add.png");
		URL cancel_url = myProfile.class.getClassLoader().getResource("hand/cancel.png");
		
		ImageIcon list = new ImageIcon(list_w_url);
		ImageIcon heart = new ImageIcon(heart_url);
		ImageIcon heart2 = new ImageIcon(hearted2_url);
		ImageIcon profile = new ImageIcon(profile_w_url);
		ImageIcon write = new ImageIcon(write_url);
		ImageIcon addcart = new ImageIcon(addcart_url);
		ImageIcon cancel = new ImageIcon(cancel_url);

		
		getContentPane().setBackground(Color.white);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setBounds(727, 10, 95, 41);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 2, 10, 0));


		// 메인페이지 들어가는 버튼
		mainbtn = new JButton(list);
		mainbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonSet(mainbtn);
		panel.add(mainbtn);
		
		// 마이 프로필 들어가는 버튼
		JButton profilebtn = new JButton(profile);
		buttonSet(profilebtn);
		panel.add(profilebtn);

		JPanel nameandwritepnl = new JPanel();
		nameandwritepnl.setBounds(59, 10, 386, 41);
		getContentPane().add(nameandwritepnl);
		nameandwritepnl.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel namepnl = new JPanel();
		nameandwritepnl.add(namepnl);
		namepnl.setBackground(Color.white);
		namepnl.setLayout(new BorderLayout(0, 0));

		JLabel lblname = new JLabel(user);
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
		lblname.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblname.setBackground(Color.white);
		namepnl.add(lblname, BorderLayout.CENTER);

		// 글쓰기 들어가는 버튼
		JButton writebtn = new JButton(write);
		writebtn.addActionListener(new ActionListener() {	// 글쓰기 버튼 이벤트처리
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Clothes writePage = new Clothes(user);
					writePage.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		writebtn.setBackground(Color.white);
		nameandwritepnl.add(writebtn);

		JPanel subtagpnl = new JPanel();
		subtagpnl.setBounds(12, 61, 810, 191);
		getContentPane().add(subtagpnl);
		subtagpnl.setLayout(null);

		JLabel tag = new JLabel("구독태그");
		tag.setHorizontalAlignment(SwingConstants.CENTER);
		tag.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		tag.setBounds(12, 10, 97, 31);
		subtagpnl.add(tag);

		JPanel subtag = new JPanel();
		subtag.setAutoscrolls(true);
		subtag.setLayout(new GridLayout(0, 5, 10, 0));
		subtag.setBounds(12, 51, 786, 95);
		subtagpnl.add(subtag);

		ImageIcon tagInst = new ImageIcon(kit.getImage(tagInstBtn_url));
		JButton updateTag = new JButton(tagInst);
		updateTag.setBounds(658, 156, 140, 25);

		// 구독태그 볼 수 있는 곳
		JLabel[] lblsubtag;
		lblsubtag = new JLabel[dao.readTag(user).size()];
		try {
			for (int i = 0; i < dao.readTag(user).size(); i++) {
				List<String> query = dao.readTag(user);

				lblsubtag[i] = new JLabel(query.get(i).toString());
				lblsubtag[i].setHorizontalAlignment(SwingConstants.LEFT);
				lblsubtag[i].setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				subtag.add(lblsubtag[i]);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//구독 태그 설정하는 버튼
		ActionListener AL = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateTag Tag = new updateTag(myProfile.this, user);
				Tag.setVisible(true);
				
				
			}	
		};

		updateTag.addActionListener(AL);

		subtagpnl.add(updateTag);

		// 탭 추가
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBackground(Color.BLACK);
		tab.setAutoscrolls(true);
		tab.setBounds(12, 262, 810, 539);
		tab.setBackground(Color.BLACK);
		tab.setUI(new UI());
		getContentPane().add(tab);

		JPanel myFeed = new JPanel();
		JScrollPane myFeedScroll = new JScrollPane(myFeed);
		myFeedScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		myFeedScroll.setBackground(Color.WHITE);
		myFeedScroll.getVerticalScrollBar().setUnitIncrement(20);
		tab.addTab("", new ImageIcon(kit.getImage(myFeed_sel_url)), myFeedScroll, null);
		myFeed.setBackground(Color.white);
		myFeed.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel cart = new JPanel();
		JScrollPane cartScroll = new JScrollPane(cart);
		cartScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cartScroll.setBackground(Color.WHITE);
		cartScroll.getVerticalScrollBar().setUnitIncrement(20);
		tab.addTab("", new ImageIcon(kit.getImage(cart_url)), cartScroll, null);
		cart.setBackground(Color.white);
		cart.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel like = new JPanel();
		JScrollPane likeScroll = new JScrollPane(like);
		likeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		likeScroll.setBackground(Color.WHITE);
		likeScroll.getVerticalScrollBar().setUnitIncrement(20);
		tab.addTab("", new ImageIcon(kit.getImage(like_url)), likeScroll, null);
		like.setBackground(Color.white);
		like.setLayout(new GridLayout(0, 2, 0, 0));
		
		try {

			JLabel[] lblDataLine;
			for (int i = 0; i < dao.read(user).size(); i++) {
				List<Blob> query = dao.read(user);
				InputStream in = query.get(i).getBinaryStream();
				BufferedImage blobImage = ImageIO.read(in);
				Image img = blobImage;
				ImageIcon convertedImage = new ImageIcon(img);
				lblDataLine = new JLabel[dao.read(user).size()];
				lblDataLine[i] = new JLabel(convertedImage);
				myFeed.add(lblDataLine[i]);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 로그인한 유저가 상품 담아놓은게 있으면 아이콘 변경하기!!!
		/// [담기]버튼을 누르면 cart 테이블에 담아지고,
		// 두 번 누르면 cart 테이블에서 삭제된다.

		try {
			JPanel[] pnlDataLine;
			JLabel[] lblDataLine;
			;
			for (int i = 0; i < dao.cart(user).size(); i++) {
				List<Blob> query = dao.cart(user);
				InputStream in = query.get(i).getBinaryStream();
				BufferedImage blobImage = ImageIO.read(in);
				Image img = blobImage;
				ImageIcon convertedImage = new ImageIcon(img);
				pnlDataLine = new JPanel[dao.cart(user).size()];
				lblDataLine = new JLabel[dao.cart(user).size()];
				cartBtn.add(new JButton(cancel));
				buttonSet(cartBtn.get(i));
				iscart.add(true);
				
				pnlDataLine[i] = new JPanel();
				lblDataLine[i] = new JLabel(convertedImage);
				pnlDataLine[i].add(lblDataLine[i]);
				pnlDataLine[i].add(cartBtn.get(i));
				pnlDataLine[i].setBackground(Color.WHITE);
				cart.add(pnlDataLine[i]);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		///// 좋아요!!!!!
		// 좋아요 했던 코디셋 확인해서 버튼 셋팅
		// [좋아요] 버튼 누르면 좋아요 테이블에 담기는거, 두 번 누르면 삭제되는거

		try {
			JPanel[] pnlDataLine;
			JLabel[] lblDataLine;

			for (int i = 0; i < dao.like(user).size(); i++) {
				List<Blob> query = dao.like(user);
				InputStream in = query.get(i).getBinaryStream();
				BufferedImage blobImage = ImageIO.read(in);
				Image img = blobImage;
				ImageIcon convertedImage = new ImageIcon(img);
				pnlDataLine = new JPanel[dao.like(user).size()];
				lblDataLine = new JLabel[dao.like(user).size()];
				likeBtn.add(new JButton(heart2));
				buttonSet(likeBtn.get(i));
				isheart.add(true);
				
				pnlDataLine[i] = new JPanel();
				lblDataLine[i] = new JLabel(convertedImage);
				pnlDataLine[i].add(lblDataLine[i]);
				pnlDataLine[i].add(likeBtn.get(i));
				pnlDataLine[i].setBackground(Color.WHITE);
				likeScroll.add(pnlDataLine[i]);
				like.add(pnlDataLine[i]);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		ChangeListener l = new ChangeListener() {
			// 탭에 따라 글씨 색이 바뀌도록 함.
			@Override
			public void stateChanged(ChangeEvent e) {
				int cases = 0;
				if (myFeed.isShowing()) {
					cases = 1;
				} else if (cart.isShowing()) {
					cases = 2;
				} else if (like.isShowing()) {
					cases = 3;
				}
				;

				switch (cases) {
				case 1: {
					tab.setIconAt(0, new ImageIcon(kit.getImage(myFeed_sel_url)));
					tab.setIconAt(1, new ImageIcon(kit.getImage(cart_url)));
					tab.setIconAt(2, new ImageIcon(kit.getImage(like_url)));

					break;
				}
				case 2: {
					tab.setIconAt(0, new ImageIcon(kit.getImage(myFeed_url)));
					tab.setIconAt(1, new ImageIcon(kit.getImage(cart_sel_url)));
					tab.setIconAt(2, new ImageIcon(kit.getImage(like_url)));
					break;
				}
				case 3: {
					tab.setIconAt(0, new ImageIcon(kit.getImage(myFeed_url)));
					tab.setIconAt(1, new ImageIcon(kit.getImage(cart_url)));
					tab.setIconAt(2, new ImageIcon(kit.getImage(like_sel_url)));
					break;
				}
				}
			}

		};
		//좋아요 버튼 누르면 좋아요  db삭제됨. 
				try {
					for(int i =0; i<dao.like(user).size() ; i++) {
						int j = i;
						likeBtn.get(i).addActionListener(new ActionListener() {

							

							@Override
							public void actionPerformed(ActionEvent e) {
								// 좋아요가 된 상태라면
								if (isheart.get(j) == true) {
									System.out.println("좋아요 취소 -> 좋아요 db에 데이터 삭제");
									likeBtn.get(j).setIcon(heart);
									isheart.set(j,false);
									try {
										System.out.println("유저아이디:" + user + " 코디셋 번호: " + dao.likeint(user).get(j) + "가 삭제됩니다.");
										daoheart.delete(user, dao.likeint(user).get(j));
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
									likeBtn.get(j).setVisible(false);
								} 
							}
						});
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
						
				//담기 취소 버튼 누르면 담기 db삭제됨. 
				try {
					for(int i =0; i<dao.cart(user).size() ; i++) {
						int  k =i;
						cartBtn.get(i).addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if (iscart.get(k) == true) {
									System.out.println("담기 취소 -> cart db에 데이터 삭제");
									cartBtn.get(k).setIcon(addcart);
									iscart.set(k, false);
									try {
										System.out.println("유저아이디:" + user + "상품명:" + dao.cartstring(user).get(k));
										daocart.delete(user, dao.cartstring(user).get(k));
									} catch (MalformedURLException e1) {
										e1.printStackTrace();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
									cartBtn.get(k).setVisible(false);
								} 
							}
						});
					}
				} catch (SQLException | MalformedURLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
				
				tab.addChangeListener(l);

				setSize(850, 850);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}