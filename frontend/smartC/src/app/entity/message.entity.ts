/**
 * 实体类
 */
export class Message{
	//success、fail、timeout
    judge: string;
    //记录当前时间
    nowTime: string;
    //记录消息内容
    message: string;
    //设备ownId
    ownId: string;
    //教学楼教室号
    buildClass: string;
    //device、video 区分设备管理和视频管理
    tab: string;
    
    userId: number;
}