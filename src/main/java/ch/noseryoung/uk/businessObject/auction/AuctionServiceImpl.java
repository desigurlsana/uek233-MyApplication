package ch.noseryoung.uk.businessObject.auction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


// This is an example service implementation with coded out CRUD logic
// Note that the @Service annotation belongs on here as the effective logic is found here
@Service
public class AuctionServiceImpl implements AuctionService {

    private AuctionRepository auctionRepository;

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    // The logic for creating a new auction
    @Override
    public Auction create(Auction auction) {
        return auctionRepository.save(auction);
    }

    // The logic for retrieving all authorities
    @Override
    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }

    // The logic for retrieving a single authority with a given id
    @Override
    public Auction findById(int id) {
        return auctionRepository.findById(id).get();
    }

    @Override
    public Auction updateById(int id, Auction auction) {
        if (auctionRepository.existsById(id)) {
            auction.setId(id);
            auctionRepository.save(auction);

            return auction;
        } else {
            throw new NoSuchElementException("No value present");
        }
    }

    // The logic for deleting an authority with a given id
    @Override
    public void deleteById(int id) {
        auctionRepository.deleteById(id);
    }

    @Override
    public List<Auction> getAuctionByRange(int maxPrice, int minPrice) {

        List<Auction> auctions = auctionRepository.findAll();
        List<Auction> auctionsToReturn = new ArrayList<>();
        for (Auction auction : auctions) {
            if (auction.getPrice() < maxPrice && auction.getPrice() > minPrice) {
                auctionsToReturn.add(auction);
            }
        }
        return auctionsToReturn;
    }
}
