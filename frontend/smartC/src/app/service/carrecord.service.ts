import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class CarRecordService {
  private dailyOperationUrl: string;
  private options: any;
  private headers = new Headers({ 'Content-Type': 'application/json' });

  constructor(private constant: Constant, private http: Http) {
    this.dailyOperationUrl = constant.URL + 'dailyOperation/';
    this.options = new RequestOptions({ withCredentials: true, headers: this.getHeaders() });
  }

  getCarRecords(): Promise<any> {
    return this.http
      .get(this.dailyOperationUrl, this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError);
  }

  addCarRecord(entity): Promise<any> {
    return this.http
      .post(this.dailyOperationUrl + 'dailyOperationAdd', entity, this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError);
  }

  editCarRecord(id, entity): Promise<any> {
    return this.http
      .put(this.dailyOperationUrl + 'dailyOperationUpdate' + '/' + id, entity, this.options)
      .toPromise()
      .then(response => response.json().data)
      .catch(this.handleError);
  }

  deleteCarRecord(id): Promise<any> {
    return this.http
      .delete(this.dailyOperationUrl + id, this.options)
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
