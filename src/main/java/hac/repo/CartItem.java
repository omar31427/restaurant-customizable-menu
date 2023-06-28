package hac.repo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CartItem implements Serializable {
    @Setter
    @Getter
    private MenuItem item;
    @Setter
    @Getter
    @Max(100)
    @PositiveOrZero(message = "Price has to be more than or equal to 0.00")
    private int amount =0;

    public CartItem ()
    {
        this.item = new MenuItem();
        this.amount = 0;
    }

    public CartItem (MenuItem item, int amt)
    {
        this.item = item;
        this.amount = amt;
    }

    public void increaseAmount(){
        this.amount++;
    }

    public void decreaseAmount(){
        this.amount--;
    }
}
