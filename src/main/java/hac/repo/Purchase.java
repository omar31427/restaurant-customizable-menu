package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.relational.core.mapping.Table;
@Entity
@Table("Purchase")
public class Purchase implements Serializable {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotEmpty(message = "First name is mandatory")
    private String firstName;

    @Getter
    @Setter
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;

    @Getter
    @Setter
    @Email
    private String email;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    @PositiveOrZero(message = "Payment must be positive or zero")
    private double payment = 0.0;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private List<MenuItem> purchaseItems = new ArrayList<>();
    public Purchase(String email, Double total, String firstName, String lastName, MenuItem item) {
        this.email = email;
        this.payment = total;
        this.firstName = firstName;
        this.lastName = lastName;
        this.purchaseItems.add(item);
    }
    public Purchase(String name, double payment, MenuItem item){
        this.userName = name;
        this.payment = payment;
        this.purchaseItems.add(item);
    }
    public Purchase() {

    }
    public void addItemToPurchase(MenuItem item){
        this.purchaseItems.add(item);
    }
}


