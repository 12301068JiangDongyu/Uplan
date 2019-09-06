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
  timeCount2018: any;
  timeCount2019: any;

  carTypeUsagePieOption: any;
  userCountBarOption: any;
  monUsageBarOption: any;
  operateScatter: any;

  constructor(private statisticsService: StatisticsService) {}

  getStatistics(): void {
    this.statisticsService.getStatistics().then(data => {
      this.carTypeCount = data.carTypeCount;
      this.userCount = data.userCount;
      this.timeCount2018 = data.timeCount2018;
      this.timeCount2019 = data.timeCount2019;
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
    return this.userCount.map(x => [x.keyValue, x.keyName]);
  }

  initUserCountBarChart(): void {
    let data = this.formatUserCountBarChart();
    data.unshift(['amount', 'name']);
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
      dataset: {
        source: data,
      },
      grid: { containLabel: true },
      xAxis: { name: '次数' },
      yAxis: { type: 'category' },
      visualMap: {
        orient: 'horizontal',
        left: 10,
        top: 10,
        min: 1,
        max: 100,
        text: ['高', '低'],
        dimension: 0,
        inRange: {
          color: ['#D7DA8B', '#E15457'],
        },
      },
      series: [
        {
          type: 'bar',
          encode: {
            // Map the "amount" column to X axis.
            x: 'amount',
            // Map the "product" column to Y axis
            y: 'name',
          },
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

  initOperateScatter(): void {
    var dataOil = [
      ['京AVBCHS', 55, 300, 201909],
      ['京AVBCHA', 25, 10, 201802],
    ];
    var dataFix = [
      ['京AVBCHS', 15, 150, 201902],
      ['京AVBCHA', 5, 100, 201903],
    ];
    var dataViolate = [
      ['京AVBCHS', 155, 200, 201901],
      ['京AVBCHA', 125, 140, 201912],
    ];

    var schema = [
      { name: 'license', index: 0, text: '车牌号' },
      { name: 'count', index: 1, text: '总次数' },
      { name: 'cost', index: 2, text: '总花销' },
      { name: 'time', index: 3, text: '投入使用时间' },
    ];

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
        formatter: function (obj) {
            var value = obj.value;
            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
                + value[0]
                + '</div>'
                + schema[1].text + '：' + value[1] + '<br>'
                + schema[2].text + '：' + value[2] + '元<br>'
                + schema[3].text + '：' + String(value[3]).slice(0,4) + '年' + String(value[3]).slice(4) + '月<br>';
        }
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
            color: ['rgba(255,255,255,.2)'],
          },
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
          show: false,
        },
      ],
      series: [
        {
          name: '加油',
          type: 'scatter',
          itemStyle: itemStyle,
          data: dataOil,
        },
        {
          name: '维修',
          type: 'scatter',
          itemStyle: itemStyle,
          data: dataFix,
        },
        {
          name: '违章',
          type: 'scatter',
          itemStyle: itemStyle,
          data: dataViolate,
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
