import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer,SafeHtml } from '@angular/platform-browser'

//entity
import { BuildClass } from '../../../../entity/buildclass.entity';
import { Classroom } from '../../../../entity/classroom.entity';

//service
import { BuildClassService } from '../../../../service/buildClass.service';

import { nameValidator } from '../../../../providers/validator';

declare var $:any;

@Component({
  selector: 'page-add-buildclass',
  templateUrl: './addBuildClass.html'
})
export class addBuildClassPage implements OnInit{
	
  	buildClasses : BuildClass[];

  	MaxInputs : number = 9;
    currentLen : number = 0;
	items : any[] = [];

	judgeTip: boolean = true;
	judgeMsg: string[] = ['请输入20字以下教学楼！','请输入20字以下教室号！','教室已存在！',
							'添加成功！','修改成功！','删除成功！',
							'添加失败！','修改失败！','删除失败！'];
	tip : string = '';
    
	constructor(
		private _sanitizer: DomSanitizer,	  	
		private buildClassService: BuildClassService,
	  	private router: Router) {
	}

	ngOnInit(): void{
	}

	/**
	 * [addBuildClass 添加教学楼教室]
	 */
	addBuildClass(): void{
		let buildingNum = $('#inputBuilding').val();
	    let classrooms : Classroom[] = [];
	    let classroomNums = [];
	    let judge = 0;//是否需要调用接口 0调用 1不调用
	    let msgNum = 3;
	    this.judgeTip = false;
	    
	    //判断教学楼是否输入合法
	    if(!nameValidator(buildingNum)){
	    	msgNum = 0;
	    	judge = 1;
	    }else{
	    	$('.inputClassroom').each(function(i, obj){
		    	let value = $(this).val();
		    	if(nameValidator(value)){
		    		classroomNums.push(value);
		    	}else{
		    		msgNum = 1;
		    		judge = 1;
		    		return;
		    	}	        
		    });
	    }
	    if(!judge){
	    	//教室列表
	    	for(let i=0;i<classroomNums.length;i++){
	    		let classroom : Classroom = {id : null,classroomNum : classroomNums[i],bid : null};
	    		classrooms.push(classroom);
	    	}
	    	//调用添加教室接口
	    	this.buildClassService.addBuildCLass(buildingNum,classrooms).then(data=>{
	    		console.log(data);
	    		if(data.judge == 0) msgNum = 3;
	    		else if(data.judge == -9) msgNum = 6;
	    		else msgNum = Math.abs(data.judge)-1;
	    		this.tip = this.judgeMsg[msgNum];
	    		//添加成功
	    		if(msgNum == 3) this.router.navigate(['/buildClassManage']);
	    	})
	    }else{
	    	this.tip = this.judgeMsg[msgNum];
	    }
	}

	/**
	 * [addInput 添加输入框]
	 * @return {boolean} [description]
	 */
	addInput(): void{

	 	if(this.currentLen <= this.MaxInputs) //max input box allowed
	    {
          this.items[this.currentLen] = {classroom:''};
          this.currentLen ++;          
          this.items.length = this.currentLen; 
		}

	}
	/**
	 * [removeInput 移除输入框]
	 * @param  {[type]}  i      [description]
	 * @param  {[type]}  _event [description]
	 * @return {boolean}        [description]
	 */
	removeInput(i,_event): boolean{
	 	if( this.currentLen>1 ) {
            this.currentLen--;
            this.items.splice(i,1);     
        }
        return false;
	}



}