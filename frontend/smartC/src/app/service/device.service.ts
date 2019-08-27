/**
 * 设备service类
 */
import { Device } from '../entity/device.entity'
import { DeviceClass } from '../entity/deviceclass.entity'
import { Classroom } from '../entity/classroom.entity'

import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class DeviceService {
  private deviceUrl : string;
  private deviceInfoUrl : string;
	private assignDeviceUrl : string;
  private options: any;
	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private constant : Constant,private http: Http) { 
     this.deviceUrl = constant.URL+'deviceMonitor/';
     this.deviceInfoUrl = constant.URL+'deviceInfos/';
     this.assignDeviceUrl = constant.URL+'assignDevice/';
     this.options = new RequestOptions({withCredentials: true,headers: this.getHeaders()});
  }
	
	  /**
	   * [getDevices 获取设备列表]
	   * @return {Promise<Device[]>} [设备列表]
	   */
  	getDevices(): Promise<Device[]> {
    	return this.http.get(this.deviceUrl,this.options)
             .toPromise()
             .then(response => response.json().data.deviceStatusList as Device[])
             .catch(this.handleError);
  	}
    /**
     * [operateDevice 操作设备状态]
     * @param {[type]} id      [教室设备列表id]
     * @param {[type]} device  [设备类型]
     * @param {[type]} operate [操作类型 open close]
     */
    operateDevice(id,device,operate): Promise<any>{
      
      let url = this.deviceUrl+id+'?device='+device+'&operation='+operate;
      return this.commonOperatGetFunc(url);
    }

    /**
     * [operateCamera 操作摄像机状态]
     * @param {[type]} deviceId [设备id]
     * @param {[type]} cameraId [摄像机id]
     * @param {[type]} operate  [操作类型 open close]
     */
    operateCamera(deviceId,cameraId,code,operate): Promise<any>{
      //let url = '/ajax_edit_camera_status';
      let url = this.deviceUrl+'camera/'+cameraId+'?code='+code+'&did='+deviceId+'&operation='+operate;
      return this.commonOperatGetFunc(url);
    }

    /**
     * [operateAllDevice 一键操作所有设备]
     * @param {[type]} operate [操作类型 open close]
     */
    operateAllDevice(operate): Promise<any>{
      //let url = '/ajax_edit_all_device_status';
      let url = this.deviceUrl+'all?operation='+operate;
      return this.commonOperatGetFunc(url);
    }

    getDeviceInfoById(id): Promise<Device>{
      let url = '/ajax_get_classroom_info';
      let data = {
        'did' : id
      };
      return this.http
          .post(url, JSON.stringify(data), this.options)
          .toPromise()
          .then(response => response.json().data.deviceInfo as Device)
          .catch(this.handleError);
    }
    /**
     * [getDeviceTypes 获取全部设备类型信息]
     * @return {Promise<any>} [description]
     */
    getDeviceTypes(): Promise<any>{
      return this.http
        .get(this.deviceInfoUrl,this.options)
        .toPromise()
        .then(response=>response.json().data)
        .catch(this.handleError)
    }
    /**
     * [addType 添加设备类型]
     * @param  {[type]}       entity [设备类型实体]
     * @param  {[type]}       type   [设备类型]
     * @return {Promise<any>}        [description]
     */
    addType(entity,type): Promise<any>{
      return this.http
        .post(this.deviceInfoUrl+type,entity,this.options)
        .toPromise()
        .then(response=>response.json().data)
        .catch(this.handleError);
    }
    /**
     * [editType 修改设备类型]
     * @param  {[type]}       id     [设备类型ID]
     * @param  {[type]}       entity [设备类型实体]
     * @param  {[type]}       type   [设备类型]
     * @return {Promise<any>}        [description]
     */
    editType(id,entity,type): Promise<any>{
      return this.http
        .put(this.deviceInfoUrl+type+id,entity,this.options)
        .toPromise()
        .then(response=>response.json().data)
        .catch(this.handleError);
    }
    /**
     * [deleteType 删除设备类型]
     * @param  {[type]}       id   [设备类型ID]
     * @param  {[type]}       type [设备类型]
     * @return {Promise<any>}      [description]
     */
    deleteType(id,type): Promise<any>{
      return this.http
        .delete(this.deviceInfoUrl+id+'?device='+type,this.options)
        .toPromise()
        .then(response=>response.json().data)
        .catch(this.handleError);
    }

    /**
     * [getAssignDevices 获取已经分配设备的教室]
     * @return {Promise<Device[]>} [description]
     */
    getAssignDevices():Promise<DeviceClass[]>{
      return this.http
        .get(this.assignDeviceUrl,this.options)
        .toPromise()
        .then(response=>response.json().data.deviceInfoList as DeviceClass[])
        .catch(this.handleError);
    }

    /**
     * [getDeviceList 获取设备型号列表 教学楼教室]
     * @return {Promise<any>} [description]
     */
    getDeviceList():Promise<any>{
      return this.http
        .get(this.assignDeviceUrl+'deviceList',this.options)
        .toPromise()
        .then(response=>response.json().data)
        .catch(this.handleError);
    }

    /**
     * [changeClassByBuildName 根据选择的教学楼重新获取教室号]
     * @param  {[string]}               name [教学楼名]
     * @return {Promise<Classroom[]>}      [教室列表]
     */
    changeClassByBuildName(name): Promise<Classroom[]> {
      let url = this.assignDeviceUrl + 'ajax_change_building?buildingNum='+name;
      return this.http
              .get(url,this.options)
              .toPromise()
              .then(response => response.json().data.classroomList as Classroom[])
              .catch(this.handleError);
    }

    /**
     * [assignClassroomDevice 分配教学楼教室]
     * @param  {[type]}       type        [add edit]
     * @param  {[type]}       id          [教室设备ID]
     * @param  {[type]}       assignModel [修改信息实体]
     * @param  {[type]}       cameraList  [摄像头列表]
     * @return {Promise<any>}             [description]
     */
    assignClassroomDevice(type,id,assignModel,cameraList): Promise<any>{
      var data = {
        "buildingNum":assignModel.buildingNum,
        "classroomNum":assignModel.classroomNum,
        "computerTypeId":assignModel.computerTypeId,
        "projectorTypeId":assignModel.projectorTypeId,
        "raspberryTypeId":assignModel.raspberryTypeId,
        "singlechipTypeId":assignModel.singlechipTypeId,
        "cameraList":cameraList
      }
      if(type=='add'){
         return this.http
            .post(this.assignDeviceUrl,data,this.options)
            .toPromise()
            .then(response=>response.json().data)
            .catch(this.handleError);
      }else{
        return this.http
            .put(this.assignDeviceUrl+id,data,this.options)
            .toPromise()
            .then(response=>response.json().data)
            .catch(this.handleError);
      }
      
    }
    /**
     * [getDeviceClassroomById 根据ID获取分配教室信息]
     * @param  {[type]}       id [教室设备ID]
     * @return {Promise<any>}    [description]
     */
    getDeviceClassroomById(id):Promise<any>{
      return this.http
              .get(this.assignDeviceUrl+id,this.options)
              .toPromise()
              .then(response=>response.json().data)
              .catch(this.handleError);
    }

    /**
     * [deleteDeviceClassroom 根据ID删除分配教室信息]
     * @param  {[type]}       id [教室设备ID]
     * @return {Promise<any>}    [description]
     */
    deleteDeviceClassroom(id):Promise<any>{
      return this.http
      .delete(this.assignDeviceUrl+id,this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError)
    }

    openRaspberry(id): Promise<any>{
      return this.http
            .put(this.assignDeviceUrl+'raspberry/'+id,{},this.options)
            .toPromise()
            .then(response=>response.json().data)
            .catch(this.handleError);
    }
    /**
     * [commonOperatFunc 公共方法]
     * @param {[type]} url  [访问地址]
     * @param {[type]} data [传输数据]
     */
    commonOperatFunc(url,data): void{
      console.log(data);
      this.http
        .post(url, JSON.stringify(data), this.options)
        .toPromise()
        .then(res => res.json().data)
        .catch(this.handleError);
    }

    /**
     * [commonOperatFunc 公共方法]
     * @param {[type]} url  [访问地址]
     * @param {[type]} data [传输数据]
     */
    commonOperatGetFunc(url): Promise<any>{
      //console.log(data);
      return this.http
        .get(url,this.options)
        .toPromise()
        .then(res => res.json().data)
        .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
      console.error('An error occurred', error); // for demo purposes only
      if ( String(error).indexOf('token')) {
        localStorage.removeItem('user');
        location.reload();
        }
      return Promise.reject(error.message || error);
    }
    private getHeaders(){
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return headers;
    }
}