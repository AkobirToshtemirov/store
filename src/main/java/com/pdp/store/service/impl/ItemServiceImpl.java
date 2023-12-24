package com.pdp.store.service.impl;

import com.pdp.store.entity.Item;
import com.pdp.store.entity.Upload;
import com.pdp.store.exception.NotFoundException;
import com.pdp.store.repository.ItemRepository;
import com.pdp.store.repository.UploadRepository;
import com.pdp.store.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Path ROOT_PATH = Path.of("C:\\Users\\Hp\\Desktop\\photos");
    private final ItemRepository itemRepository;
    private final UploadRepository uploadRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UploadRepository uploadRepository) {
        this.itemRepository = itemRepository;
        this.uploadRepository = uploadRepository;
    }

    @Override
    public Item add(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        if (existingItem != null)
            itemRepository.deleteById(id);
    }

    @Override
    public void update(Long id, Item updatedItem) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        BeanUtils.copyProperties(updatedItem, existingItem, "id");
        itemRepository.save(existingItem);

    }

    @Override
    public Upload uploadFile(Long itemId, MultipartFile file) throws IOException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + itemId));

        Upload upload = new Upload();

        upload.setOriginalName(file.getOriginalFilename());
        upload.setSize(file.getSize());
        upload.setMimeType(file.getContentType());
        String generatedName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        upload.setGeneratedName(generatedName);

        try {
            Files.copy(file.getInputStream(), ROOT_PATH.resolve(upload.getGeneratedName()), StandardCopyOption.REPLACE_EXISTING);

            String uploadedPath = ROOT_PATH + generatedName;
            upload.setUploadedPath(uploadedPath);

            uploadRepository.save(upload);

            item.setPath(uploadedPath);

            itemRepository.save(item);
        } catch (IOException e) {
            throw new IOException("Failed to upload the file: " + e.getMessage());
        }

        return upload;
    }

    @Override
    public void updateItemWithPath(Long itemId, String uploadedPath) {
        Item itemToUpdate = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + itemId));
        if (itemToUpdate != null) {
            itemToUpdate.setPath(uploadedPath);
            itemRepository.save(itemToUpdate);
        }
    }
}
