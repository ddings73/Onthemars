package onthemars.back.farm.dto.response;

import lombok.Getter;

@Getter
public class CharacterResDto {

    private String color;

    public CharacterResDto(String color) {
        this.color = color;
    }

}
