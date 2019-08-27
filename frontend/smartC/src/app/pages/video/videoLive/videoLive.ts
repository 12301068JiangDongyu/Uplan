import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Params } from "@angular/router";
import { Location }     from '@angular/common';

import { VideoService } from '../../../service/video.service';

import { Device } from '../../../entity/device.entity';
import { Camera } from '../../../entity/camera.entity';

declare var jwplayer: any;
declare var cyberplayer: any;

@Component({
	selector: 'page-video-live',
	templateUrl: './videoLive.html'
})

export class videoLivePage implements OnInit {
	
	address : string = null;
	id : any;
	cameras : Camera[];
	device : Device;
	cameraCode : number = 0;

	constructor(
		private route: ActivatedRoute,
		private videoService: VideoService) {}

	/**
	 * [getPullAddress 获取视频播放地址]
	 * @param {[type]} cameraId [摄像头ID]
	 */
	getPullAddress(cameraId,code): void{
		console.log(cameraId,code);
		//this.getCode(cameraId);
		this.cameraCode = code+1;
		this.videoService.getPullAddress(this.id,this.cameraCode).then(
			data=>{
    		this.address = data.address;
    		this.getLineVedio(this.address);
    		
    	})
	}

	/**
	 * [InitDeviceAddress 进入页面初始化设备信息以及获取地址]
	 */
	InitDeviceAddress(): void{
		this.videoService.getDeviceInfoById(this.id).then(device => {
			this.device = device;
			console.log(this.device);
			//this.getLineVedio('rtmp://play.bcelive.com/live/lss-gm4k64ts8y7kevfi');
			this.cameras = device.cameraList;
			if(this.address == null&&this.cameras.length>0){
				this.getPullAddress(this.cameras[0].cameraId,0)
			}
		})
	}

	/**
    * [getLineVedio 根据地址进行视频直播]
    * @param {[string]} url [视频地址]
    */
    getLineVedio(url): void{
    	console.log(url);
    	var player = jwplayer('playerVideoBox').setup({
		    /*flashplayer: 'js/plugins/mediaplayer-5.7/player.swf',*/
		    file : url,
		    width : '100%',
		    height : '100%',
		    fallback : 'false',
		    autostart : 'true',
		    primary : 'flash',
		    rtmp : {
		        bufferlength : 0.1
		    }
		});
	}

	/**
	 * [directorCamera description]
	 * @param {[type]} direction [description]
	 */
	directorCamera(direction): void{
		this.videoService.directorCamera(this.id,this.cameraCode,direction);
	}

	ngOnInit(): void{
        this.route.queryParams.subscribe((data: any) => {
    		this.id = data.id;
	    })
	    this.InitDeviceAddress();
	}
}