/**
 * 用户service类
 */
import { User } from '../entity/user.entity';
import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Md5 } from "ts-md5/dist/md5";

@Injectable()

export class UserService {
	private headers = new Headers({'Content-Type': 'application/json'});
	private userUrl: string;
	private options: any;
	constructor(private http: Http,private constant: Constant) {
		this.userUrl = constant.URL+'users/';
		this.options = new RequestOptions({
			withCredentials: true,
			headers: this.getHeaders()
		});
	 }
	 /**
	  * [login 登录]
	  * @param  {string}       username [用户名]
	  * @param  {string}       password [密码]
	  * @return {Promise<any>}          [description]
	  */
	login(username: string,password: string): Promise<any>{
		
		let url = this.constant.URL+'login?username='+username+'&password='+Md5.hashStr(password).toString();
		
	    return this.http      
	      .get(url,this.options)
	      .toPromise()
	      .then(response => response.json().data)
	      .catch(this.handleError);
	}

	/**
	 * [logout 注销登录]
	 * @return {Promise<any>} [description]
	 */
	logout(): Promise<any>{
		let url = this.constant.URL + 'logout'
		return this.http      
	      .post(url,{},this.options)
	      .toPromise()
	      .then(response => response.json().data)
	      .catch(this.handleError);
	}

	/**
	 * [getUsers 获取用户列表]
	 * @return {Promise<any>} []
	 */
	getUsers(): Promise<any>{
		return this.http
			.get(this.userUrl,this.options)
			.toPromise()
			.then(response => response.json().data)
     		.catch(this.handleError);

	}

	getUser(id): Promise<any>{
		return this.http.
			get(this.userUrl+id,this.options)
			.toPromise()
			.then(response=>response.json().data)
			.catch(this.handleError);
	}

	/**
	 * [addUser 添加用户]
	 * @param  {[type]}       username [用户名]
	 * @param  {[type]}       password [密码]
	 * @param  {[type]}       role     [角色ID]
	 * @return {Promise<any>}          []
	 */
	addUser(username,password,role): Promise<any>{
		let data = {
			"username":username,
	        "password":Md5.hashStr(password).toString(),
	        "r_id":role
		}
		return this.http
			.post(this.userUrl,data,this.options)
			.toPromise()
			.then(response => response.json().data)
			.catch(this.handleError)
	}

	editUser(user:User,judge): Promise<any>{
		var data = user;
		return this.http
			.put(this.userUrl+user.id+'?judge='+judge,data,this.options)
			.toPromise()
			.then(response => response.json().data)
			.catch(this.handleError)
	}

	deleteUser(id): Promise<any>{
		return this.http
			.delete(this.userUrl+id,this.options)
			.toPromise()
			.then(response => response.json().data)
			.catch(this.handleError)
	}

	private handleError(error: any): Promise<any> {
	  	console.error("Error1:"+String(error)); // for demo purposes only
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