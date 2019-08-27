import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

//entity
import { Message } from '../../../entity/message.entity';
//service
import { MessageService } from '../../../service/message.service';



@Component({
  selector: 'page-message-manage',
  templateUrl: './messageManage.html'
})
export class messageManagePage implements OnInit{
	data : Message[];

  	constructor(	  	
  		private messageSevice: MessageService,
		private router: Router) {
  	}

  	/**
  	 * [getMessages 初始获取消息列表]
  	 */
  	getMessages(): void{
  		this.messageSevice.getMessages().then(data=>{
  			//console.log(data);
  			this.data = data;
  			
  		})
  	}

  	ngOnInit(): void{
  		this.getMessages();
  	}

}