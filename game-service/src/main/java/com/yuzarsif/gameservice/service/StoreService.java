package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.request.CreateDiscountRequest;
import com.yuzarsif.gameservice.dto.request.CreateStoreRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.Currency;
import com.yuzarsif.gameservice.model.Game;
import com.yuzarsif.gameservice.model.Platform;
import com.yuzarsif.gameservice.model.Store;
import com.yuzarsif.gameservice.repository.StoreRepository;
import com.yuzarsif.gameservice.utils.DateConverter;
import com.yuzarsif.gameservice.utils.TimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final GameService gameService;
    private final PlatformService platformService;

    public StoreService(StoreRepository storeRepository, GameService gameService, PlatformService platformService) {
        this.storeRepository = storeRepository;
        this.gameService = gameService;
        this.platformService = platformService;
    }

    public void createStore(CreateStoreRequest createStoreRequest) {
        Game game = gameService.findById(createStoreRequest.gameId());
        Platform platform = platformService.findById(createStoreRequest.platformId());

        Store store = Store
                .builder()
                .storeName(createStoreRequest.storeName())
                .price(createStoreRequest.price())
                .game(game)
                .platform(platform)
                .url(createStoreRequest.url())
                .currency(Currency.valueOf(createStoreRequest.currency().toUpperCase()))
                .build();

        Store savedStore = storeRepository.save(store);
        log.info("Created store: " + savedStore);
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
