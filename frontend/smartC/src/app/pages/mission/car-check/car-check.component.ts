import { Component, OnInit } from '@angular/core';
import { CarApplyInfoService } from '../../../service/car.service';
import { CarApplyInfo } from '../../../entity/carApplyInfo.entity';

@Component({
  selector: 'app-car-check',
  templateUrl: './car-check.component.html',
  styleUrls: ['./car-check.component.css']
})
export class CarCheckComponent implements OnInit {

  carApplyInfos: CarApplyInfo[];
  applyId: number;
  statusOperate: string;
  judgeMsg: string[] = [
    '修改成功',//>0
    '状态为已审核！'];//<0
  tip: string = '';

  constructor(private carApplyInfoService: CarApplyInfoService) { }


  ngOnInit() {
    this.getCarApplyInfos();
  }


  getApplyId(id, statusOperate): void {
    this.applyId = id;
    this.statusOperate = statusOperate;
    this.tip = "";
  }

  /**
  	 * [ 初始点击对应的数据id]
  	 */
  checkApply(): void {
    this.carApplyInfoService.checkApplyByApplyId(this.applyId, this.statusOperate).then(data => {
      console.log(data);
      // this.judgeDelete = false;
      if (data.judge > 0) {
        this.tip = this.judgeMsg[0];
        location.reload();
      } else {
        this.tip = this.judgeMsg[1];
      }      
    });
    
  }

  /**
  	 * [ 初始获取申请列表]
  	 */
  getCarApplyInfos(): void {
    this.carApplyInfoService.getCarApplyInfos().then(data=>{
      console.log(data);
      this.carApplyInfos = data.officialCarApplyList;
    })
  }

  /**
  	 * [ 初始获取申请列表]
  */
  findCarApplyListByStatus(queryContent: string): void {
    if (queryContent == "") {
      this.getCarApplyInfos();
    } else {
      this.carApplyInfoService.findCarApplyListByStatus(queryContent).then(data=>{
        console.log(data);
        this.carApplyInfos = data.officialCarApplyList;
      })
    }

  }

}
