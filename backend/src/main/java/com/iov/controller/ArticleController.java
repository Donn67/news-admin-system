package com.iov.controller;

import com.iov.dto.ArticleQueryDTO;
import com.iov.dto.ArticleSaveDTO;
import com.iov.dto.ArticleUpdateDTO;
import com.iov.pojo.Result;
import com.iov.service.ArticleService;
import com.iov.vo.ArticleVO;
import com.iov.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article" )
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public Result<String> add(@RequestBody @Validated ArticleSaveDTO dto){
        articleService.add(dto);
        return Result.success();
    }
    @GetMapping
    public Result<PageVO<ArticleVO>> pageList(@Validated ArticleQueryDTO dto){
        PageVO<ArticleVO> page = articleService.pageList(dto);
        return Result.success(page);
    }
    @PutMapping
    public Result update(@Validated ArticleUpdateDTO dto){
        articleService.edit(dto);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        articleService.removeById(id);
        return Result.success();
    }

}
