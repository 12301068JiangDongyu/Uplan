import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { UserService } from '../../service/user.service';
import { User } from '../../entity/user.entity';
import { StorageService } from '../../service/storage.service';


@Component({
  selector: 'sidebar-component',
  templateUrl: './sidebar.html',
  providers: [StorageService]
})
export class SidebarComponent {
  user: User;
  
  judgeOptions = {
    deviceManage : true,
    videoManage : true,
    systemManage : true,
    editDevice : true,
    deviceInfo : true,
    assignDevice : true,
    deviceMonitor : true,
    videoStream: true,
    buildClassManage : true,
    privilegeManage : true,
    userManage : true,
    roleManage : true,
    messageManage : true
  };

  constructor(
  	public router: Router,
    private storageService: StorageService) {
  	this.user = this.storageService.read<User>('user');
    this.changeSiderStatus();
  // Push a search term into the observable stream.
  }

  changeSiderStatus(): void{
    var permissions = this.user.role.p_ids;
    for(let i=0;i<permissions.length;i++){
      this.permissionMap(permissions[i]);
    }
  }

  permissionMap(permission): void{
    switch (permission) {
      case 11:
        this.judgeOptions.deviceManage = false;
        break;
      case 21:
        this.judgeOptions.videoManage = false;
        break;
      case 31:
        this.judgeOptions.systemManage = false;
        break;
      case 111:
        this.judgeOptions.editDevice = false;
        break;
      case 112:
        this.judgeOptions.deviceMonitor = false;
        break;
      case 211:
        this.judgeOptions.videoStream = false;
        break;
      case 311:
        this.judgeOptions.buildClassManage = false;
        break;
      case 312:
        this.judgeOptions.privilegeManage = false;
        break;
      case 313:
        this.judgeOptions.messageManage = false;
        break;
      case 1111:
        this.judgeOptions.deviceInfo = false;
        break;
      case 1112:
        this.judgeOptions.assignDevice = false;
        break;
      case 3121:
        this.judgeOptions.userManage = false;
        break;
      case 3122:
        this.judgeOptions.roleManage = false;
        break;
      default:
        // code...
        break;
    }
  }
}
