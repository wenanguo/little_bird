package com.cmtt.base.service.impl;

import com.cmtt.base.entity.Article;
import com.cmtt.base.mapper.ArticleMapper;
import com.cmtt.base.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发布号作者表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-10-15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
