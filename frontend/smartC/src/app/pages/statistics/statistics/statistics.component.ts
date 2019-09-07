import { Component, OnInit } from '@angular/core';
import { StatisticsService } from '../../../service/statistics.service';
import { Statistics } from '../../../entity/statistics.entity';
declare var $: any;

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css'],
})
export class StatisticsComponent implements OnInit {
  carTypeCount: Statistics[];
  userCount: Statistics[];
  userRuleCount: Statistics[];
  timeCount2018: any;
  timeCount2019: any;
  oilList: any;
  repairList: any;
  ruleList: any;
  cardNames: any;

  carTypeUsagePieOption: any;
  userCountBarOption: any;
  monUsageBarOption: any;
  operateScatter: any;

  constructor(private statisticsService: StatisticsService) {}

  getStatistics(): void {
    this.statisticsService.getStatistics().then(data => {
      this.carTypeCount = data.carTypeCount;
      this.userCount = data.userCount;
      this.userRuleCount = data.userRuleCount;
      this.timeCount2018 = data.timeCount2018;
      this.timeCount2019 = data.timeCount2019;
      this.oilList = data.oilList;
      this.repairList = data.repairList;
      this.ruleList = data.ruleList;
      this.cardNames = data.cardNames;
      this.initCharts();
    });
  }

  formatCarTypeUsagePieChart(): any {
    let legendData = this.carTypeCount.map(x => x.keyName);
    let seriesData = this.carTypeCount.map(x => {
      return {
        name: x.keyName,
        value: x.keyValue,
      };
    });
    let selected = this.carTypeCount.map(x => {
      return {
        [x.keyName]: x.keyValue > 2,
      };
    });
    return {
      legendData,
      seriesData,
      selected,
    };
  }

  initCarTypeUsagePieChart(): void {
    let data = this.formatCarTypeUsagePieChart();

    this.carTypeUsagePieOption = {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      toolbox: {
        show: true,
        feature: {
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true },
        },
      },
      legend: {
        type: 'scroll',
        orient: 'vertical',
        left: 10,
        top: 0,
        bottom: 20,
        data: data.legendData,
        selected: data.selected,
      },
      series: [
        {
          name: '车型',
          type: 'pie',
          radius: '55%',
          center: ['40%', '50%'],
          data: data.seriesData,
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)',
            },
          },
        },
      ],
    };
  }

  formatUserCountBarChart(): any {
    let userCount = this.userCount.map(x => [x.keyName, x.keyValue]);
    let userRuleCount = this.userRuleCount.map(x => [x.keyName, x.keyValue]);

    return {
      userCount,
      userRuleCount,
    };
  }

  initUserCountBarChart(): void {
    let data = this.formatUserCountBarChart();
    this.userCountBarOption = {
      toolbox: {
        show: true,
        feature: {
          dataView: { show: true, readOnly: false },
          magicType: { show: true, type: ['line', 'bar'] },
          restore: { show: true },
          saveAsImage: { show: true },
        },
      },
      legend: {
        data: ['预约', '违章'],
        align: 'left',
        left: 10,
      },
      tooltip: {},
      grid: { containLabel: true },
      yAxis: { type: 'value', name: '次数' },
      xAxis: { type: 'category' },
      series: [
        {
          type: 'bar',
          name: '预约',
          data: data.userCount,
        },
        {
          type: 'bar',
          name: '违章',
          data: data.userRuleCount,
        },
      ],
    };
  }

  initMonUsageBarChart(): void {
    this.monUsageBarOption = {
      tooltip: {
        trigger: 'axis',
      },
      legend: {
        data: ['2018', '2019'],
        x: 'left',
      },
      toolbox: {
        show: true,
        feature: {
          dataView: { show: true, readOnly: false },
          magicType: { show: true, type: ['line', 'bar'] },
          restore: { show: true },
          saveAsImage: { show: true },
        },
      },
      calculable: true,
      xAxis: [
        {
          type: 'category',
          data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        },
      ],
      yAxis: [
        {
          type: 'value',
        },
      ],
      series: [
        {
          name: '2018',
          type: 'bar',
          data: this.timeCount2018,
          markPoint: {
            data: [{ type: 'max', name: '最大值' }, { type: 'min', name: '最小值' }],
          },
          markLine: {
            data: [{ type: 'average', name: '平均值' }],
          },
        },
        {
          name: '2019',
          type: 'bar',
          data: this.timeCount2019,
          markPoint: {
            data: [{ type: 'max', name: '最大值' }, { type: 'min', name: '最小值' }],
          },
          markLine: {
            data: [{ type: 'average', name: '平均值' }],
          },
        },
      ],
    };
  }

  formatOperateScatter(): any {
    let oilList = this.cardNames.map(x => {
      let data = this.oilList.find(ele => ele.card == x);
      return data ? [data.card, data.count, data.cost, Number(data.time)] : [x, 0, 0, 0];
    });
    let repairList = this.cardNames.map(x => {
      let data = this.repairList.find(ele => ele.card == x);
      return data ? [data.card, data.count, data.cost, Number(data.time)] : [x, 0, 0, 0];
    });
    let ruleList = this.cardNames.map(x => {
      let data = this.ruleList.find(ele => ele.card == x);
      return data ? [data.card, data.count, data.cost, Number(data.time)] : [x, 0, 0, 0];
    });
    return {
      oilList,
      repairList,
      ruleList,
    };
  }

  initOperateScatter(): void {
    let data = this.formatOperateScatter();
    var schema = [{ name: 'license', index: 0, text: '车牌号' }, { name: 'count', index: 1, text: '总次数' }, { name: 'cost', index: 2, text: '总花销' }, { name: 'time', index: 3, text: '投入使用时间' }];

    var itemStyle = {
      normal: {
        opacity: 0.8,
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowOffsetY: 0,
        shadowColor: 'rgba(0, 0, 0, 0.5)',
      },
    };

    this.operateScatter = {
      toolbox: {
        show: true,
        feature: {
          dataView: { show: true, readOnly: false },
          magicType: { show: true, type: ['line', 'bar'] },
          restore: { show: true },
          saveAsImage: { show: true },
        },
      },
      backgroundColor: '#404a59',
      color: ['#dd4444', '#fec42c', '#80F1BE'],
      legend: {
        y: 'top',
        data: ['加油', '维修', '违章'],
        textStyle: {
          color: '#fff',
          fontSize: 16,
        },
      },
      grid: {
        x: '10%',
        x2: 150,
        y: '18%',
        y2: '10%',
      },
      tooltip: {
        padding: 10,
        backgroundColor: '#222',
        borderColor: '#777',
        borderWidth: 1,
        formatter: function(obj) {
          var value = obj.value;
          return (
            '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">' +
            obj.seriesName +
            '记录：' +
            value[0] +
            '</div>' +
            schema[1].text +
            '：' +
            value[1] +
            '<br>' +
            schema[2].text +
            '：' +
            value[2] +
            '元<br>' +
            schema[3].text +
            '：' +
            String(value[3]).slice(0, 4) +
            '年' +
            String(value[3]).slice(4) +
            '月<br>'
          );
        },
      },
      xAxis: {
        type: 'category',
        name: '车牌号',
        nameGap: 16,
        nameTextStyle: {
          color: '#fff',
          fontSize: 14,
        },
        splitLine: {
          show: false,
        },
        axisLine: {
          lineStyle: {
            color: '#eee',
          },
        },
      },
      yAxis: {
        type: 'value',
        name: '次数',
        nameLocation: 'end',
        nameGap: 20,
        nameTextStyle: {
          color: '#fff',
          fontSize: 16,
        },
        axisLine: {
          lineStyle: {
            color: '#eee',
          },
        },
        splitLine: {
          show: false,
        },
      },
      visualMap: [
        {
          dimension: 2,
          inRange: {
            symbolSize: [10, 70],
          },
          outOfRange: {
            symbolSize: [10, 70],
            // color: ['rgba(255,255,255,.2)'],
          },
          min: 10000,
          max: 100000,
          show: false,
        },
        {
          dimension: 3,
          inRange: {
            colorLightness: [1, 0.5],
          },
          outOfRange: {
            color: ['rgba(255,255,255,.2)'],
          },
          min: 201712,
          max: 201912,
          show: false,
        },
      ],
      series: [
        {
          name: '加油',
          type: 'scatter',
          itemStyle: itemStyle,
          data: data.oilList,
        },
        {
          name: '维修',
          type: 'scatter',
          itemStyle: itemStyle,
          data: data.repairList,
        },
        {
          name: '违章',
          type: 'scatter',
          itemStyle: itemStyle,
          data: data.ruleList,
        },
      ],
    };
  }

  initCharts(): void {
    this.initCarTypeUsagePieChart();
    this.initUserCountBarChart();
    this.initMonUsageBarChart();
    this.initOperateScatter();
  }

  ngOnInit() {
    this.getStatistics();
  }
}
