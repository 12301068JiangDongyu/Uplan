import { Component, OnInit } from '@angular/core';
import { CarRecord } from '../../../entity/carrecord.entity';
import { Constant } from '../../../common/constant';

@Component({
  selector: 'app-car-record',
  templateUrl: './car-record.component.html',
  styleUrls: ['./car-record.component.css'],
})
export class CarRecordComponent implements OnInit {
  //list
  oilList: CarRecord[] = [
    { id: 1, license: '123', occurrenceTime: '2019-9-10', creator: 'xixi', cost: '32', remark: '', type: 2 },
    { id: 2, license: '122', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
    { id: 2, license: '122', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
    { id: 2, license: '122', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
    { id: 2, license: '122', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
    { id: 2, license: '122', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
    { id: 2, license: '122', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
  ];
  oilList2: CarRecord[] = [
    { id: 1, license: '344', occurrenceTime: '2019-9-10', creator: 'gg', cost: '30', remark: '', type: 2 },
    { id: 2, license: '322', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
    { id: 2, license: '211', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '232', remark: '', type: 2 },
    { id: 2, license: '322', occurrenceTime: '2019-9-11', creator: 'asd', cost: '31', remark: '', type: 2 },
    { id: 2, license: '322', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '21', remark: '', type: 2 },
    { id: 2, license: '322', occurrenceTime: '2019-9-11', creator: 'fa', cost: '31', remark: '', type: 2 },
    { id: 2, license: '322', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
  ];
  oilList3: CarRecord[] = [
    { id: 1, license: '321', occurrenceTime: '2019-9-10', creator: 'fae', cost: '13', remark: '', type: 2 },
    { id: 2, license: '321', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '31', remark: '', type: 2 },
    { id: 2, license: '321', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '43', remark: '', type: 2 },
    { id: 2, license: '321', occurrenceTime: '2019-9-11', creator: 'fas', cost: '31', remark: '', type: 2 },
    { id: 2, license: '321', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '435', remark: '', type: 2 },
    { id: 2, license: '321', occurrenceTime: '2019-9-11', creator: 'fgr', cost: '31', remark: '', type: 2 },
    { id: 2, license: '321', occurrenceTime: '2019-9-11', creator: 'xixi', cost: '14', remark: '', type: 2 },
  ];
  fixList: CarRecord[];
  violateList: CarRecord[];

  carRecordModel: CarRecord = { id: null, license: '', occurrenceTime: '', creator: '', cost: '', remark: '', type: 2 };

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
  judgeMsg = ['信息输入不合法！', '信息不能为空！', '该设备型号已存在!', '该设备型号不存在！', '添加成功！', '修改成功！', '删除成功！', '添加失败！', '修改失败！', '删除失败！'];
  tip = '';


  constructor(private constant: Constant) {}


  getCarRecord( entity): void {
    this.judgeTips.status = false;
    this.carRecordModel = entity;
  }

  getCarRecordId(id) {
    this.deleteId = id;
  }

  /**
   * [resetModal 清除模态框数据 切换成add状态]
   */
  resetModal(type): void {
    let recordType = this.constant.RecordTypeMap.find(x=>x.value == type).key;
    console.log(recordType);
    this.judgeTips.status = true;
    this.judgeTips.addEdit = true;
    this.carRecordModel = { id: null, license: '', occurrenceTime: '', creator: '', cost: '', remark: '', type: recordType};
  }

  ngOnInit() {}
}
