package com.iov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iov.dto.CategorySaveDTO;
import com.iov.dto.CategoryUpdateDTO;
import com.iov.pojo.Category;
import com.iov.vo.CategoryVO;

import java.util.List;

public interface CategoryService extends IService<Category> {
    void add(CategorySaveDTO dto);

    List<CategoryVO> listAll();

    void edit(CategoryUpdateDTO dto);
}
