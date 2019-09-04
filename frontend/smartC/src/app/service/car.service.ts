
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
        this.carApplyUrl = constant.URL + 'carAppplyList/infoList';
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
            .get(this.constant.URL + "carAppply/carList", this.options)
            .toPromise()
            .then(response => response.json().data)
            .catch(this.handleError)
    }

    addCarApply(): Promise<any> {
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