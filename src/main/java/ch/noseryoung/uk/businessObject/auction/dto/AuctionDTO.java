package ch.noseryoung.uk.businessObject.auction.dto;

// This an example class on how a DTO could be built
// A DTO is pretty much just a regular POJO, there is no need for any fancy annotations
public class AuctionDTO {

    // Representative attributes, make sure they are called the same way as the attribute that they represent
    private int id;

    private String name;

    private double price;



    // Standard empty constructor
    public AuctionDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}