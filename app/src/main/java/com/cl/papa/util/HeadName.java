package com.cl.papa.util;

import com.cl.papa.R;

import java.util.Random;

/**
 * Created by Administrator on 2016/5/12.
 */
public class HeadName {

    private static int index;

    private static HeadName headName = null;

    public static HeadName getHeadNameClass(){
        if(headName == null)
            headName = new HeadName();

        return headName;
    }

    private String[] names = {
            "有钱任性买辣条","你说南方下雨了","你是病毒我却不忍用360","何方妖孽","拿着试卷唱忐忑i","海绵宝宝强迫症",
            "最萌成绩差i","怪咖女王嘻唰唰","蛋挞学长","妖怪哪里逃","炸掉学校后我就是雷锋","光着屁股的猫","张油条",
            "老堂堂主","吃货的孩纸最怕饿","我爱的少年是奇葩","作业被我养的白白的","咖啡少女不加糖","超级无敌噼里啪啦大boss",
            "转角遇到抢劫的","超级无敌大萌比","太阳是我啃园的","哈利波特别大","叼炸天","喋喋以喋以喋喋","胸毛少女",
            "有本事来咬我","男人不流氓发育不正常","疯了的圆规","人闲屁事多","期中你个老不死的","干掉如来我做佛",
            "飞天女侠啵啵啵","伯贤不咸他很甜","盯着作业唱征服丶","傻里傻气傻孩纸","姑娘我这朵奇葩红遍天下","不可一世的2百",
            "梅仁耀","花街卖笑的","小II货","秀恩愛死得快","你活的像个疯子","老天会爱笨小孩","冇灰机","未成年面包彡",
            "鎶爆耐迪","逗比知道姐的战斗无敌","打灰机的蜡笔小新","嚼着炫迈说爱我","亡命天涯不如早点回家",
            "考试就是哪里不会考哪里","姐法眼一睜就知道你是妖孽","闺蜜是个神话","饼干公主","慕后煮屎者","活着活着就死了",
            "骑驴玩漂移","﹌ 绝世小攻","雷帝嘎嘎","骨折旳蚯蚓","吃素的蚊子","嫦娥奔丧","选择性失忆","一吼，二怒，三切腹"
    };
    private int[] icons = {R.drawable.f1,R.drawable.f2,R.drawable.f3,R.drawable.f4,R.drawable.f5,R.drawable.f6
            ,R.drawable.f7,R.drawable.f8,R.drawable.f9,R.drawable.f10,R.drawable.f11,R.drawable.f12,R.drawable.f13
            ,R.drawable.f14,R.drawable.f15,R.drawable.f16,R.drawable.f17,R.drawable.f18,R.drawable.f19,R.drawable.f20
            ,R.drawable.f21,R.drawable.f22,R.drawable.f23,R.drawable.f24,R.drawable.f25,R.drawable.f26,R.drawable.f27
            ,R.drawable.f28,R.drawable.f29,R.drawable.f30,R.drawable.f31,R.drawable.f49};

    public String getHeadName(){
        Random random = new Random();
        index = random.nextInt(names.length);
        return names[index];
    }

    public int getIcon(){
        Random random = new Random();
        index = random.nextInt(names.length);
        return icons[index%icons.length];
    }
}
