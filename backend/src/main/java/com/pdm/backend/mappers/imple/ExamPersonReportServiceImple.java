package com.pdm.backend.mappers.imple;

import org.modelmapper.ModelMapper;

import com.pdm.backend.mappers.Mapper;
import com.pdm.backend.models.ExamPersonReport;
import com.pdm.backend.models.dto.ExamPersonReportDto;

public class ExamPersonReportServiceImple implements Mapper<ExamPersonReport , ExamPersonReportDto> {


    private ModelMapper modelMapper;

    @Override 
    public ExamPersonReport mapfrom(ExamPersonReportDto examPersonReportDto){
         return modelMapper.map(examPersonReportDto, ExamPersonReport.class);
    }

    @Override
    public ExamPersonReportDto mapto(ExamPersonReport examPersonReport){
        return modelMapper.map(examPersonReport, ExamPersonReportDto.class);
    }
}
