import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Device } from '../../../../entity/device.entity';
import { Camera } from '../../../../entity/camera.entity';
import { Computer } from '../../../../entity/computer.entity';
import { Raspberry } from '../../../../entity/raspberry.entity';
import { Projector } from '../../../../entity/projector.entity';
import { Singlechip } from '../../../../entity/singlechip.entity';
import { DeviceService } from '../../../../service/device.service';

import { nameValidator,numValidator } from '../../../../providers/validator';

// declare var DataTable:any;

// declare var $:any;

@Component({
  selector: 'page-device-info',
  templateUrl: './deviceInfo.html'
})
export class deviceInfoPage implements OnInit{
  	//list
    computers : Computer[];
    cameras : Camera[];
    raspberrys : Raspberry[];
    projectors : Projector[];
    singlechips : Singlechip[];
    
    computerModel : Computer = {computerTypeId : null,computerTypeName : '',diskSize : '',memorySize : '',operatingSystem : ''};
    cameraModel : Camera = {cameraId : null,cameraTypeId : null,cameraStatus : null,cameraAngle : '',cameraTypeName:''};
    raspberryModel : Raspberry = {raspberryTypeId : null,raspberryTypeName : ''};
    projectorModel : Projector = {projectorTypeId : null,projectorTypeName : ''};
    singlechipModel : Singlechip = {singlechipTypeId : null,singlechipTypeName : ''};
    
    deleteMsg = {
      id : null,
      type : ''
    }
    /**
     * [judgeTips 消息提示]
     * @type {Object}
     * status 判断是true：添加 false：修改模态框
     * addEdit 添加修改消息提示是否显示 true：不显示  
     * delete 删除消息提示是否显示
     */
    judgeTips = {
      status : true,
      addEdit : true,
      delete : true
    }
    judgeMsg = ['信息输入不合法！','信息不能为空！','该设备型号已存在!','该设备型号不存在！',
                '添加成功！','修改成功！','删除成功！',
                '添加失败！','修改失败！','删除失败！'];
    tip = '';

    urls = ['computerType/','cameraType/','projectorType/','raspberryType/','singleChipType/']
  	constructor(
  		private deviceService: DeviceService,
      private router: Router) {
  	} 
  	
  	/**
  	 * [getDeviceTypes 获取设备型号列表]
  	 */
  	getDeviceTypes(): void {
  		this.deviceService.getDeviceTypes().then(data=>{
			  this.computers = data.computerTypeList;
        this.cameras = data.cameraTypeList;
        this.raspberrys = data.raspberryTypeList;
        this.projectors = data.projectorTypeList;
        this.singlechips = data.singleChipTypeList;
  		});
  	}
    /**
     * [resetModal 清除模态框数据 切换成add状态]
     */
    resetModal(): void{
      this.judgeTips.status = true;
      this.judgeTips.addEdit = true;
      this.computerModel = {computerTypeId : null,computerTypeName : '',diskSize : '',memorySize : '',operatingSystem : ''};
      this.cameraModel = {cameraId : null,cameraTypeId : null,cameraStatus : null,cameraAngle : '',cameraTypeName:''};
      this.raspberryModel = {raspberryTypeId : null,raspberryTypeName : ''};
      this.projectorModel = {projectorTypeId : null,projectorTypeName : ''};
      this.singlechipModel = {singlechipTypeId : null,singlechipTypeName : ''};
    }

    /**
     * [handleComputer 添加或修改电脑]
     * @param {[type]} type [add edit]
     */
    handleComputer(type): void {
      if(nameValidator(this.computerModel.computerTypeName)&&nameValidator(this.computerModel.operatingSystem)&&
        numValidator(this.computerModel.diskSize)&&numValidator(this.computerModel.memorySize)){
        if(type == 'add'){
          this.deviceService.addType(this.computerModel,this.urls[0]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          });
        }else{
          this.deviceService.editType(this.computerModel.computerTypeId,this.computerModel,this.urls[0]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          })
        }
      }else{
        this.judgeTips.addEdit = false;
        this.tip = this.judgeMsg[0];
      }
    }

    /**
     * [handleCamera 添加或修改摄像头]
     * @param {[type]} type [add edit]
     */
    handleCamera(type):void{
      console.log(nameValidator(this.cameraModel.cameraTypeName));
      if(nameValidator(this.cameraModel.cameraTypeName)){
        if(type == 'add'){
          this.deviceService.addType(this.cameraModel,this.urls[1]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          });
        }else{
          this.deviceService.editType(this.cameraModel.cameraTypeId,this.cameraModel,this.urls[1]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          })
        }
      }else{
        this.judgeTips.addEdit = false;
        this.tip = this.judgeMsg[0];
      }
    }

    /**
     * [handleProjector 添加或修改投影仪]
     * @param {[type]} type [add edit]
     */
    handleProjector(type): void{
      if(nameValidator(this.projectorModel.projectorTypeName)){
        if(type == 'add'){
          this.deviceService.addType(this.projectorModel,this.urls[2]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          });
        }else{
          this.deviceService.editType(this.projectorModel.projectorTypeId,this.projectorModel,this.urls[2]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          })
        }
      }else{
        this.judgeTips.addEdit = false;
        this.tip = this.judgeMsg[0];
      }
    }  
    /**
     * [handleRaspberry 添加或修改树莓派]
     * @param {[type]} type [add edit]
     */
    handleRaspberry(type): void{
      if(nameValidator(this.raspberryModel.raspberryTypeName)){
        if(type == 'add'){
          this.deviceService.addType(this.raspberryModel,this.urls[3]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          });
        }else{
          this.deviceService.editType(this.raspberryModel.raspberryTypeId,this.raspberryModel,this.urls[3]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          })
        }
      }else{
        this.judgeTips.addEdit = false;
        this.tip = this.judgeMsg[0];
      }
    }  
    /**
     * [handleSinglechip 添加或修改单片机]
     * @param {[type]} type [add edit]
     */
    handleSinglechip(type): void{
      if(nameValidator(this.singlechipModel.singlechipTypeName)){
        if(type == 'add'){
          this.deviceService.addType(this.singlechipModel,this.urls[4]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          });
        }else{
          this.deviceService.editType(this.singlechipModel.singlechipTypeId,this.singlechipModel,this.urls[4]).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          })
        }
      }else{
        this.judgeTips.addEdit = false;
        this.tip = this.judgeMsg[0];
      }
    }

    /**
     * [getDeviceInfo 获取设备类型信息]
     * @param {[type]} type   [类型 computer camera]
     * @param {[type]} entity [设备实体]
     */
    getDeviceInfo(type,entity): void{
      //切换成修改按钮
      this.judgeTips.status = false;
      switch (type) {
        case "computer":
          this.computerModel = entity;
          break;
        case "camera":
          this.cameraModel = entity;
          break;
        case "raspberry":
          this.raspberryModel = entity;
          break;
        case "projector":
          this.projectorModel = entity;
          break;
        case "singlechip":
          this.singlechipModel = entity;
          break;
      }
    }

    /**
     * [getDeviceId 获取设备类型ID]
     * @param {[type]} id   [设备类型ID]
     * @param {[type]} type [设备类型]
     */
    getDeviceId(id,type){
      this.deleteMsg.id = id;
      this.deleteMsg.type = type;
    }

    /**
     * [deleteDeviceType 删除设备类型]
     */
    deleteDeviceType(): void{
      this.deviceService.deleteType(this.deleteMsg.id,this.deleteMsg.type).then(data=>{
        this.handleMsg('delete',Math.abs(data.judge));
      })
    }

    /**
     * [handleMsg 处理服务器返回的消息]
     * @param {[type]} type  [事件类型 add edit delete]
     * @param {[type]} judge [消息值]
     */
    handleMsg(type,judge): void{
      switch (type) {
        case "add":
          this.judgeTips.addEdit = false;
          if(judge==0) { this.tip = this.judgeMsg[4]; location.reload();}
          else if(judge < 5){ this.tip = this.judgeMsg[1];}
          else if(judge == 5){ this.tip = this.judgeMsg[2];}
          else{ this.tip = this.judgeMsg[7]; location.reload();}
          break;
        case "edit":
        this.judgeTips.addEdit = false;
          if(judge==0) { this.tip = this.judgeMsg[5];}
          else if(judge < 5){ this.tip = this.judgeMsg[1];}
          else if(judge == 5){ this.tip = this.judgeMsg[2];}
          else if(judge == 6){ this.tip = this.judgeMsg[3];}
          else{ this.tip = this.judgeMsg[8]; }
          location.reload();
          break;
        case "delete":
          this.judgeTips.delete = false;
          console.log(judge);
          if(judge==0) { this.tip = this.judgeMsg[6];}
          else if(judge == 1){ this.tip = this.judgeMsg[3];}
          else{ this.tip = this.judgeMsg[9]; }
          location.reload();
          break;
      }
    }
  	ngOnInit(): void{
  		this.getDeviceTypes();
  	}
}