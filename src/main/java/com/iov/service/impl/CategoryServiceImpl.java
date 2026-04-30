package com.iov.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iov.dto.CategorySaveDTO;
import com.iov.dto.CategoryUpdateDTO;
import com.iov.mapper.CategoryMapper;
import com.iov.pojo.Category;
import com.iov.service.CategoryService;
import com.iov.utils.ThreadLocalUtil;
import com.iov.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper mapper;

    private static CategoryVO apply(Category c) {
        CategoryVO cv = new CategoryVO();
        BeanUtils.copyProperties(c, cv);
        return cv;
    }
    private static Category apply(CategoryUpdateDTO dto){
        Category c=new Category();
        BeanUtils.copyProperties(dto,c);
        return c;
    }
    @Override
    public void add(CategorySaveDTO dto) {
        Category category=new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryAlias(dto.getCategoryAlias());
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String ,Object> map=ThreadLocalUtil.get();
        Integer id=(Integer) map.get("id");
        category.setCreateUser(id);
        this.save(category);
    }

    @Override
    public List<CategoryVO> listAll() {//文章分类共享
//        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
//        Map<String ,Object> map=ThreadLocalUtil.get();
//        Integer id=(Integer) map.get("id");
//        wrapper.eq(Category::getCreateUser,id);
        List<Category> list=mapper.selectList(null);
        return list.stream().map(CategoryServiceImpl::apply).collect(Collectors.toList());
    }

    @Override
    public void edit(CategoryUpdateDTO dto) {
        Category category=apply(dto);
        category.setUpdateTime(LocalDateTime.now());
        updateById(category);
    }
}
