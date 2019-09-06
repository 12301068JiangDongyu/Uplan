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
  styleUrls: ['./car-manage.component.css'],
})
export class CarManageComponent implements OnInit {
  //list
  cars: Car[];
  cartypes: Cartype[];

  carModel: Car = { id: null, license_plate_num: '', brand: '', type: null, status: null, car_type_id: null, mileage: null, oil_remained: null, run_time: '' };

  deleteMsg = {
    id: null,
    type: '',
  };
  /**
   * [judgeTips 消息提示]
   * @type {Object}
   * status 判断是true：添加 false：修改模态框
   * addEdit 添加修改消息提示是否显示 true：不显示
   * delete 删除消息提示是否显示
   */
  judgeTips = {
    status: true,
    addEdit: true,
    delete: true,
  };
  judgeMsg = ['添加成功！', '修改成功！', '删除成功！', '添加失败！', '修改失败！', '删除失败！'];
  tip = '';

  urls = ['carType/'];
  constructor(private carService: CarService, private cartypeService: CarTypeService, private router: Router) {}

  /**
   * [getCarNotes 获取车辆信息列表]
   */

  getCarNotes(): void {
    this.carService.getCarNotes().then(data => {
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
  resetModal(): void {
    this.tip = '';
    this.judgeTips.status = true;
    this.judgeTips.addEdit = true;
    this.judgeTips.delete = true;
    this.carModel = { id: null, license_plate_num: '', brand: '', type: null, status: null, car_type_id: null, mileage: null, oil_remained: null, run_time: '' };
    $('#dateTimePicker').datetimepicker('update', new Date());
  }

  /**
   * [handleCar 添加或修改车辆]
   * @param {[type]} type [add edit]
   */
  handleCar(type): void {
    let datetime = $('#dateTimePicker')
      .data('datetimepicker')
      .getDate()
      .getTime();

    if (type == 'add') {
      this.carService.addCar(Object.assign(this.carModel, { run_time: datetime })).then(data => {
        this.handleMsg(type, Math.abs(data.judge));
      });
    } else {
      this.carService.editCar(this.carModel.id, Object.assign(this.carModel, { run_time: datetime })).then(data => {
        this.handleMsg(type, Math.abs(data.judge));
      });
    }
  }

  getCarId(id, type) {
    this.deleteMsg.id = id;
    this.deleteMsg.type = type;
  }

  getCarInfo(entity): void {
    this.resetModal();
    this.judgeTips.status = false;
    this.carModel = entity;
    console.log(entity);
    $('#dateTimePicker').datetimepicker('update', new Date(this.carModel.run_time.time));
  }

  deleteCar(): void {
    this.carService.deleteCar(this.deleteMsg.id, this.deleteMsg.type).then(data => {
      this.handleMsg('delete', Math.abs(data.judge));
    });
  }

  /**
   * [handleMsg 处理服务器返回的消息]
   * @param {[type]} type  [事件类型 add edit delete]
   * @param {[type]} judge [消息值]
   */
  handleMsg(type, judge): void {
    switch (type) {
      case 'add':
        this.judgeTips.addEdit = false;
        if (judge == 0) {
          this.tip = this.judgeMsg[0];
          this.getCarNotes();
          $('#carModal').modal('hide');
        } else {
          this.tip = this.judgeMsg[3];
        }
        break;
      case 'edit':
        this.judgeTips.addEdit = false;
        if (judge == 0) {
          this.tip = this.judgeMsg[1];
          this.getCarNotes();
          $('#carModal').modal('hide');
        } else {
          this.tip = this.judgeMsg[4];
        }
        break;
      case 'delete':
        this.judgeTips.delete = false;
        console.log(judge);
        if (judge == 0) {
          this.tip = this.judgeMsg[2];
          this.getCarNotes();
          $('#deleteModal').modal('hide');
        } else {
          this.tip = this.judgeMsg[5];
        }
        break;
    }
  }

  ngOnInit(): void {
    this.getCarNotes();
    this.getCarTypes();
    $('#dateTimePicker').datetimepicker({
      weekStart: 1,
      todayBtn: 1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      minView: 2,
    });
  }
}
