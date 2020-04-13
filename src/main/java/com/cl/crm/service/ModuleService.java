package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.ModuleMapper;
import com.cl.crm.dao.PermissionMapper;
import com.cl.crm.dao.RoleMapper;
import com.cl.crm.dto.TreeDto;
import com.cl.crm.po.Module;
import com.cl.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RoleMapper roleMapper;

    public List<TreeDto> queryAllModules(Integer roleId){
        List<TreeDto> treeDtos = moduleMapper.queryAllModules();
        AssertUtil.isTrue(null==roleId||roleMapper.selectByPrimaryKey(roleId)==null,"该角色不存在！");
        //勾选已授权的权限
        List<Integer> moduleIds= permissionMapper.queryRoleHasAllModuleIdsByRoleId(roleId);
        if(moduleIds.size()>0&& null!=moduleIds){
            treeDtos.forEach(treeDto -> {
                if (moduleIds.contains(treeDto.getId())){
                    treeDto.setChecked(true);
                }
            });
        }
        return treeDtos;
    }
}
