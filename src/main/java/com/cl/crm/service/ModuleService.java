package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.ModuleMapper;
import com.cl.crm.dao.PermissionMapper;
import com.cl.crm.dao.RoleMapper;
import com.cl.crm.dto.ModuleDto;
import com.cl.crm.dto.TreeDto;
import com.cl.crm.po.Module;
import com.cl.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveModule(Module module){
        /**
         * 1.参数校验
         *     模块名-module_name
         *         非空  同一层级下模块名唯一
         *     url
         *         二级菜单  非空  不可重复
         *     上级菜单-parent_id
         *         一级菜单   null
         *         二级|三级菜单 parent_id 非空 必须存在
         *      层级-grade
         *          非空  0|1|2
         *       权限码 optValue
         *          非空  不可重复
         * 2.参数默认值设置
         *     is_valid  create_date update_date
         * 3.执行添加 判断结果
         */
        String moduleName = module.getModuleName();
        AssertUtil.isTrue(StringUtils.isBlank(moduleName),"菜单名不能为空！");
        Integer grade=module.getGrade();
        AssertUtil.isTrue(null==grade || !(grade==0||grade==1||grade==2),"菜单层级不合法！");
        AssertUtil.isTrue(null !=moduleMapper.queryByModuleName(module.getModuleName(),module.getGrade()),"该层级下菜单名重复!");

        //二级菜单限制条件
        if(grade==1){
            AssertUtil.isTrue(null==module.getUrl(),"二级菜单url不能为空！");
            AssertUtil.isTrue(null!=moduleMapper.queryByUrl(grade,module.getUrl()),"二级菜单url重复！");
        }
        //二、三级菜单限制条件
        if(grade!=0){
            AssertUtil.isTrue(null==module.getParentId(),"上级菜单不能为空！");
            AssertUtil.isTrue(null==selectByPrimaryKey(module.getParentId()),"上级菜单不存在！");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空！");
        AssertUtil.isTrue(null!=moduleMapper.queryOptValue(module.getOptValue()),"权限码重复！");
        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(module)<1,"菜单添加失败！");
    }

    public List<Map<String, Object>> queryAllModulesByGrade(Integer grade){
        return moduleMapper.queryAllModulesByGrade(grade);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module){
        AssertUtil.isTrue(null==module.getId() || null==selectByPrimaryKey(module.getId()),"待更新记录不存在！");
        String moduleName = module.getModuleName();
        AssertUtil.isTrue(StringUtils.isBlank(moduleName),"菜单名不能为空！");
        Integer grade=module.getGrade();
        AssertUtil.isTrue(null==grade || !(grade==0||grade==1||grade==2),"菜单层级不合法！");
        Module temp = moduleMapper.queryByModuleName(moduleName, grade);
        if(null!=temp){
            AssertUtil.isTrue(!(module.getId().equals(temp.getId())),"该层级下菜单名重复!");
        }
        //二级菜单限制条件
        if(grade==1){
            AssertUtil.isTrue(null==module.getUrl(),"二级菜单url不能为空！");
            temp = moduleMapper.queryByUrl(grade, module.getUrl());
            if(null!=temp){
                AssertUtil.isTrue(!(module.getId().equals(temp.getId())),"二级菜单url重复！");
            }
        }
        //二、三级菜单限制条件
        if(grade!=0){
            AssertUtil.isTrue(null==module.getParentId(),"上级菜单不能为空！");
            AssertUtil.isTrue(null==selectByPrimaryKey(module.getParentId()),"上级菜单不存在！");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空！");
        temp =moduleMapper.queryOptValue(module.getOptValue());
        if(null!=temp){
            AssertUtil.isTrue(!(module.getId().equals(temp.getId())),"权限码重复！");
        }
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(module)<1,"更新失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModule(Integer mid){
        AssertUtil.isTrue(null == mid,"待删除记录不存在！");
        Module temp =selectByPrimaryKey(mid);
        AssertUtil.isTrue(temp==null,"待删除记录不存在！");
        //判断是否有子菜单
        Integer count = moduleMapper.countModuleByParentId(mid);
        AssertUtil.isTrue(count>0,"存在子菜单，不支持删除操作！");
        //判断权限表 是否存在
        count = permissionMapper.countPermissionsByModuleId(mid);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByModuleId(mid)<count,"菜单删除失败!");
        }
        temp.setIsValid((byte) 0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"删除失败！");
    }

    public List<ModuleDto> queryUserHasRoleHasModuleDtos(Integer userId){
        List<ModuleDto> moduleDtos= moduleMapper.queryUserHasRoleHasModuleDtos(userId,0,null);
        if(null!=moduleDtos || moduleDtos.size()>0){
            moduleDtos.forEach(moduleDto -> {
                moduleDto.setSubModules(moduleMapper.queryUserHasRoleHasModuleDtos(userId,1,moduleDto.getId()));
            });
        }
        return moduleDtos;
    }

}
