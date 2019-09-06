
import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { CarInfo } from 'app/entity/carinfo.entity';
import { CarApplyInfo } from 'app/entity/carApplyInfo.entity';

@Injectable()

export class CarApplyInfoService {
    private carApplyUrl: string;
    private options: any;
    private headers = new Headers({ 'Content-Type': 'application/json','Accept': 'application/json' });

    private getHeaders() {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return headers;
    }


    constructor(private constant: Constant, private http: Http) {
        this.carApplyUrl = constant.URL;
        this.options = new RequestOptions({withCredentials: true,headers: this.getHeaders()});
    }

    deleteApplyInfoById(id): Promise<any> {
        return this.http
            .delete(this.carApplyUrl + id, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    getApplyInfoByUserId(id): Promise<any> {
        console.log(id);
        return this.http
            .get(this.carApplyUrl+"officialCarApply/carListByUser?user_id="+id, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }


    // 获取用车申请所有数据

    getCarUseList():Promise<any> {
        return this.http
            .get(this.carApplyUrl+"officialCarApply/getAllCarListInfo", this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

     /**
  	 * [ 获取申请列表]
  	 */
    getCarApplyInfos():Promise<any> {
        return this.http
            .get(this.carApplyUrl+"applications/", this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }


    //请求某一天的汽车数据:
    getCarListByDate(date): Promise<any> {
        return this.http
            .get(this.constant.URL + "officialCarApply/getAvailCarList?dateInfo="+date, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)

        // $.ajax({
        //     type: "GET",
        //     url: this.constant.URL + "officialCarApply/getAvailCarList?dateInfo="+date,
        //     async: false,
        //     dataType: "json",
        //     success: function(res){
        //         console.log(res.data);
        //         return res.data;
        //     }
                       
        //   });
    }

    //通过公车id请求某个公车的详细数据:
    getCarInfoByCarId(car_id): Promise<any> {
        return this.http
            .get(this.carApplyUrl + "car/getCar/"+car_id, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    // 增加申请
    addCarApply(applyInfo : CarApplyInfo): Promise<any> {
        // let data = {
        // 	"car_id" : car_id,
        // 	"user_id" : user_id,
        //     "reason" : reason,
        //     "start_time" : startTime
        // };
        return this.http
            .post(this.carApplyUrl + "officialCarApply/carApply", applyInfo, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }


    // 通过状态查询公车申请列表
    findCarApplyListByStatus(status: string): Promise<any> {
        let s : number;
        switch(status) {
            case "未审核":s=0; break;
            case "通过":s=1; break;
            case "不通过":s=2; break;
            case "撤销":s=3; break;
            default:s=0;
       } 
        return this.http
            .post(this.constant.URL + "searchOfficialCarApplyByStatus?status="+s, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    checkApplyByApplyId(applyId:number, status: string): Promise<any> {
        let s : number;
        switch(status) {
            case "未审核":s=0; break;
            case "通过":s=1; break;
            case "不通过":s=2; break;
            case "撤销":s=3; break;
            default:s=0;
       }
        let data = {
        	"id" : applyId,
        	"status" : s
        };
        console.log(data);
        return this.http
            .post(this.constant.URL + "officialCarApply/carRepeal",data, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        if (String(error).indexOf('token')) {
            // localStorage.removeItem('user');
            // location.reload();
        }
        return Promise.reject(error.message || error);
    }

   


}