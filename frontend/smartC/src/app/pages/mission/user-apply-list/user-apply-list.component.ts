import { Component, OnInit } from '@angular/core';

//entity
import { CarApplyInfo } from '../../../entity/carApplyInfo.entity';
import { User } from '../../../entity/user.entity'

//service
import { CarApplyInfoService } from '../../../service/car.service';
import { StorageService } from '../../../service/storage.service';
import { of } from 'rxjs/observable/of';



@Component({
	selector: 'userApplyListComponent',
	templateUrl: './user-apply-list.component.html'
})
export class UserApplyListComponent implements OnInit {

	userId: number;
	applyId: number;
	judgeDelete: boolean = true;
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


	is_show:boolean[];
	
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
	constructor(private carApplyInfoService: CarApplyInfoService, private storageService: StorageService) {
	}

	/**
  	 * [ 初始点击对应的数据id]
  	 */
	getApplyId(id): void {
		this.applyId = id;
		console.log(this.applyId);
	}

	/**
  	 * [ 初始点击对应的数据id]
  	 */
	dropApply(): void {
		this.carApplyInfoService.dropApply(this.applyId, "撤回").then(data => {
			this.judgeDelete = false;
			if (data.judge > 0) {
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
		this.carApplyInfoService.getApplyInfoByUserId(this.userId).then(data => {
			this.carApplyInfos = data;
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

	ngOnInit(): void {
		this.userId = this.storageService.read<User>('user').id;	
		this.getCarApplyInfosByUserId();
	}

}
