import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Management extends JFrame {
	private AdminDaoImpl dao = new AdminDaoImpl();

	// 등록창과 수정창 생성자. 만일을 대비해 두 개를 따로 생성. 오류방지목적으로 동시 실행불가 처리해놓음.
	ManagementOfRegist registWindow = new ManagementOfRegist();
	ManagementOfEdit editWindow = new ManagementOfEdit();
//	List<JLabel> lblDataList = new ArrayList<>();
	static List<Integer> checkedList = new ArrayList<>();
	static List<Item> itemList = new ArrayList<>();
	static List<Integer> intList = new ArrayList<>();
	List<JCheckBox> checkBoxList = new ArrayList<>();
	// 세개 모두 한패널에 생성, 등록된 상품을 나타내는 라벨
	// 체크박스
//	private JCheckBox ckBox;
	// 이미지 들어가는 라벨
	private JLabel[] lblImages;
	// 상품 정보를 담는 라벨
	private JLabel[] lblDataLines;
	// ----------------------------------------

	private int count;

	private int push = 1;

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

	Management() {
		super("관리자용");
		
		Toolkit kit = Toolkit.getDefaultToolkit();		
		
		Color color = new Color(200, 221, 242);
		// 의상관리 타이틀 ---
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel pnlTop = new JPanel();
		pnlTop.setBorder(new LineBorder(color));
		pnlTop.setBackground(Color.WHITE);
		panel.add(pnlTop);

		JLabel lblTitle = new JLabel("의상관리");
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setFont(new Font("휴먼편지체", Font.PLAIN, 40));
		pnlTop.add(lblTitle);
		// -------------

		// 데이터 목록 보이는 패널
		JPanel pnlMain = new JPanel();
		pnlMain.setBorder(new LineBorder(color));
		pnlMain.setEnabled(false);
		pnlMain.setBackground(Color.WHITE);
		JPanel pnlData = new JPanel();
		pnlData.setFont(new Font("굴림", Font.PLAIN, 16));
		pnlData.setBackground(Color.WHITE);
		pnlData.setAlignmentX(Component.LEFT_ALIGNMENT);

		// 체크박스 일단 100개 생성; 나중에 데이터 길이만큼 불러옴.
		JPanel pnlBtn = new JPanel();
		pnlBtn.setBounds(250, 250, 250, 250);

		JPanel pnlBtnManage = new JPanel();
		pnlBtnManage.setBorder(new LineBorder(color));
		pnlBtnManage.setBackground(Color.WHITE);
		panel.add(pnlBtnManage);
		pnlBtnManage.setLayout(new GridLayout(0, 6, 0, 0));

		JButton btnRefresh = new JButton();
		btnRefresh.setToolTipText("새로고침");
		pnlBtnManage.add(btnRefresh);
		


		URL refresh_url = MainFrame3.class.getClassLoader().getResource("white/refresh.png");
		
		ImageIcon refresh = new ImageIcon(refresh_url);
		btnRefresh.setMargin(new Insets(2, 4, 2, 4));
		btnRefresh.setIcon(refresh);
		settingBtn(btnRefresh);

		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 창 새로고치기
				Management.this.dispose();
				new Management().setVisible(true);
			}
		});

		JButton btnAllCheck = new JButton();
		btnAllCheck.setFocusPainted(false);
		URL allCheck_url = MainFrame3.class.getClassLoader().getResource("white/allCheck.png");
		ImageIcon allCheckIcon = new ImageIcon(allCheck_url);
		btnAllCheck.setIcon(allCheckIcon);
//		btnAllCheck.setFont(new Font("굴림", Font.PLAIN, 12));
		btnAllCheck.setMargin(new Insets(2, 4, 2, 4));
		btnAllCheck.setToolTipText("전체선택");
		settingBtn(btnAllCheck);
		pnlBtnManage.add(btnAllCheck);

		JButton btnAllUnCheck = new JButton();
		btnAllUnCheck.setFocusPainted(false);
		URL allUnCheck_url = MainFrame3.class.getClassLoader().getResource("white/allUnCheck.png");
		ImageIcon allUnCheckIcon = new ImageIcon(allUnCheck_url);
		btnAllUnCheck.setIcon(allUnCheckIcon);
//		btnAllUnCheck.setFont(new Font("Gulim", Font.PLAIN, 12));
		btnAllUnCheck.setMargin(new Insets(2, 4, 2, 4));
		settingBtn(btnAllUnCheck);
		btnAllUnCheck.setToolTipText("전체해제");
		pnlBtnManage.add(btnAllUnCheck);

		JButton btConfirm = new JButton();
		btConfirm.setFocusPainted(false);
		URL add_url = MainFrame3.class.getClassLoader().getResource("white/add.png");
		ImageIcon registIcon = new ImageIcon(add_url);
		btConfirm.setIcon(registIcon);
		btConfirm.setMargin(new Insets(2, 4, 2, 4));
		btConfirm.setToolTipText("등록하기");
		settingBtn(btConfirm);
		pnlBtnManage.add(btConfirm);

		JButton btEdit = new JButton();
		btEdit.setFocusPainted(false);
		URL edit_url = MainFrame3.class.getClassLoader().getResource("white/edit.png");
		ImageIcon editIcon = new ImageIcon(edit_url);
		btEdit.setIcon(editIcon);
		btEdit.setEnabled(false);
		btEdit.setMargin(new Insets(2, 4, 2, 4));
		btEdit.setToolTipText("수정하기");
		settingBtn(btEdit);
		pnlBtnManage.add(btEdit);

		JButton btDelete = new JButton();
		btDelete.setFocusPainted(false);
		URL delete_url = MainFrame3.class.getClassLoader().getResource("white/delete.png");
		ImageIcon deleteIcon = new ImageIcon(delete_url);
		btDelete.setIcon(deleteIcon);
		btDelete.setMargin(new Insets(2, 4, 2, 4));
		btDelete.setToolTipText("삭제하기");
		settingBtn(btDelete);
		pnlBtnManage.add(btDelete);

		// 관리자 데이터 확인용
////////////////////////////////////////////////////////////////////////////////////////

		// 데이터 길이 만큼 레이블 생성 넣기
//		JLabel[] lblData = new JLabel[여기에 데이터 length];
		String query = "";
		Blob imgUrl = null;
		ImageIcon convertedImage;
		try {
			intList.clear();
			for (count = 0; count < dao.read().size(); count++) {
				query = dao.read().get(count).toString();
				intList.add(dao.read().get(count).getId());
//				System.out.println("인트리스트사진가져올때씀" + intList);
				// 7/27 DB 컬럼변경
				// ImageUrl이 String으로된 절대경로값에서 Blob형식으로 변경되었다.
				// Item객체 생성자 및 DAO 에서 ImageUrl 값을 Blob으로 모두 변경
				// DAOImpl에서 생성, 수정 파라미터에 Blob형식 추가함.
				// 관리자가 'Item'을 등록할 때도, Blob형식으로 등록되고, 등록된 Blob형식의 이미지를
				// JLabel 등에서 불러와서 미리보기로 사용한다.
				// DB가 느려질 수 있으나, 서버를 배우지 않은 상태에서 그나마 서버효과를 낼 수 있을 듯.
				// Blob형태의 이미지를 JLabel에 나타내기 위해 변환하는 과정 -------
				if (dao.read().get(count).getImageUrl() != null) {
					imgUrl = dao.read().get(count).getImageUrl();
					InputStream in = imgUrl.getBinaryStream();
					BufferedImage blobImage = ImageIO.read(in);
					Image img = blobImage;
					convertedImage = new ImageIcon(img);
				} else {
					convertedImage = new ImageIcon();
				}
				// -----------------------------------------------

//				Toolkit kit = Toolkit.getDefaultToolkit();
				// url 안쓰고, 쿼리문 긁어와서 kit.getImage('''여기에 쿼리문''');
//				URL url1 = this.getClass().getClassLoader().getResource("가방/가방1.png");
//				ImageIcon imageIcon = new ImageIcon(kit.getImage(imgUrl));
//				ImageIcon imageIcon = new ImageIcon("D:\\BSS\\workspace\\lookProject\\resource\\가방\\가방2.jpg");
				// 이건 되는데 resource꺼는 왜안됨? => Toolkit으로 해결.
//				ImageIcon imageIcon = new ImageIcon("D:\\BSS\\스쉐프젝\\룩\\룩3\\가방3.jpg");

				JCheckBox ckBox = new JCheckBox();
				lblImages = new JLabel[dao.read().size()];
				lblImages[count] = new JLabel(scaleImage(convertedImage, 100, 100));
				lblDataLines = new JLabel[dao.read().size()];
				lblDataLines[count] = new JLabel(query);
				pnlData.add(ckBox);
				pnlData.add(lblImages[count]);
				pnlData.add(lblDataLines[count]);
				ckBox.setMargin(new Insets(7, 2, 7, 2));
				checkBoxList.add(ckBox);
				ckBox.setBackground(Color.WHITE);

//				lblDataList.add(lblDataLine[i]);
/////////////////////////////////////////////////////////////////////////////////////////

				// 체크박스 전체 선택, 해제
				btnAllCheck.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ckBox.setSelected(true);
					}
				});
				btnAllUnCheck.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ckBox.setSelected(false);
					}
				});

				// 수정 버튼은 체크박스가 여러개 체크되있을 경우, 비활성화 시킨다.
				int index = count;
				ckBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						// selectNum은 0부터 시작이라 read할때, item의 number는 1부터라서 +1을 해준다.
						int selectNum = index;
						if (e.getStateChange() == ItemEvent.SELECTED) {
							checkedList.add(selectNum);
							System.out.println("전체 체크 박스인덱스 " + checkedList);
							System.out.println("체크한 번호 " + intList.get(checkedList.get(0)) + " ← 상품번호");
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							checkedList.remove(checkedList.indexOf(selectNum));
						}
						if (checkedList.size() > 1 || checkedList.size() == 0) {
							btEdit.setEnabled(false);
							btEdit.setToolTipText("수정할 데이터는 1개만 선택 해주세요");
							ToolTipManager m = ToolTipManager.sharedInstance();
							m.setInitialDelay(0);
						} else {
							btEdit.setEnabled(true);
						}
//						System.out.println(checkedList);
					}
				});

				// 창을 닫을 때, 다시 버튼이 활성화 된다.
				WindowListener toggleBtn = new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						ckBox.setSelected(false);
						btConfirm.setEnabled(true);
						// 하나라도 선택 되어있으면 수정버튼 활성, 아니면 비활성
						if (checkedList.size() == 1) {
							btEdit.setEnabled(true);
						}
					}
				};
				registWindow.addWindowListener(toggleBtn);
				editWindow.addWindowListener(toggleBtn);

				// 삭제 버튼 수정필수 *************************************
				ActionListener deleteCheck = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// 여기에 삭제 쿼리문
						ckBox.setSelected(false);
					}
				};
				btDelete.addActionListener(deleteCheck);

			}
			registWindow.btnRegist.addActionListener(new ActionListener() {
				String name;
				String size;
				String color;
				String category;
				String subCategory;
				String imageUrl;
				File file;
				String season;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (!registWindow.tfName.getText().isEmpty() && !registWindow.tfImageUrl.getText().isEmpty()
								&& !registWindow.tfCategory.getText().isEmpty()) {
							name = registWindow.tfName.getText();
							if (name.equals("")) {
								name = "미지정";
							}
							size = registWindow.tfSize.getText();
							if (size.equals("")) {
								size = "미지정";
							}
							color = registWindow.tfColor.getText();
							if (color.equals("")) {
								color = "미지정";
							}
							if (!registWindow.tfCategory.getText().isEmpty()) {
								if (Integer.valueOf(registWindow.tfCategory.getText()) == 1) {
									category = "top";
								} else if (Integer.valueOf(registWindow.tfCategory.getText()) == 2) {
									category = "bottom";
								} else if (Integer.valueOf(registWindow.tfCategory.getText()) == 3) {
									category = "bag";
								} else if (Integer.valueOf(registWindow.tfCategory.getText()) == 4) {
									category = "shoes";
								} else if (Integer.valueOf(registWindow.tfCategory.getText()) == 5) {
									category = "acc";
								} else {
									category = "임의분류";
								}
							}
							subCategory = registWindow.tfSubCategory.getText();
							if (subCategory.equals("")) {
								subCategory = "미지정";
							}
							imageUrl = registWindow.tfImageUrl.getText();
							if (imageUrl.equals("")) {
								imageUrl = "미지정";
							}
							season = registWindow.tfSeason.getText();
							if (season.equals("")) {
								season = "미지정";
							}
							file = new File(imageUrl);
//						System.out.println("auto_increment 값" + dao.findIncrement());
							dao.alterIncrement(dao.findIncrement() + 1);
							dao.create(name, size, color, category, subCategory, imageUrl, file, season);
							// 체크박스 선택할때 숫자
							count++;
							registWindow.dispose();
							JOptionPane.showMessageDialog(Management.this, "등록되었습니다.");
						} else {
							JOptionPane.showMessageDialog(registWindow, "★표시된 값은 꼭 입력해주세요.(이미지는 불러오기만 하면됩니다.)");
						}

					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "1~5의 숫자만 입력해주세요.");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});

			editWindow.btnRegist.addActionListener(new ActionListener() {
				int number;
				String name;
				String size;
				String color;
				String category;
				String subCategory;
				String season;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (!editWindow.tfName.getText().isEmpty() && !editWindow.lbltfImage.getText().isEmpty()
								&& !editWindow.tfCategory.getText().isEmpty()) {
							number = dao.read(itemList.get(0).getId()).getId();
							name = editWindow.tfName.getText();
							if (name.equals("")) {
								name = "미지정";
							}
							size = editWindow.tfSize.getText();
							if (size.equals("")) {
								size = "미지정";
							}
							color = editWindow.tfColor.getText();
							if (color.equals("")) {
								color = "미지정";
							}
							if (!editWindow.tfCategory.getText().isEmpty()) {
								if (Integer.valueOf(editWindow.tfCategory.getText()) == 1) {
									category = "top";
								} else if (Integer.valueOf(editWindow.tfCategory.getText()) == 2) {
									category = "bottom";
								} else if (Integer.valueOf(editWindow.tfCategory.getText()) == 3) {
									category = "bag";
								} else if (Integer.valueOf(editWindow.tfCategory.getText()) == 4) {
									category = "shoes";
								} else if (Integer.valueOf(editWindow.tfCategory.getText()) == 5) {
									category = "acc";
								} else {
									category = "임의분류";
								}
							}
							subCategory = editWindow.tfSubCategory.getText();
							if (subCategory.equals("")) {
								subCategory = "미지정";
							}
							season = editWindow.tfSeason.getText();
							if (season.equals("")) {
								season = "미지정";
							}
							dao.update(number, name, size, color, category, subCategory, season);
							editWindow.dispose();
//							Management.this.dispose();
//							new Management().setVisible(true);
							JOptionPane.showMessageDialog(Management.this, "수정되었습니다.");
						} else {
							JOptionPane.showMessageDialog(registWindow, "★표시된 값은 꼭 입력해주세요.(이미지는 불러오기만 하면됩니다.)");
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "1~5의 숫자만 입력해주세요.");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});

			// -------------------------------------
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pnlMain.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		pnlMain.add(pnlData);
		pnlData.setLayout(new BoxLayout(pnlData, BoxLayout.Y_AXIS));

		pnlBtn.setLayout(new GridLayout(0, 3, 0, 0));
		JScrollPane scrollPnl = new JScrollPane(pnlMain);
		scrollPnl.setBackground(Color.WHITE);

		getContentPane().add(scrollPnl);

		setSize(850, 850);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		////////////////////////// 창 열고 닫을 때, 버튼 활성<->비활성 체크
		////////////////////////////////////////////////////////

		// 등록버튼 : 등록 창 띄우기
		ActionListener regiAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registWindow.setVisible(true);
				if (registWindow.isVisible()) {
					btConfirm.setEnabled(false);
					btEdit.setEnabled(false);
				}
			}
		};

		// 수정버튼 : 수정 창 띄우기, 기본 정보 입력해주기위한 아이템리스트에 체크한것 담기
		ActionListener editAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editWindow.setVisible(true);
				itemList.clear();
				try {
					// itemList에 체크박스 선택한 객체 담기.
					System.out.println(
							"itemList에 체크박스 선택한 객체 담기." + dao.read(intList.get(checkedList.get(0))).toString());

					itemList.add(dao.read(intList.get(checkedList.get(0))));
//					System.out.println("아이템리스트 전체보기 " + itemList);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (editWindow.isVisible()) {
					btEdit.setEnabled(false);
					btConfirm.setEnabled(false);
				}

			}
		};
		// ---------------------------------

		btConfirm.addActionListener(regiAction);
		btEdit.addActionListener(editAction);
		//////////////////////////////////

		// 삭제 버튼 수정필수 *************************************
		ActionListener deleteAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for (int j = 0; j < checkedList.size(); j++) {
						System.out.println("상품번호 : " + (intList.get(checkedList.get(j))) + "의 데이터를 지움");
						dao.delete(intList.get(checkedList.get(j)));
					}
					// 너무 느려서 새로고침 없앰.
//					Management.this.dispose();
//					new Management().setVisible(true);
					JOptionPane.showMessageDialog(Management.this, "삭제되었습니다. 확인 → 새로고침.");
				} catch (SQLIntegrityConstraintViolationException e2) {
					JOptionPane.showMessageDialog(Management.this, "다른 곳에서 사용중인 옷입니다. 참조를 없애고 다시 시도해주세요.");
//					System.out.println("참조 중인 아이템");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		};
		btDelete.addActionListener(deleteAction);
		// ------------------------------------------------
		scrollPnl.getVerticalScrollBar().setUnitIncrement(20);
		Management.this.setResizable(false);
		registWindow.setResizable(false);
		editWindow.setResizable(false);

	}

	// 버튼 모양 세팅
	public void settingBtn(JButton btn) {
//		btn.setBounds(102, 325, 69, 28);
		btn.setPreferredSize(new Dimension(45, 32));
		btn.setContentAreaFilled(false);// 버튼 안 색 채우기 안함
		btn.setBorderPainted(false);// 버튼 외각선 안 보이게
		btn.setFocusPainted(false);// 버튼 눌렀을 때 외곽선 안 보이게
	}

	public static void main(String[] args) {
		Management m = new Management();
		m.setVisible(true);
	}
}
