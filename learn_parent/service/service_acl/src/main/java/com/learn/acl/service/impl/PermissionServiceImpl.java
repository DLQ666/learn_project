package com.learn.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.acl.entity.Permission;
import com.learn.acl.entity.RolePermission;
import com.learn.acl.entity.User;
import com.learn.acl.helper.MemuHelper;
import com.learn.acl.helper.PermissionHelper;
import com.learn.acl.mapper.PermissionMapper;
import com.learn.acl.service.PermissionService;
import com.learn.acl.service.RolePermissionService;
import com.learn.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserService userService;
    
    //获取全部菜单
    @Override
    public List<Permission> queryAllMenu() {

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        List<Permission> result = bulid(permissionList);

        return result;
    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }

    //给角色分配权限
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {

        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));

  

        List<RolePermission> rolePermissionList = new ArrayList<>();
        for(String permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
      
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    //递归删除菜单
    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }

    //===========================递归查询菜单================================================
    //获取全部菜单
    @Override
    public List<Permission> findAllMenu() {
        //1、查询菜单表所有数据
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(queryWrapper);

        //2、查询出来的 所有菜单list集合 按照要求进行封装
        List<Permission> resultList = bulidPermission(permissionList);
        return resultList;
    }
    //把 返回所有菜单list集合 进行封装的方法
    public static List<Permission> bulidPermission(List<Permission> permissionList) {

        //创建一个List集合，用于最终的数据封装
        List<Permission> finalNode = new ArrayList<>();
        //把所有菜单的list集合遍历，得到顶层菜单 pid=0菜单，设置level值为1
        //这样就得到递归的入口    //增强for循环缩写 iter
        for (Permission permissionNode : permissionList) {
            //得到顶层菜单 pid=0菜单，设置level值为1
            if ("0".equals(permissionNode.getPid())){
                //设置顶层菜单的level为1
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面继续查询子菜单,封装到finalNode里面
                finalNode.add(selectChildren(permissionNode,permissionList));
            }
        }
        return finalNode;
    }
    //根据顶层菜单，向里面继续查询子菜单,封装到finalNode里面
    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        //1、因为向一级菜单里存入二级菜单，二级菜单里要存三级菜单。等。。。先把对象初始化
        permissionNode.setChildren(new ArrayList<>());

        //2、遍历所有菜单list集合，进行判断比较，比较 id 和 pid 值是否相同
        for (Permission it : permissionList) {
            //it 为 每个菜单的对象
            //判断id 和 pid 值是否相同
            if (permissionNode.getId().equals(it.getPid())){
                //把父菜单的level值+1
                int level = permissionNode.getLevel()+1;
                it.setLevel(level);
                //如果children为空，进行初始化
                if (permissionNode.getChildren() == null){
                    permissionNode.setChildren(new ArrayList<>());
                }
                //把查询出来的子菜单放入到父菜单
                permissionNode.getChildren().add(selectChildren(it,permissionList));
            }
        }
        return permissionNode;
    }

    //===============================递归删除菜单====================================================
    //递归删除菜单
    @Override
    public void delChildById(String id) {
        //1、创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        //2、向 idList 集合设置删除菜单id
        this.selectPermissonChildById(id,idList);

        //把当前 id 封装到 idList 里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //2、根据当前菜单id，查询菜单中子菜单的id，封装到list集合
    private void selectPermissonChildById(String id, List<String> idList) {
        //1、查询菜单中子菜单的id
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        queryWrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(queryWrapper);

        //把 childIdList 里面菜单id值取出来，封装到idList里面，在做递归查询下一层子菜单封装到idList中去，以此类推
        childIdList.stream().forEach(item->{  // JDK8写法 lamanda表达式 此方法遍历跟for循环遍历一样，，多种写法
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.selectPermissonChildById(item.getId(),idList);
        });
    }

    //=====================================给角色添加权限==========================================
    //给角色添加权限
    @Override
    public void addRolePermissionRealtionShip(String roleId, String[] permissionId) {
        //roleId 角色id
        //permissionId 菜单id 数组形式

        //1、创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionsList = new ArrayList<>();
        //遍历所有菜单数组
        for (String perId : permissionId) {
            //RolePermission 对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionsList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionsList);
    }
}
