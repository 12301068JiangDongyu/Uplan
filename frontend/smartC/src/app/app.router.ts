import { Routes, RouterModule } from '@angular/router';

import { LoginPage } from "./pages/login/login";
import { homePage } from "./pages/home/home";

import { SidebarComponent } from './pages/sidebar/sidebar';
import { NavComponent } from './pages/navigation/navigation';
import { deviceMonitorPage } from './pages/device/deviceMonitor/deviceMonitor';
import { deviceInfoPage } from './pages/device/deviceManage/deviceInfo/deviceInfo';
import { assignDevicePage } from './pages/device/deviceManage/assignDevice/assignDevice';
import { assignDeviceClassroomPage } from './pages/device/deviceManage/assignDevice/assignDeviceClassroom/assignDeviceClassroom';
import { videoManagePage } from './pages/video/videoManage/videoManage';
import { videoLivePage } from './pages/video/videoLive/videoLive';
import { userManagePage } from './pages/settings/userManage/userManage';
import { roleManagePage } from './pages/settings/roleManage/roleManage';
import { messageManagePage } from './pages/settings/messageManage/messageManage';
import { buildClassManagePage } from './pages/settings/buildClassManage/buildClassManage';
import { addBuildClassPage } from './pages/settings/buildClassManage/addBuildClass/addBuildClass';

import {ActivateGuard} from './guard/auth.guard';
import {DeactivateGuard} from './guard/deactivate.guard';

import { CarTypeManageComponent } from './pages/info/car-type-manage/car-type-manage.component';
import { CarManageComponent } from './pages/info/car-manage/car-manage.component';
import { CarRecordComponent } from './pages/info/car-record/car-record.component';
import { CarApplyComponent } from './pages/mission/carApply/carApply';
import { CarCheckComponent } from './pages/mission/car-check/car-check.component';
import { StatisticsComponent } from './pages/statistics/statistics/statistics.component'
import { UserApplyListComponent } from './pages/mission/user-apply-list/user-apply-list.component';

export const MAINMENU_ROUTES = [
    //full : makes sure the path is absolute path
    { path: '', redirectTo: '/home', pathMatch: 'full', canActivate: [ActivateGuard],data: {route:1}},
    { path: 'login', component: LoginPage, canActivate: [DeactivateGuard]},
    { path: 'home', component: homePage, canActivate: [ActivateGuard],data: {route:1} },
    { path: 'deviceMonitor', component: deviceMonitorPage, canActivate: [ActivateGuard],data: {route:112} },
    { path: 'videoManage', component: videoManagePage, canActivate: [ActivateGuard],data: {route:21} },
    { path: 'videoLive', component: videoLivePage, canActivate: [ActivateGuard],data: {route:21} },
    { path: 'userManage', component: userManagePage, canActivate: [ActivateGuard],data: {route:3121} },
    { path: 'roleManage', component: roleManagePage, canActivate: [ActivateGuard],data: {route:3122} },
    { path: 'buildClassManage', component: buildClassManagePage, canActivate: [ActivateGuard],data: {route:311} },
    { path: 'addBuildClass', component: addBuildClassPage, canActivate: [ActivateGuard],data: {route:311} },
    { path: 'deviceInfo', component: deviceInfoPage, canActivate: [ActivateGuard],data: {route:1111} },
    { path: 'assignDevice', component: assignDevicePage, canActivate: [ActivateGuard],data: {route:1112} },
    { path: 'assignDeviceClassroom', component: assignDeviceClassroomPage, canActivate: [ActivateGuard],data: {route:1112} },
    { path: 'messageManage', component: messageManagePage, canActivate: [ActivateGuard],data: {route:313} },
    
    { path: 'carTypeManage', component: CarTypeManageComponent},
    { path: 'carManage', component: CarManageComponent},
    { path: 'carRecord', component: CarRecordComponent},
    { path: 'carApply', component: CarApplyComponent},
    { path: 'carApplyList', component: UserApplyListComponent},
    { path: 'carCheck', component: CarCheckComponent},
    { path: 'statistics', component: StatisticsComponent},
];
//export const CONST_ROUTING = RouterModule.forRoot(MAINMENU_ROUTES);