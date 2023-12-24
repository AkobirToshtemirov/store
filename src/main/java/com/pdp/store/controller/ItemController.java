package com.pdp.store.controller;

import com.pdp.store.entity.Item;
import com.pdp.store.entity.Upload;
import com.pdp.store.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public ResponseEntity<Item> addItem(@Valid @RequestBody Item item) {
        Item newItem = itemService.add(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable Long id, @Valid @RequestBody Item updatedItem) {
        itemService.update(id, updatedItem);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{itemId}/upload")
    public ResponseEntity<Upload> uploadFile(@PathVariable Long itemId, @RequestParam("file") MultipartFile file) throws IOException {
        Upload upload = itemService.uploadFile(itemId, file);
        return new ResponseEntity<>(upload, HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}/update-path")
    public ResponseEntity<Void> updateItemWithPath(@PathVariable Long itemId, @RequestParam("uploadedPath") String uploadedPath) {
        itemService.updateItemWithPath(itemId, uploadedPath);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
