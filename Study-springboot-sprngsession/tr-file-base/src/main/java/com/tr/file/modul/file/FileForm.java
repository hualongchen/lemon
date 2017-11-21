package com.tr.file.modul.file;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;


@Data
public class FileForm implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String argeementid;

    private String sourceUrl ;

    private String vid ;

    private String resourcePath ;

    private String accessUrl ;




}
