import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class StatisticsService {
  private statisticsUrl: string;
  private options: any;

  constructor(private constant: Constant, private http: Http) {
    this.statisticsUrl = constant.URL + 'statistic/';
    this.options = new RequestOptions({ withCredentials: true, headers: this.getHeaders() });
  }

  /**
   * [getDeviceTypes 获取全部设备类型信息]
   * @return {Promise<any>} [description]
   */

  getStatistics(): Promise<any> {
    return this.http
      .get(this.statisticsUrl, this.options)
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
