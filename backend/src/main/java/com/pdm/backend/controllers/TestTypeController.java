package com.pdm.backend.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pdm.backend.services.TestTypeServices;
import com.pdm.backend.mappers.Mapper;
import com.pdm.backend.models.TestType;
import com.pdm.backend.models.dto.TestTypeDto;

@RestController
public class TestTypeController {
     
    private  TestTypeServices testTypeServices;
    private Mapper<TestType , TestTypeDto> typeMapper;

    public TestTypeController(TestTypeServices testTypeServices , Mapper<TestType , TestTypeDto> testMapper){
        this.testTypeServices = testTypeServices;
        this.typeMapper = testMapper;

    }

    @PutMapping(path = "/types/{type_id}")
    public ResponseEntity<TestTypeDto> CreatedTestType(@PathVariable("type_id") long type_id , @RequestBody TestTypeDto testTypeDto){

        TestType testType = typeMapper.mapfrom(testTypeDto);
        TestType savedTestType = testTypeServices.saveTestType(type_id, testType);
        return new ResponseEntity<>(typeMapper.mapto(savedTestType) , HttpStatus.CREATED);
    }

    @GetMapping(path = "/types")
    public List<TestTypeDto> GetTypeList(){
          
        List<TestType> result = testTypeServices.getAllTestTypes();
        return result.stream().map(typeMapper::mapto).collect(Collectors.toList());
    }

    @GetMapping(path="/types/{type_id}")
    public ResponseEntity<TestTypeDto> GetTypeID(@PathVariable("type_id") long type_id , @RequestBody TestTypeDto testTypeDto){

        boolean found = testTypeServices.isExist(type_id);
        if(!found){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

        
    }

    // @PutMapping(path = "/types/{type_id}")
    // public ResponseEntity<TestTypeDto> FullUpdate(@PathVariable("type_id") long type_id , @RequestBody TestTypeDto testTypeDto){

    //     boolean found = testTypeServices.isExist(type_id);

    //     if(!found){
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }

    //     testTypeDto.setTypeID(type_id);
    //     TestType testTypeEntity = typeMapper.mapfrom(testTypeDto);
    //     TestType savedTestTypeEntity = testTypeServices.saveTestType(type_id, testTypeEntity);
    //     return new ResponseEntity<>(typeMapper.mapto(savedTestTypeEntity) , HttpStatus.OK);
    // }


    @PatchMapping(path = "/types/{type_id}")
    public ResponseEntity<TestTypeDto> partialUpdate(@PathVariable("type_id") long type_id , @RequestBody TestTypeDto testTypeDto){

        Optional<TestType> result = testTypeServices.findOne(type_id);
        if(!result.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        testTypeDto.setTypeID(type_id);
        TestType testTypeEn = typeMapper.mapfrom(testTypeDto);
        TestType partialUpdatedTestType = testTypeServices.partialUpdated(type_id, testTypeEn);
        return new ResponseEntity<>(typeMapper.mapto(partialUpdatedTestType) , HttpStatus.OK);
    }


    @DeleteMapping(path = "/types/{type_id}")
    public ResponseEntity delete(@PathVariable("type_id")long type_id){
        testTypeServices.delete(type_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
