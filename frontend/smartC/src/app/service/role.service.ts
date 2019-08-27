import { User } from '../entity/user.entity';
import { Role } from '../entity/role.entity';
import { Constant } from '../common/constant';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()

export class RoleService {
	private headers = new Headers({'Content-Type': 'application/json'});
	private roleUrl: string;
	private options: any;
	constructor(
		private constant: Constant,
		private http: Http) {
		this.roleUrl = constant.URL + 'roles/';
		this.options = new RequestOptions({withCredentials: true,headers: this.getHeaders()});}
	/**
	 * [getRoles 获取权限列表]
	 * @return {Promise<Role[]>} [description]
	 */
	getRoles(): Promise<Role[]>{
		return this.http
			.get(this.roleUrl,this.options)
			.toPromise()
			.then(response => response.json().data.roleList as Role[])
			.catch(this.handleError)
	}
	/**
	 * [getRole 根据ID获取单个权限]
	 * @param  {[type]}       id [权限ID]
	 * @return {Promise<any>}    [description]
	 */
	getRole(id): Promise<any>{
		return this.http
			.get(this.roleUrl+id,this.options)
			.toPromise()
			.then(response => response.json().data)
			.catch(this.handleError)
	}
	/**
	 * [getPermissions 获取权限树]
	 * @return {Promise<any>} [description]
	 */
	getPermissions(): Promise<any>{
		return this.http
			.get(this.roleUrl+'permissions/',this.options)
			.toPromise()
			.then(response => response.json().data.permissionList)
			.catch(this.handleError)
	}
	/**
	 * [addRole 添加角色]
	 * @param  {[type]}       rname           [角色名称]
	 * @param  {[type]}       permissionNodes [角色树]
	 * @return {Promise<any>}                 [description]
	 */
	addRole(rname,permissionNodes): Promise<any>{
		let data = {
        	"r_name" : rname,
        	"p_ids" : permissionNodes
    	};
		return this.http
			.post(this.roleUrl,data,this.options)
			.toPromise()
			.then(response => response.json().data)
			.catch(this.handleError)
	}
	/**
	 * [editRole 修改角色]
	 * @param  {[type]}       id              [角色ID]
	 * @param  {[type]}       rname           [角色名称]
	 * @param  {[type]}       permissionNodes [角色树]
	 * @param  {[type]}       judge           [角色名是否修改 true修改 false未修改]
	 * @return {Promise<any>}                 [description]
	 */
	editRole(id,rname,permissionNodes,judge): Promise<any>{
		let data = {
	        "rid": id,
	        "r_name" : rname,
	        "p_ids" : permissionNodes
	    };
		return this.http
			.put(this.roleUrl+id+'?judge='+judge,data,this.options)
			.toPromise()
			.then(response => response.json().data)
			.catch(this.handleError)
	}

	/**
	 * [deleteRole 删除角色]
	 * @param  {[type]}       id [角色ID]
	 * @return {Promise<any>}    [description]
	 */
	deleteRole(id): Promise<any>{
		return this.http
			.delete(this.roleUrl+id,this.options)
			.toPromise()
			.then(response => response.json().data)
			.catch(this.handleError)
	}

	private handleError(error: any): Promise<any> {
	  	console.error('An error occurred', error); // for demo purposes only
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