package com.pku.system.service.impl;

import com.pku.system.dao.StatisticDao;
import com.pku.system.model.Statistic;
import com.pku.system.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName StatisticServiceImpl
 * @Description: TODO
 * @Author jiangdongyu
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Service
public class StatisticServiceImpl implements StatisticService{
    @Autowired
    private StatisticDao statisticDao;


    @Override
    public List<Statistic> selectAll() {
        return statisticDao.selectAll();
    }
}
