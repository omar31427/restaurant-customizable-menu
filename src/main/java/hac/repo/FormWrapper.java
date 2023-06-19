package hac.repo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Getter
@Setter
@Component
public class FormWrapper {

    private Menu menu;
    private MenuItem menuItem;



}
