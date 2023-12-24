package com.pdp.store.service.impl;

import com.pdp.store.entity.Upload;
import com.pdp.store.exception.NotFoundException;
import com.pdp.store.repository.UploadRepository;
import com.pdp.store.service.UploadService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {
    private final UploadRepository uploadRepository;

    public UploadServiceImpl(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Override
    public Upload add(Upload upload) {
        return uploadRepository.save(upload);
    }

    @Override
    public List<Upload> getAll() {
        return uploadRepository.findAll();
    }

    @Override
    public Upload getById(Long id) {
        return uploadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Upload not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        Upload existingUpload = uploadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Upload not found with id: " + id));

        if (existingUpload != null)
            uploadRepository.deleteById(id);
    }

    @Override
    public void update(Long id, Upload updatedUpload) {
        Upload existingUpload = uploadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Upload not found with id: " + id));

        if (existingUpload != null && updatedUpload != null) {
            BeanUtils.copyProperties(updatedUpload, existingUpload, "id");
            uploadRepository.save(existingUpload);
        }
    }
}
