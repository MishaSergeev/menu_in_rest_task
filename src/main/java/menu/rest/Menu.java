package menu.rest;

import javax.persistence.*;

@Entity

public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;
    private int price;
    private int weight;
    private Boolean discount;

    public Menu() {
    }

    public Menu(String name, String price, int weight, Boolean discount) {
    }

    public Menu(String name, int price, int weight, Boolean discount) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount=discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", discount='" + discount + '\'' +
                '}';
    }
}
