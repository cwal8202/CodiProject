import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class 합치기 {
	public static void main(String[] args) {
		MainFrame3 mainPage = new MainFrame3(null, false);
		mainPage.setVisible(true);
		mainPage.loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login login = new login();
				login.setVisible(true);
			}
		});
		
	}
}
