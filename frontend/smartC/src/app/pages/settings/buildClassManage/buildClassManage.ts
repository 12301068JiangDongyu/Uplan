import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

//entity
import { BuildClass } from '../../../entity/buildclass.entity';
import { Building } from '../../../entity/building.entity';

//service
import { BuildClassService } from '../../../service/buildClass.service';

import { nameValidator } from '../../../providers/validator';

@Component({
  selector: 'page-buildclass-manage',
  templateUrl: './buildClassManage.html'
})
export class buildClassManagePage implements OnInit{
	
  	data : BuildClass[] = [];
  	buildClass : BuildClass = {
  		b_id : null,
		building : {
			id : null,
			buildingNum : null
		},
		classroomNum : null,
		id : null//classroom id
  	};
  	buildings : Building[] = [];

  	buildingNum = {
  		old : null,
  		new : null
  	}

  	judgeTips = {
	    edit: true,
	    delete : true //判断tip是否需要显示 true不需要
	}

	judgeMsg: string[] = ['请输入20字以下教学楼教室号！','请输入20字以下教学楼！','请输入20字以下教室号！',
							'教室已存在！','教室不存在',
							'添加成功！','修改成功！','删除成功！',
							'添加失败！','修改失败！','删除失败！'];
	tip : string = '';

	constructor(	  	
		private buildClassService: BuildClassService,
	  	private router: Router) {
	}

	/**
	 * [getBuildClasses 获取教学楼教室列表]
	 */
	getBuildClasses(): void{
		this.buildClassService.getBuildClasses().then(data=>this.data = data);
	}

	/**
	 * [getBuildings 获取教学楼列表]
	 */
	getBuildings(): void{
		this.buildClassService.getBuildings().then(buildings => {
			this.buildings = buildings;
			if(this.buildings.length>0){
				this.buildingNum.old = this.buildings[0].id;
			}
		});
	}

	/**
	 * [editBuildClass 修改教学楼教室]
	 */
	editBuildClass(): void{
		let buildingNum = this.buildClass.building.buildingNum;
		let classroomNum = this.buildClass.classroomNum;
		this.judgeTips.edit = false;
		if(nameValidator(buildingNum)&&nameValidator(classroomNum)){
			this.buildClassService.editBuildClass(this.buildClass).then(data=>{
				console.log(data);
				let judge = Math.abs(data.judge);
				if(judge == 0) {
					this.tip = this.judgeMsg[6];
					location.reload();
				}else if(judge == 9){
					this.tip = this.judgeMsg[9];
					location.reload();
				}else
					this.tip = this.judgeMsg[judge];
			})
		}else{
			this.tip = this.judgeMsg[0];
		}
		
	}

	editBuilding(): void{
		console.log(this.buildingNum);
		let building : Building = {id: this.buildingNum.old,buildingNum:this.buildingNum.new};
		if(nameValidator(this.buildingNum.new)){
			this.buildClassService.editBuilding(building).then(data=>{
				console.log(data);
				let judge = Math.abs(data.judge);
				if(judge == 0) {
					this.tip = this.judgeMsg[6];//修改成功
					location.reload();
				}else if(judge == 9){
					this.tip = this.judgeMsg[9];//修改失败
					location.reload();
				}else if(judge == 2)
					this.tip = this.judgeMsg[judge+1];//已存在
				else
					this.tip = this.judgeMsg[11];//未输入
			})
		}else{
			this.tip = this.judgeMsg[1];
		}
	}
	/**
	 * [deleteBuildClass 删除教学楼教室]
	 */
	deleteBuildClass(): void{
		this.buildClassService.deletebuildClass(this.buildClass.id).then(data=>{
			console.log(data);
			let msgNum = 7;
			this.judgeTips.delete = false;
			if(data.judge == -9) msgNum = 10;
			else if(data.judge == -1) msgNum = 4;
			this.tip = this.judgeMsg[msgNum];
			location.reload();
		});
	}

	/**
	 * [recordInfo 修改删除记录信息]
	 * @param {[type]} bid          [教学楼ID]
	 * @param {[type]} cid          [教室ID]
	 * @param {[type]} buildingNum  [教学楼名称]
	 * @param {[type]} classroomNum [教室名称]
	 */
	recordInfo(bid,cid,buildingNum,classroomNum): void{
		this.buildClass.b_id = bid;
		this.buildClass.id = cid;
		this.buildClass.classroomNum = classroomNum;
		this.buildClass.building.buildingNum = buildingNum;
		this.buildClass.building.id = bid;
	}

	ngOnInit(): void{
		this.getBuildClasses();
	}

}