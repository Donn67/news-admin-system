package com.iov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iov.dto.ArticleQueryDTO;
import com.iov.dto.ArticleSaveDTO;
import com.iov.dto.ArticleUpdateDTO;
import com.iov.pojo.Article;
import com.iov.vo.ArticleVO;
import com.iov.vo.PageVO;

public interface ArticleService extends IService<Article> {
    void add(ArticleSaveDTO dto);

    PageVO<ArticleVO> pageList(ArticleQueryDTO dto);

    void edit(ArticleUpdateDTO dto);
}
