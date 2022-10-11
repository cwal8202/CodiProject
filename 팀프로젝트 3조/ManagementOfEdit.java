import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ManagementOfEdit extends JFrame {
	protected JTextField tfSize;
	protected JTextField tfName;
	protected JTextField tfColor;
	protected JTextField tfCategory;
	protected JLabel lbltfImage;
	protected JTextField tfSeason;
	protected AdminDaoImpl dao = new AdminDaoImpl();
	protected JTextField tfSubCategory;
	protected JButton btnRegist;
	protected String path;

	ManagementOfEdit() {
		super("관리자용 데이터 수정");
		JPanel pnlMain = new JPanel();

		getContentPane().add(pnlMain);
		pnlMain.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(290, 10, 230, 40);
		pnlMain.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblRegiEdit = new JLabel("의상 등록 & 수정");
		panel.add(lblRegiEdit, BorderLayout.CENTER);
		lblRegiEdit.setFont(new Font("휴먼편지체", Font.PLAIN, 30));
		lblRegiEdit.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel pnlLoadBtn = new JPanel();
		pnlLoadBtn.setBounds(183, 637, 230, 35);
		pnlMain.add(pnlLoadBtn);

		JButton btLoad = new JButton("이미지 불러오기");
		pnlLoadBtn.add(btLoad);

		JPanel pnlImage = new JPanel();
		pnlImage.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		pnlImage.setBounds(12, 60, 401, 567);
		pnlMain.add(pnlImage);
		pnlImage.setLayout(new BorderLayout(0, 0));

		JLabel lblImageDisplay = new JLabel("");
		lblImageDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		pnlImage.add(lblImageDisplay, BorderLayout.NORTH);

		JPanel pnlRegiEditArea = new JPanel();
		pnlRegiEditArea.setBounds(425, 356, 397, 271);
		pnlMain.add(pnlRegiEditArea);

		JLabel lblName = new JLabel("★Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("궁서체", Font.ITALIC, 24));

		tfName = new JTextField();

		JLabel lblSize = new JLabel("Size");
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setFont(new Font("궁서체", Font.ITALIC, 24));

		tfSize = new JTextField();

		JLabel lblColor = new JLabel("Color");
		lblColor.setHorizontalAlignment(SwingConstants.CENTER);
		lblColor.setFont(new Font("궁서체", Font.ITALIC, 24));

		tfColor = new JTextField();

		JLabel lblCategory = new JLabel("★Category");
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategory.setFont(new Font("궁서체", Font.ITALIC, 24));

		tfCategory = new JTextField();

		JLabel lblImage = new JLabel("★Image");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setFont(new Font("궁서체", Font.ITALIC, 24));

		lbltfImage = new JLabel();

		JLabel lblSeason = new JLabel("Season");
		lblSeason.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeason.setFont(new Font("궁서체", Font.ITALIC, 24));

		tfSeason = new JTextField();

		btnRegist = new JButton("Regist");
		btnRegist.setFont(new Font("궁서체", Font.ITALIC, 20));
		pnlRegiEditArea.setLayout(new GridLayout(0, 2, 0, 3));
		pnlRegiEditArea.add(lblName);
		pnlRegiEditArea.add(tfName);
		pnlRegiEditArea.add(lblSize);
		pnlRegiEditArea.add(tfSize);
		pnlRegiEditArea.add(lblColor);
		pnlRegiEditArea.add(tfColor);
		pnlRegiEditArea.add(lblCategory);
		pnlRegiEditArea.add(tfCategory);

		JLabel lblSubCategory = new JLabel("SubCategory");
		lblSubCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubCategory.setFont(new Font("궁서체", Font.ITALIC, 24));
		pnlRegiEditArea.add(lblSubCategory);

		tfSubCategory = new JTextField();
		pnlRegiEditArea.add(tfSubCategory);
		tfSubCategory.setColumns(10);
		pnlRegiEditArea.add(lblImage);
		pnlRegiEditArea.add(lbltfImage);
		pnlRegiEditArea.add(lblSeason);
		pnlRegiEditArea.add(tfSeason);
		// 공백 라벨
		JLabel lblBlank = new JLabel("");
		pnlRegiEditArea.add(lblBlank);
		pnlRegiEditArea.add(btnRegist);

		JPanel pnlHelp = new JPanel();
		pnlHelp.setBounds(425, 60, 397, 286);
		pnlMain.add(pnlHelp);
		pnlHelp.setLayout(new GridLayout(8, 0, 0, 0));

		JLabel lblHelp = new JLabel("사용 설명서");
		lblHelp.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelp);

		JLabel lblHelpName = new JLabel("★ Name : 상품명");
		lblHelpName.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpName);

		JLabel lblHelpSize = new JLabel("Size : 상품 사이즈");
		lblHelpSize.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpSize);

		JLabel lblHelpColor = new JLabel("Color : 상품 색상");
		lblHelpColor.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpColor);

		JLabel lblHelpCategory = new JLabel("★Category : 분류 숫자만 입력(1.상의, 2.하의, 3.가방, 4.신발, 5.악세)");
		lblHelpCategory.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpCategory);

		JLabel lblNewLabel_1 = new JLabel("SubCategory : 상품 상세 분류(셔츠, 반팔, 등등 입력)");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblNewLabel_1);

		JLabel lblHelpImage = new JLabel("★ Image : 상품 이미지경로 (자동으로 설정 됩니다.)");
		lblHelpImage.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpImage);

		JLabel lblHelpSeason = new JLabel("Season : 상품 시즌");
		lblHelpSeason.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpSeason);
		setSize(850, 780);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

//////////////////////////////////////////////////////////////////////////////////
		// 처음 창이 열리면 기본 정보가 자동으로 입력되게 만들기. windowListener를 사용할 듯함.
		WindowListener makeInfoToggle = new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// 선택한 객체 읽어오기.
//				dao.read(name);
				int number = Management.itemList.get(0).getId();
				Item loadItem;
				try {
					Blob imgUrl = dao.read(number).getImageUrl();
					InputStream in = imgUrl.getBinaryStream();
					BufferedImage blobImage = ImageIO.read(in);
					Image img = blobImage;
					ImageIcon convertedImage = new ImageIcon(img);
					loadItem = dao.read(number);
					tfName.setText(loadItem.getName());
					tfSize.setText(loadItem.getSize());
					tfColor.setText(loadItem.getColor());
					int numCategory = 0;
					if (loadItem.getCategory() != null) {
						if (loadItem.getCategory().equals("top")) {
							numCategory = 1;
							tfCategory.setText(String.valueOf(numCategory));
						} else if (loadItem.getCategory().equals("bottom")) {
							numCategory = 2;
							tfCategory.setText(String.valueOf(numCategory));
						} else if (loadItem.getCategory().equals("bag")) {
							numCategory = 3;
							tfCategory.setText(String.valueOf(numCategory));
						} else if (loadItem.getCategory().equals("shoes")) {
							numCategory = 4;
							tfCategory.setText(String.valueOf(numCategory));
						} else if (loadItem.getCategory().equals("acc")) {
							numCategory = 5;
							tfCategory.setText(String.valueOf(numCategory));
						}
					} else {
						tfCategory.setText("");
					}
					tfSubCategory.setText(loadItem.getSubCategory());
					lbltfImage.setText("DB에 등록된 이미지");
					tfSeason.setText(loadItem.getSeason());
					lblImageDisplay.setIcon(scaleImage(convertedImage));
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
//		};

			// 화면을 닫을 때, 표시한것 모두 초기화
			@Override
			public void windowClosed(WindowEvent e) {
				tfName.setText("");
				tfSize.setText("");
				tfColor.setText("");
				tfCategory.setText("");
				tfSubCategory.setText("");
				lbltfImage.setText("");
				tfSeason.setText("");
				lblImageDisplay.setIcon(null);
			}
		};
		this.addWindowListener(makeInfoToggle);

		// 사진 수정하려면 불러와야됨.
		JFileChooser chooser = new JFileChooser();

		btLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showOpenDialog(pnlMain);

				if (result == JFileChooser.APPROVE_OPTION) {
					path = chooser.getSelectedFile().getAbsolutePath();
					ImageIcon image = new ImageIcon(path);
					lblImageDisplay.setIcon(scaleImage(image));
					lbltfImage.setText(path);
					File imageBlob = new File(path);
					try {
						dao.updateImage(dao.read(Management.itemList.get(0).getId()).getId(), imageBlob);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

//		// Regist버튼 => 수정값으로 DB의 데이터 바꾸기.
//		btnRegist.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
	}

	// 사진 사이즈 조절
	public ImageIcon scaleImage(ImageIcon icon) {
		return new ImageIcon(
				icon.getImage().getScaledInstance((int) (401 / 1.2), (int) (567 / 1.2), Image.SCALE_DEFAULT));
	}
}
