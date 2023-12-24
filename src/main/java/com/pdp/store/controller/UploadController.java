package com.pdp.store.controller;

import com.pdp.store.entity.Upload;
import com.pdp.store.service.UploadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/uploads")
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    public ResponseEntity<Upload> addUpload(@Valid @RequestBody Upload upload) {
        Upload newUpload = uploadService.add(upload);
        return new ResponseEntity<>(newUpload, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Upload>> getAllUploads() {
        return ResponseEntity.ok(uploadService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Upload> getUploadById(@PathVariable Long id) {
        return ResponseEntity.ok(uploadService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUpload(@PathVariable Long id, @Valid @RequestBody Upload updatedUpload) {
        uploadService.update(id, updatedUpload);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUpload(@PathVariable Long id) {
        uploadService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
