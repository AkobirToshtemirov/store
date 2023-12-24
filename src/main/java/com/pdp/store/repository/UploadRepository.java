package com.pdp.store.repository;

import com.pdp.store.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<Upload, Long> {
}
