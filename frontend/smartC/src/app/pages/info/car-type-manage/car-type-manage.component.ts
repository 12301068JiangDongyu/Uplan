import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Cartype } from '../../../entity/cartype.entity';
import { CarTypeService } from '../../../service/cartype.service';

declare var $: any;

@Component({
  selector: 'app-car-type-manage',
  templateUrl: './car-type-manage.component.html',
  styleUrls: ['./car-type-manage.component.css'],
})
export class CarTypeManageComponent implements OnInit {
  //list
  cartypes: Cartype[];

  carModel: Cartype = { id: null, brand: '', capacity: '', price: null, buy_time: '', seat_num: null, oil_type: null };

  buyTime = { key: '', value: '' };

  deleteMsg = { id: null };

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

  urls = 'carType/';

  constructor(private cartypeService: CarTypeService, private router: Router) {}

  /**
   * [getCarTypes 获取车型列表]
   */
  getCarTypes(): void {
    this.cartypeService.getCarTypes().then(data => {
      this.cartypes = data.carTypeList;
    });
  }

  /**
   * [resetModal 清除模态框数据 切换成add状态]
   */
  resetModal(): void {
    this.judgeTips.status = true;
    this.judgeTips.addEdit = true;
    this.carModel = { id: null, brand: '', capacity: '', price: null, buy_time: '', seat_num: null, oil_type: null };
  }

  /**
   * [handleCar 添加或修改车型信息]
   * @param {[type]} type [add edit]
   */
  handleCar(type): void {
    let jqtime = Date.parse($('#datepicker').data().datepicker.dates[0]).toString();
    let datetime = jqtime ? jqtime : this.buyTime.value;
    console.log(datetime);

    if (type == 'add') {
      this.cartypeService.addType(Object.assign(this.carModel, { buy_time: datetime })).then(data => {
        console.log(data);
        this.handleMsg(type, Math.abs(data.judge));
      });
    } else {
      this.cartypeService.editType(this.carModel.id, Object.assign(this.carModel, { buy_time: datetime })).then(data => {
        console.log(data);
        this.handleMsg(type, Math.abs(data.judge));
      });
    }
  }

  getCarTypeInfo(entity): void {
    this.resetModal();
    //切换成修改按钮
    this.judgeTips.status = false;
    this.carModel = entity;
    this.buyTime.key = this.carModel.buy_time.time;
    this.buyTime.value = new Date(this.buyTime.key).toLocaleDateString();
  }

  getCarId(id) {
    this.deleteMsg.id = id;
  }

  deleteCarType(): void {
    this.cartypeService.deleteType(this.deleteMsg.id).then(data => {
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
          this.getCarTypes();
          $("#carModal").modal('hide');
        } else {
          this.tip = this.judgeMsg[3];
        }
        break;
      case 'edit':
        this.judgeTips.addEdit = false;
        if (judge == 0) {
          this.tip = this.judgeMsg[1];
          this.getCarTypes();
          $("#carModal").modal('hide');
        } else {
          this.tip = this.judgeMsg[4];
        }
        break;
      case 'delete':
        this.judgeTips.delete = false;
        console.log(judge);
        if (judge == 0) {
          this.tip = this.judgeMsg[2];
          this.getCarTypes();
          $("#deleteModal").modal('hide');
        } else {
          this.tip = this.judgeMsg[5];
        }
        break;
    }
  }

  ngOnInit(): void {
    this.getCarTypes();
    $('#datepicker').datepicker({
      autoclose: true,
      format: 'yyyy/mm/dd',
      changeMonth: true,
      changeYear: true,
    });
  }
}
