package com.pdp.store.controller;

import com.pdp.store.entity.Store;
import com.pdp.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<Store> addStore(@Valid @RequestBody Store store) {
        Store newStore = storeService.add(store);
        return new ResponseEntity<>(newStore, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStore(@PathVariable Long id, @Valid @RequestBody Store updatedStore) {
        storeService.update(id, updatedStore);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
