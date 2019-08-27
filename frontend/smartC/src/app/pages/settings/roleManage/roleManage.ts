import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

//entity
import { User } from '../../../entity/user.entity';
import { Role } from '../../../entity/role.entity';
import { ZNode } from '../../../entity/znode.entity';
//service
import { UserService } from '../../../service/user.service';
import { RoleService } from '../../../service/role.service';
import { StorageService } from '../../../service/storage.service';

import { Md5 } from 'ts-md5/dist/md5';
import 'ztree';
declare var $: any;

@Component({
  selector: 'page-role-manage',
  templateUrl: './roleManage.html',
})
export class roleManagePage implements OnInit {
  user: User;
  users: User[];
  roles: Role[];
  oldRname: string;
  id: number;
  //控制模态框
  modalOption = {
    type: 'add',
    name: '添加角色',
    role: { r_id: null, r_name: '', p_ids: [], p_idsStr: '' },
  };
  //tree
  setting: any = {
    check: {
      enable: true,
    },
    data: {
      simpleData: {
        enable: true,
      },
    },
  };
  zNodes: any = [];
  editNodes: any = [];
  permissionNodes: any = [];
  code: any;
  //tip
  judgeTip = {
    addedit: true,
    delete: true, //判断tip是否需要显示 true不需要
  };
  judgeMsg: string[] = [
    '请输入20位以下角色名！', //0
    '该角色已存在！', //1
    '角色不存在！', //2
    '添加失败！', //3
    '修改失败！', //4
    '删除失败！', //5
    '添加成功！', //6
    '修改成功！', //7
    '删除成功！',
  ]; //8
  tip: string = '';

  imgPath = '../assets/adminlte/img/';

  constructor(private userSevice: UserService, private storageService: StorageService, private roleService: RoleService, private router: Router) {
    this.user = this.storageService.read<User>('user');
  }

  /**
   * [gerRoles 初始获取角色列表]
   */
  gerRoles(): void {
    this.roleService.getRoles().then(roles => {
      //console.log(roles);
      this.roles = roles;
    });
  }
  getRole(): void {
    this.roleService.getRole(this.id).then(data => {
      let judge = data.judge;
      if (judge == 0) {
        this.modalOption.role = data.role;
        this.oldRname = data.role.r_name;
        this.getPermissionTree('edit');
      } else {
        this.judgeTip.addedit = false;
        this.tip = this.judgeMsg[2];
        setTimeout(function() {
          location.reload();
        }, 3000);
      }
    });
  }
  getRoleId(id, from): void {
    this.id = id;
    if (from == 'edit') {
      this.getRole();
    }
  }
  /**
   * [getPermissionTree 获取权限树]
   * @param {[type]} from [edit add]
   */
  getPermissionTree(from): void {
    if (from == 'edit') {
      this.modalOption.type = 'edit';
      this.modalOption.name = '修改角色';
      this.getEditTree();
    } else {
      this.modalOption.type = 'add';
      this.modalOption.name = '添加角色';
      this.getAddTree();
    }
  }
  /**
   * [getEditTree 修改权限树]
   */
  getEditTree(): void {
    this.roleService.getPermissions().then(data => {
      this.editNodes = [];
      for (let i = 0; i < data.length; i++) {
        let node: ZNode = { id: 0, pId: 0, name: '', open: true, icon: '../assets/adminlte/img/roll.png', checked: false };
        node.id = data[i]['id'];
        node.pId = data[i]['parent_id'];
        node.name = data[i]['name'];
        node.icon = this.imgPath + data[i]['icon'];
        let p_ids = this.modalOption.role.p_ids;
        if (p_ids.length > 0) {
          if ($.inArray(node.id, p_ids) != -1) {
            node.checked = true;
          }
        }
        this.editNodes.push(node);
      }
      this.initTree('edit');
    });
  }
  /**
   * [getAddTree 添加权限树]
   */
  getAddTree(): void {
    //如果树不为空，则不获取
    if (this.zNodes.length == 0) {
      this.modalOption.role = { r_id: null, r_name: '', p_ids: [], p_idsStr: '' };
      this.roleService.getPermissions().then(data => {
        for (let i = 0; i < data.length; i++) {
          let node: ZNode = { id: 0, pId: 0, name: '', open: true, icon: '../assets/adminlte/img/roll.png', checked: false };
          node.id = data[i]['id'];
          node.pId = data[i]['parent_id'];
          node.name = data[i]['name'];
          node.icon = this.imgPath + data[i]['icon'];
          this.zNodes.push(node);
        }
        this.initTree('add');
      });
    }
  }
  /**
   * zTree
   */
  setCheck(): void {
    var zTree = $.fn.zTree.getZTreeObj('ztree'),
      type = { Y: 'ps', N: 'ps' };
    zTree.setting.check.chkboxType = type;
    this.showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
  }
  showCode(str): void {
    if (!this.code) this.code = $('#code');
    this.code.empty();
    this.code.append('<li>' + str + '</li>');
  }
  /**
   * [initTree 初始化树]
   * @param {[type]} from [edit add]
   */
  initTree(from): void {
    if (from == 'edit') {
      $.fn.zTree.init($('#ztree'), this.setting, this.editNodes);
    } else {
      $.fn.zTree.init($('#ztree'), this.setting, this.zNodes);
    }

    this.setCheck();
    $('#py').bind('change', this.setCheck);
    $('#sy').bind('change', this.setCheck);
    $('#pn').bind('change', this.setCheck);
    $('#sn').bind('change', this.setCheck);
  }

  /**
   * [getPermissionCheckedAll 获取所有选中的权限]
   */
  getPermissionCheckedAll(): void {
    var treeObj = $.fn.zTree.getZTreeObj('ztree');
    var nodes = treeObj.getCheckedNodes(true);
    this.permissionNodes = [];
    for (var i = 0; i < nodes.length; i++) {
      this.permissionNodes.push(nodes[i].id);
    }
    console.log(this.permissionNodes);
  }
  /**
   * [addRole 添加角色]
   */
  addRole(): void {
    this.getPermissionCheckedAll();
    let rname = this.modalOption.role.r_name;
    //判断用户输入角色名
    if (this.validateInput()) {
      this.roleService.addRole(rname, this.permissionNodes).then(data => {
        console.log(data);
        let judge = Math.abs(data.judge);
        this.judgeTip.addedit = false;
        //添加成功
        if (judge == 0) {
          this.tip = this.judgeMsg[6];
          location.reload();
        }
        //添加失败
        else if (judge == 9) {
          this.tip = this.judgeMsg[3];
        }
        //其他
        else {
          this.tip = this.judgeMsg[judge - 1];
        }
      });
    }
  }
  editRole(): void {
    this.getPermissionCheckedAll();
    let rname = this.modalOption.role.r_name;
    let judge = true;
    //判断角色名有无修改
    if (rname == this.oldRname) {
      judge = false;
    }
    //验证角色名
    if (this.validateInput()) {
      console.log(this.permissionNodes);
      this.roleService.editRole(this.id, rname, this.permissionNodes, judge).then(data => {
        console.log(data);
        let judge = Math.abs(data.judge);
        this.judgeTip.addedit = false;
        //成功
        if (judge == 0) {
          this.tip = this.judgeMsg[7];
          if (rname == this.user.role.r_name) {
            this.tip += '修改本账户权限，请重新登录！';
            this.storageService.remove('user');
          }
          location.reload();
        }
        //失败
        else if (judge == 9) {
          this.tip = this.judgeMsg[4];
        }
        //其他
        else {
          this.tip = this.judgeMsg[judge - 1];
        }
      });
    }
  }
  /**
   * [deleteRole 删除角色]
   */
  deleteRole(): void {
    this.roleService.deleteRole(this.id).then(data => {
      //显示消息提示
      this.judgeTip.delete = false;
      let judge = data.judge;
      //成功
      if (judge == 0) {
        this.tip = this.judgeMsg[8];
        if (this.id == this.user.role.r_id) {
          this.tip += '删除本账户角色，请重新登录！';
          this.storageService.remove('user');
        }
        location.reload();
      }
      //用户不存在
      else if (judge == -1) {
        this.tip = this.judgeMsg[2];
        location.reload();
      }
      //删除失败
      else {
        this.tip = this.judgeMsg[5];
      }
    });
  }
  /**
   * [validateInput 查看用户有无输入角色名称]
   * @return {boolean} [description]
   */
  validateInput(): boolean {
    let rname = this.modalOption.role.r_name;
    let re = /^[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$/;
    if (!re.test(rname)) {
      this.judgeTip.addedit = false;
      this.tip = this.judgeMsg[0];
      return false;
    }
    return true;
  }
  ngOnInit(): void {
    this.gerRoles();
  }
}
