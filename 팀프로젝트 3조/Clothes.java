
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;


import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

class Edit extends Clothes {
	public Edit(String user_id, int id) throws SQLException, IOException {
		super(user_id);
		super.setVisible(true);

		JCheckBox[] codiBox;
		JPanel cart = super.getCart();
		JTabbedPane editPane = super.getTabbedPane();
		ItemListener checkedTop = super.getCheckedTop();
		ItemListener checkedBottom = super.getCheckedBottom();
		ItemListener checkedBag = super.getCheckedBag();
		ItemListener checkedShoes = super.getCheckedShoes();
		ItemListener checkedAcc = super.getCheckedAcc();
		JComboBox comboBox = super.getComboBox();

		JPanel codi = new JPanel();
		codi.setBounds(245, 10, 227, 441);
		codi.setBackground(Color.white);
		cart.setLayout(null);

		ManagementDaoImpl dao = new ManagementDaoImpl();
		for (int i = 0; i < dao.readFromCodi(user_id, id).size(); i++) {
			Blob imageBlob = dao.readFromCodi(user_id, id).get(i).getImageUrl();
			InputStream in = imageBlob.getBinaryStream();
			BufferedImage bimg = ImageIO.read(in);
			Image blobImage = bimg;
			ImageIcon convertedImage = new ImageIcon(blobImage.getScaledInstance(150, 150, Image.SCALE_DEFAULT));

			codiBox = new JCheckBox[i + 1];
			codiBox[i] = new JCheckBox(convertedImage);
			codiBox[i].setName(dao.readFromCodi(user_id, id).get(i).getName());
			codiBox[i].setBackground(Color.white);
			codi.add(codiBox[i]);
			codi.setPreferredSize(new Dimension(400, 0));

			codi.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
			switch (dao.readFromCodi(user_id, id).get(i).getCategory()) {
			case "top":
				codiBox[i].addItemListener(checkedTop);
				break;
			case "bottom":
				codiBox[i].addItemListener(checkedBottom);
				break;
			case "bag":
				codiBox[i].addItemListener(checkedBag);
				break;
			case "shoes":
				codiBox[i].addItemListener(checkedShoes);
				break;
			case "acc":
				codiBox[i].addItemListener(checkedAcc);
				break;
			}
		}
		JScrollPane codiScroll = new JScrollPane(codi, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		editPane.add("??? ??????", codiScroll);
		codiScroll.getVerticalScrollBar().setUnitIncrement(20);

		CodiSetDaoImpl codiDao = new CodiSetDaoImpl();
		comboBox.setSelectedItem(codiDao.readCodi(user_id, id).getTag_theme());
		JLabel lblTop = super.getLblTop();
		JLabel lblBottom = super.getLblBottom();
		JLabel lblBag = super.getLblBag();
		JLabel lblShoes = super.getLblShoes();
		JLabel lblAcc1 = super.getLblAcc1();
		JLabel lblAcc2 = super.getLblAcc2();
		JLabel lblAcc3 = super.getLblAcc3();

		for (int i = 0; i < dao.readFromCodi(user_id, id).size(); i++) {
			Blob imageBlob = dao.readFromCodi(user_id, id).get(i).getImageUrl();
			InputStream in = imageBlob.getBinaryStream();
			BufferedImage bimg = ImageIO.read(in);
			Image blobImage = bimg;
			ImageIcon convertedImage;
			if (dao.readFromCodi(user_id, id).get(i).getName().contains("??????")) {
				if (!dao.readFromCodi(user_id, id).get(i).getSubCategory().contains("?????????")) {
					convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
					lblTop.setBounds(60, 100, 200, 200);
				} else {
					convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 400, Image.SCALE_DEFAULT));
					lblTop.setBounds(60, 100, 200, 400);
				}

				lblTop.setIcon(convertedImage);
				lblTop.setText(dao.readFromCodi(user_id, id).get(i).getName());
			} else if (dao.readFromCodi(user_id, id).get(i).getName().contains("??????")) {
				if (!dao.readFromCodi(user_id, id).get(i).getSubCategory().contains("???")) {
					convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
					lblTop.setBounds(60, 100, 200, 200);
				} else {
					convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 350, Image.SCALE_DEFAULT));
					lblTop.setBounds(60, 100, 200, 350);
				}

				convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
				lblBottom.setIcon(convertedImage);
				lblBottom.setText(dao.readFromCodi(user_id, id).get(i).getName());
			} else if (dao.readFromCodi(user_id, id).get(i).getName().contains("??????")) {
				convertedImage = new ImageIcon(blobImage.getScaledInstance(130, 130, Image.SCALE_DEFAULT));
				lblBag.setIcon(convertedImage);
				lblBag.setText(dao.readFromCodi(user_id, id).get(i).getName());
			} else if (dao.readFromCodi(user_id, id).get(i).getName().contains("??????")) {
				convertedImage = new ImageIcon(blobImage.getScaledInstance(120, 120, Image.SCALE_DEFAULT));
				lblShoes.setIcon(convertedImage);
				lblShoes.setText(dao.readFromCodi(user_id, id).get(i).getName());
			} else if (dao.readFromCodi(user_id, id).get(i).getName().contains("??????")) {
				convertedImage = new ImageIcon(blobImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				if (lblAcc1.getIcon() == null) {
					lblAcc1.setIcon(convertedImage);
					lblAcc1.setText(dao.readFromCodi(user_id, id).get(i).getName());
				} else if (lblAcc2.getIcon() == null) {
					lblAcc2.setIcon(convertedImage);
					lblAcc2.setText(dao.readFromCodi(user_id, id).get(i).getName());
				} else {
					lblAcc3.setIcon(convertedImage);
					lblAcc3.setText(dao.readFromCodi(user_id, id).get(i).getName());
				}
			}
		}

	}
}

public class Clothes extends JFrame {
	private JPanel pnl;
	private JLabel lblTop = new JLabel();
	private JLabel lblBottom = new JLabel();
	private JLabel lblBag = new JLabel();
	private JLabel lblShoes = new JLabel();
	private JLabel lblAcc1 = new JLabel();
	private JLabel lblAcc2 = new JLabel();
	private JLabel lblAcc3 = new JLabel();
	private ItemListener checkedTop = new checkedTop();
	private ItemListener checkedBottom = new checkedBottom();
	private ItemListener checkedBag = new checkedBag();
	private ItemListener checkedShoes = new checkedShoes();
	private ItemListener checkedAcc = new checkedAcc();
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private Tag_ThemeDao tagDao = new Tag_ThemeDao();
	private JComboBox comboBox = new JComboBox(tagDao.readTag().toArray(new String[tagDao.readTag().size()]));
	private JPanel cart = new JPanel();
	JCheckBox[] topBox;
	JCheckBox[] bottomBox;
	JCheckBox[] bagBox;
	JCheckBox[] shoesBox;
	JCheckBox[] accBox;
	JCheckBox[] cartBox = null;

	private static JLabel lblCodiSet;

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public JLabel getLblTop() {
		return lblTop;
	}

	public void setLblTop(JLabel lblTop) {
		this.lblTop = lblTop;
	}

	public JLabel getLblBottom() {
		return lblBottom;
	}

	public void setLblBottom(JLabel lblBottom) {
		this.lblBottom = lblBottom;
	}

	public JLabel getLblBag() {
		return lblBag;
	}

	public void setLblBag(JLabel lblBag) {
		this.lblBag = lblBag;
	}

	public JLabel getLblShoes() {
		return lblShoes;
	}

	public void setLblShoes(JLabel lblShoes) {
		this.lblShoes = lblShoes;
	}

	public JLabel getLblAcc1() {
		return lblAcc1;
	}

	public void setLblAcc1(JLabel lblAcc1) {
		this.lblAcc1 = lblAcc1;
	}

	public JLabel getLblAcc2() {
		return lblAcc2;
	}

	public void setLblAcc2(JLabel lblAcc2) {
		this.lblAcc2 = lblAcc2;
	}

	public JLabel getLblAcc3() {
		return lblAcc3;
	}

	public void setLblAcc3(JLabel lblAcc3) {
		this.lblAcc3 = lblAcc3;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public ItemListener getCheckedTop() {
		return checkedTop;
	}

	public void setCheckedTop(ItemListener checkedTop) {
		this.checkedTop = checkedTop;
	}

	public ItemListener getCheckedBottom() {
		return checkedBottom;
	}

	public void setCheckedBottom(ItemListener checkedBottom) {
		this.checkedBottom = checkedBottom;
	}

	public ItemListener getCheckedBag() {
		return checkedBag;
	}

	public void setCheckedBag(ItemListener checkedBag) {
		this.checkedBag = checkedBag;
	}

	public ItemListener getCheckedShoes() {
		return checkedShoes;
	}

	public void setCheckedShoes(ItemListener checkedShoes) {
		this.checkedShoes = checkedShoes;
	}

	public ItemListener getCheckedAcc() {
		return checkedAcc;
	}

	public void setCheckedAcc(ItemListener checkedAcc) {
		this.checkedAcc = checkedAcc;
	}

	public JPanel getCart() {
		return cart;
	}

	public void setCart(JPanel cart) {
		this.cart = cart;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	// ?????? ??? ?????????
	public Clothes(String user_id) throws SQLException {

		pnl = new JPanel();
		pnl.setBackground(Color.WHITE);

		JPanel codiSet = new JPanel();
		codiSet.setBackground(Color.WHITE);
		codiSet.setForeground(new Color(0, 0, 0));
		codiSet.setBounds(12, 26, 400, 600);
		codiSet.setLayout(null);
		lblCodiSet = new JLabel();
		lblCodiSet.setBackground(Color.WHITE);
		lblCodiSet.setBounds(0, 0, 400, 600);
		lblCodiSet.setForeground(new Color(220, 220, 220));

		codiSet.add(lblCodiSet);
		codiSet.setBorder(UIManager.getBorder("ProgressBar.border"));
		
		pnl.add(codiSet);
		lblBag.setBackground(Color.WHITE);
		lblCodiSet.add(lblBag);

		lblBag.setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
		lblBag.setForeground(new Color(0, 0, 0, 0));
		lblClick(lblBag);
		lblBag.setBounds(10, 190, 130, 130);
		lblClick(lblTop);
		lblTop.setBackground(Color.WHITE);
		lblCodiSet.add(lblTop);
		lblTop.setBounds(60, 100, 200, 200);
		lblTop.setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
		lblTop.setForeground(new Color(0, 0, 0, 0));
		lblClick(lblBottom);
		lblBottom.setBackground(Color.WHITE);
		lblCodiSet.add(lblBottom);
		lblBottom.setBounds(60, 280, 200, 200);
		lblBottom.setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
		lblBottom.setForeground(new Color(0, 0, 0, 0));
		lblClick(lblShoes);
		lblShoes.setBackground(Color.WHITE);
		lblCodiSet.add(lblShoes);
		lblShoes.setBounds(270, 310, 120, 120);
		lblShoes.setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
		lblShoes.setForeground(new Color(0, 0, 0, 0));
		lblAcc1.setBackground(Color.WHITE);
		lblCodiSet.add(lblAcc1);

		lblAcc1.setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
		lblAcc1.setForeground(new Color(0, 0, 0, 0));
		lblClick(lblAcc1);
		lblAcc1.setBounds(270, 30, 100, 100);
		lblAcc2.setBackground(Color.WHITE);
		lblCodiSet.add(lblAcc2);

		lblAcc2.setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
		lblAcc2.setForeground(new Color(0, 0, 0, 0));
		lblClick(lblAcc2);
		lblAcc2.setBounds(270, 140, 100, 100);
		lblAcc3.setBackground(Color.WHITE);
		lblCodiSet.add(lblAcc3);

		lblAcc3.setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
		lblAcc3.setForeground(new Color(0, 0, 0, 0));
		lblClick(lblAcc3);
		lblAcc3.setBounds(220, 450, 100, 100);
		// ?????? ??? ??????
		JPanel top = new JPanel();
		top.setBackground(Color.WHITE);
		top.setBounds(245, 10, 400, 600);

		// ?????? ??? ??????
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.WHITE);
		bottom.setBounds(245, 10, 400, 600);

		// ?????? ??? ??????
		JPanel shoes = new JPanel();
		shoes.setBackground(Color.WHITE);
		shoes.setBounds(245, 10, 400, 600);

		// ?????? ??? ??????
		JPanel bag = new JPanel();
		bag.setBackground(Color.WHITE);
		bag.setBounds(245, 10, 227, 441);

		// ???????????? ??? ??????
		JPanel acc = new JPanel();
		acc.setBackground(Color.WHITE);
		acc.setBounds(245, 10, 227, 441);
		cart.setBackground(Color.WHITE);

		// ???????????? ??? ??????

		cart.setBounds(245, 10, 227, 441);

		// ????????? ???????????? + blob??? ????????? imageicon?????? ???????????? gui??? ??????
		ImageIcon convertedImage = null;
//		JCheckBox[] topBox;
//		JCheckBox[] bottomBox;
//		JCheckBox[] bagBox;
//		JCheckBox[] shoesBox;
//		JCheckBox[] accBox;
//		JCheckBox[] cartBox;

		String[] kind = { "top", "bottom", "bag", "shoes", "acc" };

		try {
			ManagementDaoImpl dao = new ManagementDaoImpl();
			for (int j = 0; j < kind.length; j++) {
				System.out.println("??? ?????? : " + kind[j]);
				for (int i = 0; i < dao.readByKind(kind[j]).size(); i++) {
					Blob imageBlob = dao.readByKind(kind[j]).get(i).getImageUrl();
					InputStream in = imageBlob.getBinaryStream();
					BufferedImage bimg = ImageIO.read(in);
					Image blobImage = bimg;
					convertedImage = new ImageIcon(blobImage.getScaledInstance(150, 150, Image.SCALE_DEFAULT));

//					convertedImage = scaleImage(convertedImage, 150, 150);

					// ????????? ??????????????? ????????????!!!!
					if (kind[j].equals("top")) {
						topBox = new JCheckBox[i + 1];
						topBox[i] = new JCheckBox(convertedImage);
						topBox[i].setName(dao.readByKind("top").get(i).getName());
//						topBox[i].setAlignmentX(CENTER_ALIGNMENT);
						topBox[i].setIcon(convertedImage);
						topBox[i].setBackground(Color.WHITE);
//						topBox[i].setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
//						topBox[i].setHorizontalTextPosition(0); // ????????? ????????? ????????? ????????????
//						topBox[i].setForeground(new Color(0, 0, 0, 0)); // ?????? ???????????? ??????
//						if(i % 2 == 0) {
//							System.out.println("??????" + i);
//							topL.add(topBox[i]);
//						} else if (i % 2 == 1) {
//							System.out.println("?????????" + i);
//							topR.add(topBox[i]);
//						}

						top.add(topBox[i]);
						topBox[i].addItemListener(checkedTop);
						top.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
					} else if (kind[j].equals("bottom")) {
						bottomBox = new JCheckBox[i + 1];
						bottomBox[i] = new JCheckBox(convertedImage);
						bottomBox[i].setIcon(convertedImage);
						bottomBox[i].setName(dao.readByKind("bottom").get(i).getName());
						bottomBox[i].setBackground(Color.WHITE);

//						if(i % 2 == 0) {
//							System.out.println("??????" + i);
//							bottomL.add(bottomBox[i]);
//						} else if (i % 2 == 1) {
//							System.out.println("?????????" + i);
//							bottomR.add(bottomBox[i]);
//						}
						bottom.add(bottomBox[i]);
						bottomBox[i].addItemListener(checkedBottom);
						bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
					} else if (kind[j].equals("bag")) {
						bagBox = new JCheckBox[i + 1];
						bagBox[i] = new JCheckBox(convertedImage);
						bagBox[i].setName(dao.readByKind("bag").get(i).getName());
						bagBox[i].setBackground(Color.WHITE);
						bag.add(bagBox[i]);

						bag.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
						bagBox[i].addItemListener(checkedBag);
					} else if (kind[j].equals("shoes")) {
						shoesBox = new JCheckBox[i + 1];
						shoesBox[i] = new JCheckBox(convertedImage);
						shoesBox[i].setName(dao.readByKind("shoes").get(i).getName());
						shoesBox[i].setBackground(Color.WHITE);
						shoes.add(shoesBox[i]);

						shoes.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
						shoesBox[i].addItemListener(checkedShoes);
					} else if (kind[j].equals("acc")) {
						accBox = new JCheckBox[i + 1];
						accBox[i] = new JCheckBox(convertedImage);
						accBox[i].setName(dao.readByKind("acc").get(i).getName());
						accBox[i].setBackground(Color.WHITE);
						acc.add(accBox[i]);

						acc.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
						accBox[i].addItemListener(checkedAcc);
					}

				}

			}

			top.setPreferredSize(new Dimension(400, ((int) (topBox.length + 1) / 2 * 170)));
			bottom.setPreferredSize(new Dimension(400, ((int) (bottomBox.length + 1) / 2 * 170)));
			bag.setPreferredSize(new Dimension(400, ((int) (accBox.length + 1) / 2 * 170)));
			shoes.setPreferredSize(new Dimension(400, ((int) (shoesBox.length + 1) / 2 * 170)));
			acc.setPreferredSize(new Dimension(400, ((int) (accBox.length + 1) / 2 * 170)));

			for (int i = 0; i < dao.readFromCart(user_id).size(); i++) {
				Blob imageBlob = dao.readFromCart(user_id).get(i).getImageUrl();
				InputStream in = imageBlob.getBinaryStream();
				BufferedImage bimg = ImageIO.read(in);
				Image blobImage = bimg;
				convertedImage = new ImageIcon(blobImage.getScaledInstance(150, 150, Image.SCALE_DEFAULT));

				cartBox = new JCheckBox[i + 1];
//				for(int j = 0; j < topBox.length; j++) {
//					 System.out.println(convertedImage);
//					if(convertedImage == topBox[j].getIcon()) {
//						cartBox[i] = topBox[j];
//					} 
//				} 
//				for(int j = 0; j < bottomBox.length; j++) {
//					if(convertedImage == bottomBox[j].getIcon()) {
//						cartBox[i] = bottomBox[j];
//					} 
//				} 
//				for(int j = 0; j < bagBox.length; j++) {
//					if(convertedImage == bagBox[j].getIcon()) {
//						cartBox[i] = bagBox[j];
//					} 
//				} 
//				for(int j = 0; j < accBox.length; j++) {
//					if(convertedImage == accBox[j].getIcon()) {
//						cartBox[i] = accBox[j];
//					} 
//				} 

				cartBox[i] = new JCheckBox(convertedImage);
				cartBox[i].setName(dao.readFromCart(user_id).get(i).getName());
				cartBox[i].setBackground(Color.white);
				cart.add(cartBox[i]);

				cart.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
				switch (dao.readFromCart(user_id).get(i).getCategory()) {
				case "top":
					cartBox[i].addItemListener(checkedTop);
					break;
				case "bottom":
					cartBox[i].addItemListener(checkedBottom);
					break;
				case "bag":
					cartBox[i].addItemListener(checkedBag);
					break;
				case "shoes":
					cartBox[i].addItemListener(checkedShoes);
					break;
				case "acc":
					cartBox[i].addItemListener(checkedAcc);
					break;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		cart.setPreferredSize(new Dimension(400, ((int) (cartBox.length + 1) / 2 * 170)));
		if(cartBox == null) {
	         cart.setPreferredSize(new Dimension(400, 170));
	      } else if(cartBox.length != 0) {
	         cart.setPreferredSize(new Dimension(400, ((int) (cartBox.length + 1) / 2 * 170)));
	      }
		
		// ??? ?????????

		Toolkit kit = Toolkit.getDefaultToolkit();
		URL url = Clothes.class.getClassLoader().getResource("dance/shirt.png");
		ImageIcon topImage = new ImageIcon(kit.getImage(url).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		URL url2 = Clothes.class.getClassLoader().getResource("dance/skirt.png");
		ImageIcon bottomImage = new ImageIcon(kit.getImage(url2).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		URL url3 = Clothes.class.getClassLoader().getResource("dance/purse.png");
		ImageIcon bagImage = new ImageIcon(kit.getImage(url3).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		URL url4 = Clothes.class.getClassLoader().getResource("dance/high-heel.png");
		ImageIcon shoesImage = new ImageIcon(kit.getImage(url4).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		URL url5 = Clothes.class.getClassLoader().getResource("dance/bow-tie.png");
		ImageIcon accImage = new ImageIcon(kit.getImage(url5).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		URL url6 = Clothes.class.getClassLoader().getResource("dance/shopping-cart.png");
		ImageIcon cartImage = new ImageIcon(kit.getImage(url6).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		
		
		
		
		tabbedPane.setBounds(424, 26, 400, 600);
		tabbedPane.setBackground(Color.white);
		JScrollPane topScroll = new JScrollPane(top, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		topScroll.setBackground(Color.WHITE);
		
		
		tabbedPane.add("??????", topScroll);
		tabbedPane.setTabComponentAt(0, new JLabel("top",topImage, 0));
		topScroll.getVerticalScrollBar().setUnitIncrement(20);
//	        tabbedPane.add("??????", new JScrollPane(top, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
//	              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		// ??????????????????.add("????????????",???????????????);
		JScrollPane bottomScroll = new JScrollPane(bottom, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		bottomScroll.setBackground(Color.WHITE);
		tabbedPane.add("??????", bottomScroll);
		tabbedPane.setTabComponentAt(1, new JLabel("bottom",bottomImage, 0));
		bottomScroll.getVerticalScrollBar().setUnitIncrement(20);
//	        tabbedPane.add("??????", new JScrollPane(bottom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//	              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JScrollPane bagScroll = new JScrollPane(bag, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		bagScroll.setBackground(Color.WHITE);
		tabbedPane.add("??????", bagScroll);
		tabbedPane.setTabComponentAt(2, new JLabel("bag", bagImage, 0));
		bagScroll.getVerticalScrollBar().setUnitIncrement(20);
//	        tabbedPane.add("??????",
//	              new JScrollPane(bag, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JScrollPane shoesScroll = new JScrollPane(shoes, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		shoesScroll.setBackground(Color.WHITE);
		tabbedPane.add("??????", shoesScroll);
		tabbedPane.setTabComponentAt(3, new JLabel("shoes",shoesImage, 0));
		shoesScroll.getVerticalScrollBar().setUnitIncrement(20);

//	        tabbedPane.add("??????", new JScrollPane(shoes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//	              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JScrollPane accScroll = new JScrollPane(acc, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		accScroll.setBackground(Color.WHITE);
		tabbedPane.add("????????????", accScroll);
		tabbedPane.setTabComponentAt(4, new JLabel("acc",accImage, 0));
		accScroll.getVerticalScrollBar().setUnitIncrement(20);
//	        tabbedPane.add("????????????",
//	              new JScrollPane(acc, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JScrollPane cartScroll = new JScrollPane(cart, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cartScroll.setBackground(Color.WHITE);
		tabbedPane.add("????????????", cartScroll);
		tabbedPane.setTabComponentAt(5, new JLabel("cart",cartImage, 0));
		cartScroll.getVerticalScrollBar().setUnitIncrement(20);
//	        tabbedPane.add("????????????",
//	              new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		pnl.add(tabbedPane);

		JLabel lblNewLabel = new JLabel("?????? ??????");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(263, 669, 80, 30);
		pnl.add(lblNewLabel);

		comboBox.setBounds(424, 669, 286, 30);
		comboBox.setBackground(Color.white);
		pnl.add(comboBox);

		JButton btnApply = new JButton("\uB4F1\uB85D"); // ????????????
		btnApply.setToolTipText("");
		btnApply.setBounds(315, 763, 97, 23);
		btnApply.setBackground(Color.white);
		pnl.add(btnApply);

		btnApply.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane confirm = new JOptionPane();
				int clickValue = confirm.showOptionDialog(null, "?????????????????????????", "??????", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (clickValue == JOptionPane.YES_OPTION) {
					Blob blob = null;

					BufferedImage im = new BufferedImage(lblCodiSet.getWidth(), lblCodiSet.getHeight(),
							BufferedImage.TYPE_INT_RGB);
					// ????????? ?????? ?????????(??????, ??????, ????????? ?????? ?????? ??????)
					Graphics g = im.createGraphics();
					// ?????? ????????? ?????? ??????, im.createGraphics() : ????????? ?????????
					g.setColor(Color.WHITE);
					// ?????? ????????? ????????? ???????????? ???????????? ??????
					g.fillRect(0, 0, lblCodiSet.getWidth(), lblCodiSet.getHeight());
					// ????????? ????????? ???????????? ????????? ???
					lblCodiSet.paint(g);
					// ???????????? ?????? ??????????????? ???????????? ???????????? ????????? ????????? => ?????????.???????????????(????????? ????????? ???????????????);
					try {
						// ????????? ??????
//						ImageIO.write(im, "PNG", new File(
//								"C:\\Users\\jyldp\\Desktop\\???????????????2\\TeamProject - codi\\TeamProject2\\resource\\?????????\\shot.png"));
						try {
							blob = Bytes(im);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} catch (IOException e2) {
						e2.printStackTrace();
					}

//					String Acc1;
//					if (!lblAcc1.getText().equals(null)) {
//						Acc1 = lblAcc1.getText();
//					} else if (lblAcc1.getText().equals(null) && !lblAcc2.getText().equals(null)) {
//						Acc1 = lblAcc2.getText();
//					} else if (lblAcc2.getText().equals(null) && !lblAcc3.getText().equals(null)) {
//						Acc1 = lblAcc3.getText();
//					}

					List<String> productList = new ArrayList<>();
					productList.add(lblTop.getText());
					productList.add(lblBottom.getText());
					productList.add(lblBag.getText());
					productList.add(lblShoes.getText());
					productList.add(lblAcc1.getText());
					productList.add(lblAcc2.getText());
					productList.add(lblAcc3.getText());
					System.out.println(productList.toString());

					while (productList.remove(null)) {
					}
					while (productList.remove("")) {
					}

					System.out.println(productList);
					System.out.println(productList.size());

					List<String> codiProduct = new ArrayList<>();

					for (int i = 0; i < productList.size(); i++) {
						codiProduct.add(productList.get(i));
					}
					codiProduct.add(user_id);
					codiProduct.add(comboBox.getSelectedItem().toString());

					try {
						CodiSetDaoImpl gogo = new CodiSetDaoImpl();
						gogo.createCodiSet(codiProduct, blob);
						System.out.println("DB??? ????????? ????????? ??????");
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} finally {
						dispose();
						JOptionPane.showMessageDialog(null, "?????????????????????!");
					}
				} else if (clickValue == 2) {

				}
			}

		});

		JButton btnCancel = new JButton("\uCDE8\uC18C");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblTop.setIcon(null);
				lblBottom.setIcon(null);
				lblBag.setIcon(null);
				lblShoes.setIcon(null);
				lblAcc1.setIcon(null);
				lblAcc2.setIcon(null);
				lblAcc3.setIcon(null);
			}
		});
		btnCancel.setBounds(424, 763, 97, 23);
		btnCancel.setBackground(Color.white);
		pnl.add(btnCancel);

		pnl.setLayout(null);
		getContentPane().add(pnl);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(850, 850);
	}

	public Blob Bytes(BufferedImage im) throws IOException, SQLException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, "PNG", baos);
		// png ???????????? ?????? baos outstream??? ??????
		byte[] fileArray = baos.toByteArray();
		// png ????????? ????????? byte??? ??????

//		Encoder encoder = Base64.getEncoder();
//		// base64??? ????????? ?????? -> png???????????? ???????????? ???
//		byte[] comFileArray = encoder.encode(fileArray);
		Blob blob = null;
		blob = new SerialBlob(fileArray);

		System.out.println("????????? png???????????? blob??? ??????" + blob);
		return blob;
	}

	class checkedTop implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			ManagementDaoImpl dao = new ManagementDaoImpl();
			JCheckBox eventCheckBox = (JCheckBox) e.getSource();
			if (e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
				try {
					Blob imageBlob = dao.readProdcutImage(eventCheckBox.getName()).getImageUrl();
					InputStream in = imageBlob.getBinaryStream();
					BufferedImage bimg = ImageIO.read(in);
					Image blobImage = bimg;
					ImageIcon convertedImage;
					if (!dao.readProdcutImage(eventCheckBox.getName()).getSubCategory().contains("?????????")) {
						convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
						lblTop.setBounds(60, 100, 200, 200);
					} else {
						convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 400, Image.SCALE_DEFAULT));
						lblTop.setBounds(60, 100, 200, 400);
					}

					lblTop.setIcon(convertedImage);
					lblTop.setText(eventCheckBox.getName());
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class checkedBottom implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			ManagementDaoImpl dao = new ManagementDaoImpl();
			JCheckBox eventCheckBox = (JCheckBox) e.getSource();
			if (e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
				try {
					Blob imageBlob = dao.readProdcutImage(eventCheckBox.getName()).getImageUrl();
					InputStream in = imageBlob.getBinaryStream();
					BufferedImage bimg = ImageIO.read(in);
					Image blobImage = bimg;
					ImageIcon convertedImage;
					if (!dao.readProdcutImage(eventCheckBox.getName()).getSubCategory().contains("???")) {
						convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
						lblBottom.setBounds(60, 280, 200, 200);

					} else {
						convertedImage = new ImageIcon(blobImage.getScaledInstance(200, 340, Image.SCALE_DEFAULT));
						lblBottom.setBounds(60, 280, 200, 340);
					}

					lblBottom.setIcon(convertedImage);
					lblBottom.setText(eventCheckBox.getName());
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class checkedBag implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			ManagementDaoImpl dao = new ManagementDaoImpl();
			JCheckBox eventCheckBox = (JCheckBox) e.getSource();
			if (e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
				try {
					Blob imageBlob = dao.readProdcutImage(eventCheckBox.getName()).getImageUrl();
					InputStream in = imageBlob.getBinaryStream();
					BufferedImage bimg = ImageIO.read(in);
					Image blobImage = bimg;
					ImageIcon convertedImage = new ImageIcon(
							blobImage.getScaledInstance(130, 130, Image.SCALE_DEFAULT));

					lblBag.setIcon(convertedImage);
					lblBag.setText(eventCheckBox.getName());
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class checkedShoes implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			ManagementDaoImpl dao = new ManagementDaoImpl();
			JCheckBox eventCheckBox = (JCheckBox) e.getSource();
			if (e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
				try {
					Blob imageBlob = dao.readProdcutImage(eventCheckBox.getName()).getImageUrl();
					InputStream in = imageBlob.getBinaryStream();
					BufferedImage bimg = ImageIO.read(in);
					Image blobImage = bimg;
					ImageIcon convertedImage = new ImageIcon(
							blobImage.getScaledInstance(120, 120, Image.SCALE_DEFAULT));

					lblShoes.setIcon(convertedImage);
					lblShoes.setText(eventCheckBox.getName());
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class checkedAcc implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			ManagementDaoImpl dao = new ManagementDaoImpl();
			JCheckBox eventCheckBox = (JCheckBox) e.getSource();
			if (e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
				try {
					Blob imageBlob = dao.readProdcutImage(eventCheckBox.getName()).getImageUrl();
					InputStream in = imageBlob.getBinaryStream();
					BufferedImage bimg = ImageIO.read(in);
					Image blobImage = bimg;
					ImageIcon convertedImage = new ImageIcon(
							blobImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT));

					if (lblAcc1.getIcon() == null) {
						lblAcc1.setIcon(convertedImage);
						lblAcc1.setText(eventCheckBox.getName());
					} else if (lblAcc2.getIcon() == null && !lblAcc1.getText().equals(eventCheckBox.getName())) {
						lblAcc2.setIcon(convertedImage);
						lblAcc2.setText(eventCheckBox.getName());
					} else if (lblAcc3.getIcon() == null && !lblAcc1.getText().equals(eventCheckBox.getName())
							&& !lblAcc2.getText().equals(eventCheckBox.getName())) {
						lblAcc3.setIcon(convertedImage);
						lblAcc3.setText(eventCheckBox.getName());
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void lblClick(JLabel label) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				label.setIcon(null);
				label.setText(null);
			}
		});
	}

	// ?????? ????????? ?????? ?????????
//	public ImageIcon scaleImage(ImageIcon icon, int w, int h) {
//		int nw = icon.getIconWidth();
//		int nh = icon.getIconHeight();
//
//		if (icon.getIconWidth() > w) {
//			nw = w;
//			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
//		}
//
//		if (nh > h) {
//			nh = h;
//			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
//		}
//		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
//	}

	public static void main(String[] args) throws SQLException, IOException {
//		JButton btnApply = new JButton();
//		??? ????????? ?????? ??? ???????????????!
//		btnApply.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				JButton eventButton = (JButton) e.getSource();
//				if (eventButton == btnApply) {
//					try {
//						new Cloth().setVisible(true);
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				} else if (eventButton != btnApply) {
//					try {
//						new Edit();
//					} catch (SQLException | IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//
//			}
//		});

//		System.out.println("1?????? ??????, 2?????? ??????, 3?????? ??????");
//		Scanner scan = new Scanner(System.in);
//		int choice = scan.nextInt();
//		while (true) {
//			if (choice == 1) {
//		new Clothes("test1").setVisible(true);
//				break;
//			} else if (choice == 2) {
//		new Edit("test1", 25);
//				break;
//			} else if (choice == 3) {
//				break;
//			} else {
//				System.out.println("?????? ????????????");
//			}
//		}
	}
}
