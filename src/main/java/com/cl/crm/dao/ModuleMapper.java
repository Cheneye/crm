package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.dto.TreeDto;
import com.cl.crm.po.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    List<TreeDto> queryAllModules();

}