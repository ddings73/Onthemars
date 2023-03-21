package onthemars.back.code.dto;

import onthemars.back.code.domain.Code;
public class MyCodeFactory {
    public static MyCode create(MyCode myCode, Code code){
        return myCode.create(code);
    }
}
