import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { User } from '../../entity/user.entity';
import { StorageService } from '../../service/storage.service';


@Component({
  selector: 'nav-component',
  templateUrl: './navigation.html',
  providers: [StorageService]
})
export class NavComponent{
  user: User;

  constructor(
    private storageService: StorageService,
    private userService: UserService
    ) {
    this.user = this.storageService.read<User>('user');
  }

  logout(): void{
    this.userService.logout().then(data=>{
      if(data.judge==0){
        this.storageService.remove("user");
        location.reload();
      }
    })
    
  }
}
