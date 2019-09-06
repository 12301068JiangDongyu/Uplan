import { Data } from "@angular/router";

export class CarApplyInfo {
    id : number;
    car_id: number;
    brand:string;
    user_id:number;
    userName:string;
    distination:string;
    startTime:String;
    start_time: Date;
    end_time: Date;
    reason:string;
    travel_distance:number;
    creatTime:String;
    creat_time:Date;
    updateTime:String;
    update_time: Data;
    oil_used: number;
    remark:string;
    status_name:String;
}