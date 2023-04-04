package onthemars.back.nft.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum SearchKeyword {
    // bg
    CLR01("white", "하얀색", "하양"),
    CLR02("red", "빨간색", "빨강"),
    CLR03("orange", "주황색", "주황"),
    CLR04("yellow", "노란색", "노랑"),
    CLR05("green", "초록색", "초록"),
    CLR06("blue", "파란색", "파랑"),
    CLR07("navy", "남색", "남"),
    CLR08("purple", "보라색", "보라"),
    CLR09("pink", "분홍색", "분홍"),
    CLR10("brown", "갈색", "갈"),

    // cropType
    CRS01("carrot", "당근", "홍당무"),
    CRS02("corn", "옥수수", "강냉이"),
    CRS03("cucumber", "오이", "가시오이"),
    CRS04("eggplant", "가지", "aubergine"),
    CRS05("pineapple", "파인애플", "ananas"),
    CRS06("potato", "감자", "왕감자"),
    CRS07("radish", "무", "무시"),
    CRS08("strawberry", "딸기", "양딸기"),
    CRS09("tomato", "토마토", "도마도"),
    CRS10("wheat", "밀", "소맥"),

    // eye
    EYE01("default eye", "기본 눈", "둥근"),
    EYE02("chic", "시크 눈", "쿨한 눈"),
    EYE03("adonis", "순정만화 눈", "반짝반짝 눈"),
    EYE04("sleep", "감은 눈", "졸린 눈"),
    EYE05("smile", "웃는 눈", "애교살"),
    EYE06("sad", "슬픈 눈", "우는 눈"),
    EYE07("mad", "화난 눈", "올라간 눈"),

    // headGear
    HDG01("default head gear", "기본 머리 장식", "머리 장식 없음"),
    HDG02("hairband", "헤어밴드", "머리띠"),
    HDG03("ribbon", "리본", "리본 삔"),
    HDG04("headset", "헤드셋", "노래"),
    HDG05("nutrients", "영양제", "건강"),
    HDG06("fork", "포크", "찍힌"),
    HDG07("worm", "벌레", "벌레 먹은"),

    // mouth
    MOU01("default mouth", "기본 입", "입술"),
    MOU02("smile", "웃는 입", "활짝 웃는 입"),
    MOU03("mustache", "수염", "프링글스 수염"),
    MOU04("tongue", "냠", "할짝"),
    MOU05("sad", "으앙", "우는 입"),
    MOU06("wow", "오", "놀란 입"),
    MOU07("chu", "뽀뽀", "내민 입");


    private String synonym1;
    private String synonym2;
    private String synonym3;

    SearchKeyword(String synonym1, String synonym2, String synonym3) {
        this.synonym1 = synonym1;
        this.synonym2 = synonym2;
        this.synonym3 = synonym3;
    }

    public List<String> getSynonyms() {
        return new ArrayList<>(Arrays.asList(synonym1, synonym2, synonym3));
    }
}
