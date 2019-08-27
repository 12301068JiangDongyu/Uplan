/**
 * 设备service类
 */
import { Device } from '../entity/device.entity';
import { Building } from '../entity/building.entity';
import { Classroom } from '../entity/classroom.entity';
import { BuildClass } from '../entity/buildclass.entity';

import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http ,RequestOptions} from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class VideoService {
	private videoUrl : string;
	private headers = new Headers({'Content-Type': 'application/json;charset=UTF-8'});
  private options: any;

	constructor(
    private constant : Constant,
    private http: Http) { 
    this.options = new RequestOptions({withCredentials: true,headers: this.getHeaders()});
    this.videoUrl = constant.URL+'videos/'
  }
	

    /**
     * [getVideoDevices 获取设备列表]
     * @return {Promise<Device[]>} [设备列表]
     */
    getVideoDevices(): Promise<Device[]> {
      return this.http.get(this.videoUrl,this.options)
             .toPromise()
             .then(response => response.json().data.deviceStatusList as Device[])
             .catch(this.handleError);
    }
   
    /**
     * [getDeviceInfoById 根据ID获取设备]
     * @param  {[type]}          id [设备ID]
     * @return {Promise<Device>}    [description]
     */
    getDeviceInfoById(id): Promise<Device>{
      let url = this.videoUrl+'classroomInfo/'+id;
      let data = {
        'did' : id
      };
      return this.http
          .get(url,this.options)
          .toPromise()
          .then(response => response.json().data.deviceInfo as Device)
          .catch(this.handleError);
    }
	  /**
     * [getPushingBuildClass 获取正在推流的教学楼和教室]
     * @return {Promise<any>} [教学楼列表 以及 第一个教学楼写的教室列表]
     */
  	getPushingBuildClass(): Promise<any> {
    	return this.http.get(this.videoUrl+'pushBuilding',this.options)
             .toPromise()
             .then(response => response.json().data)
             .catch(this.handleError);
  	}

    /**
     * [changeClassByBuildName 根据选择的教学楼重新获取教室号]
     * @param  {[string]}               name [教学楼名]
     * @return {Promise<Classroom[]>}      [教室列表]
     */
    changeClassByBuildName(name): Promise<Classroom[]> {
      let url = this.videoUrl + 'classroomByBuilding?name='+name;
      let data = {
        "name": name
      };
      return this.http
              .get(url,this.options)
              .toPromise()
              .then(response => response.json().data.classroomList as Classroom[])
              .catch(this.handleError);
    }
  	
     /**
     * [operateStream 操作视频推拉流]
     * @param {[type]} id      [教室设备列表id]
     * @param {[type]} operate [操作类型 start_push|broadcast stop_push|pull|broadcast]
     */
    operateStream(id,operate): Promise<any>{
      //let url = '/ajax_edit_stream_status';
      let url = this.videoUrl+id+'?operation='+operate;
      let data = {
        "did":id,
        "operation":operate
      }
      return this.commonOperateGetFunc(url);
    }

    /**
     * [getMultiPullStreamTree 获取可以拉流的教学楼教室列表]
     * @return {Promise<BuildClass[]>} [教学楼教室列表]
     */
    getMultiPullStreamTree(): Promise<BuildClass[]>{
      let url = this.videoUrl + 'pullStreamTree';
      return this.http
              .get(url,this.options)
              .toPromise()
              .then(response => response.json().data as BuildClass[])
              .catch(this.handleError);
    }

    /**
     * [startMultiplePullOperate 操作多个教室拉流]
     * @param  {[type]}       inputBuilding  [选择的教学楼]
     * @param  {[type]}       inputClassroom [选择的教室]
     * @param  {[type]}       pullList       [需要拉流的多个教室列表]
     * @return {Promise<any>}                []
     */
    startMultiplePullOperate(inputBuilding,inputClassroom,pullList): Promise<any>{
      //let url = '/ajax_multiple_pull_stream_status';
      let url = this.videoUrl+'multiplePullStreamStatus';
      let data = {
        "buildingNum":inputBuilding,
        "classroomNum":inputClassroom,
        "pullList":pullList
      };
      return this.commonOperatFunc(url,data);
    }

    /**
     * [startPullOperate 操作单个教室拉流]
     * @param  {[type]}       inputBuilding  [选择的教学楼]
     * @param  {[type]}       inputClassroom [选择的教室]
     * @param  {[type]}       pullId         [设备ID]
     * @return {Promise<any>}                []
     */
    startPullOperate(inputBuilding,inputClassroom,pullId): Promise<any>{
      //let url = '/ajax_pull_stream_status';

      let url = this.videoUrl+'pull/'+pullId+'?buildingNum='+inputBuilding+'&classroomNum='+inputClassroom;
      let data = {
        "buildingNum":inputBuilding,
        "classroomNum":inputClassroom,
        "did":pullId
      };
      return this.commonOperateGetFunc(url);
    }

    /**
     * [getPullAddress 获取视频播放地址]
     * @param  {[type]}       id     [设备ID]
     * @param  {[type]}       code   [摄像头code 区分]
     * @return {Promise<any>}        []
     */
    getPullAddress(id,code): Promise<any>{
      let url = this.videoUrl+'play/'+id+'?code='+code;
      //let url = '/ajax_get_pull_address';
      let data = {
        'did': id,
        'code':code
      };
      return this.commonOperateGetFunc(url);
    }

    /**
     * [directorCamera 摄像头导播]
     * @param  {[type]}       id        [设备ID]
     * @param  {[type]}       code      [区分摄像头（1、2、3...） ]
     * @param  {[type]}       direction [方向 up down left right]
     * @return {Promise<any>}           []
     */
    directorCamera(id,code,direction): Promise<any>{
      let url = this.videoUrl+'camera/'+id+'?code='+code+'&direction='+direction;
      //let url = '/ajax_director_camera';
      // let data = {
      //   'did': id,
      //   'code': code,
      //   'direction': direction
      // }
      return this.commonOperateGetFunc(url)
    }
    /**
     * [commonOperatFunc 公共Post方法]
     * @param {[type]} url  [访问地址]
     * @param {[type]} data [传输数据]
     */
    commonOperatFunc(url,data): Promise<any>{
      console.log(data);
      return this.http
        .post(url,JSON.stringify(data),this.options)
        .toPromise()
        .then(response => response.json().data)
        .catch(this.handleError);
    }

    /**
     * [commonOperaGetFunc 公共Get方法]
     * @param {[type]} url  [访问地址]
     */
    commonOperateGetFunc(url): Promise<any>{
      console.log(url);
      return this.http
        .get(url,this.options)
        .toPromise()
        .then(response => response.json().data)
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