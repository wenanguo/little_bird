package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbSubject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 栏目表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
public interface ILbSubjectService extends IService<LbSubject> {

    public IPage<LbSubject> getLbSubjectPostList(IPage<LbSubject> page);
}
