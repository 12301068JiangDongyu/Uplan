import { Data } from "@angular/router";

export class CarApplyInfo {
    id : number;
    car_id: number;
    brand:string;
    user_id:number;
    user_name:string;
    distination:string;
    start_time: Date;
    end_time: Date;
    reason:string;
    travel_distance:number;
    update_time: Data;
    oil_used: number;
    remark:string;
    status:number;
}