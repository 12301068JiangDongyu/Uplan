import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { UserService } from '../../service/user.service';
import { User } from '../../entity/user.entity';
import { StorageService } from '../../service/storage.service';

@Component({
  selector: 'sidebar-component',
  templateUrl: './sidebar.html',
  providers: [StorageService],
})
export class SidebarComponent {
  user: User;

  judgeOptions = {
    infoManage: true,
    carMissionManage: true,
    systemManage: true,
    statisticsManage: true,
    carTypeManage: true,
    carManage: true,
    carRecord: true,
    carApply: true,
    myApply: true,
    carCheck: true,
    privilegeManage: true,
    userManage: true,
    roleManage: true,
    statistics: true,
  };

  constructor(public router: Router, private storageService: StorageService) {
    this.user = this.storageService.read<User>('user');
    this.changeSiderStatus();
    // Push a search term into the observable stream.
  }

  changeSiderStatus(): void {
    var permissions = this.user.role.p_ids;
    for (let i = 0; i < permissions.length; i++) {
      this.permissionMap(permissions[i]);
    }
  }

  permissionMap(permission): void {
    switch (permission) {
      case 11:
        this.judgeOptions.infoManage = false;
        break;
      case 21:
        this.judgeOptions.carMissionManage = false;
        break;
      case 31:
        this.judgeOptions.statisticsManage = false;
        break;
      case 41:
        this.judgeOptions.systemManage = false;
      case 111:
        this.judgeOptions.carTypeManage = false;
        break;
      case 112:
        this.judgeOptions.carManage = false;
        break;
      case 113:
        this.judgeOptions.carRecord = false;
        break;
      case 211:
        this.judgeOptions.carApply = false;
        break;
      case 212:
        this.judgeOptions.myApply = false;
        break;
      case 213:
        this.judgeOptions.carCheck = false;
        break;
      case 311:
        this.judgeOptions.statistics = false;
        break;
      case 411:
        this.judgeOptions.privilegeManage = false;
        break;
      case 4111:
        this.judgeOptions.userManage = false;
        break;
      case 4112:
        this.judgeOptions.roleManage = false;
        break;
      default:
        // code...
        break;
    }
  }
}
