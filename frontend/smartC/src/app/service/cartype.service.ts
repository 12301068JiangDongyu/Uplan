
import { Cartype } from '../entity/cartype.entity'

import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class CarTypeService {
  private carInfoUrl : string;
  private options: any;
	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private constant : Constant,private http: Http) { 
     this.carInfoUrl = constant.URL+'carType/';
     this.options = new RequestOptions({withCredentials: true,headers: this.getHeaders()});
  }

    /**
     * [getDeviceTypes 获取全部设备类型信息]
     * @return {Promise<any>} [description]
     */

    getCarTypes(): Promise<any>{
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
        .post(this.carInfoUrl+'carTypeAdd',entity,this.options)
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
        .put(this.carInfoUrl+'carTypeUpdate'+'/'+id,entity,this.options)
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
        .delete(this.carInfoUrl+id+'?device='+type,this.options)
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