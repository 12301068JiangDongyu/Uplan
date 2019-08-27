import { Camera } from './camera.entity';
import { Computer } from './computer.entity';
import { Raspberry } from './raspberry.entity';
import { Projector } from './projector.entity';
import { Singlechip } from './singlechip.entity';

export class  DeviceClass{
	id : number;
    buildingNum : string;
    classroomNum : string;
    singlechipType : Singlechip;
    raspberryType : Raspberry;
    computerType : Computer;
    projectorType : Projector;
    cameraList : Camera[];
}