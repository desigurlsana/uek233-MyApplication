package ch.noseryoung.uk.businessObject.auction;

import ch.noseryoung.uk.businessObject.auction.dto.AuctionDTO;
import ch.noseryoung.uk.businessObject.auction.dto.AuctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is an example controller with CRUD logic
@RestController
@RequestMapping("/auctions")
public class AuctionController {

    // The newly created service to be injected
    private AuctionService auctionService;

    // The mapper to be injected
    private AuctionMapper auctionMapper;


    // Injecting the dependency via constructor
    @Autowired
    public AuctionController(AuctionService auctionService, AuctionMapper auctionMapper) {
        this.auctionService = auctionService;
        this.auctionMapper = auctionMapper;
    }

    // This endpoint creates a new auction with the data given, currently this process is being mocked
    @PostMapping({"/", ""})
    public ResponseEntity<AuctionDTO> create(@RequestBody AuctionDTO auctionDTO) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.create(auctionMapper.fromDTO(auctionDTO))), HttpStatus.CREATED);
    }

    // This endpoint retrieves all auctions as a list
    @GetMapping({"/", "", "getAll"})
    public ResponseEntity<List<AuctionDTO>> getAll() {

        return new ResponseEntity<>(auctionMapper.toDTOs(auctionService.findAll()), HttpStatus.OK);
    }

    // This endpoint retrieves a single auction by it's id
    @GetMapping("/{id}")
    public ResponseEntity<AuctionDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.findById(id)), HttpStatus.OK);
    }

    // This endpoint updates an existing auction with the id and data given, currently this process is being mocked
    @PutMapping("/{id}")
    public ResponseEntity<AuctionDTO> updateById(@PathVariable int id, @RequestBody AuctionDTO auctionDTO) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.updateById(id, auctionMapper.fromDTO(auctionDTO))), HttpStatus.OK);
    }

    // This endpoint deletes an existing auction with the id given, currently this process is being mocked
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        auctionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/between/{maxPrice}/{minPrice}")
    public ResponseEntity<List<Auction>> getAuctionByRange(@PathVariable int maxPrice, @PathVariable int minPrice) {
        return new ResponseEntity<>(auctionService.getAuctionByRange(maxPrice, minPrice), HttpStatus.OK);
    }

}

