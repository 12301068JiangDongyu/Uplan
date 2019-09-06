import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CarInfo } from '../../../entity/carinfo.entity'
import { User } from '../../../entity/user.entity'

import { CarApplyInfo } from '../../../entity/carApplyInfo.entity'
import { CarApplyInfoService } from 'app/service/car.service';
import { StorageService } from 'app/service/storage.service';
declare var $: any;

@Component({
  selector: 'CarApplyComponent',
  templateUrl: './carApply.html',
  styles: ['td.fc-day:hover{background-color:skyblue;}']
})

export class CarApplyComponent implements OnInit {

  cars: CarInfo[] = [{ id: 0, brand: "", license_plate_num: "", status: "" }];
  carInfo: CarInfo = { id: 0, brand: "", license_plate_num: "", status: "" };
  applyInfo: CarApplyInfo = {
    id: 0, car_id: 0, brand: "", user_id: 0, userName: "", distination: "",
    start_time: new Date(), end_time: new Date(), reason: "", travel_distance: 0,
    creatTime: "", creat_time: new Date(), updateTime: "", startTime: "",
    update_time: new Date(), oil_used: 0, remark: "", status_name: ""
  };
  allApplyList = [{
    title: '丰田阿尔法',
    start: new Date(2019, 8, 7),
    backgroundColor: '#f56954', //red
    borderColor: '#f56954' //red
  }];
  strHtml: string;
  applyReason: string = "";
  // res: number;



  constructor(private carApplyInfoService: CarApplyInfoService, private storageService: StorageService) {

  }

  // 初始化函数
  ngOnInit(): void {
    let today = new Date().getFullYear() + "-" + new Date().getMonth() + "-" + new Date().getDay();
    this.getCarInfoByDate(today);
  }

  // 获取所有申请列表
  getAllApplyList(): void {
    let that = this;
    that.carApplyInfoService.getCarUseList().then(data => {
      console.log("所有申请汽车数据");
      console.log(data);
      for (let info of data) {
        let temp = {
          title: info.brand,
          start: new Date(info.start_time.year,info.start_time.month,info.start_time.day),
          backgroundColor: '#f56954', //red
          borderColor: '#f56954' //red
        }
        that.allApplyList.push(temp);
      }
      console.log(this.allApplyList);
      that.initCalendar();

      var originalEventObject = $(this).data('eventObject')
      // we need to copy it, so that multiple events don't have a reference to the same object
      var copiedEventObject = $.extend({}, this.allApplyList)
      $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

    });

  }

  // 通过日期获取可用汽车信息， 给cars变量赋值
  getCarInfoByDate(date): void {
    let that = this
    console.log(date);
    this.carApplyInfoService.getCarListByDate(date).then(data => {
      that.cars = data; 
      console.log(that.cars);
      let strHtml = "";
      for (let car of that.cars) {
        strHtml += "<div class='external-event bg-green' id='" + car.id + "' (click)='this.getCarInfoByCarId(" + car.id + ")'>" + car.brand + "-" + car.id + "</div>";
      };
      $("#external-events").on('click', 'div', '', function () {
        var num = $(this).attr('id');
        that.getCarInfoByCarId(num);
      });
      $("#external-events").html(strHtml);
      that.getAllApplyList()
      // that.initCalendar();
    });
  }



  // 申请理由内容绑定
  onEnter(value: string): void {
    this.applyReason = value;
  }


  // 通过id获取汽车的具体信息
  getCarInfoByCarId(carId) {
    let that = this;
    that.carApplyInfoService.getCarInfoByCarId(carId).then(data => {
      that.carInfo.id = data.carList.id;
      that.carInfo.brand = data.carTypeList.brand;
      that.carInfo.license_plate_num = data.carList.license_plate_num;
      that.carInfo.status = "可用";

      $("#car-info").html(" <li class='list-group-item'> 汽车编号：" + this.carInfo.id + "</li><li class='list-group-item'>品牌：" + this.carInfo.brand + "</li><li class='list-group-item'>车牌号：" + this.carInfo.license_plate_num + "</li> <li class='list-group-item'>汽车状态：" + this.carInfo.status + "</li>")
    });
  }


  // 初始化日历插件
  initCalendar(): void {
    var that = this

    /* initialize the external events
     -----------------------------------------------------------------*/
    function init_events(ele) {
      ele.each(function () {

        // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
        // it doesn't need to have a start or end
        var eventObject = {
          title: $.trim($(this).text()) // use the element's text as the event title
        }

        // store the Event Object in the DOM element so we can get to it later
        $(this).data('eventObject', eventObject)

        // make the event draggable using jQuery UI
        $(this).draggable({
          zIndex: 1070,
          revert: true, // will cause the event to go back to its
          revertDuration: 0  //  original position after the drag
        })

      })
    }
    init_events($('#external-events div.external-event'))

    /* initialize the calendar
     -----------------------------------------------------------------*/
    //Date for the calendar events (dummy data)
    var date = new Date();
    var d = date.getDate(),
      m = date.getMonth(),
      y = date.getFullYear()
    $('#calendar').fullCalendar({
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,listMonth'
      },
      buttonText: {
        today: '今天',
        month: '日历展示',
        list: '列表展示'
      },
      //Random default events
      events: that.allApplyList,//将已有记录显示
      eventLimit: true,
      views: {
        agenda: {
          eventLimit: 3 // adjust to 6 only for agendaWeek/agendaDay
        }
      },
      navLinks: true,
      locale: 'zh-cn',
      editable: true,
      droppable: true, // this allows things to be dropped onto the calendar !!!
      drop: function (date, allDay) { // this function is called when something is dropped

        // retrieve the dropped element's stored Event Object
        var originalEventObject = $(this).data('eventObject')

        // we need to copy it, so that multiple events don't have a reference to the same object
        var copiedEventObject = $.extend({}, originalEventObject)

        // assign it the date that was reported
        copiedEventObject.start = date;
        copiedEventObject.allDay = true;
        copiedEventObject.backgroundColor = $(this).css('background-color');
        copiedEventObject.borderColor = $(this).css('border-color');
        // 向后端插入数据
        that.applyInfo.car_id = that.carInfo.id;//创建插入数据
        that.applyInfo.user_id = that.storageService.read<User>('user').id;//创建插入数据
        that.applyInfo.reason = that.applyReason;//创建插入数据
        that.applyInfo.start_time = date;//创建插入数据

        that.carApplyInfoService.addCarApply(that.applyInfo).then(data => {
          console.log(data);
          let res = data.judge;
          console.log(res);
          if (res > 0) {
            $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
            $(this).remove();
            $("#res").text("插入成功");
          } else{
              $("#res").text("插入失败");
          }
          $("#ensureModal").modal('show');
        });        
      },
      //给日期添加点击事件,刷 
      dayClick: function (date, jsEvent, view) {
        that.getCarInfoByDate(date.format());
        // init_events($('#external-events div.external-event'));
      }
    })
  }


}




