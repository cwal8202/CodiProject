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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ManagementOfRegist extends JFrame {
	protected JTextField tfName;
	protected JTextField tfSize;
	protected JTextField tfColor;
	protected JTextField tfCategory;
	protected JTextField tfSubCategory;
	protected JTextField tfImageUrl;
	protected JTextField tfSeason;
	protected AdminDaoImpl dao = new AdminDaoImpl();
	protected JButton btnRegist;

	ManagementOfRegist() {
		super("관리자용 데이터 등록");
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
		pnlImage.add(lblImageDisplay);

		JPanel pnlRegiEditArea = new JPanel();
		pnlRegiEditArea.setBounds(425, 356, 397, 271);
		pnlMain.add(pnlRegiEditArea);

		JLabel lblName = new JLabel("★Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("궁서체", Font.ITALIC, 24));

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

		JLabel lblSeason = new JLabel("Season");
		lblSeason.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeason.setFont(new Font("궁서체", Font.ITALIC, 24));

		btnRegist = new JButton("Regist");
		btnRegist.setFont(new Font("궁서체", Font.ITALIC, 20));
		pnlRegiEditArea.setLayout(new GridLayout(0, 2, 0, 3));
		pnlRegiEditArea.add(lblName);

		tfName = new JTextField();
		pnlRegiEditArea.add(tfName);

		JLabel lblSize = new JLabel("Size");
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setFont(new Font("궁서체", Font.ITALIC, 24));
		pnlRegiEditArea.add(lblSize);
		pnlRegiEditArea.add(tfSize);
		pnlRegiEditArea.add(lblColor);
		pnlRegiEditArea.add(tfColor);
		pnlRegiEditArea.add(lblCategory);
		pnlRegiEditArea.add(tfCategory);

		JLabel lblNewLabel = new JLabel("SubCategory");
		lblNewLabel.setFont(new Font("궁서체", Font.ITALIC, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pnlRegiEditArea.add(lblNewLabel);

		tfSubCategory = new JTextField();
		pnlRegiEditArea.add(tfSubCategory);
		pnlRegiEditArea.add(lblImage);

		tfImageUrl = new JTextField();
		pnlRegiEditArea.add(tfImageUrl);
		pnlRegiEditArea.add(lblSeason);

		tfSeason = new JTextField();
		pnlRegiEditArea.add(tfSeason);
		tfSeason.setColumns(10);
		// 공백 라벨
		JLabel lblblock = new JLabel("");
		pnlRegiEditArea.add(lblblock);
		pnlRegiEditArea.add(btnRegist);

		JPanel pnlHelp = new JPanel();
		pnlHelp.setBounds(425, 60, 397, 286);
		pnlMain.add(pnlHelp);
		pnlHelp.setLayout(new GridLayout(8, 0, 0, 0));

		JLabel lblHelp = new JLabel("사용 설명서 - '★'는 필수 입력 ");
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

		JLabel lblHelpImage = new JLabel("★ Image : 상품 이미지경로(자동으로 설정됩니다.)");
		lblHelpImage.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpImage);

		JLabel lblHelpSeason = new JLabel("Season : 상품 시즌");
		lblHelpSeason.setHorizontalAlignment(SwingConstants.CENTER);
		pnlHelp.add(lblHelpSeason);
		setSize(850, 780);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//////////////////////////// 프레임 끝 ///////////////////////////////////
		// -----------------------------------------------------------------//
		// 등록창
		// 사진 불러오기 버튼 -------- 파일불러오는 파일선택창
		JFileChooser chooser = new JFileChooser();

		btLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showOpenDialog(pnlMain);

				if (result == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					ImageIcon image = new ImageIcon(path);
					lblImageDisplay.setIcon(scaleImage(image));
					tfImageUrl.setText(path);
				}
			}
		});

		// 화면을 껏다키면 아까 썼던 글이나 사진을 원상태로 돌리기.
		WindowListener resetAll = new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				tfName.setText("");
				tfSize.setText("");
				tfColor.setText("");
				tfCategory.setText("");
				tfSubCategory.setText("");
				tfImageUrl.setText("");
				tfSeason.setText("");
				lblImageDisplay.setIcon(null);
			}
		};
		this.addWindowListener(resetAll);
	}

	// 사진 사이즈 조절
	public ImageIcon scaleImage(ImageIcon icon) {

		return new ImageIcon(
				icon.getImage().getScaledInstance((int) (401 / 1.2), (int) (567 / 1.2), Image.SCALE_DEFAULT));
	}
}
