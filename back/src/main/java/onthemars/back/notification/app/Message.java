package onthemars.back.notification.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter @ToString
@AllArgsConstructor
public class Message {
    private String title;
    private String content;
}
