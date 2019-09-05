import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

//entity
import { User } from '../../../entity/user.entity';
import { Role } from '../../../entity/role.entity';
//service
import { UserService } from '../../../service/user.service';
import { RoleService } from '../../../service/role.service';
import { StorageService } from '../../../service/storage.service';

import { Md5 } from 'ts-md5/dist/md5';

declare var $: any;

@Component({
  selector: 'page-user-manage',
  templateUrl: './userManage.html',
})
export class userManagePage implements OnInit {
  me: User;
  users: User[];
  roles: Role[];
  //add
  addInputName: string = '';
  addSelectRole: number = 0;
  addInputPassword: string = '123456';
  addRealName: string = '';
  addLicense: string = '';

  //edit
  editInputName: string = '';
  editSelectRole: number = 0;
  editRadio: string = 'no';
  editInputPassword: string;
  editRealName: string = '';
  editLicense: string = '';
  user: User;

  //delete
  userId: number;

  //tip
  judgeAddInput: boolean = true; //提示信息是否显示 true不显示 false验证失败显示
  judgeEditInput: boolean = true;
  judgeDelete: boolean = true;
  judgeMsg: string[] = [
    '请输入20位以下用户名！', //0
    '请输入6位以上密码！', //1
    '该用户已存在！', //2
    '用户不存在！', //3
    '添加失败！', //4
    '修改失败！', //5
    '删除失败！', //6
    '添加成功！', //7
    '修改成功！', //8
    '删除成功！',
  ]; //9
  tip: string = '';

  constructor(private userSevice: UserService, private storageService: StorageService, private roleService: RoleService, private router: Router) {
    this.me = this.storageService.read<User>('user');
  }

  /**
   * [getUsers 初始获取用户列表]
   */
  getUsers(): void {
    this.userSevice.getUsers().then(data => {
      console.log(data);
      this.users = data.userList;
      this.roles = data.roleList;
      if (this.roles.length > 0) {
        this.addSelectRole = this.roles[0].r_id;
      }
    });
  }
  /**
   * [getUserInfo 根据用户ID获取单个用户信息]
   * @param {[type]} id [用户ID]
   */
  getUserInfo(id): void {
    this.userSevice.getUser(id).then(data => {
      console.log(data);
      this.user = data.user;
      this.editInputName = this.user.username;
      this.editSelectRole = this.user.r_id;
      this.editRealName = this.user.real_name;
      this.editLicense = this.user.license;
    });
  }

  /**
   * [getUserId 获取点击删除的ID]
   * @param {[type]} id [用户ID]
   */
  getUserId(id): void {
    this.userId = id;
  }
  /**
   * [addUser 添加用户]
   */
  addUser(): void {
    this.validateInput(this.addInputName, this.addInputPassword);
    if (this.judgeAddInput) {
      this.userSevice.addUser(this.addInputName, this.addInputPassword, this.addSelectRole, this.addRealName, this.addLicense).then(data => {
        let judge = Math.abs(data.judge);
        this.judgeAddInput = false;
        if (judge == 0) {
					this.tip = this.judgeMsg[7];
					this.reGetUsers();
					$('#addModal').modal('hide');
          // location.reload();
          //this.judgeAddInput = true;
        } else if (judge == 9) {
          this.tip = this.judgeMsg[4];
        } else {
          //judge 1、2、3
          this.tip = this.judgeMsg[judge - 1];
        }
      });
    }
  }
  /**
   * [editUser 修改用户]
   */
  editUser(): void {
    let editUser: User = this.user;
    let judge = true;
    editUser.username = this.editInputName;
    editUser.r_id = this.editSelectRole;
    editUser.real_name = this.editRealName;
    editUser.license = this.editLicense;
    //判断是否需要修改密码
    if (this.editRadio == 'yes') {
      editUser.password = this.editInputPassword;
      //加密
      editUser.password = Md5.hashStr(editUser.password).toString();
    }
    //判断用户名有无修改
    if (editUser.username == this.user.username) {
      judge = false;
    }
    this.validateInput(editUser.username, editUser.password);

    //校验通过
    if (this.judgeEditInput) {
      this.userSevice.editUser(editUser, judge).then(data => {
        let judge = Math.abs(data.judge);
        let count = 0;
        this.judgeEditInput = false;
        if (judge == 0) {
          this.tip = this.judgeMsg[8];
          if (editUser.username != this.me.username) {
            this.tip += '修改本账户名，';
            count++;
          }
          if (editUser.r_id != this.me.r_id) {
            this.tip += '修改本账户角色，';
            count++;
          }
          if (count > 0) {
            this.tip += '请重新登录！';
            this.storageService.remove('user');
					}
					this.reGetUsers();
					$('#editModal').modal('hide');
          // location.reload();
          //this.judgeEditInput = true;
        } else if (judge == 9) {
          this.tip = this.judgeMsg[5];
        } else {
          //judge 1、2、3
          this.tip = this.judgeMsg[judge - 1];
        }
      });
    }
  }

  /**
   * [deleteUser 删除用户]
   */
  deleteUser(): void {
    this.userSevice.deleteUser(this.userId).then(data => {
      this.judgeDelete = false;
      if (data.judge == 0) {
        this.tip = this.judgeMsg[9];
        if (this.userId == this.me.id) {
          this.tip += '删除本账户！';
          this.storageService.remove('user');
				}
				this.reGetUsers();
				$('#deleteModal').modal('hide');
        // location.reload();
        //this.judgeDelete = true;
      } else {
        if (data.judge == -1) {
					this.tip = this.judgeMsg[4];
        } else {
          this.tip = this.judgeMsg[6];
        }
      }
      console.log(data);
    });
  }
  /**
   * [validateInput 确认用户输入信息是否正确]
   * @param {[type]} username [用户名]
   * @param {[type]} password [密码]
   */
  validateInput(username, password): void {
    var usernameRegex = /^[(\u4e00-\u9fa5)0-9a-zA-Z\_\s@]{1,20}$/;
    var passwordRegex = /^[a-zA-Z0-9_]{6,}$/;
    this.judgeAddInput = false;
    this.judgeEditInput = false;
    if (!usernameRegex.test(username)) {
      this.tip = this.judgeMsg[0];
    } else if (!passwordRegex.test(password)) {
      this.tip = this.judgeMsg[1];
    } else {
      this.judgeAddInput = true;
      this.judgeEditInput = true;
    }
  }

  reGetUsers(): void {
    this.addInputName = '';
    this.addSelectRole = 0;
    this.addInputPassword = '123456';
    this.addRealName = '';
    this.addLicense = '';
    this.getUsers();
  }

  ngOnInit(): void {
    this.getUsers();
  }
}
