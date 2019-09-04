import { Component, OnInit } from '@angular/core';

//entity
import { CarApplyInfo } from '../../../entity/carApplyInfo.entity';
//service
import { CarApplyInfoService } from '../../../service/car.service';


@Component({
	selector: 'userApplyListComponent',
	templateUrl: './user-apply-list.component.html'
})
export class UserApplyListComponent implements OnInit {
	me: CarApplyInfo;
	userId: number;
	judgeDelete: boolean = true;
	carApplyInfos: CarApplyInfo[] = [{ c_id: 1, c_brand: "doge", user_name: "a", c_plateNum: "d123323", destination: "济南1", reason: "没有原因", start_time: "2019-01-01", end_time: "2019-01-01", status: "未审核" }];
	judgeMsg: string[] = [
		'请输入20位以下用户名！',//0
		'请输入6位以上密码！',//1
		'该用户已存在！',//2
		'用户不存在！',//3
		'添加失败！',//4
		'修改失败！',//5
		'撤销失败！',//6
		'添加成功！',//7
		'修改成功！',//8
		'撤销成功！'];//9
	tip: string = '';

	// carApplyInfos : CarApplyInfo[];
	constructor(private carApplyInfoService: CarApplyInfoService) {

	}

	/**
  	 * [ 初始点击对应的数据id]
  	 */
	getApplyId(id): void {
		this.userId = id;
	}

	/**
  	 * [ 初始点击对应的数据id]
  	 */
	deleteApply(): void {
		this.carApplyInfoService.deleteApplyInfoById(this.userId).then(data => {
			this.judgeDelete = false;
			if (data.judge == 0) {
				this.tip = this.judgeMsg[9];
				location.reload();
			} else {
				if (data.judge == -1) {
					this.tip = this.judgeMsg[4];
					location.reload();
				} else {
					this.tip = this.judgeMsg[6];
				}
			}
			console.log(data);
		});
	}

	/**
	 * [ 初始获取申请列表]
	 */
	getCarApplyInfosByUserId(): void {
		this.carApplyInfoService.getApplyInfoByUserId(1).then(data => {
			// console.log(data);
			// this.carApplyInfos = data.carApplyInfoList;
		})
	}

	ngOnInit(): void {
		this.getCarApplyInfosByUserId();
	}

}
