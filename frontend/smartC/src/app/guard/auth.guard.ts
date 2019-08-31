import { CanActivate, ActivatedRoute } from '@angular/router';
import { Router,ActivatedRouteSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';

import { StorageService } from '../service/storage.service';
import { User } from '../entity/user.entity';
@Injectable()
export class ActivateGuard implements CanActivate {
  user: User;
  private can: boolean = false;
//constructor(private snapshot: ActivatedRouteSnapshot){
  constructor(private router:Router,
              private route: ActivatedRoute,
              private storageService: StorageService){
    this.user = this.storageService.read<User>('user');
  }

  canActivate(route: ActivatedRouteSnapshot) {
    var pid = route.data.route;
    // debugger;
    if(this.user == null) {
        this.router.navigate(['/login']);
        return false;
    }else{
        var permissions = this.user.role.p_ids;
        if(this.user!=null&&pid==0){
            this.router.navigate(['/home']);
            return false;
        }
        if(pid == 1){
            this.can = true;
        }else{
            this.can = permissions.find(x=>x===pid)==undefined?false:true;
        }
        if (!this.can) {
          alert('无权限！');
          this.router.navigate(['/home']);
          return false;
        }
        return true;
    }
  }

}