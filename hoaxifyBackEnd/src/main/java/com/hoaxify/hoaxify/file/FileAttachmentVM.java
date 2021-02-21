package com.hoaxify.hoaxify.file;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
public class FileAttachmentVM {

    private String name;

    private String fileType;

    public FileAttachmentVM(FileAttachment fileAttachment) {
        this.setName(fileAttachment.getName());
        this.setFileType(fileAttachment.getFileType());
    }
}