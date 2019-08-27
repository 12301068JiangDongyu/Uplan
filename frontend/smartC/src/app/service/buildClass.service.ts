import { Building } from '../entity/building.entity';
import { Classroom } from '../entity/classroom.entity';
import { BuildClass } from '../entity/buildclass.entity';

import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class BuildClassService {
	private headers = new Headers({'Content-Type': 'application/json'});
  private buildClassUrl: string;
  private options: any;
  constructor(private http: Http,private constant: Constant) {
    this.buildClassUrl = constant.URL+'buildingClassrooms/';
    this.options = new RequestOptions({withCredentials:true,headers: this.getHeaders()});
   }

  /**
   * [getBuildClasses 获取教学楼教室列表]
   * @return {Promise<BuildClass[]>} [description]
   */
  getBuildClasses(): Promise<BuildClass[]>{
    return this.http
      .get(this.buildClassUrl,this.options)
      .toPromise()
      .then(response=>response.json().data.buildingClassroomList as BuildClass[])
      .catch(this.handleError)
  }

  /**
   * [getBuildings 获取所有教学楼列表]
   * @return {Promise<Building[]>} [description]
   */
  getBuildings(): Promise<Building[]>{
    return this.http
      .get(this.buildClassUrl+'buildings',this.options)
      .toPromise()
      .then(response=>response.json().data.buildingList)
      .catch(this.handleError)
  }

  /**
   * [addBuildCLass 添加教学楼教室]
   * @param  {[type]}       buildingNum   [教学楼]
   * @param  {[type]}       classroomList [教室列表]
   * @return {Promise<any>}               [description]
   */
  addBuildCLass(buildingNum,classroomList): Promise<any>{
    let data = {
          "buildingNum" : buildingNum,
          "classroomList" : classroomList
      };
    return this.http
      .post(this.buildClassUrl,data,this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError)
  }

  /**
   * [editBuildClass 修改教学楼教室]
   * @param  {BuildClass}   buildClass [教学楼教室实体]
   * @return {Promise<any>}            [description]
   */
  editBuildClass(buildClass:BuildClass): Promise<any>{
    var data = buildClass;
    return this.http
      .put(this.buildClassUrl+buildClass.id,data,this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError)
  }

  /**
   * [editBuilding 修改教学楼名称]
   * @param  {Building}     building [教学楼实体]
   * @return {Promise<any>}          [description]
   */
  editBuilding(building:Building): Promise<any>{
    var data = building;
    return this.http
      .put(this.buildClassUrl+'buildings/'+building.id,data,this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError)
  }

  /**
   * [deletebuildClass 删除教学楼教室]
   * @param  {[type]}       id [教室ID]
   * @return {Promise<any>}    [description]
   */
  deletebuildClass(id): Promise<any>{
    return this.http
      .delete(this.buildClassUrl+id,this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError)
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