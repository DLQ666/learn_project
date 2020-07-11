package com.learn.acl.controller;


import com.learn.acl.entity.Permission;
import com.learn.acl.service.PermissionService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ResponseResult indexAllPermission() {
        List<Permission> list =  permissionService.findAllMenu();
        return ResponseResult.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public ResponseResult remove(@PathVariable String id) {
        permissionService.delChildById(id);
        return ResponseResult.ok().message("删除成功");
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public ResponseResult doAssign(String roleId,String[] permissionId) {
        permissionService.addRolePermissionRealtionShip(roleId,permissionId);
        return ResponseResult.ok().message("添加成功");
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public ResponseResult toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return ResponseResult.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public ResponseResult save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public ResponseResult updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return ResponseResult.ok();
    }

}

