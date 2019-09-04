import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Cartype } from '../../../entity/cartype.entity';
import { CarTypeService } from '../../../service/cartype.service';

import { nameValidator,numValidator } from '../../../providers/validator';

declare var $: any;

@Component({
  selector: 'app-car-type-manage',
  templateUrl: './car-type-manage.component.html',
  styleUrls: ['./car-type-manage.component.css']
})
export class CarTypeManageComponent implements OnInit {
    //list
    cartypes : Cartype[];

    carModel : Cartype = {id : null, brand : '', capacity : '', price : null, buy_time : '', seat_num : null, oil_type : null};
    test = new Date(1568250042000).toLocaleDateString();
    testTime : any = this.test ;

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

    urls = 'carType/';

    constructor(
      private cartypeService: CarTypeService,
      private router: Router) {
    } 

     /**
     * [getCarTypes 获取车型列表]
     */
    getCarTypes(): void {
      //this.cartypes = [{carId : 1,carBrand : '大众POLO', carCapacity : '1.4',carPrice : //14390000,carBuyTime : '2017-09-01',carSeatNum : 4,carOilType : 1},
      //{carId : 2,carBrand : '别克GL8',carCapacity : '2.4', carPrice : 23290000,carBuyTime : //'2018-08-12',carSeatNum : 7,carOilType : 1},
      //{carId : 3,carBrand : '奥迪A8',carCapacity : '3.0', carPrice : 80000000,carBuyTime : //'2016-07-12',carSeatNum : 4,carOilType : 1}]

        this.cartypeService.getCarTypes().then(data=>{
        this.cartypes = data.carTypeList;
      });
    }

    /**
     * [resetModal 清除模态框数据 切换成add状态]
     */
    resetModal(): void{
      this.judgeTips.status = true;
      this.judgeTips.addEdit = true;
      this.carModel = {id : null, brand : '', capacity : '', price : null, buy_time : '', seat_num : null, oil_type : null}
    }

     /**
     * [handleCar 添加或修改车型信息]
     * @param {[type]} type [add edit]
     */
    handleCar(type): void {
//      if(nameValidator(this.carModel.brand)&&nameValidator(this.carModel.capacity)&&
//      numValidator(this.carModel.price)&&numValidator(this.carModel.buy_time)&&numValidator(
//this.carModel.seat_num)&&numValidator(this.carModel.oil_type)){
        console.log(this.carModel.buy_time);
        console.log(this.testTime);
        if(type == 'add'){
          this.cartypeService.addType(this.carModel,this.urls).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          });
        }else{
          let time = this.carModel.buy_time.time;
          // console.log(time);
          this.cartypeService.editType(this.carModel.id,Object.assign(this.carModel,{buy_time: time}),this.urls).then(data=>{
            console.log(data);
            this.handleMsg(type,Math.abs(data.judge));
          })
        }
//      }else{
  //      this.judgeTips.addEdit = false;
    //    this.tip = this.judgeMsg[0];
      //}
    }

    /**
     * [getCarTypeInfo 获取设备类型信息]
     * @param {[type]} type   [类型 computer camera]
     * @param {[type]} entity [设备实体]
     */
    getCarTypeInfo(type,entity): void{
      //切换成修改按钮     
      this.judgeTips.status = false;
      switch (type) {
        case "cartype":
          this.carModel = entity;
          break;
      }
    }

    /**
     * [getDeviceId 获取车辆ID]
     * @param {[type]} id   [设备类型ID]
     * @param {[type]} type [设备类型]
     */
    getCarId(id,type){
      this.deleteMsg.id = id;
      this.deleteMsg.type = type;
    }

    /**
     * [deleteDeviceType 删除设备类型]
     */
    deleteCarType(): void{
      this.cartypeService.deleteType(this.deleteMsg.id,this.deleteMsg.type).then(data=>{
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
      this.getCarTypes();
      $('#datepicker').datepicker({
        autoclose: true,
        format: 'yyyy/mm/dd'
      })
    }
}
