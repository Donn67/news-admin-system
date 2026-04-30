package com.iov.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iov.dto.ArticleQueryDTO;
import com.iov.dto.ArticleSaveDTO;
import com.iov.dto.ArticleUpdateDTO;
import com.iov.mapper.ArticleMapper;
import com.iov.pojo.Article;
import com.iov.service.ArticleService;
import com.iov.utils.ThreadLocalUtil;
import com.iov.vo.ArticleVO;
import com.iov.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(ArticleSaveDTO dto) {
        Article article = new Article();
        Map<String ,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer) map.get("id");
        BeanUtils.copyProperties(dto,article);
        article.setCreateUser(id);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        save(article);
    }

    @Override
    public PageVO<ArticleVO> pageList(ArticleQueryDTO dto) {
        Page<Article> page=new Page<>(dto.getPageNum(),dto.getPageSize());
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        if (dto.getCategoryId()!=null){
            wrapper.eq(Article::getCategoryId,dto.getCategoryId());}
        if (dto.getState()!=null){
            wrapper.eq(Article::getState,dto.getState());}
        wrapper.orderByDesc(Article::getCreateTime);
        IPage<Article> result=page(page,wrapper);
        List<Article> records = result.getRecords();
        List<ArticleVO> articleVOS = records.stream().map(article -> {
            ArticleVO articleVo = new ArticleVO();
            BeanUtils.copyProperties(article, articleVo);
            return articleVo;
        }).collect(Collectors.toList());
        Long total= result.getTotal();
        PageVO<ArticleVO> pageVO = new PageVO<>();
        pageVO.setItems(articleVOS);
        pageVO.setTotal(total);
        return pageVO;
    }

    @Override
    public void edit(ArticleUpdateDTO dto) {
        Article article = new Article();
        BeanUtils.copyProperties(dto,article);
        article.setUpdateTime(LocalDateTime.now());
        updateById(article);
    }
}
