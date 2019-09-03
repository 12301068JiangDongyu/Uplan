export class CarRecord {
  id: number;
  // 车牌号
  license: string;
  // 记录时间
  occurrenceTime: string;
  // 责任人
  creator: string;
  // 花费
  cost: string;
  // 备注
  remark: string;
  // 类型（1维修，2加油，3违章）
  type: number;
}
