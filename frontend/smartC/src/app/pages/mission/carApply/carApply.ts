import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CarInfo } from '../../../entity/carinfo.entity'
import { CarApplyInfo } from '../../../entity/carApplyInfo.entity'
import { CarApplyInfoService } from 'app/service/car.service';
declare var $: any;

@Component({
  selector: 'CarApplyComponent',
  templateUrl: './carApply.html',
  styles: ['td.fc-day:hover{background-color:skyblue;}']
})

export class CarApplyComponent implements OnInit {

  cars: CarInfo[];
  carInfo: CarInfo = {c_id: 0, c_brand: "", c_plateNum: "", status: ""};
  applyInfo: CarApplyInfo = {id:0,car_id:0,brand:"",user_id:0,user_name:"",distination:"",
                              start_time:new Date(),end_time: new Date(),reason:"",travel_distance:0,
                              creat_time: new Date(),update_time: new Date(),oil_used:0,remark:"",status:0};
  strHtml: string;
  applyReason: string = "";
  res: string;

  constructor(private carApplyInfoService: CarApplyInfoService) {

  }

  ngOnInit(): void {
    this.getCarInfoByDate(new Date());
    // this.getCarInfoByCarId(1);
    this.initCalendar();
  }

  getCarInfoByDate(data): void {
    let that = this
    that.cars = [{ c_id: 1, c_brand: "doge", c_plateNum: "d123323", status: "可用" },
    { c_id: 2, c_brand: "保时捷-怕那美拉", c_plateNum: "d123323", status: "可用" },
    { c_id: 6, c_brand: "保时捷-怕那美拉", c_plateNum: "d123444", status: "可用" },
    { c_id: 3, c_brand: "宝马750Li", c_plateNum: "d123323", status: "可用" },
    { c_id: 4, c_brand: "丰田阿尔法", c_plateNum: "d123323", status: "可用" }];

    that.carApplyInfoService.getCarListByDate(data).then(date => {
      that.cars = date;
    });

    let strHtml = "";
    for (let car of that.cars) {
      strHtml += "<div class='external-event bg-green' id='"+ car.c_id +"' (click)='this.getCarInfoByCarId("+ car.c_id +")'>" + car.c_brand + "-" + car.c_id + "</div>";
    };
    $("#external-events").on('click','div','', function(){
      var num = $(this).attr('id');
      that.getCarInfoByCarId(num);
    });

    $("#external-events").html(strHtml);
  }

  // 申请理由内容绑定
  onEnter(value: string): void {
     this.applyReason = value; 
  }

  // 通过id获取汽车的具体信息
  getCarInfoByCarId(carId){
    let that = this;
    that.carApplyInfoService.getCarInfoByCarId(carId).then(data => {
      that.carInfo.c_id = data.carList.id;
      that.carInfo.c_brand = data.carTypeList.brand;
      that.carInfo.c_plateNum = data.carList.license_plate_num;
      that.carInfo.status = data.carList.status == 1? "可用":"不可用";

      $("#car-info").html(" <li class='list-group-item'> 汽车编号："+ this.carInfo.c_id +"</li><li class='list-group-item'>品牌：" + this.carInfo.c_brand +"</li><li class='list-group-item'>车牌号：" + this.carInfo.c_plateNum +"</li> <li class='list-group-item'>汽车状态：" + this.carInfo.status +"</li>")
    });
  }


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
    var date = new Date()
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
      events: [
        {
          title: '丰田阿尔法',
          start: new Date(y, m, 1, 7, 12),
          backgroundColor: '#f56954', //red
          borderColor: '#f56954' //red
        },
        {
          title: '吉利熊猫3.0',
          start: new Date(y, m, d + 5),
          allDay: false,
          backgroundColor: '#f39c12', //yellow
          borderColor: '#f39c12' //yellow
        },
        {
          title: '宝马750Li',
          start: new Date(y, m, d, 10, 30),
          allDay: false,
          backgroundColor: '#0073b7', //Blue
          borderColor: '#0073b7' //Blue
        }
      ],
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
        copiedEventObject.start = date
        copiedEventObject.allDay = true
        copiedEventObject.backgroundColor = $(this).css('background-color')
        copiedEventObject.borderColor = $(this).css('border-color')
        // 向后端插入数据
        that.applyInfo.car_id = that.carInfo.c_id;//创建插入数据
        that.applyInfo.user_id = 1;//创建插入数据
        that.applyInfo.reason = that.applyReason;//创建插入数据
        that.applyInfo.start_time = date.format();//创建插入数据

        var res : number;
        that.carApplyInfoService.addCarApply(that.carInfo.c_id, 1, that.applyReason, date.format()).then(data => {
          res = data.judge;
        });
        // res = "成功";//将执行结果返回给也页面
        if (res === 1) {
          // render the event on the calendar
          // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
          $('#calendar').fullCalendar('renderEvent', copiedEventObject, true)
          $(this).remove()

          // }
        }
        $("#res").text(res);
        $("#ensureModal").modal('show')
      },
      //给日期添加点击事件,刷 
      dayClick: function (date, jsEvent, view) {
        that.getCarInfoByDate(date.format());
        init_events($('#external-events div.external-event'));
      }
    })

    // $(".fc-day").hover("")   

  }


}




