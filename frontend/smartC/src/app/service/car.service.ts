
import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class CarApplyInfoService {

    private carApplyUrl: string;
    private options: any;
    private headers = new Headers({ 'Content-Type': 'application/json' });

    // data : any = {c_id:1,c_brand:"doge",c_plateNum:"d123323",destination:"济南1",start_time:"2019-01-01",end_time:"2019-01-01",status:"通过"};




    constructor(private constant: Constant, private http: Http) {
        this.carApplyUrl = constant.URL;
        this.options = new RequestOptions({
            // withCredentials: true,
            headers: this.getHeaders()
        });
    }

    deleteApplyInfoById(id): Promise<any> {
        return this.http
            .delete(this.carApplyUrl + id, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    getApplyInfoByUserId(id): Promise<any> {
        return this.http
            .get(this.carApplyUrl, this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }


    //请求汽车数据:
    getCarListByDate(date): Promise<any> {
        return this.http
            .get(this.constant.URL + "car/getCar", this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    //通过公车id请求某个公车的详细数据:
    getCarInfoByCarId(car_id): Promise<any> {
        return this.http
            .get(this.carApplyUrl + "carAppply/carList", this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    // 增加申请
    addCarApply(): Promise<any> {
        return this.http
            .post(this.carApplyUrl + "carAppply/carList", this.options)
            .toPromise()
            .then(response => response.json().res)
            .catch(this.handleError)
    }

    // 通过状态查询公车申请列表
    findCarApplyListByStatus(status: string): Promise<any> {
        return this.http
            .post(this.constant.URL + "carAppply/carList", this.options)
            .toPromise()
            .then(response => response.json().res)
            .catch(this.handleError)
    }

    checkApplyByApplyId(applyId:number, status: string): Promise<any> {
        return this.http
            .post(this.constant.URL + "carAppply/carList", this.options)
            .toPromise()
            .then(response => response.json().res)
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

    private getHeaders() {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return headers;
    }


}