import { Component,OnInit,ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';

import { StorageService } from './service/storage.service';
import { MessageService } from './service/message.service';

import { User } from './entity/user.entity';
import { Message } from './entity/message.entity';

import { Observable } from 'rxjs/Rx';
import { ToastsManager,Toast } from 'ng2-toastr/ng2-toastr';

import * as $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
	judgeLogin : boolean = false;
  dismissTime : number = 5000;
  user : User;
  messages : Message[];

  constructor(
          private storageService: StorageService,
  			  private router: Router,
          private messageService: MessageService,
          private toastr: ToastsManager,
          private vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
    this.user = this.storageService.read<User>('user');
    console.log(1);
    if(this.user != null){
      this.judgeLogin = true;
      //this.router.navigate(['/home']);
      //this.router.navigate(['/ParentRouter']);
      console.log(2);
    }
    else{
      this.judgeLogin = false;
      console.log(3);
    	this.router.navigate(['/login']);
    }
  }


  ngOnInit(): void{}

}
