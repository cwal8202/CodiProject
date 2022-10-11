import java.util.Scanner;

public class 팀프로젝흐름 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("메인화면 시작");
		int input = 0;
		boolean login = false;
		do {
			System.out.println("메인화면 (많이 담는 옷 탑3, 좋아요 탑3) : 1. 로그인, 2. 내정보, 3. 남의 코디셋, 4. 글쓰기 5. 종료");
			input = scan.nextInt();
			switch (input) {
				case 1 : System.out.println("로그인 창으로 이동했다.");
				System.out.println("00입력은 관리자 나머지는 일반유저");
				input = scan.nextInt();
				if (input == 00) {
					System.out.println("관리자 창이 켜집니다");
					System.out.println("관리자창 끌려면 아무키 입력");
					input = scan.nextInt();
					break;
				} 
				login = true;
				System.out.println("일반유저의 로그인이 됐습니다.");
				System.out.println("메인화면 가기 = 아무키 입력");
				input = scan.nextInt();
					break;
				case 2 : System.out.println("내정보로 이동했다");
				if (login == true) {
					System.out.println("로그인을 했으므로 로그인한 유저의 정보");
				} else {
					System.out.println("로그인을 하세요!!!!!!!!!!!!!!!!");
				}
				System.out.println("메인화면 가기 = 아무키 입력");
				input = scan.nextInt();
					break;
				case 3 : System.out.println("남의 코디셋으로 이동했다");
				System.out.println("메인화면 가기 = 아무키 입력");
				input = scan.nextInt();
					break;
				case 4 : System.out.println("글쓰기로 이동했다");
				if (login == true) {
					System.out.println("로그인을 했으므로 글 쓸수 있습니다");
				} else {
					System.out.println("로그인을 하세요!!!!!!!!!!!!!!!!");
				}
				System.out.println("메인화면 가기 = 아무키 입력");
				input = scan.nextInt();
					break;
				case 5 : System.out.println("종료");
					break;
			}
		} while (input != 5);
		System.out.println("시스템 종료");
	}
}
