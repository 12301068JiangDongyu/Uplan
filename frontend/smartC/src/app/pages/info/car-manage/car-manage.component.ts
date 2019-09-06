import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Car } from '../../../entity/car-manage.entity';
import { CarService } from '../../../service/car-manage.service';
import { Cartype } from '../../../entity/cartype.entity';
import { CarTypeService } from '../../../service/cartype.service';

declare var $: any;
@Component({
  selector: 'app-car-manage',
  templateUrl: './car-manage.component.html',
  styleUrls: ['./car-manage.component.css']
})
export class CarManageComponent implements OnInit {

  //list
    cars : Car[];
    cartypes: Cartype[];
    carModel : Car = {id : null,license_plate_num : '',brand: '',type:null,status :null, car_type_id: null,mileage:null,oil_remained:null,run_time: ''};
    // carTypeModel: Cartype = { id: null, brand: '', capacity: '', price: null, buy_time: '', seat_num: null, oil_type: null };
    
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
    judgeMsg = ['信息输入不合法！','信息不能为空！','该车辆已存在!','该车辆不存在！',
                '添加成功！','修改成功！','删除成功！',
                '添加失败！','修改失败！','删除失败！'];
    tip = '';

    urls = ['carType/']
  	constructor(
  		private carService: CarService,
      private cartypeService: CarTypeService,
      private router: Router) {
  	} 
  	
  	/**
  	 * [getCarNotes 获取车辆信息列表]
  	 */
  
  	getCarNotes(): void {
  		this.carService.getCarNotes().then(data=>{
			  this.cars = data.carList;
        		});
  	}

  getCarTypes(): void {
    this.cartypeService.getCarTypes().then(data => {
      this.cartypes = data.carTypeList;
    });
  }
  

    /**
     * [resetModal 清除模态框数据 切换成add状态]
     */
    resetModal(): void{
      this.judgeTips.status = true;
      this.judgeTips.addEdit = true;
      this.carModel = {id : null,license_plate_num : '',brand: '',type: null,status : null, car_type_id: null,mileage:null,oil_remained:null,run_time: ''};
      $('#dateTimePicker').datetimepicker('update', new Date());
    }

    /**
     * [handleCar 添加或修改车辆]
     * @param {[type]} type [add edit]
     */
    // handleCar(type): void {
    //    let datetime = $("#dateTimePicker").data("datetimepicker").getDate().getTime();
    //     if(type == 'add'){
    //       this.carService.addType(Object.assign(this.carModel,{ run_time: datetime })).then(data=>{
    //         console.log(data);
    //         this.handleMsg(type,Math.abs(data.judge));
    //       });
    //     }else{
    //       this.carService.editType(Object.assign(this.carModel,{ run_time: datetime })).then(data=>{
    //         console.log(data);
    //         this.handleMsg(type,Math.abs(data.judge));
    //       })
    //     }
  
    // }
handleCar(type): void {
    let datetime = $("#dateTimePicker").data("datetimepicker").getDate().getTime();
    console.log(datetime);

    if (type == 'add') {
      this.carService.addType(Object.assign(this.carModel, { run_time: datetime })).then(data => {
        console.log(data);
        this.handleMsg(type, Math.abs(data.judge));
      });
    } else {
      this.carService.editType(this.carModel.id, Object.assign(this.carModel, { run_time: datetime })).then(data => {
        console.log(data);
        this.handleMsg(type, Math.abs(data.judge));
      });
    }
  }
    

    /**
     * [getDeviceId 获取设备类型ID]
     * @param {[type]} id   [设备类型ID]
     * @param {[type]} type [设备类型]
     */
    getCarId(id,type){
      this.deleteMsg.id = id;
      this.deleteMsg.type = type;
    }

 /**
     * [getDeviceInfo 获取设备类型信息]
     * @param {[type]} type   [类型 computer camera]
     * @param {[type]} entity [设备实体]
     */

    getCarInfo(entity): void{
    //   //切换成修改按钮
  
       this.resetModal();
       this.judgeTips.status = false;
       this.carModel = entity;
       console.log(entity);
        $('#dateTimePicker').datetimepicker('update', new Date(this.carModel.run_time.time));
    }

    /**
     * [deleteDeviceType 删除设备类型]
     */
    deleteDeviceType(): void{
      this.carService.deleteType(this.deleteMsg.id,this.deleteMsg.type).then(data=>{
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
  		this.getCarNotes();
      this.getCarTypes();
      $('#dateTimePicker').datetimepicker({
      weekStart: 1,
      todayBtn: 1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      minView: 2
    });
  	}
}

