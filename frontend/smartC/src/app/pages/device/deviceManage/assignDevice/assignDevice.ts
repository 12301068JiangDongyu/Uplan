import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Device } from '../../../../entity/device.entity';
import { DeviceClass } from '../../../../entity/deviceclass.entity';

import { DeviceService } from '../../../../service/device.service';

import { nameValidator,numValidator } from '../../../../providers/validator';

// declare var DataTable:any;

// declare var $:any;

@Component({
  selector: 'page-assign-device',
  templateUrl: './assignDevice.html'
})

export class assignDevicePage implements OnInit{
  	//list
    data : DeviceClass[];
    id : number;
    
    judgeMsg = ['删除成功！','该教室不存在！','删除失败！'];
    judgeTip = true;
    tip = '';

  	constructor(
  		private deviceService: DeviceService,
      private router: Router) {
  	} 
  	
  	/**
  	 * [getDeviceTypes 获取设备型号列表]
  	 */
  	getDeviceTypes(): void {
  		this.deviceService.getAssignDevices().then(data=>{
			  this.data = data;
        
  		});
  	}
    /**
     * [getDeviceClassroomId 记录点击删除的教室ID]
     * @param {[type]} id [教室设备ID]
     */
    getDeviceClassroomId(id): void{
      this.id = id;
    }
    /** [deleteDeviceClassroom 删除教室] */
    deleteDeviceClassroom(): void{
      this.judgeTip = false;
      this.deviceService.deleteDeviceClassroom(this.id).then(data=>{
        let judge = Math.abs(data.judge);
        if(judge<=1) this.tip = this.judgeMsg[judge];
        else this.tip = this.judgeMsg[2];//失败
        location.reload();
      })
    }
    /**
     * [openRaspberry 强制开启树莓派]
     * @param {[type]} id [设备ID]
     */
    openRaspberry(id): void{
      this.deviceService.openRaspberry(id).then(data=>{
        let judge = Math.abs(data.judge);
        if(judge!=9) location.reload();
      })
    }

  	ngOnInit(): void{
  		this.getDeviceTypes();
  	}
}