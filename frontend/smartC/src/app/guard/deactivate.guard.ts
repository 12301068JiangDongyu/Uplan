import { CanActivate, ActivatedRoute } from '@angular/router';
import { Router,ActivatedRouteSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';

import { StorageService } from '../service/storage.service';
import { User } from '../entity/user.entity';
@Injectable()
export class DeactivateGuard implements CanActivate {
  user: User;
  private can: boolean = false;
	constructor(private router:Router, 
              private route: ActivatedRoute,
              private storageService: StorageService){
    this.user = this.storageService.read<User>('user');
  }

  	canActivate(route: ActivatedRouteSnapshot) {
	    if(this.user != null) {
	        this.router.navigate(['/home']);
	        return false;
	    }
	    return true;
    }  

}