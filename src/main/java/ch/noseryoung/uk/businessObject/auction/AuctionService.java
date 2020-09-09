package ch.noseryoung.uk.businessObject.auction;

import java.util.List;

// This is an example service with method signatures for CRUD logic
public interface AuctionService {

    Auction create(Auction authority);

    List<Auction> findAll();

    Auction findById(int id);

    Auction updateById(int id, Auction auction);

    void deleteById(int id);

    List <Auction> getAuctionByRange(int maxPrice, int minPrice);
}
