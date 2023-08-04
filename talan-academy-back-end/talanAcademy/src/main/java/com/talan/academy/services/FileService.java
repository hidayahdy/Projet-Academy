package com.talan.academy.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String save(MultipartFile file);

	Resource load(String filename);

}
