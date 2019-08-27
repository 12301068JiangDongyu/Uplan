import { Camera } from './camera.entity';

export class  Device{
	id : number;
    buildingNum : string;
    classroomNum : string;
    singlechipTypeId : number;
    singlechipStatus : number;
    raspberryTypeId : number;
    raspberryStatus : number;
    raspberryCode : string;
    raspberryStreamStatus : number;
    cameraStatus : number;
    computerTypeId : number;
    computerStatus : number;
    projectorTypeId : number;
    projectorStatus : number;
    cameraList : Camera[];
}