package com.kosa.mapbegood.domain.common.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/feed")
public class FeedImageController {

	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam String id, @RequestParam String opt, @RequestParam List<MultipartFile> files) throws IOException {
	    String attachesDir = "C:\\kosa202307\\attaches";
	    String fileNamePrefix = id + "_" + opt + "_";

	    List<String> uploadedFiles = new ArrayList<>();

	    for (MultipartFile mf : files) {
	        if (mf != null && mf.getSize() > 0) {
	            String fileName = fileNamePrefix + mf.getOriginalFilename();
	            File targetFile = new File(attachesDir, fileName);

	            FileCopyUtils.copy(mf.getBytes(), targetFile);
	            
	            uploadedFiles.add(fileName);
	        } else {
	            return "upload FAIL";
	        }
	    }

	    File[] existingFiles = new File(attachesDir).listFiles();
	    if (existingFiles != null) {
	        for (File existingFile : existingFiles) {
	            String existingFileName = existingFile.getName();
	            if (existingFileName.startsWith(fileNamePrefix) && !uploadedFiles.contains(existingFileName)) {
	                existingFile.delete();
	            }
	        }
	    }

	    return "upload OK";
	}
	
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> download(@RequestParam String id, @RequestParam String opt) throws IOException {
        String attachesDir = "C:\\KOSA202307\\attaches";
        File dir = new File(attachesDir);

        String fileName = id + "_" + opt + "_";

        File[] files = dir.listFiles();
        if (files != null) {
            List<Map<String, Object>> images = new ArrayList<>();

            for (File f : files) {
                String existFileName = f.getName();
                if (existFileName.startsWith(fileName) && (existFileName.endsWith(".jpg") || existFileName.endsWith(".png"))) {
                    String contentType = Files.probeContentType(f.toPath());
                    System.out.println("File MIME Type: " + contentType);

                    String base64Data = Base64.getEncoder().encodeToString(Files.readAllBytes(f.toPath()));

                    Map<String, Object> image = new HashMap<>();
                    image.put("data", base64Data); // Provide base64 encoded data
                    image.put("mimeType", contentType);

                    images.add(image);
                }
            }

            if (!images.isEmpty()) {
                return ResponseEntity.ok(images);
            }
        }

	    HttpStatus status = HttpStatus.NOT_FOUND;
	    ResponseEntity<?> entity = new ResponseEntity<>("프로필 썸네일 파일이 없거나 올바른 형식이 아닙니다", status);
	    return (ResponseEntity<List<Map<String, Object>>>) entity;
	}


    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String id, @RequestParam String opt) {
        String attachesDir = "C:\\KOSA202307\\attaches";
        String fileName = id + "_"+ opt +"_";

        File[] files = new File(attachesDir).listFiles();
        if (files != null) {
            for (File file : files) {
                String existFileName = file.getName();
                if (existFileName.startsWith(fileName)) {
                    if (file.delete()) {
                        return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("파일 삭제 중 오류가 발생했습니다.");
                    }
                }
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 파일을 찾을 수 없습니다.");
    }

}
