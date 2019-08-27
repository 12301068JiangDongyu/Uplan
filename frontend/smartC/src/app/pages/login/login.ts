import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FormBuilder, FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
//entity
import { User } from '../../entity/user.entity';
//service
import { UserService } from '../../service/user.service';
import { StorageService } from '../../service/storage.service';

//providers
import { usernameValidator } from '../../providers/validator';

@Component({
  selector: 'page-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginPage{
	judgeMessage : boolean = true;
	user : User;
  	constructor(
	  	private formBuilder: FormBuilder,
	  	private userSevice: UserService,
		private storageService: StorageService,
		private router: Router) {
  		
  	}

  	loginForm = new FormGroup({
	    'LoginName': new FormControl('', [Validators.required, Validators.minLength(2), usernameValidator]),// 第一个参数是默认值
	    'LoginPwd': new FormControl('', [Validators.required, Validators.minLength(6)])
	 });
	login(user,event): void{
		this.userSevice.login(user.LoginName,user.LoginPwd).then(data => {
			console.log(data);
			if(data.judge == "0"){
				this.storageService.write('user',data.user);
				location.href='';      		
			}else{
				this.judgeMessage = false;
			}
		});
	}

	// ngOnInit(): void{
 //       	this.user = this.storageService.read<User>('user');
 //  		if(this.user!=null){
 //  			location.href=''; 
 //  		}
	// }

}