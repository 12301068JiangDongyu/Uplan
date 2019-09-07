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
  today : string = this.formatDate(new Date()) ;

	formatDate(date): string{
		var d = new Date(date),
			month = '' + (d.getMonth() + 1),
			day = '' + d.getDate(),
			year = d.getFullYear();
	
		if (month.length < 2) month = '0' + month;
		if (day.length < 2) day = '0' + day;
	
		return [year, month, day].join('-');
	}

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
  	 * [ 改变数据状态]
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
      this.carApplyInfos = data.officialCarApplyList;
      console.log(data);
      for(let i = 0; i < this.carApplyInfos.length; i++){
        if(this.today > this.carApplyInfos[i].startTime || this.carApplyInfos[i].status === 3 || this.carApplyInfos[i].status === 2){
          this.carApplyInfos[i].is_delay = false;

        }else{
          this.carApplyInfos[i].is_delay = true;
        }
      }
    })
  }

  /**
  	 * [ 通过查询条件获取申请列表]
  */
  findCarApplyListByStatus(queryContent: string): void {
    if (queryContent == "") {
      this.getCarApplyInfos();
    } else {
      this.carApplyInfoService.findCarApplyListByStatus(queryContent).then(data=>{
        this.carApplyInfos = data.officialCarApplyList;
        console.log(this.carApplyInfos);

        for(let i = 0; i < this.carApplyInfos.length; i++){
          if(this.today > this.carApplyInfos[i].startTime || this.carApplyInfos[i].status === 3 || this.carApplyInfos[i].status === 2){
            this.carApplyInfos[i].is_delay = false;
  
          }else{
            this.carApplyInfos[i].is_delay = true;
          }
        }
      })
    }

  }

}
