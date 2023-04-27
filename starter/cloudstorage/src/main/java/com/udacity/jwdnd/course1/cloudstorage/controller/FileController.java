package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("")
    public String file(Authentication authentication, FileForm fileForm, RedirectAttributes redirectAttributes) throws IOException {
        MultipartFile multipartFile = fileForm.getFile();
        String fileName = multipartFile.getOriginalFilename();

        boolean isDuplicatedFileName = fileService.checkDuplicatedFileName(authentication, fileName);

        if (isDuplicatedFileName) {
            redirectAttributes.addFlashAttribute("result", "error");
            redirectAttributes.addFlashAttribute("message", "File name is existed.");
            return "redirect:/result";
        }

        fileService.save(authentication, multipartFile);
        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/result";
    }

    @GetMapping(value = "/delete/{fileName}")
    public String deleteFile(Authentication authentication, @PathVariable String fileName, Model model, RedirectAttributes redirectAttributes) {
        fileService.deleteFileByAuthenticationAndFileName(authentication, fileName);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/result";
    }

    @GetMapping(
            value = "/view/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(Authentication authentication, @PathVariable String fileName) {
        return fileService.findContentFileByAuthenticationAndFileName(authentication, fileName);
    }

}
