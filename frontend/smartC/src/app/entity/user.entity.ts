import { Role } from './role.entity'
/**
 * 实体类
 */
export class User{
	id : number;
	username : string;
	password : string;
	r_id : number;
	role : Role;
	real_name: string;
	license: string;
}