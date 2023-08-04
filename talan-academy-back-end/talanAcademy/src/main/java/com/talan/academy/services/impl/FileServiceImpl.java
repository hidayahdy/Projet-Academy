package com.talan.academy.services.impl;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.exception.InvalidEntityException;
import com.talan.academy.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	private String userDirectory = System.getProperty("user.home");

	private final Path root = Paths.get(userDirectory + userDirectory.substring(2, 3) + "uploads");
	
	

	@Override
	public String save(MultipartFile file) {
		String filename;
		try {
			if (!Files.exists(root)) {
				Files.createDirectories(root);
			}
			filename = StringUtils.cleanPath(file.getOriginalFilename());
			System.out.println(filename);
		    filename = String.valueOf(new Date().getTime()).concat(filename.toLowerCase());
		    System.out.println(filename);
			Files.copy(file.getInputStream(), this.root.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
		
		} catch (Exception e) {
			throw new InvalidEntityException("could not save the file");
		}
		return filename;
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable())
				return resource;
		
		} catch (MalformedURLException e) {
			throw new InvalidEntityException("Erreur");
		}
		return null;
	}
	
	

}
