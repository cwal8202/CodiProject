import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
/*
 * -주요 컴포넌트 -
 * userNamelbl: 해당 코디셋을 작성한 유저의 이름이 들어가는 라벨
 * backBtn : [뒤로가기]버튼 . 메인페이지로 돌아간다.
 * likeBtn : [좋아요] 버튼. 좋아요 테이블에 db가 전달되어야 함.
 * getBtn[0-4] : [담기] 버튼 -> 누르면 해당 옷 db가 현재 사용자의 장바구니 db에 담긴다. 
 * 
 * */
public class LookCodiset extends JDialog {
	boolean isheart;
	String login_userId;
	int liketop;
	int liketop2;
	int liketop3;
	private String productname =" ";
	private JButton backBtn;
	private JButton likeBtn;
	private JButton getBtn2;
	boolean loginyesno;
	private List<JButton> getBtn;
	private DAO_allproduct daoallpro;
	private DAO_codiset daocodiset;
	private DAO_cart daocart;
	private DAO_heart daoheart;
	private Color color;
	private ImageIcon back;
	private ImageIcon addcart;
	private ImageIcon cancel;
	private ImageIcon like;
	private ImageIcon like2;
	//MainFrame3를 owner 로 쓴다. 
	//로그인 여부를 생성자로 받아옴~~~~~~~~~~
	public LookCodiset(JFrame owner,String login_userId, int liketop,boolean loginyesno){
/////////////////생성자 시작~~~~		
		super(owner,true);
//		back = new ImageIcon(".\\img\\back.png");
//		addcart = new ImageIcon(".\\img\\add.png");
//		cancel = new ImageIcon(".\\img\\cancel.png");
//		like = new ImageIcon(".\\img\\likeImg.png");
//		like2 = new ImageIcon(".\\img\\like2.png");
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		URL url1 = LookCodiset.class.getClassLoader().getResource("hand/back.png");
		URL url2 = LookCodiset.class.getClassLoader().getResource("hand/likeImg.png");
		URL url3 = LookCodiset.class.getClassLoader().getResource("hand/like2.png");
		URL url4 = LookCodiset.class.getClassLoader().getResource("hand/add.png");
		URL url5 = LookCodiset.class.getClassLoader().getResource("hand/cancel.png");
		back = new ImageIcon(kit.getImage(url1));
		like = new ImageIcon(kit.getImage(url2));
		like2 = new ImageIcon(kit.getImage(url3));
		addcart = new ImageIcon(kit.getImage(url4));
		cancel = new ImageIcon(kit.getImage(url5));
		
		
		
		
		
		daocodiset = new DAO_codiset();
		daocart = new DAO_cart();
		daoheart = new DAO_heart();
		daoallpro = new DAO_allproduct();
		color = new Color(200,221,242);
		
		
		//제일 큰 프레임
		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		pnl.setBackground(Color.white);
		
		
		////////////top
		JPanel topPnl = new JPanel();
		topPnl.setBounds(0, 5, 834, 35);
		topPnl.setLayout(null);//좌표값으로 위치를 정해주려면 기본 layout설정을 변경해주어야 하기 때문에 setLayout(null로 지정하여 주어야 한다)
		topPnl.setBackground(Color.white);
		//코디셋 만든사람 이름 들어가는 라벨
		JLabel userNamelbl = new JLabel("코디셋을 만든 사람의 아이디 ");
		userNamelbl.setHorizontalAlignment(SwingConstants.RIGHT);
		userNamelbl.setLocation(543, 3);
		userNamelbl.setSize(100, 25);
		JLabel lbl = new JLabel("님의 코디셋");
		lbl.setBounds(672, 5, 100, 25);
		lbl.setHorizontalAlignment(SwingConstants.LEFT);
		
		//이미지 아이콘
		//[뒤로가기] 버튼
		
		backBtn = new JButton(back);
		backBtn.setBounds(24, 2, 38, 27);
		backBtn.setPreferredSize(new Dimension(32, 32));
		backBtn.setContentAreaFilled(false);//버튼 안 색 채우기 안함
		backBtn.setBorderPainted(false);//버튼 외각선 안 보이게
		backBtn.setFocusPainted(false);// 버튼 눌렀을 때 외각선 안 보이게
		
		//[좋아요]버튼
	
		likeBtn = new JButton(like);
		likeBtn.setBounds(784, 2, 38, 27);
		likeBtn.setPreferredSize(new Dimension(32, 32));
		likeBtn.setContentAreaFilled(false);//버튼 안 색 채우기 안함
		likeBtn.setBorderPainted(false);//버튼 외각선 안 보이게
		likeBtn.setFocusPainted(false);// 버튼 눌렀을 때 외각선 안 보이게
		
		topPnl.add(backBtn);
		topPnl.add(userNamelbl);
		topPnl.add(lbl);
		topPnl.add(likeBtn);
		/////////////
		
		
		
		/////////// middle
		//중간 제일 큰 패널
		JPanel middlePnl = new JPanel();
		middlePnl.setBounds(0, 40, 834, 737);
		middlePnl.setLayout(new GridLayout(0, 2));// 세로로 두 개로 나눔
		middlePnl.setBackground(Color.white);
		//중간-왼쪽
		JPanel leftPnlCodiset = new JPanel();
		JLabel codisetphoto = new JLabel();
		leftPnlCodiset.add(codisetphoto);		
		leftPnlCodiset.setBorder(new LineBorder(color,2));//영역 볼려고 테두리 설정해두었음
		middlePnl.add(leftPnlCodiset);
		leftPnlCodiset.setBackground(Color.white);
		
		//중간- 오른쪽
		JPanel rightPnl = new JPanel();
		rightPnl.setBackground(Color.white);
		//5개의 구성요소가 있어야 함. 
		//배열에다 구성요소 하나하나 담는다. 
		List<JPanel> component = new ArrayList<JPanel>();
		List<JLabel> photo = new ArrayList<JLabel>();
		List<JLabel> text = new ArrayList<JLabel>();
		getBtn = new ArrayList<JButton>();
		List<Boolean> iscart = new ArrayList<Boolean>();
		try {
			for(int i =0; i<daocodiset.codisetproname(liketop).size();i++) {
				//컴포넌트 생성
				JPanel component2 = new JPanel();
				JLabel photo2 = new JLabel();
				JLabel text2 = new JLabel("정보가 들어가는 부분");
				getBtn2 = new JButton(addcart);
				settingBtn(getBtn2);
				boolean iscart2 = false;
				//각 컴포넌트들을 배열에 담는다. 
				component.add(component2);
				photo.add(photo2);
				text.add(text2);
				getBtn.add(getBtn2);
				iscart.add(iscart2);
				
				//하나의 패널에 컴포넌트들을 담는다. 
				component2.add(photo2);
				component2.add(text2);
				component2.add(getBtn2);
				component2.setLayout(new BoxLayout(component2,BoxLayout.X_AXIS));
				component2.setBorder(new LineBorder(color,2));
				component2.setBackground(Color.white);
				//패널을 또 패널에~~~
				component.add(component2);
				rightPnl.add(component2);
				

				//만들어진 컴포넌트에 의상 사진 넣기 
				//코디셋의 구성요소 사진 띄움!!!
				productname  = daocodiset.codisetproname(liketop).get(i);
				InputStream in = daocodiset.productImg(productname).get(0).getBinaryStream();
				BufferedImage blobImage = ImageIO.read(in);
				Image img = blobImage;
				ImageIcon convertedImage = new ImageIcon(img);
				photo.get(i).setIcon(scaleImage(convertedImage,100,100));
				
				
				//코디셋의 구성요소 이름 띄우기!!
				text.get(i).setText(productname);
				System.out.print("이 코디셋 구성요소:"+ productname+",");
				System.out.println();
				//컴포넌트가 몇개 생길 줄 모르니까 그리드레이아웃으로 비율 맞춰서 넣는다. 
				rightPnl.setLayout(new GridLayout(daocodiset.codisetproname(liketop).size(),0));
			}
		} catch (MalformedURLException | SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		middlePnl.add(leftPnlCodiset);
		middlePnl.add(rightPnl);
		////////////중간 끝
		
		//구성요소 더하기
		pnl.add(topPnl);
		pnl.add(middlePnl);
		add(pnl);
		BoxLayout allLayout = new BoxLayout(pnl,BoxLayout.Y_AXIS);
		setSize(850,850);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		btnSetLogin(loginyesno);
		
////////////////////////////구성요소 끝
		
		
		
		
/////////////////////////이벤트
		
		/////좋아요!!!!!
		//좋아요 했던 코디셋 확인해서 버튼 셋팅 
		try {
			//코디셋 아이디
			for(int i =0; i< daoheart.likedCodisetId(login_userId).size();i++) {
				if(daoheart.likedCodisetId(login_userId).get(i) == liketop) {
					likeBtn.setIcon(like2);
					isheart =true;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
		//[좋아요] 버튼 누르면 좋아요 테이블에 담기는거, 두 번 누르면 삭제되는거   
		likeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//좋아요가 된 상태라면
				if (isheart==true) {
					System.out.println("좋아요 취소 -> 좋아요 db에 데이터 삭제");
					likeBtn.setIcon(like);
					isheart=false;
					try {
						System.out.println("유저아이디:" + login_userId +" 코디셋 번호: "+ liketop + "가 삭제됩니다.");
						daoheart.delete(login_userId, liketop);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else {
					System.out.println("좋아요 버튼 클릭 -> 좋아요 db에 데이터 삽입");
					likeBtn.setIcon(like2);
					isheart=true;
					try {
						System.out.println("유저아이디:" + login_userId+" 코디셋 번호: "+ liketop+"가 추가됩니다.");
						daoheart.create(login_userId, liketop);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		//[뒤로가기]버튼 누르면 창이 꺼지도록 하기.
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				owner.setVisible(true);
				dispose();
				System.out.println("뒤로가기 버튼 눌렀음. 메인화면으로 돌아갑니다.");
			}
		});
		
		
/////////왼쪽 큰 라벨에 코디셋 사진 띄운다!
		try {
			// 그 코디셋의 사진
			daocodiset.codiImg(liketop);
			InputStream in = daocodiset.codiImg(liketop).get(0).getBinaryStream();
			BufferedImage blobImage = ImageIO.read(in);
			Image img = blobImage;
			ImageIcon convertedImage = new ImageIcon(img);
			codisetphoto.setIcon(scaleImage(convertedImage, 417, 737));
		} catch (MalformedURLException | SQLException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//코디셋을 만든 사람의 이름을 오른쪽 상단에 띄운다!!!
		try {
			String what = daocodiset.madeBy(liketop);
			userNamelbl.setText(what);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		

		
		
		//로그인한 유저가 상품 담아놓은게 있으면 아이콘 변경하기!!!
		try {
			for(int j =0; j<daocart.readcart(login_userId).size();j++) {
				for (int i = 0; i <daocodiset.codisetproname(liketop).size(); i++) {
					if(daocodiset.codisetproname(liketop).get(i).equals(daocart.readcart(login_userId).get(j))){
						getBtn.get(i).setIcon(cancel);
						iscart.set(i,true);
					}
				}
			}
		} catch (SQLException | MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		///[담기]버튼을 누르면 cart 테이블에 담아지고, 
		//두 번 누르면 cart 테이블에서 삭제된다. 
		try {
			for(int j =0; j<daocodiset.codisetproname(liketop).size();j++) {
				int whatwhat =j;
				String productname = daocodiset.codisetproname(liketop).get(j);
				getBtn.get(j).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (iscart.get(whatwhat)==true) {
								System.out.println("담기 취소 -> cart db에 데이터 삭제");
								System.out.println("유저아이디:"+login_userId+"상품명:"+productname);
								daocart.delete(login_userId, productname);
								getBtn.get(whatwhat).setIcon(addcart);
								iscart.set(whatwhat,false);
							}else {
								System.out.println("담기  -> cart테이블에 product이름을 넣습니다.");
								System.out.println("유저아이디:"+login_userId+"상품명:"+productname);
								daocart.create(login_userId,productname);
								getBtn.get(whatwhat).setIcon(cancel);
								iscart.set(whatwhat,true);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		} catch (MalformedURLException | SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
//////////////////////생성자 끝 		
	}
	
	
///////////메소드	
	// 사진 사이즈 조절 메소드
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
	
	//버튼 예쁘게 셋팅하기~~~
	public void settingBtn(JButton btn) {
		btn.setBounds(102, 325, 69, 28);
		btn.setPreferredSize(new Dimension(32, 32));
		btn.setContentAreaFilled(false);// 버튼 안 색 채우기 안함
		btn.setBorderPainted(false);// 버튼 외각선 안 보이게
		btn.setFocusPainted(false);// 버튼 눌렀을 때 외곽선 안 보이게
	}
	

	// 로그아웃 된 상태에서는 쓸데 없는 버튼 들이 보이지 않게
	public void btnSetLogin(boolean yesno) {
		likeBtn.setVisible(yesno);
		for(int i =0; i<getBtn.size(); i++) {
			getBtn.get(i).setVisible(yesno);
		}

	}
}

