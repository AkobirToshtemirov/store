package com.pdp.store.service;

import com.pdp.store.entity.Item;
import com.pdp.store.entity.Upload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ItemService extends CrudService<Item> {
    Upload uploadFile(Long itemId, MultipartFile file) throws IOException;

    void updateItemWithPath(Long itemId, String uploadedPath);
}
