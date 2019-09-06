export class CarRecord {
  id: number;
  car_id: number;
  // 车牌号
  license_num: string;
  // 记录时间
  occurrence_time: any;
  // 责任人
  creator: number;
  real_name: string;
  // 花费
  cost: string;
  // 备注
  remark: string;
  // 类型（1维修，2加油，3违章）
  type: number;
}
