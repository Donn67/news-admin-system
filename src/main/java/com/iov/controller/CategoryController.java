package com.iov.controller;

import com.iov.dto.CategorySaveDTO;
import com.iov.dto.CategoryUpdateDTO;
import com.iov.pojo.Category;
import com.iov.pojo.Result;
import com.iov.service.CategoryService;

import com.iov.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public Result add(@RequestBody @Validated CategorySaveDTO dto){
        categoryService.add(dto);
        return Result.success();
    }
    @GetMapping
    public Result<List<CategoryVO>> list(){
        return Result.success(categoryService.listAll());
    }
    @GetMapping("/{id}")
    public Result<CategoryVO> find(@PathVariable("id") Integer id){
        Category c = categoryService.getById(id);
        CategoryVO cv=new CategoryVO();
        BeanUtils.copyProperties(c,cv);
        return Result.success(cv);
    }
    @PutMapping
    public Result update(@RequestBody @Validated CategoryUpdateDTO dto){
        categoryService.edit(dto);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        categoryService.removeById(id);
        return Result.success();
    }
}
