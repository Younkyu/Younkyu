package goriproject.ykjw.com.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Younkyu on 2017-03-27.
 */

public class TutorLoader {

    public static List<tutor> datas = new ArrayList<>();

    public static void loadData() {

        tutor tutor1 = new tutor();
        tutor1.setCampus("고려대");
        tutor1.setCategory("외국어");
        tutor1.setLocation("잠실");
        tutor1.setTutor_name("김지홍");
        tutor1.setClass_name("외국어사용설명서");
        tutor1.setTutor_rating(10);
        datas.add(tutor1);

        tutor tutor2 = new tutor();
        tutor2.setCampus("연세대");
        tutor2.setCategory("컴퓨터");
        tutor2.setLocation("신촌");
        tutor2.setTutor_name("구영재");
        tutor2.setClass_name("컴퓨터사용설명서");
        tutor2.setTutor_rating(30);
        datas.add(tutor2);

        tutor tutor3 = new tutor();
        tutor3.setCampus("서울대");
        tutor3.setCategory("헬스/뷰티");
        tutor3.setLocation("강남");
        tutor3.setTutor_name("장한솔");
        tutor3.setClass_name("필라테스사용설명서");
        tutor3.setTutor_rating(50);
        datas.add(tutor3);

        tutor tutor4 = new tutor();
        tutor4.setCampus("홍익대");
        tutor4.setCategory("외국어");
        tutor4.setLocation("사당");
        tutor4.setTutor_name("강선미");
        tutor4.setClass_name("태국어똠양꿍");
        tutor4.setTutor_rating(70);
        datas.add(tutor4);

        tutor tutor5 = new tutor();
        tutor5.setCampus("홍익대");
        tutor5.setCategory("컴퓨터");
        tutor5.setLocation("잠실");
        tutor5.setTutor_name("박지언");
        tutor5.setClass_name("C언어사용설명서");
        tutor5.setTutor_rating(80);
        datas.add(tutor5);


        tutor tutor6 = new tutor();
        tutor6.setCampus("서울대");
        tutor6.setCategory("헬스/뷰티");
        tutor6.setLocation("사당");
        tutor6.setTutor_name("김다영");
        tutor6.setClass_name("스쿠버다이빙지랄");
        tutor6.setTutor_rating(10);
        datas.add(tutor6);

        tutor tutor7 = new tutor();
        tutor7.setCampus("연세대");
        tutor7.setCategory("음악/미술");
        tutor7.setLocation("강남");
        tutor7.setTutor_name("전주은");
        tutor7.setClass_name("단소로 발바닥때리기");
        tutor7.setTutor_rating(90);
        datas.add(tutor7);

        tutor tutor8 = new tutor();
        tutor8.setCampus("고려대");
        tutor8.setCategory("외국어");
        tutor8.setLocation("사당");
        tutor8.setTutor_name("장재광");
        tutor8.setClass_name("스페인어초급부터 완벽하게");
        tutor8.setTutor_rating(100);
        datas.add(tutor8);

        tutor tutor9 = new tutor();
        tutor9.setCampus("서울대");
        tutor9.setCategory("컴퓨터");
        tutor9.setLocation("신촌");
        tutor9.setTutor_name("김태호");
        tutor9.setClass_name("나사실컴터못함");
        tutor9.setTutor_rating(100);
        datas.add(tutor9);

        tutor tutor10 = new tutor();
        tutor10.setCampus("연세대");
        tutor10.setCategory("헬스/뷰티");
        tutor10.setLocation("신촌");
        tutor10.setTutor_name("김환희");
        tutor10.setClass_name("나는예쁘니>~<");
        tutor10.setTutor_rating(95);
        datas.add(tutor10);

    }


}
