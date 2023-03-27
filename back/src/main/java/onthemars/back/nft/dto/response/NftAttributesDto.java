package onthemars.back.nft.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class NftAttributesDto {

    public static List<Attribute> of(
        String tier,
        String bg,
        String eyes,
        String mouth,
        String headGear
    ) {
        final Attribute tierAttr = new Attribute("Tier", tier);
        final Attribute bgAttr = new Attribute("Background", bg);
        final Attribute eyesAttr = new Attribute("Eyes", eyes);
        final Attribute MouthAttr = new Attribute("Mouth", mouth);
        final Attribute HeadGearAttr = new Attribute("Head Gear", headGear);

        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(tierAttr);
        attrs.add(bgAttr);
        attrs.add(eyesAttr);
        attrs.add(MouthAttr);
        attrs.add(HeadGearAttr);

        return attrs;
    };

    public static List<Attribute> of(
        String tier,
        String bg
    ) {
        final Attribute tierAttr = new Attribute("Tier", tier);
        final Attribute bgAttr = new Attribute("Background", bg);

        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(tierAttr);
        attrs.add(bgAttr);

        return attrs;
    };

    private final List<Attribute> attributes;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attribute {
        private String value;
        private String data;
    }
}
