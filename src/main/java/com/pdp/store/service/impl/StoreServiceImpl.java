package com.pdp.store.service.impl;

import com.pdp.store.entity.Store;
import com.pdp.store.exception.NotFoundException;
import com.pdp.store.repository.StoreRepository;
import com.pdp.store.service.StoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store add(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store getById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Store is not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        Store existingStore = storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Store is not found with id: " + id));

        if (existingStore != null)
            storeRepository.deleteById(id);
    }

    @Override
    public void update(Long id, Store updatedStore) {
        Store existingStore = storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Store is not found with id: " + id));

        if (existingStore != null && updatedStore != null) {
            BeanUtils.copyProperties(updatedStore, existingStore, "id");
            storeRepository.save(existingStore);
        }
    }
}
