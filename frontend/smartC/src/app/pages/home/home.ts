import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Params } from "@angular/router";
import { Location }     from '@angular/common';

@Component({
	selector: 'page-home',
	templateUrl: './home.html'
})

export class homePage implements OnInit {
	
	constructor(
		private route: ActivatedRoute) {}


	ngOnInit(): void{
       
	}
}