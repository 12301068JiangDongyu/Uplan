/**
 * 设备car-manage类
 */
import { Car } from '../entity/car-manage.entity'


import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class CarService {
  private carInfoUrl : string;
  private options: any;
	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private constant : Constant,private http: Http) { 
     this.carInfoUrl = constant.URL+'car/';
     this.options = new RequestOptions({withCredentials: true,headers: this.getHeaders()});
  }
	

    /**
     * [getCarNotes 获取车辆信息列表]
     * @return {Promise<any>} [description]
     */
    getCarNotes(): Promise<any>{
      return this.http
        .get(this.carInfoUrl,this.options)
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
        .post(this.carInfoUrl+'carAdd',entity,this.options)
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
    editType(id,entity,): Promise<any>{
      return this.http
        .put(this.carInfoUrl+'carUpdate/'+id,entity,this.options)
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
        .delete(this.carInfoUrl+id,this.options)
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
        //localStorage.removeItem('user');
        //location.reload();
        }
      return Promise.reject(error.message || error);
    }
    private getHeaders(){
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return headers;
    }
}