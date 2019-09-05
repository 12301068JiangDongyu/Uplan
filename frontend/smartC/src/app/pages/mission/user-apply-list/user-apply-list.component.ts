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
	userId: number;
	applyId: number;
	judgeDelete: boolean = true;
	
	carApplyInfos: CarApplyInfo[];
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
		this.applyId = id;
	}

	/**
  	 * [ 初始点击对应的数据id]
  	 */
	deleteApply(): void {
		this.carApplyInfoService.deleteApplyInfoById(this.applyId).then(data => {
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
		this.carApplyInfoService.getApplyInfoByUserId(3).then(data => {
			console.log(data);
			this.carApplyInfos = data.carApplyInfoList;
		})
	}

	ngOnInit(): void {
		this.getCarApplyInfosByUserId();
	}

}
