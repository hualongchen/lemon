package com.tr.file.modul.file;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;


@Data
public class FileForm implements Serializable {


    private String fileid;

    @NotBlank
    private String username;

    @NotBlank
    private String argeementid;


    private  String fileName ;


}
