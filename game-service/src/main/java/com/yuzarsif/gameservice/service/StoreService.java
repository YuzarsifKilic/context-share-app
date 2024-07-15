package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.request.CreateDiscountRequest;
import com.yuzarsif.gameservice.dto.request.CreateStoreRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Game;
import com.yuzarsif.gameservice.model.Store;
import com.yuzarsif.gameservice.repository.StoreRepository;
import com.yuzarsif.gameservice.utils.DateConverter;
import com.yuzarsif.gameservice.utils.TimeConverter;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final GameService gameService;

    public StoreService(StoreRepository storeRepository, GameService gameService) {
        this.storeRepository = storeRepository;
        this.gameService = gameService;
    }

    public void createStore(CreateStoreRequest createStoreRequest) {
        Game game = gameService.findById(createStoreRequest.gameId());

        Store store = Store
                .builder()
                .storeName(createStoreRequest.storeName())
                .price(createStoreRequest.price())
                .game(game)
                .build();

        storeRepository.save(store);
    }

    public void createDiscount(Long storeId, CreateDiscountRequest createDiscountRequest) {
        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id " + storeId));

        store.setDiscount(createDiscountRequest.discount());
        store.setFinalPrice(createDiscountRequest.finalPrice());
        store.setDiscountStartDate(DateConverter.convert(createDiscountRequest.discountStartDate()));
        store.setDiscountEndDate(DateConverter.convert(createDiscountRequest.discountEndDate()));
        store.setDiscountStartTime(TimeConverter.convert(createDiscountRequest.discountStartTime()));
        store.setDiscountEndTime(TimeConverter.convert(createDiscountRequest.discountEndTime()));

        storeRepository.save(store);
    }

}
