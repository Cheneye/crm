package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.dto.ModuleDto;
import com.cl.crm.dto.TreeDto;
import com.cl.crm.po.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    List<TreeDto> queryAllModules();

    Module queryByModuleName(@Param(value = "moduleName") String moduleName,@Param(value = "grade") Integer grade);

    Module queryByUrl(@Param(value = "grade") Integer grade,@Param("url") String url);

    Module queryOptValue(String optValue);

    List<Map<String, Object>> queryAllModulesByGrade(Integer grade);

    Integer countModuleByParentId(Integer mid);

    List<ModuleDto> queryUserHasRoleHasModuleDtos(@Param("userId") Integer userId,@Param("grade") int grade,@Param("parentId") Integer parentId);
}