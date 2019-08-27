/**
 * 设备service类
 */
import { Message } from '../entity/message.entity'

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import { Constant } from '../common/constant';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class MessageService {
	private messageUrl : string;
	private headers = new Headers({'Content-Type': 'application/json'});
	private options: any;

	constructor(private constant : Constant,private http: Http) { 
		this.messageUrl = constant.URL+'messages/';
		this.options = new RequestOptions({withCredentials:true});
	}

	getMessages(): Promise<Message[]>{
		
	    return this.http      
	      .get(this.messageUrl+'allList/',this.options)
	      .toPromise()
	      .then(response => response.json().data.list as Message[])
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
}