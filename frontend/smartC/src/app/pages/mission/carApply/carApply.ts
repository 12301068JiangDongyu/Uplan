import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CarInfo } from '../../../entity/carinfo.entity'
import { CarApplyInfoService } from 'app/service/car.service';
declare var $: any;

@Component({
  selector: 'CarApplyComponent',
  templateUrl: './carApply.html',
  styles:['td.fc-day:hover{background-color:skyblue;}']
})

export class CarApplyComponent implements OnInit {

  cars: CarInfo[];
  strHtml: string;
  res: string;

  constructor(private carApplyInfoService: CarApplyInfoService) {

  }

  ngOnInit(): void {
    this.initCalendar();
  }

  getCarInfo(date, cars):void {
    cars = [{ c_id: 1, c_brand: "doge", c_plateNum: "d123323", status: "可用" },
    { c_id: 2, c_brand: "保时捷-怕那美拉", c_plateNum: "d123323", status: "可用" },
    { c_id: 3, c_brand: "宝马750Li", c_plateNum: "d123323", status: "可用" },
    { c_id: 4, c_brand: "丰田阿尔法", c_plateNum: "d123323", status: "可用" }];

    // this.carApplyInfoService.getCarListByDate(date).then(cars => {
    //   this.cars = cars;
    // });

    let strHtml = "";
    for (let car of cars) {
      strHtml += "<div class='external-event bg-green'>" + car.c_brand + "</div>";
    };
    $("#external-events").html(strHtml);
  }


  initCalendar(): void {
    var getCarInfo = this.getCarInfo
    var res = this.res
    var cars = this.cars

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
    getCarInfo(new Date(), cars)

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
        // right: 'month,agendaWeek,agendaDay'
      },
      buttonText: {
        today: '今天',
        month: '月份展示',
        list: '列表展示'
        // week: '周',
        // day: '日'
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
      eventLimit : true,
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
        copiedEventObject.allDay = allDay
        copiedEventObject.backgroundColor = $(this).css('background-color')
        copiedEventObject.borderColor = $(this).css('border-color')

        // var res = this.carApplyInfoService.addCarApply()
        res = "成功"

        if (res === "成功") {
          // render the event on the calendar
          // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
          $('#calendar').fullCalendar('renderEvent', copiedEventObject, true)
          // is the "remove after drop" checkbox checked?
          // if ($('#drop-remove').is(':checked')) {
          // if so, remove the element from the "Draggable Events" list
          $(this).remove()

          // }
        }

        $("#res").text(res);
        $("#ensureModal").modal('show')
      },
      //给日期添加点击事件,刷 
      dayClick: function (date, jsEvent, view) {
        getCarInfo(date.format(),cars);
        init_events($('#external-events div.external-event'));
      }
    })

    // $(".fc-day").hover("")   
    
  }


}




