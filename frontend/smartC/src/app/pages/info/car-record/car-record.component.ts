import { Component, OnInit } from '@angular/core';
import { CarRecord } from '../../../entity/carrecord.entity';
import { Constant } from '../../../common/constant';
import { CarRecordService } from '../../../service/carrecord.service';
import { User } from 'app/entity/user.entity';
import { UserService } from 'app/service/user.service';
import { CarService } from 'app/service/car-manage.service';
import { Car } from 'app/entity/car-manage.entity';

declare var $: any;

@Component({
  selector: 'app-car-record',
  templateUrl: './car-record.component.html',
  styleUrls: ['./car-record.component.css'],
})
export class CarRecordComponent implements OnInit {
  //list
  oilList: CarRecord[];
  repairList: CarRecord[];
  ruleList: CarRecord[];
  userList: User[];
  carList: Car[];

  carRecordModel: CarRecord = { id: null, car_id: null, license_num: '', occurrence_time: '', real_name: '', creator: null, cost: '', remark: '', type: 2 };

  deleteId = null;

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

  constructor(private constant: Constant, private carRecordService: CarRecordService, private userService: UserService, private carService: CarService) {}

  getCarRecordList(): void {
    this.carRecordService.getCarRecords().then(data => {
      this.oilList = data.oilList;
      this.repairList = data.repairList;
      this.ruleList = data.ruleList;
    });
  }

  getUserList(): void {
    this.userService.getUsers().then(data => {
      this.userList = data.userList;
    });
  }

  getCarList(): void {
    this.carService.getCarNotes().then(data => {
      this.carList = data.carList;
    });
  }

  getCarRecord(entity, type): void {
    this.resetModal(type);
    this.judgeTips.status = false;
    this.carRecordModel = entity;
    $('#dateTimePicker').datetimepicker('update', new Date(this.carRecordModel.occurrence_time.time));
  }

  getCarRecordId(id, type) {
    this.resetModal(type);
    this.deleteId = id;
  }

  deleteCarRecord(): void {
    this.carRecordService.deleteCarRecord(this.deleteId).then(data => {
      this.handleMsg('delete', Math.abs(data.judge));
    });
  }

  handleCarRecord(type) {
    let datetime = $('#dateTimePicker')
      .data('datetimepicker')
      .getDate()
      .getTime();
    if (type == 'add') {
      this.carRecordService.addCarRecord(Object.assign(this.carRecordModel, { occurrence_time: datetime })).then(data => {
        this.handleMsg(type, Math.abs(data.judge));
      });
    } else {
      this.carRecordService.editCarRecord(this.carRecordModel.id, Object.assign(this.carRecordModel, { occurrence_time: datetime })).then(data => {
        this.handleMsg(type, Math.abs(data.judge));
      });
    }
  }

  handleMsg(type, judge): void {
    switch (type) {
      case 'add':
        this.judgeTips.addEdit = false;
        if (judge == 0) {
          this.tip = this.judgeMsg[0];
          this.getCarRecordList();
          $('#recordModal').modal('hide');
        } else {
          this.tip = this.judgeMsg[3];
        }
        break;
      case 'edit':
        this.judgeTips.addEdit = false;
        if (judge == 0) {
          this.tip = this.judgeMsg[1];
          this.getCarRecordList();
          $('#recordModal').modal('hide');
        } else {
          this.tip = this.judgeMsg[4];
        }
        break;
      case 'delete':
        this.judgeTips.delete = false;
        if (judge == 0) {
          this.tip = this.judgeMsg[2];
          this.getCarRecordList();
          $('#deleteModal').modal('hide');
        } else {
          this.tip = this.judgeMsg[5];
        }
        break;
    }
  }
  /**
   * [resetModal 清除模态框数据 切换成add状态]
   */
  resetModal(type): void {
    let recordType = this.constant.RecordTypeMap.find(x => x.value == type).key;
    console.log(recordType);
    this.tip = '';
    this.judgeTips.status = true;
    this.judgeTips.addEdit = true;
    this.judgeTips.delete = true;
    this.carRecordModel = { id: null, car_id: null, license_num: '', occurrence_time: '', real_name: '', creator: null, cost: '', remark: '', type: recordType };
    $('#dateTimePicker').datetimepicker('update', new Date());
  }

  ngOnInit() {
    this.getCarRecordList();
    this.getUserList();
    this.getCarList();
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
