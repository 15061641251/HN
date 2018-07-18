package com.sbq.service.impl;

import com.sbq.dao.IMyFileDao;
import com.sbq.entity.MyFile;
import com.sbq.service.BaseService;
import com.sbq.service.IMyFileService;
import com.sbq.tools.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by zhangyuan on 2017/3/28.
 */
@Service
public class MyFileServiceImpl extends BaseService implements IMyFileService {

    @Autowired
    private IMyFileDao myFileDao;

    @Override
    public void insertMyFile(MyFile file) {
        myFileDao.insert(file);
    }

    @Override
    public MyFile selectByUUID(String uuid) {

        Example example = new Example(MyFile.class);
        example.createCriteria().andEqualTo("uuid", uuid);

        List<MyFile> myFileList = myFileDao.selectByExample(example);

        if (!NullUtil.isNull(myFileList)) {
            return myFileList.get(0);
        }
        return null;
    }

    @Override
    public List<MyFile> selectMyFileByIds(String file_ids) {
        return myFileDao.selectMyFileByIds(file_ids);
    }
}
