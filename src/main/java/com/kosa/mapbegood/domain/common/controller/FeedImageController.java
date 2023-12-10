package com.kosa.mapbegood.domain.common.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedImageController {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam String id, @RequestParam String opt, @RequestParam List<MultipartFile> f1) throws IOException {
        for (MultipartFile mf : f1) {
            if (mf != null && mf.getSize() > 0) {
                String fileName = id + "_" + opt + "_" + mf.getOriginalFilename();
                File targetFile2 = new File("C:\\kosa202307\\attaches", fileName);
                FileCopyUtils.copy(mf.getBytes(), targetFile2);
            } else {
                return "upload FAIL";
            }
        }
        return "upload OK";
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<?> download(@RequestParam String id, @RequestParam String opt) throws IOException {
        String attachesDir = "C:\\KOSA202307\\attaches";
        File dir = new File(attachesDir);

        String fileName = id + "_" + opt + "_";

        for (File f : dir.listFiles()) {
            String existFileName = f.getName();
            if (existFileName.startsWith(fileName)) {
                HttpStatus status = HttpStatus.OK;
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + URLEncoder.encode(existFileName, "UTF-8"));

                System.out.println("in download file: " + f + ", file size:" + f.length());
                String contentType = Files.probeContentType(f.toPath());
                headers.add(HttpHeaders.CONTENT_TYPE, contentType);
                headers.add(HttpHeaders.CONTENT_LENGTH, "" + f.length());

                byte[] bArr = FileCopyUtils.copyToByteArray(f);
                ResponseEntity<?> entity = new ResponseEntity<>(bArr, headers, status);
                return entity;
            }
        }
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseEntity<?> entity = new ResponseEntity<>("프로필썸네일파일이 없습니다", status);
        return entity;
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
