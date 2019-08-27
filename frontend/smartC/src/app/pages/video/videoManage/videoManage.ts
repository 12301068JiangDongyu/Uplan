import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';

import { Device } from '../../../entity/device.entity';
import { Classroom } from '../../../entity/classroom.entity';
import { Building } from '../../../entity/building.entity';
import { BuildClass } from '../../../entity/buildclass.entity';
import { ZNode } from '../../../entity/znode.entity';
import { Constant } from '../../../common/constant';

import { DeviceService } from '../../../service/device.service';
import { VideoService } from '../../../service/video.service';
import { ToastService } from '../../../service/toast.service';

import { ToastsManager,Toast } from 'ng2-toastr/ng2-toastr';

import 'ztree';
declare var $:any;

@Component({
  selector: 'page-video-manage',
  templateUrl: './videoManage.html'
})
export class videoManagePage implements OnInit{
	data : Device[] = [];
	buildings : Building[];
	classrooms : Classroom[];
	buildclasses : BuildClass[];

  public filterQuery = "";
  public rowsOnPage = 10;
  public sortOrder = "asc";

  judgeFrom: boolean = true;
  judgeRight: boolean = true;
  buildModel: string;
  classModel: string;
  pullId: number;

  //zTree
  setting :any = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    }
  };
	zNodes: any = [];
	code : any;

	constructor(
    private toastr: ToastsManager,
    private vcr: ViewContainerRef,
    private toastService: ToastService,
		private deviceService: DeviceService,
		private videoService: VideoService,
    private router: Router,
    private constant: Constant) {
    this.toastr.setRootViewContainerRef(vcr);
	}

	/**
	 * [getDevices 获取设备列表]
	 */
	getDevices(): void {
		this.videoService.getVideoDevices().then(devices => {
		this.data = devices;
		});
	}

	/**
	 * [operateStream 操作视频推拉流]
	 * @param {[type]} id      [设备ID]
	 * @param {[type]} operate [操作 start_push stop_push start_pull stop_pull start_broadcast stop_broadcast]
   * @param {[type]} code    [根据点击按钮 修改其他按钮的点击状态(status:1,2,3,4,5)]
	 * @param {[type]} _event  [点击事件]
	 */
	operateStream(id,operate,code,_event): void{
    console.log(_event);
    this.clickButtonChange(_event);
		//this.changeOtherButton(id,code);
    let device:Device = this.findDeviceById(id);
    let buildClass = device.buildingNum+device.classroomNum;
		this.videoService.operateStream(id,operate).then(data=>{
      console.log(data);
      let message = data.wSocketMessage;
      if(operate.indexOf('broadcast')!=-1){//广播
        if(data.judge==0){
          this.data = data.deviceInfoList;

          this.handleMessage(message.judge,buildClass,message.message);
        }else
          this.handleMessage('fail',buildClass,message.message);
      }else{//其他

        //更改状态
        for(let i=0;i<this.data.length;i++){
          if(this.data[i].id == id){
            this.data[i] = data.deviceInfo;
            break;
          }
        }
        this.handleMessage(message.judge,buildClass,message.message);
      }
    });
	}

	/**
	 * [getBuildClass 获取正在推流的教学楼教室]
	 * @param {[type]} id     [设备ID]
	 * @param {[type]} _event [点击事件]
	 * @param {[type]} from   [多个教室同时拉流：multiple 单个教室：single]
	 */
	getBuildClass(id,_event,from): void{
		//把上一次选择的教学楼教室删除
		this.buildModel = '';
		this.classModel = '';
		this.judgeFrom = true;
    this.pullId = id;
		this.videoService.getPushingBuildClass().then(
			data => {
				this.classrooms = data.classroomList;
				this.buildings = data.buildingList;
        if(this.buildings.length>0&&this.classrooms.length>0){
          this.buildModel = this.buildings[0].buildingNum;
          this.classModel = this.classrooms[0].classroomNum;
        }
				if(from == 'multiple'){
					//获取可以拉流的教学楼教室列表
					this.getMultiPullStreamTree();
				}
			});
	}

	/**
	 * [changeClassroomByBuilding 根据选择的教学楼重新获取教室号]
	 * @param {[type]} _event [点击事件]
	 */
	changeClassroomByBuilding(_event): void{
		console.log(_event);
		this.videoService.changeClassByBuildName(_event).then(classrooms => {
      this.classrooms = classrooms;
      if(classrooms.length>0){
        this.classModel = this.classrooms[0].classroomNum;
      }
    });
	}

  /**
   * [getMultiPullStreamTree 获取可以拉流的教学楼教室列表]
   */
	getMultiPullStreamTree(): void{
		this.videoService.getMultiPullStreamTree().then(buildclasses => {
			this.buildclasses = buildclasses;
			this.zNodes = new Array();
      let buildNum = "";
      let count = 0;
      let countChild = 1;
      for (let i=0;i<buildclasses.length;i++){
          let nodeHead: ZNode = {id: 0,pId : 0,name : '',open : true,icon : "../assets/adminlte/img/roll.png",checked:false};
          let buildingNum = buildclasses[i]['buildingNum'];
          if(buildclasses[i]['buildingNum']!=buildNum){
              count++;
              nodeHead.id = count;
              nodeHead.pId = 0;
              nodeHead.name = buildclasses[i]['buildingNum'];
              nodeHead.open = true;
              this.zNodes.push(nodeHead);
              let node: ZNode = {id: 0,pId : 0,name : '',open : true,icon : "../assets/adminlte/img/roll.png",checked:false};
              node.id = count*10+countChild;
              node.pId = count;
              node.name = buildclasses[i]['classroomNum'];
              this.zNodes.push(node);
              countChild++;
              buildNum = buildclasses[i]['buildingNum'];
          }else{
              let node: ZNode = {id: 0,pId : 0,name : '',open : true,icon : "../assets/adminlte/img/roll.png",checked:false};
              node.id = count*10+countChild;
              node.pId = count;
              node.name = buildclasses[i]['classroomNum'];
              this.zNodes.push(node);
              countChild++;
          }
      }
      this.judgeFrom = false;
      $.fn.zTree.init($("#ztree"), this.setting, this.zNodes);
      this.setCheck();
      $("#py").bind("change", this.setCheck);
      $("#sy").bind("change", this.setCheck);
      $("#pn").bind("change", this.setCheck);
      $("#sn").bind("change", this.setCheck);
		});
	}

	/**
	 * zTree
	 */
	setCheck(): void {
    var zTree = $.fn.zTree.getZTreeObj("ztree"),
        type = { "Y" : "ps", "N" : "ps" };
    zTree.setting.check.chkboxType = type;
    this.showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
	}
	showCode(str): void {
	    if (!this.code) this.code = $("#code");
	    this.code.empty();
	    this.code.append("<li>"+str+"</li>");
	}
	/**
	 * [GetCheckedAll 获取所有选中节点的值]
	 */
	GetCheckedAll() :void{
	    let treeObj = $.fn.zTree.getZTreeObj("ztree");
	    let nodes = treeObj.getCheckedNodes(true);
	    let buildClassNodes = new Array();
	    let buildingNum = '';
	    for (let i = 0; i < nodes.length; i++) {
	        let buildClass : BuildClass;

	        if(nodes[i].pId==null){
	            //buildClass.buildingNum = nodes[i].name;
	            buildingNum = nodes[i].name;
	        }else{
	            buildClassNodes.push(buildingNum+";"+nodes[i].name);
	        }

	    }
	    this.startMultiplePullOperate(buildClassNodes);
	}

	/**
	 * [startMultiplePullOperate 操作多个教室拉流]
	 * @param {[type]} pullList [被选中的需要拉流教室的列表]
	 */
	startMultiplePullOperate(pullList): void{
    console.log(pullList);
    if((this.buildModel.length==0) || (this.classModel.length==0) || (pullList.length==0)){
      this.judgeRight = false;
    }else{
      this.videoService.startMultiplePullOperate(this.buildModel,this.classModel,pullList).then(
        data=>{
          location.reload();

        });
    }
	}

  /**
   * [startPullOperate 单个教室拉流]
   */
  startPullOperate(): void{
    console.log(this.pullId);
    if((this.buildModel.length==0) || (this.classModel.length==0) ){
      this.judgeRight = false;
      $('#modalStartPullBtn').removeAttr('data-dismiss');
    }else{
      $('#modalStartPullBtn').attr('data-dismiss','modal');
      this.videoService.startPullOperate(this.buildModel,this.classModel,this.pullId).then(
        data=>{
          //this.changeOtherButton(this.pullId,4);
          let message = data.wSocketMessage;
          let buildClass = data.deviceInfo.buildingNum+data.deviceInfo.classroomNum;
          //更改状态
          for(let i=0;i<this.data.length;i++){
            if(this.data[i].id == this.pullId){
              this.data[i] = data.deviceInfo;
              break;
            }
          }
          this.handleMessage(message.judge,buildClass,message.message);
        });
    }
  }

  /**
   * [changeOtherButton 根据点击按钮 修改其他按钮的点击状态]
   * @param {[type]} id   [点击的设备ID]
   * @param {[type]} code [raspberryStreamStatus 1，2，3，4，5]
   */
  changeOtherButton(id,code): void{
    this.data.find(x => x.id === id).raspberryStreamStatus = code;

  }

  findDeviceById(id): Device{
    return this.data.find(x => x.id === id);
  }

  /**
   * [handleMessage 处理消息]
   * @param {[type]} type    [类型]
   * @param {[type]} title   [标题]
   * @param {[type]} message [消息内容]
   */
  handleMessage(type,title,message){
    console.log(type,title,message);
    switch (type) {
      case "success":
        this.toastService.showSuccess(this.toastr,title,message);
        break;
      case "fail":
        this.toastService.showError(this.toastr,title,message);
        break;
      case "offline":
        this.toastService.showWarning(this.toastr,title,message);
        break;
      default:
        // code...
        break;
    }
  }
  /**
   * [clickButtonChange 禁止多次点击]
   * @param {[type]} _event [点击事件]
   */
  clickButtonChange(_event): void{
    let buttonText = _event.toElement.textContent;
    _event.toElement.disabled = true;
    _event.toElement.textContent = "点击成功，5s后恢复";
    setTimeout(function(){
      _event.toElement.textContent = buttonText;
      _event.toElement.disabled = false;
    },this.constant.WaitTime);
  }
	ngOnInit(): void{
    this.getDevices();
	}
}