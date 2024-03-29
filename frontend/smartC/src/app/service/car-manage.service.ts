/**
 * car-manage类
 */
import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class CarService {
  private carInfoUrl: string;
  private options: any;

  constructor(private constant: Constant, private http: Http) {
    this.carInfoUrl = constant.URL + 'car/';
    this.options = new RequestOptions({ withCredentials: true, headers: this.getHeaders() });
  }

  /**
   * [getCarNotes 获取车辆信息列表]
   * @return {Promise<any>} [description]
   */
  getCarNotes(): Promise<any> {
    return this.http
      .get(this.carInfoUrl, this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError);
  }

  addCar(entity): Promise<any> {
    return this.http
      .post(this.carInfoUrl + 'carAdd', entity, this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError);
  }

  editCar(id, entity): Promise<any> {
    return this.http
      .put(this.carInfoUrl + 'carUpdate/' + id, entity, this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError);
  }

  deleteCar(id, type): Promise<any> {
    return this.http
      .delete(this.carInfoUrl + id, this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    if (String(error).indexOf('token')) {
      //localStorage.removeItem('user');
      //location.reload();
    }
    return Promise.reject(error.message || error);
  }
  private getHeaders() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return headers;
  }
}
