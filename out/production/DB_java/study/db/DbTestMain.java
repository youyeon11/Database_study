package study.db;

import java.util.Scanner;

public class DbTestMain {

    public static void main(String[] args) {

        MemberService memberService = new MemberService();
        //dbTest.dbSelect();
        // dbTest.dbInsert();
        //dbTest.dbUpdate();
        // dbTest.dbDelete();

        Scanner scanner = new Scanner(System.in);

        String memberType = "email";

        System.out.println("아이디입력:>>>");
        String userId = scanner.next();
        System.out.println("비밀번호입력:>>>");
        String password = scanner.next();
        System.out.println("이름입력:>>>");
        String name = scanner.next();

        Member member = new Member(); // 이걸 이용하면 아예 메소드의 인자로 하나하나 입력이 아니라 그냥 Member class를 사용해서 한번에 작성하기
        member.setMemberType(memberType);
        member.setUserId(userId);
        member.setPassword(password);
        member.setName(name);

        memberService.register(member);
    }
}